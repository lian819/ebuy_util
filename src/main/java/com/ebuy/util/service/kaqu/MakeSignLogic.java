package com.ebuy.util.service.kaqu;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * $
 *
 * @author Lian
 * @date 2017/4/17
 * @since 1.0
 */
public class MakeSignLogic {

	public MakeSignLogic() {

	}

	public String buildSign(Map<String, String> paramMap, String key) {
		// 准备验签数据
		String keySortString = getKeySortDataStringForNoneEquals(paramMap, key);
//		logger.debug("buildSign keySortString : " + keySortString);
		String md5Result = toSHA1(keySortString).toLowerCase();

		return md5Result;
	}

	/**
	 * 得到加密前的字符串
	 *
	 * @param paramMap
	 * @param sign_key
	 * @return
	 */
	private static String getKeySortDataStringForNoneEquals(Map<String, String> paramMap, String sign_key) {

		List<String> keys = new ArrayList<String>(paramMap.keySet());
		Collections.sort(keys);

		StringBuilder sb = new StringBuilder();

		// 内容部分循环拼接KeyValue
		String key, value;
		for (int i = 0; i < keys.size(); i++) {
			key = keys.get(i);
			value = nullToEmpty(paramMap.get(key));

			if (isNullOrEmpty(value)) {
				continue;
			}

			sb.append(value);
		}
		// 尾部拼上签名
		sb.append(sign_key);

		// 返回拼接完毕的字符串
		return sb.toString();
	}

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	private static String toSHA1(String origin) {
		String resultString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(origin.getBytes("UTF-8"));

			resultString = byteArrayToHexString(md.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultString;
	}

	/**
	 * Null转换为空
	 *
	 * @return true/false
	 */
	private static String nullToEmpty(String str) {

		if (isNullOrEmpty(str)) {
			return "";
		}

		return str.trim();
	}

	/**
	 * 关于字符(NullOrEmpty)的判断(半角空格也被作为Empty)
	 *
	 * @return true/false
	 */
	private static boolean isNullOrEmpty(String str) {

		if (str == null) {
			return true;
		}

		if (str.trim().equals("")) {
			return true;
		}

		return false;
	}
}