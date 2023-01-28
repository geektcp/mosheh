package com.geektcp.common.core.util;

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
import java.security.Key;
import java.util.Objects;

/**
 * @author tanghaiyang on 2020-04-16 16:21
 */
public class EncryptUtils {

    private static RSA rsa;
    private EncryptUtils(){
    }

    private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALeUkONNs4v8wuaVqu7pIc1VCB3S56BRo45bwHJWGZtQRMHeM8onxUX/VSeyrADwYFV1nZPH1TCZSo3ilOP+uhKXfNajqjQOKqqyTakCsiSIv1TLKYooGA7IksEv1kQBQ8bvQvNvQ7F/aw2QNNcHPzVt35Vyc7nDG/UNL2+Yyj6JAgMBAAECgYAECUBHvRQ43E9pLCp4SvgfKHLPnAzTY6Qby8TfBqlUtrayR/k9xTLTQ4Y645TgRuipTFcQ8hRr93zSAoSpQBcVasTOiBHEW7bHcez7q02FmI9oR76+V861/09zoVZzOZbI/O6YpzB9PyJkOx+mX3tyOpmhEljL2iD5Gxcm/09psQJBAOFXDszC7iL4LhKEZt3vfwh7dGJqfKVi/qa9IAYxz9Exz8ecUNFOtyPbSDszFPlD+Ta53vw6Zm4/7B+/PdtoXxkCQQDQjvPEo6NgXkbA+pEtlnJoCOsnP6Aq/tSnRgnY/P0qDMFCmOrVXVkfX6VwCKpg/D3UE8KA0Zj9ybckx+zmPXjxAkEAjOobKDMSJi4a6ZuAlHMLdqt1KYI79lTEuFJ2r0kBE2nZ7JK0+18FKdgcAGE+UW6PbwinCAYhPfqdV3EJZqaLKQJAMLsGKE8X8H92xsaFP4KkrrxOvbf3I7SxWXha+rF6MeYTDg2O6VXLajI+BKRxswGdLL2FN/ZBaiNEwLpaFx4L8QJAUoJqx2njplKnzbF4x1irKMWYWwEkD4FubwKl6JD6YWGSjYg7LLmG1MQXtzUqNm7Xb/rBpNBl5/n2AEvS9yjb3Q==";

    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3lJDjTbOL/MLmlaru6SHNVQgd0uegUaOOW8ByVhmbUETB3jPKJ8VF/1UnsqwA8GBVdZ2Tx9UwmUqN4pTj/roSl3zWo6o0Diqqsk2pArIkiL9UyymKKBgOyJLBL9ZEAUPG70Lzb0Oxf2sNkDTXBz81bd+VcnO5wxv1DS9vmMo+iQIDAQAB";

    private static String strParam = "random!Psw#&.$";

    private static Cipher cipher;

    public static RSA getRsa(){
         rsa = new RSA(PRIVATE_KEY, PUBLIC_KEY);
         return rsa;
    }

    public static RSA getInstance(){
        if(Objects.isNull(rsa)){
            return getRsa();
        }
        return rsa;
    }

    private static IvParameterSpec iv = new IvParameterSpec(strParam.getBytes(StandardCharsets.UTF_8));

    public static String encrypt(String str) {
        RSA rsa = EncryptUtils.getRsa();
        byte[] encrypt1 = rsa.encrypt(StrUtil.bytes(str, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        return HexUtil.encodeHexStr(encrypt1);
    }

    public static String decrypt(String str){
        if(StringUtils.isEmpty(str)){
            return null;
        }
        try {
            RSA rsa = EncryptUtils.getRsa();
            byte[] bytesPassword = rsa.decrypt(str, KeyType.PrivateKey);
            return StringUtils.toEncodedString(bytesPassword, CharsetUtil.CHARSET_UTF_8);
        }catch (Exception e){
            return null;
        }
    }



    /**
     * @param source
     */
    public static String desEncrypt(String source) throws Exception {
        DESKeySpec desKeySpec = getDesKeySpec(source);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return byte2hex(
                cipher.doFinal(source.getBytes(StandardCharsets.UTF_8))).toUpperCase();
    }

    /**
     * @param source
     */
    public static String desDecrypt(String source) throws Exception {
        byte[] src = hex2byte(source.getBytes());
        DESKeySpec desKeySpec = getDesKeySpec(source);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] retByte = cipher.doFinal(src);
        return new String(retByte);
    }


    public static Key buildKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    ///////////////////////////////////////////////
    private static DESKeySpec getDesKeySpec(String source) throws Exception {
        if (source == null || source.length() == 0){
            return null;
        }
        cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        String strKey = "Passw0rd";
        return new DESKeySpec(strKey.getBytes(StandardCharsets.UTF_8));
    }

    private static String byte2hex(byte[] inStr) {
        String stmp;
        StringBuilder out = new StringBuilder(inStr.length * 2);
        for (byte b : inStr) {
            stmp = Integer.toHexString(b & 0xFF);
            if (stmp.length() == 1) {
                out.append("0").append(stmp);
            } else {
                out.append(stmp);
            }
        }
        return out.toString();
    }

    private static byte[] hex2byte(byte[] b) {
        int size = 2;
        if ((b.length % size) != 0){
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
