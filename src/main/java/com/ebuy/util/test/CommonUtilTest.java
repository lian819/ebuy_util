package com.ebuy.util.test;

import com.ebuy.util.util.CommonUtil;

/**
 * $
 *
 * @author Lian
 * @date 2016/11/23
 * @since 1.0
 */
public class CommonUtilTest {

	public static void main(String[] args) {
		long startDate = 1486483200000L;
		long endDate = 1486569599000L;

		String startDateYmd = testFormatLongDate(startDate);
		String endDateYmd = testFormatLongDate(endDate);

		System.out.println("start: " + startDateYmd);
		System.out.println("end: " + endDateYmd);

		System.out.println("compare: " + startDateYmd.compareTo(endDateYmd));
		System.out.println("flag: " + (startDateYmd.compareTo(endDateYmd) < 0));

		String tempDateYmd = startDateYmd;
		int count = 1;
		while(tempDateYmd.compareTo(endDateYmd) < 0 && count < 5) {
			System.out.println("hhh: " + count);
			tempDateYmd = CommonUtil.formatLongDate(startDate + count*24*60*60*1000L, "yyyyMMdd");
			count ++;

		}
	}

	public static String testFormatLongDate(long timestamp) {
		return CommonUtil.formatLongDate(timestamp, "yyyyMMdd");
	}
}
