package com.ebuy.util.util;

//package com.union.pufa;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @version 1.0
 * @user lianxinzhong
 * @date 2019/8/9
 */
public class CodeUtils {

    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    public CodeUtils() {
    }

    public static String byte2hex(byte[] input) {
        return Hex.encodeHexString(input);
    }

    public static byte[] hex2byte(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException var2) {
            throw new IllegalStateException("Hex Decoder exception", var2);
        }
    }

    public static String base64Encode(byte[] input) {
        return Base64.encodeBase64String(input);
    }

    public static String base64UrlSafeEncode(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }

    public static byte[] base64Decode(String input) {
        return Base64.decodeBase64(input);
    }

    public static String urlEncode(String input) {
        try {
            return URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            throw new RuntimeException("Unsupported Encoding Exception", var2);
        }
    }

    public static String urlDecode(String input) {
        try {
            return URLDecoder.decode(input, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            throw new RuntimeException("Unsupported Encoding Exception", var2);
        }
    }

    public static String htmlEscape(String html) {
        return StringEscapeUtils.escapeHtml(html);
    }

    public static String htmlUnescape(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml(htmlEscaped);
    }

    public static String xmlEscape(String xml) {
        return StringEscapeUtils.escapeXml(xml);
    }

    public static String xmlUnescape(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    public static String md5String(String str) {
        return DigestUtils.md5Hex(str);
    }

    public static String sha1String(String str) {
        return DigestUtils.sha1Hex(str);
    }

    public static String asc2ascInt2hexasc(String str) {
        byte[] b = str.getBytes();
        int[] in = new int[b.length];

        for(int i = 0; i < in.length; ++i) {
            in[i] = b[i] & 255;
        }

        StringBuffer sb = new StringBuffer(in.length);
        int[] var4 = in;
        int var5 = in.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            int num = var4[var6];
            sb.append(Integer.toString(num, 16));
        }

        return sb.toString();
    }

    public static String hexasc2ascInt2asc(String str) {
        int[] in = new int[str.length() / 2];

        for(int i = 0; i < in.length; ++i) {
            in[i] = Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
        }

        StringBuffer sb = new StringBuffer(in.length);
        int[] var3 = in;
        int var4 = in.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int i2 = var3[var5];
            sb.append((char)i2);
        }

        return sb.toString();
    }

    public static String paddingRightStr(String str, char padc, int length) {
        if (str.length() > length) {
            return str.substring(0, length);
        } else {
            int numc = length - str.length();
            StringBuffer rets = new StringBuffer(str);

            for(int i = 0; i < numc; ++i) {
                rets.append(padc);
            }

            return rets.toString();
        }
    }
}
