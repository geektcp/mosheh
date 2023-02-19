/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
     * PrivateKey path:
     * generate with linux command: ssh-keygen
     * or command openssl:
     * openssl genrsa -out rsa_private_key.pem 1024
     * openssl pkcs8 -topk8 -inform PEM -in rsa_private_key.pem -outform PEM -out rsa_private_key_01.pem -nocrypt
     * cat rsa_private_key_01.pem
     * <p>
     * example:  id_rsa
     */
    private static String idRsa = null;

    /**
     * PublicKey path:
     * generate with linux command: ssh-keygen
     * or command openssl:
     * openssl rsa -in rsa_private_key.pem -pubout -out rsa_public_key.pem
     * cat rsa_public_key.pem
     * <p>
     * example:  id_rsa.pub
     */
    private static String idRsaPub = null;

    private static final String PARAM = "thy$p*r^#a>s!m#.x@";
    private static final String DES_KEY = "thy$k*e^#>y!#.x@";
    private static final String ALGORITHM = "AES/GCM/NoPadding";

    private static Cipher cipher;
    private static RSA rsa;
    private static IvParameterSpec iv = new IvParameterSpec(PARAM.getBytes(StandardCharsets.UTF_8));


    public static void initKey(String privateKeyFilename, String publicKeyFilename) {
        idRsa = privateKeyFilename;
        idRsaPub = publicKeyFilename;
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
            return byteTohex(
                    cipher.doFinal(source.getBytes(StandardCharsets.UTF_8))).toUpperCase();
        } catch (GeneralSecurityException e) {
            Sys.p(e.getMessage());
            return null;
        }
    }

    public static String desDecrypt(String source) {
        try {
            byte[] src = hexTobyte(source.getBytes());
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

    /**
     * @see #initKey(String, String)
     * @return RSA
     *
     * example:
     *      initKey("xxx/xxx/id_rsa", "xxx/xxx/id_rsa.pub");
     *      RSA rsa = getRSA();
     */
    private static RSA init() {
        String keysDir = Sys.getResourceRootPath();
        String privateKeyPath = Objects.isNull(idRsa) ? keysDir + "/id_rsa" : idRsa;
        String publicKeyPath = Objects.isNull(idRsaPub) ? keysDir + "/id_rsa.pub" : idRsaPub;
        String privateKey = Sys.readPrivateKeyFile(privateKeyPath);
        String publicKey = Sys.readPublicKeyFile(publicKeyPath);
        rsa = new RSA(privateKey, publicKey);
        return rsa;
    }

    private static RSA getRSA() {
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

    private static String byteTohex(byte[] inStr) {
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

    private static byte[] hexTobyte(byte[] b) {
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
