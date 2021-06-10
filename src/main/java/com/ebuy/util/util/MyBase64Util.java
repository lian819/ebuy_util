package com.ebuy.util.util;

import org.apache.log4j.Logger;


/**
 * @version 1.0
 * @user lianxinzhong
 * @date 2021/5/18
 */
public class MyBase64Util {

    private static final Logger logger = Logger.getLogger(MyBase64Util.class);

    public static final String BASE64_TYPE_CC = "BASE64_TYPE_CC";               // common codec
    public static final String BASE64_TYPE_CC_URL = "BASE64_TYPE_CC_URL";       // common codec
    public static final String BASE64_TYPE_BC= "BASE64_TYPE_BC";                // bouncy castle
    public static final String BASE64_TYPE_BC_URL= "BASE64_TYPE_BC_URL";        // bouncy castle
    public static final String BASE64_TYPE_JV = "BASE64_TYPE_JV";               // java util
    public static final String BASE64_TYPE_JV_URL = "BASE64_TYPE_JV_URL";       // java util

    public static final String CHARSET_UTF8 = "UTF-8";

    public static void main(String[] args) {
        String str = "落霞与孤鹜齐飞, 秋水共长天一色";

        try {

            String encodeStr = encode(str);
            logger.info(encodeStr);

            String decodeStr =decode(encodeStr);
            logger.info(decodeStr);
        } catch (Exception e) {

        }
    }

    public static String encode(String src) throws Exception {

        return encode(src, BASE64_TYPE_CC);
    }

    public static String encode(String str, String base64_type) throws Exception {

        byte[] byteArr = str.getBytes(CHARSET_UTF8);

        // 通过base64, 将加密数组转换成字符串
        if (BASE64_TYPE_CC.equals(base64_type)) {

            return new String (org.apache.commons.codec.binary.Base64.encodeBase64(byteArr), CHARSET_UTF8);
        } else if (BASE64_TYPE_CC_URL.equals(base64_type)) {

            return new String (org.apache.commons.codec.binary.Base64.encodeBase64URLSafe(byteArr), CHARSET_UTF8);
        } else if (BASE64_TYPE_BC.equals(base64_type)) {

            return new String (org.bouncycastle.util.encoders.Base64.encode(byteArr), CHARSET_UTF8);
        } else if (BASE64_TYPE_BC_URL.equals(base64_type)) {

            return new String (org.bouncycastle.util.encoders.UrlBase64.encode(byteArr), CHARSET_UTF8);
        } else if (BASE64_TYPE_JV.equals(base64_type)) {

            return new String (java.util.Base64.getEncoder().encode(byteArr), CHARSET_UTF8);
        } else if (BASE64_TYPE_JV_URL.equals(base64_type)) {

            return new String(java.util.Base64.getUrlEncoder().encode(byteArr), CHARSET_UTF8);
        } else {

            throw new Exception();
        }
    }

    public static String decode(String src) throws Exception {
        return decode(src,BASE64_TYPE_CC);
    }

    public static String decode(String src, String base64_type) throws Exception {

        byte[] byteSrc = null;
        if (BASE64_TYPE_CC.equals(base64_type) || BASE64_TYPE_CC_URL.equals(base64_type)) {

            byteSrc = org.apache.commons.codec.binary.Base64.decodeBase64(src);
        } else if (BASE64_TYPE_BC.equals(base64_type)) {

            byteSrc = org.bouncycastle.util.encoders.Base64.decode(src);
        } else if (BASE64_TYPE_BC_URL.equals(base64_type)) {

            byteSrc = org.bouncycastle.util.encoders.UrlBase64.decode(src);
        } else if (BASE64_TYPE_JV.equals(base64_type)) {

            byteSrc = java.util.Base64.getDecoder().decode(src);
        } else if (BASE64_TYPE_JV_URL.equals(base64_type)) {

            byteSrc = java.util.Base64.getUrlDecoder().decode(src);
        } else {

            throw new Exception();
        }

        return new String (byteSrc);
    }


    public static String SafeBase64_encode(String base64str) {
        return base64str.replace("+", "-").replace("/", "_");
    }

    public static String SafeBase64_decode(String base64str) {
        return base64str.replace("-", "+").replace("_", "/");
    }
}
