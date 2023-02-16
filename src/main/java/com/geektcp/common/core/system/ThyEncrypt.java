package com.geektcp.common.core.system;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author geektcp on 2023/2/15 0:27.
 */
class ThyEncrypt {

    private ThyEncrypt() {
    }

    /**
     * PrivateKey
     * openssl genrsa -out rsa_private_key.pem 1024
     * openssl pkcs8 -topk8 -inform PEM -in rsa_private_key.pem -outform PEM -out rsa_private_key_01.pem -nocrypt
     * cat rsa_private_key_01.pem
     */
    private static final String PRIVATE_KEY =
            "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMFvz2d3fWA0keI5" +
            "l2EC5GlfZ6pTSoQRIT2dlv9RchI7wwxCKcAShH6LzByLra+hJGIRlL5700gxgOju" +
            "hDpLyuIUleHv1uMQ5Qc5HnauNrDOzFtbel9v7F/Gy6qcejCJfAQYdnpjzWwkRKeL" +
            "TcKDN3R2Hhm3HSMBFvtuuamBlYDDAgMBAAECgYEAlsrjwwO6IBI7G0DMClsn1iEC" +
            "fhu5/iQgZpkACf7izuX5kgfN5iZJVSEDt8kHV99lrnGII8oBlcjJdkvhllRZTfEV" +
            "JGLJW/gnGl0fH812K+MdSPHsFh2z9h51go/WOeDeN5zC+jheYvAovh3v5C6uWmEb" +
            "S6n6mBkbZ2mt9418d3ECQQD8Ly0A8ul/EQR9fr8bUnMyn47vnZTiUPL8vKLh0XlM" +
            "+YLKp0UvG1NT8gTAArycWpKhy9R9YDAt6li8C8J0NJ67AkEAxF0UHB/3VlV1b3nz" +
            "Wbe+ES7MLL2O7hDcRh1Q6TsnmDAk5hJRg/bqIoSArT91FwoijCGpQtOVDE+LKqHp" +
            "DzE5mQJBAJHuKri12HBzRInqYmRJHehZdLksEw+zkCi4b/kE4pCsggcLLHnJ8jpN" +
            "fnouGz7PGrMN5HR4yOiJB7gphekA2ikCQDhl32vAhqKGQwd0iD9hdbarsTq8avvX" +
            "XdzJeL60HcgpM7/czQrQ9Sha1DZuPmwSnh+PX3TxHiL+CaOCA1U4tykCQQDnwAjl" +
            "Ih+D++1ZFP7eNkdsvxZEzUk89IiLz2UXzt1IusYT+gLiC2+H89xKKGdcw1bZQ+op" +
            "3DgQhwv64DElRA2N";

    /**
     * PublicKey
     * openssl rsa -in rsa_private_key.pem -pubout -out rsa_public_key.pem
     * cat rsa_public_key.pem
     */
    private static final String PUBLIC_KEY =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBb89nd31gNJHiOZdhAuRpX2eq" +
            "U0qEESE9nZb/UXISO8MMQinAEoR+i8wci62voSRiEZS+e9NIMYDo7oQ6S8riFJXh" +
            "79bjEOUHOR52rjawzsxbW3pfb+xfxsuqnHowiXwEGHZ6Y81sJESni03Cgzd0dh4Z" +
            "tx0jARb7brmpgZWAwwIDAQAB";

    private static final String PARAM = "thy$p*r^#a>s!m#.x@";
    private static final String DES_KEY = "thy$k*e^#>y!#.x@";
    private static final String ALGORITHM = "AES/GCM/NoPadding";

    private static Cipher cipher;
    private static RSA rsa;
    private static ThyEncrypt instance;
    private static IvParameterSpec iv = new IvParameterSpec(PARAM.getBytes(StandardCharsets.UTF_8));



    public static ThyEncrypt getInstance() {
        if (Objects.isNull(instance)) {
            return new ThyEncrypt();
        }
        return instance;
    }

    public static String md5(String source) {
        String result = "";
        if (source == null || ("".equals(source))) {
            return result;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(source.getBytes(StandardCharsets.UTF_8));
            StringBuilder sign = new StringBuilder();
            for (byte b : bytes) {
                int bt = b & 0xff;
                if (bt < 16) {
                    sign.append(0);
                }
                sign.append(Integer.toHexString(bt));
            }
            result = sign.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            Sys.p(e.getMessage());
        }
        return result;
    }

    public static String encrypt(String str) {
        RSA rsa = ThyEncrypt.getRSA();
        byte[] encrypt1 = rsa.encrypt(StrUtil.bytes(str, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        return HexUtil.encodeHexStr(encrypt1);
    }

    public static String decrypt(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            RSA rsa = ThyEncrypt.getRSA();
            byte[] bytesPassword = rsa.decrypt(str, KeyType.PrivateKey);
            return StringUtils.toEncodedString(bytesPassword, CharsetUtil.CHARSET_UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    public static String desEncrypt(String source) {
        try {
            DESKeySpec desKeySpec = getDesKeySpec(source);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            return byte2hex(
                    cipher.doFinal(source.getBytes(StandardCharsets.UTF_8))).toUpperCase();
        } catch (GeneralSecurityException e) {
            Sys.p(e.getMessage());
            return null;
        }
    }

    public static String desDecrypt(String source) {
        try {
            byte[] src = hex2byte(source.getBytes());
            DESKeySpec desKeySpec = getDesKeySpec(source);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] retByte = cipher.doFinal(src);
            return new String(retByte);
        } catch (GeneralSecurityException e) {
            Sys.p(e.getMessage());
            return null;
        }
    }

    public static Key buildKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //////////////////// private function ///////////////////////////
    private static RSA init() {
        rsa = new RSA(PRIVATE_KEY, PUBLIC_KEY);
        return rsa;
    }

    public static RSA getRSA() {
        if (Objects.isNull(rsa)) {
            return init();
        }
        return rsa;
    }

    private static DESKeySpec getDesKeySpec(String source) throws GeneralSecurityException {
        if (source == null || source.length() == 0) {
            return null;
        }
        cipher = Cipher.getInstance(ALGORITHM);
        return new DESKeySpec(DES_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private static String byte2hex(byte[] inStr) {
        String tmp;
        StringBuilder out = new StringBuilder(inStr.length * 2);
        for (byte b : inStr) {
            tmp = Integer.toHexString(b & 0xFF);
            if (tmp.length() == 1) {
                out.append("0").append(tmp);
            } else {
                out.append(tmp);
            }
        }
        return out.toString();
    }

    private static byte[] hex2byte(byte[] b) {
        int size = 2;
        if ((b.length % size) != 0) {
            throw new IllegalArgumentException("size error");
        }
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += size) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
}
