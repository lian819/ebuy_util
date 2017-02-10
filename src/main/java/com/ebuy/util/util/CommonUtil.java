package com.ebuy.util.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * $
 *
 * @author Lian
 * @date 2016/11/23
 * @since 1.0
 */
public class CommonUtil {


	public final static String CHARSET_ISO_8859_1 = "iso-8859-1";
	public final static String CHARSET_UTF8 = "utf-8";

	public static String formatDecimal(double dblValue) {

		return new DecimalFormat("#.##").format(dblValue);
	}

	public static String transferStringCharset(String value, String oldCharset, String newCharset) {
		try {
			return new String(nullToEmpty(value).getBytes(oldCharset), newCharset);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
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

	public static boolean isAmount(String val) {
		try {
			Double.parseDouble(val);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String padZero(int val, int length) {
		String returnValue = "";
		for (int i = 0; i < length - Integer.toString(val).length(); i++) {
			returnValue += "0";
		}
		returnValue += Integer.toString(val);
		return returnValue;

	}

	/**
	 * 判断是否是整形
	 *
	 * @param val
	 * @return
	 */
	public static boolean isInt(String val) {
		try {
			Integer.parseInt(val);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isLong(String val) {
		try {
			Long.parseLong(val);
			return true;
		} catch (Exception e) {
			return false;
		}
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
	 * 将时间转换为字符串
	 *
	 * @param datDate
	 * @param strFormat
	 * @return
	 */
	public static String dateToString(Date datDate, String strFormat) {
		if (datDate == null) {
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(strFormat);
		String strDate = formatter.format(datDate);
		return strDate;
	}

	/**
	 * 将时间字符串按提供的格式转换为字符串
	 *
	 * @param argStrDate
	 * @return
	 */
	public static String formatStrDate(String strDate, String strOldFormat, String strNewFormat) {

		String strNewDate = "";
		if (isNullOrEmpty(strDate)) {
			return strNewDate;
		}

		SimpleDateFormat formatter = new SimpleDateFormat(strOldFormat);
		ParsePosition pos = new ParsePosition(0);
		Date tempDate = formatter.parse(strDate, pos);
		if (tempDate != null) {
			SimpleDateFormat formatter1 = new SimpleDateFormat(strNewFormat);
			strNewDate = formatter1.format(tempDate);
		}
		return strNewDate;
	}

	/**
	 * 将时间字符串按提供的格式转换为字符串
	 *
	 * @param argStrDate
	 * @return
	 */
	public static long getLongDate(String strDate, String strFormat) {

		long lngDate = 0;
		if (isNullOrEmpty(strDate)) {
			return lngDate;
		}

		SimpleDateFormat formatter = new SimpleDateFormat(strFormat);
		ParsePosition pos = new ParsePosition(0);
		Date tempDate = formatter.parse(strDate, pos);
		if (tempDate != null) {
			lngDate = tempDate.getTime();
		}
		return lngDate;
	}


	/**
	 * 将时间字符串按提供的格式转换为字符串
	 *
	 * @param argStrDate
	 * @return
	 */
	public static String formatLongDate(long lngDate, String strFormat) {

		String strDate = "";
		if (lngDate == 0) {
			return strDate;
		}
		Date tempDate = new Date();
		tempDate.setTime(lngDate);

		SimpleDateFormat formatter = new SimpleDateFormat(strFormat);
		strDate = formatter.format(tempDate);

		return strDate;
	}


	/**
	 * 将时间字符串按提供的格式转换为字符串
	 *
	 * @param argStrDate
	 * @return
	 */
	public static String getMonday(long lngDate, String strFormat) {
		GregorianCalendar greDate = new GregorianCalendar();
		greDate.setTimeInMillis(lngDate);
		int dayOfWeek = greDate.get(GregorianCalendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			dayOfWeek = 8;
		}
		greDate.add(GregorianCalendar.DAY_OF_MONTH, 2 - dayOfWeek);

		return formatLongDate(greDate.getTimeInMillis(), strFormat);
	}


	/**
	 * regixCheck
	 *
	 * @return true/false
	 */
	public static boolean regixCheck(String str, String regEx) {

		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		boolean result = m.find();
		return result;
	}

	/**
	 * isEmail
	 *
	 * @return true/false
	 */
	public static boolean isEmail(String email) {
		try {
			if (email == null || email.length() < 1 || email.length() > 256) {
				return false;
			}
			String check = "^([0-9a-zA-Z]+[_.0-9a-zA-Z-]+)@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2,3})$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			boolean isMatched = matcher.matches();
			if (isMatched) {
				return true;
			} else {
				return false;
			}
		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * 日期比较是否为同一天
	 *
	 * @param nowTime
	 * @param compareTime
	 * @return
	 */
	public static boolean isSameDay(long nowTime, long compareTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		String ds1 = sdf.format(nowTime);

		String ds2 = sdf.format(compareTime);
		if (ds1.equals(ds2)) {
			return true;
		} else {
			return false;
		}
	}


}
