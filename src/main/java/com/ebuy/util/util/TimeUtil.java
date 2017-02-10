package com.ebuy.util.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间工具类
 *
 * @auth 	Lian
 * @date 	2016/10/11
 * @since 	1.0
 */
public class TimeUtil {

	public static long getCurrentLongTimestamp() {
		return System.currentTimeMillis();
	}

	public static void main(String[] args) {
//		GregorianCalendar calendar = new GregorianCalendar();
//
//		calendar.add(Calendar.HOUR, -8);

		long currentTime = getCurrentLongTimestamp();
//		long currentTime = calendar.getTimeInMillis();

		System.out.println("currentTime:" + currentTime);

		Date date = new Date(currentTime);
		System.out.println(date);
	}
}
