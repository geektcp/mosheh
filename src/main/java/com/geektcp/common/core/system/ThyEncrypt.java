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
import java.util.Objects;

/**
 * @author geektcp on 2023/2/15 0:27.
 */
class ThyEncrypt {

    private ThyEncrypt() {
    }

    private static final String PARAM = "thy$p*r^#a>s!m#.x@";
    private static final String DES_KEY = "thy$k*e^#>y!#.x@";
    private static final String ALGORITHM = "AES/GCM/NoPadding";

    private static Cipher cipher;
    private static RSA rsa;

    /**
     * PrivateKey
     * openssl genrsa -out rsa_private_key.pem 1024
     * openssl pkcs8 -topk8 -inform PEM -in rsa_private_key.pem -outform PEM -out rsa_private_key_01.pem -nocrypt
     * cat rsa_private_key_01.pem
     * <p>
     * PublicKey
     * openssl rsa -in rsa_private_key.pem -pubout -out rsa_public_key.pem
     * cat rsa_public_key.pem
     */
    private static RSA init() {
        String keysDir = Sys.getResourceRootPath() + "keys";
        String privateKeyPath = keysDir + "/PrivateKey";
        String publicKeyPath = keysDir + "/PublicKey";
        String privateKey = Sys.readPrivateKeyFile(privateKeyPath);
        String publicKey = Sys.readPublicKeyFile(publicKeyPath);
        rsa = new RSA(privateKey, publicKey);
        return rsa;
    }

    public static RSA getInstance() {
        if (Objects.isNull(rsa)) {
            return init();
        }
        return rsa;
    }

    private static IvParameterSpec iv = new IvParameterSpec(PARAM.getBytes(StandardCharsets.UTF_8));

    public static String encrypt(String str) {
        RSA rsa = ThyEncrypt.getInstance();
        byte[] encrypt1 = rsa.encrypt(StrUtil.bytes(str, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        return HexUtil.encodeHexStr(encrypt1);
    }

    public static String decrypt(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            RSA rsa = ThyEncrypt.getInstance();
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

    ///////////////////////////////////////////////
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
