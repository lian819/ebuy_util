package com.ebuy.util.util;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.util.*;

import com.beef.util.crypto.MD5Util;
import com.ebuy.util.util.hex.HexUtil;
import com.thoughtworks.xstream.core.util.Base64Encoder;
import org.apache.log4j.Logger;


/**
 * @version 1.0
 * @user lianxinzhong
 * @date 2019/8/8
 */
public class SpdCreditCardSignUtil {

    Logger logger = Logger.getLogger(SpdCreditCardSignUtil.class);

    private final PrivateKey _privateKey;
    private final PublicKey _publicKey;

    public SpdCreditCardSignUtil() {
        _privateKey = null;
        _publicKey = null;
    }


    public String sign(Map<String, String> paramMap) throws Exception {
        //3.0验签调整
        String signOrgValue = getKeySortDataStringForEquals(paramMap);
        String md5Data = HexUtil.toHexString(MD5Util.MD5(signOrgValue.getBytes())).toUpperCase();
        String signData = myRSASign((RSAPrivateKey) _privateKey, "1", "01", md5Data);

        logger.debug("SignData()"
                + " md5Data:" + md5Data
                + " signData:" + signData
        );

        return signData;
    }


    public static String getKeySortDataStringForEquals(Map<String, String> paramMap) {
        List<String> keys = new ArrayList<String>(paramMap.keySet());
        Collections.sort(keys);

        StringBuilder sb = new StringBuilder();

        // 内容部分循环拼接Key=Value&Key=Value
        String key, value;
        for (int i = 0; i < keys.size(); i++) {
            key = keys.get(i);
            value = nullToEmpty(paramMap.get(key));

            if (isNullOrEmpty(value)) {
                continue;
            }

            sb.append(key).append("=").append(value);
            if (i < keys.size() - 1) {
                sb.append("&");
            }
        }

        // 返回拼接完毕的字符串
        return sb.toString();
    }

    public static String myRSASign(RSAPrivateKey privatekey, String dataFillMode, String hashID, String data) throws Exception {
        Signature signature = Signature.getInstance("NONEwithRSA");
        if ("01".equals(hashID)) {
            data = CodeUtils.sha1String(data).toUpperCase();
        } else if ("02".equals(hashID)) {
            data = CodeUtils.md5String(data).toUpperCase();
        }

        signature.initSign(privatekey);
        signature.update(data.getBytes());

        return new Base64Encoder().encode(signature.sign());
    }

    public boolean verifySign(Map<String, String> paramMap, String sign) throws Exception {
        //3.0验签调整
        String signOrgValue = getKeySortDataStringForEquals(paramMap);
        String md5Data = HexUtil.toHexString(MD5Util.MD5(signOrgValue.getBytes())).toUpperCase();
        return myRSAVerifySign(_publicKey, "1", "01", md5Data, sign);
    }

    public static boolean myRSAVerifySign(PublicKey publicKey, String dataFillMode, String hashID, String data, String sign) throws Exception {
        Signature signature = Signature.getInstance("NONEwithRSA");
        if ("01".equals(hashID)) {
            data = CodeUtils.sha1String(data).toUpperCase();
        } else if ("02".equals(hashID)) {
            data = CodeUtils.md5String(data).toUpperCase();
        }

        signature.initVerify(publicKey);
        signature.update(data.getBytes());
        return signature.verify(Base64.getDecoder().decode((sign)));
    }

    /**
     * 关于字符(NullOrEmpty)的判断(半角空格也被作为Empty)
     *
     * @return true/false
     */
    public static boolean isNullOrEmpty(String str) {

        if (str == null) {
            return true;
        }

        if (str.trim().equals("")) {
            return true;
        }

        return false;
    }

    /**
     * Null转换为空
     *
     * @return true/false
     */
    public static String nullToEmpty(String str) {

        if (isNullOrEmpty(str)) {
            return "";
        }

        return str.trim();
    }
}
