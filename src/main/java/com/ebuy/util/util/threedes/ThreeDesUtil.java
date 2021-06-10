package com.ebuy.util.util.threedes;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.UrlBase64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @version 1.0
 * @user lianxinzhong
 * @date 2021/1/25
 */
public class ThreeDesUtil {

    private static final Logger logger = Logger.getLogger(ThreeDesUtil.class);

    private static final String IV = "1234567-";
    private static final String CHARSET_UTF8 = "UTF-8";

    /**
     * DES CBC加密
     *
     * @param src   数据源
     * @param key   秘钥, 长度必须是8的倍数
     * @param iv    初始化向量, 长度必须是8的倍数
     * @return      返回加密后的数据
     * @throws      Exception
     */
    public String encryptDESCBC(String src, String key, String iv) throws Exception {

        // 生成key, 同时确定是DES还是DESede, 两者的key长度要求不同
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(CHARSET_UTF8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        // 加密向量
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(CHARSET_UTF8));

        /**
         * 通过Cipher执行加密得到的是一个byte数组
         * Cipher.getInstance("DES/CBC/PKCS5Padding")  参数意思为 "算法/模式/填充模式"
         * cipher.init(Cipher.ENCRYPT_MODE, secretKey) 初始化选择加密模式
         */
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] byteArr = cipher.doFinal(src.getBytes(CHARSET_UTF8));

        // 通过base64, 将加密数组转换成字符串
        return new String(Base64.encodeBase64(byteArr));
    }

    /**
     * DES CBC加密
     *
     * @param src   明文byte数组
     * @param key   秘钥, 原始字符串长度必须是8的倍数
     * @param iv    初始化向量, 原始字符串长度必须是8的倍数
     * @return      Base64编码的密文
     * @throws      Exception
     */
    public byte[] encryptDESCBC(byte[] src, byte[] key, byte[] iv) throws Exception{
        // 生成key, 同时确定是DES还是DESede, 两者的key长度要求不同
        DESKeySpec desKeySpec = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        // 加密向量
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        /**
         * 通过Cipher执行加密得到的是一个byte数组
         * Cipher.getInstance("DES/CBC/PKCS5Padding")  参数意思为 "算法/模式/填充模式"
         * cipher.init(Cipher.ENCRYPT_MODE, secretKey) 初始化选择加密模式
         */
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] byteArr = cipher.doFinal(src);

        return Base64.encodeBase64(byteArr);
    }

    /**
     * DES CBC解密
     *
     * @param src   数据源
     * @param key   秘钥, 长度必须是8的倍数
     * @param iv    初始化向量, 长度必须是8的倍数
     * @return      返回解密后的原始数据
     * @throws      Exception
     */
    public String decryptDESCBC(String src, String key, String iv) throws Exception {

        // 通过base64, 将字符串转为byte数组
        byte[] byteSrc = Base64.decodeBase64(src);

        // 解密的key
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(CHARSET_UTF8));

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        // 解密向量
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(CHARSET_UTF8));

        /**
         * 通过Cipher执行解密得到的是一个byte数组
         * Cipher.getInstance("DES/CBC/PKCS5Padding")  参数意思为 "算法/模式/填充模式"
         * cipher.init(Cipher.ENCRYPT_MODE, secretKey) 初始化选择解密模式
         */
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] byteArr = cipher.doFinal(byteSrc);

        return new String(byteArr);
    }

    /**
     * DES CBC解密
     *
     * @param src   数据源, Base64编码的密文
     * @param key   秘钥, 长度必须是8的倍数
     * @param iv    初始化向量, 长度必须是8的倍数
     * @return      返回解密后的原始数据, 明文
     * @throws      Exception
     */
    public byte[] decryptDESCBC(byte[] src, byte[] key, byte[] iv) throws Exception {

        // base64解码密文
        byte[] base64DecodeByte = Base64.decodeBase64(src);

        // 解密的key
        DESKeySpec desKeySpec = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

        // 解密向量
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        /**
         * 通过Cipher执行解密得到的是一个byte数组
         * Cipher.getInstance("DES/CBC/PKCS5Padding")  参数意思为 "算法/模式/填充模式"
         * cipher.init(Cipher.ENCRYPT_MODE, secretKey) 初始化选择解密模式
         */
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        return cipher.doFinal();
    }

    /**
     * 3DES ECB加密
     *
     * @param src   数据源,
     * @param key   秘钥, 长度必须大于等于 3*8 = 14 位
     * @return      返回加密后的Base64字符串数据
     * @throws      Exception
     */
    public String encryptThreeDesECB(String src, String key) throws Exception {

        // 根据key得到加密用的SecretKey
        DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(key.getBytes(CHARSET_UTF8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey secretKey = keyFactory.generateSecret(deSedeKeySpec);

        /**
         * 通过Cipher执行加密得到的是一个byte数组
         * Cipher.getInstance("DESede/ECB/PKCS5Padding")  参数意思为 "算法/模式/填充模式"
         * cipher.init(Cipher.ENCRYPT_MODE, secretKey) 初始化选择加密模式
         */
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] byteArr = cipher.doFinal(src.getBytes(CHARSET_UTF8));

        // 通过base64, 将加密数组转换成字符串
//        return new String(Base64.encodeBase64(byteArr));
//        return Base64.encodeBase64URLSafeString(byteArr);

        System.out.println(new String(Base64.encodeBase64(byteArr), CHARSET_UTF8));
        System.out.println(Base64.encodeBase64URLSafeString(byteArr));
        System.out.println(java.util.Base64.getUrlEncoder().encodeToString(byteArr));

        return new String(UrlBase64.encode(byteArr));
//        return java.util.Base64.getUrlEncoder().encodeToString(byteArr);
    }

    /**
     * 3DES ECB加密
     *
     * @param src   数据源, 明文byte数组
     * @param key   秘钥, 原始字符串长度必须大于等于 3*8 = 14 位
     * @return      返回加密后的Base64 byte数组
     * @throws      Exception
     */
    public byte[] encryptThreeDesECB(byte[] src, byte[] key) throws Exception {

        // 根据key得到加密用的SecretKey
        DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey secretKey = keyFactory.generateSecret(deSedeKeySpec);

        /**
         * 通过Cipher执行加密得到的是一个byte数组
         * Cipher.getInstance("DESede/ECB/PKCS5Padding")  参数意思为 "算法/模式/填充模式"
         * cipher.init(Cipher.ENCRYPT_MODE, secretKey) 初始化选择加密模式
         */
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] byteArr = cipher.doFinal(src);

        return Base64.encodeBase64(byteArr);
    }

    /**
     * 3DES ECB解密
     *
     * @param src   数据源
     * @param key   秘钥, 长度必须大于等于 3*8 = 14 位
     * @return      解密后的字符串数据
     * @throws      Exception
     */
    public String decryptThreeDesECB(String src, String key) throws Exception {

        // 通过base64, 将字符串转为byte数组
        byte[] byteSrc = Base64.decodeBase64(src);

        DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(key.getBytes(CHARSET_UTF8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey secretKey = keyFactory.generateSecret(deSedeKeySpec);

        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] byteArr = cipher.doFinal(byteSrc);

        return new String(byteArr);
    }

    /**
     * 3DES ECB解密
     *
     * @param src   数据源, Base64编码后的byte数组
     * @param key   秘钥, 长度必须大于等于 3*8 = 14 位
     * @return      明文byte数组
     * @throws      Exception
     */
    public byte[] decryptThreeDesECB(byte[] src, byte[] key) throws Exception {

        // 通过base64, 将字符串转为byte数组
        byte[] byteSrc = Base64.decodeBase64(src);

        DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey secretKey = keyFactory.generateSecret(deSedeKeySpec);

        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return cipher.doFinal(byteSrc);
    }

    public static void main(String[] args) throws Exception {

        String originStr = "abc|123";

        String key = "patspdbcccfashio20150729";

        ThreeDesUtil desUtil = new ThreeDesUtil();

        // DES CBC加密
//        String encryptStr = desUtil.encryptDESCBC(originStr, key, IV);
//        System.out.println("encrptStr: " + encryptStr);
//
//        // DES CBC解密
//        String decryptStr =  desUtil.decryptDESCBC(encryptStr, key, IV);
//        System.out.println("decryptStr: " + decryptStr);
//
//        // DES CBC加密 byte[]
//        byte[] encryptByte = desUtil.encryptDESCBC(originStr.getBytes(CHARSET_UTF8), key.getBytes(CHARSET_UTF8), IV.getBytes(CHARSET_UTF8));
//        System.out.println("encrptByteStr: " + new String(encryptByte, CHARSET_UTF8));
//
//        // DES CBC解密 byte[]
//        byte[] decryptByte =  desUtil.decryptDESCBC(encryptByte, key.getBytes(CHARSET_UTF8), IV.getBytes(CHARSET_UTF8));
//        System.out.println("decryptByteStr: " + new String(decryptByte, CHARSET_UTF8));


        String threeDesKey = "C205BC5839533270jUN1d77Y";

        String threeDesEncryptStr = desUtil.encryptThreeDesECB(originStr, threeDesKey);
        logger.info("threeDesEncryptStr: " + threeDesEncryptStr);

        threeDesEncryptStr = "sktFA8BSLm9j0xcQq6ri8pBDkg2lT0Nlw4EzYGJMbor8afFahZ1t2MCH5Z2P-1VBeQBncGBn9MJ1jdKWe5bnQZWNWfx2diq0GvXqtO2CiBlowoLPQKX9ktI4UB3UkeRofP-ZW4-xf3hRUOVkUxTjcs0aUlbstF9ZuazRmcIPJjlDIHVE49pPUIt51fyWWDa8NSl9rt318htJYI93uMXZVA..";

        String threeDesDecryptStr = desUtil.decryptThreeDesECB(threeDesEncryptStr, threeDesKey);
        logger.info("threeDesDecryptStr: " + threeDesDecryptStr);

//        byte[] threeDesEncryptByte = desUtil.encryptThreeDesECB(originStr.getBytes(CHARSET_UTF8), threeDesKey.getBytes(CHARSET_UTF8));
//        logger.info("threeDesEncryptByteStr: " + new String(threeDesEncryptByte, CHARSET_UTF8));
//
//        byte[] threeDesDecryptByte = desUtil.decryptThreeDesECB(threeDesEncryptByte, threeDesKey.getBytes(CHARSET_UTF8));
//        logger.info("threeDesDecryptByteStr: " + new String(threeDesDecryptByte, CHARSET_UTF8));

    }
}
