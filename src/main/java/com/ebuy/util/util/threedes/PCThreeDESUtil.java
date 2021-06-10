package com.ebuy.util.util.threedes;

/**
 * 盛大提供的ThreeDesUtil
 *
 * @version 1.0
 * @user lianxinzhong
 * @date 2021/1/27
 */
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.UrlBase64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

public class PCThreeDESUtil {

    private static final Logger logger = Logger.getLogger(ThreeDesUtil.class);

    private static final String CRYPT_ALGORITHM = "DESede";
    private static String result = "sktFA8BSLm9j0xcQq6ri8pBDkg2lT0Nlw4EzYGJMbor8afFahZ1t2MCH5Z2P-1VBeQBncGBn9MJ1jdKWe5bnQZWNWfx2diq0GvXqtO2CiBlowoLPQKX9ktI4UB3UkeRofP-ZW4-xf3hRUOVkUxTjcs0aUlbstF9ZuazRmcIPJjlDIHVE49pPUIt51fyWWDa8NSl9rt318htJYI93uMXZVA..";

    //解密
    public static String decrypt(String value,String key) throws Exception {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), CRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CRYPT_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decodedByte = UrlBase64.decode(value.getBytes("UTF-8"));
            byte[] decryptedByte = cipher.doFinal(decodedByte);
            return new String(decryptedByte,"UTF-8");
        } catch(Exception e) {
            logger.error("PCThreeDESUtil.decrypt : decrypt was failed!", e);
            throw e;
        }
    }
    //加密
    public static String encrypt(String value,String key) throws Exception{
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), CRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CRYPT_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            byte[] encryptedByte = cipher.doFinal(value.getBytes("UTF-8"));
            byte[] encodedByte = UrlBase64.encode(encryptedByte);
            return new String(encodedByte,"UTF-8");
        } catch(Exception e) {
            logger.error("PCThreeDESUtil.encrypt : encrypt was failed!", e);
            throw e;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
//        String key="C405BC5839533270jUN1d77Y";
        String key="C205BC5839533270jUN1d77Y";
        String szSrc="abc|123";
        System.out.println("加密前的字符串" + szSrc);

        String encoded = encrypt(szSrc,key);
        System.out.println("加密后的字符串" + encoded);

        String xmString = new String(result.getBytes("UTF-8"));
        String strUTF8 = URLEncoder.encode(xmString, "UTF-8");
        System.out.println("转编码后的字符串" + strUTF8);
        String srcBytes = decrypt(result,key);
        System.out.println("解密后的字符串" + srcBytes);
    }
}
