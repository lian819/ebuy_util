package com.ebuy.util.util.rsa;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

import org.apache.axiom.util.base64.Base64Utils;

/**
 * 新赛点RSA Util
 *
 * @version 1.0
 * @user lianxinzhong
 * @date 2021/3/31
 */
public class NewPointRSAUtil {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    public static final String SIGNATURE_OAEPSHA1 = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding";
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;
    //客户端加密
    public static final String TRANSFORMATION_RSA_ECB_PKCS1 = "RSA/ECB/PKCS1Padding";


    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData
     *            已加密数据
     * @param privateKey
     *            私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey (String encryptedData, String privateKey) throws Exception {

        String soure_temp = SafeBase64_decode(encryptedData);
        System.out.println("soure_temp[" + soure_temp + "]");
//        byte[]  encryptedDataByte = Base64Utils.decode(SafeBase64_decode(encryptedData));
        byte[]  encryptedDataByte = Base64Utils.decode(soure_temp);
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedDataByte.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedDataByte, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher .doFinal(encryptedDataByte, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData,"utf-8");
    }



    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(privateK);
        signature.update(data);
        return org.apache.axiom.util.base64.Base64Utils.encode(signature.sign());
    }
    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param source
     *            源数据
     * @param publicKey
     *            公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String source, String publicKey) throws Exception {
        byte[] data = source.getBytes("utf-8");
        byte[] keyBytes = org.apache.axiom.util.base64.Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        String result = SafeBase64_encode(org.apache.axiom.util.base64.Base64Utils.encode(encryptedData));
        return result;
    }


    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param source
     *            源数据
     * @param privateKey
     *            私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String source, String privateKey) throws Exception {
        byte[] keyBytes =org.apache.axiom.util.base64.Base64Utils.decode(privateKey);
        byte[] data = source.getBytes("utf-8");
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        String result = SafeBase64_encode(org.apache.axiom.util.base64.Base64Utils.encode(encryptedData));
        return result;
    }


    public static boolean verify(String data, String publicKey, String functionMark) throws Exception {
        String decodedData = decryptByPublicKey(data, publicKey);
        String target = new String(decodedData);
        return target.equals(functionMark);
    }

    public static String SafeBase64_encode(String base64str) {
        return base64str.replace("+", "-").replace("/", "_");
    }

    public static String SafeBase64_decode(String base64str) {
        return base64str.replace("-", "+").replace("_", "/");
    }


    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData
     *            已加密数据
     * @param publicKey
     *            公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey (String encryptedData, String publicKey) throws Exception {
        byte[]  encryptedDataByte = org.apache.axiom.util.base64.Base64Utils.decode(SafeBase64_decode(encryptedData));
        byte[] keyBytes = org.apache.axiom.util.base64.Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedDataByte.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher
                        .doFinal(encryptedDataByte, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher
                        .doFinal(encryptedDataByte, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return  new String(decryptedData,"utf-8");
    }


    public static void main(String[] args) throws Exception {
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEQGvoUsdFWUKzifAncxOZYxUBYQ/vQftxmmtj+/VK8YP3w7WSjyGAUIQmzJcgBThRvw+rVQKZ8qI/mihU00JK6yGbJxGCopEvP6XTLFagkxYVhocZUbmDz12shMl222LMq0Zri4p6R4pYNJLTAoNf4FZ8GwlGit/qKVdUWa8IhwIDAQAB";
        String privatekey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIRAa+hSx0VZQrOJ8CdzE5ljFQFhD+9B+3Gaa2P79Urxg/fDtZKPIYBQhCbMlyAFOFG/D6tVApnyoj+aKFTTQkrrIZsnEYKikS8/pdMsVqCTFhWGhxlRuYPPXayEyXbbYsyrRmuLinpHilg0ktMCg1/gVnwbCUaK3+opV1RZrwiHAgMBAAECgYAoJ08b+JYcDh/cVxMdRyk6NwUXqBqocAYh1dqpPsSY/n8s6LTmTKLFTKzzAscXp74Tw8jEIklp6GsumGWKa6ICoSSyzgi/3sub2P4xYHK0R8UKd+dhRwJ2sEPT3cl+aqwiH4lQ4Ut7H+QsWkExHcclb3x/kdLCMiRxGNTEAA72MQJBAMU9XJJtxq/DH64jzpGWxNd7Ato2tk/BMIXWLM7eJ9VRVWRsWNCj5C2OQ3EVIa/0CFRPHG+zW1P6+U4h6LmB4F0CQQCrprIufgoF2kz0kpWKTl/OHYBhACrU1z0mJGf9y3Nn0Gg6bk4v5V8fN5gkTDg4BxRax7N4xQASJFOfQbWRD04zAkBxfqiY/Z/5nU7KdoIz1ky4LWlrxVXMuL7CFIa+FqlXHzEC0zGcBOKUsCrwlBdJVyO/17HmOI+R2HXCVtLChBBlAkBCzyri9aOpC4KQXs1de4vGia0CFDKXjP777YecPKe6bF9k/rHn6nrPh6/r0M7A3+CIRNoR03tnlEQH5GdrAm4JAkAu6q5K/6kq4v0hkC7Hjf0zrS/GXd+iqe4+BgBbwSBuYAIb8emx+DO5cBMy2lIBT7hIUrwbDAQkNJp45VY94ZhY";
        String result = NewPointRSAUtil.encryptByPublicKey("123", publicKey);
        System.out.println(result);
        result = NewPointRSAUtil.decryptByPrivateKey(result, privatekey);
        System.out.println(result);
    }
}
