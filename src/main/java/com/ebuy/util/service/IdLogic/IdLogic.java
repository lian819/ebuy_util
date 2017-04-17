package com.ebuy.util.service.IdLogic;

import com.beef.util.HexUtil;

import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * $
 *
 * @author Lian
 * @date 2017/4/17
 * @since 1.0
 */
public class IdLogic {

	private final Object _lockForUserId = new Object();
	private int _serverNum = 0;
	private long _userZeroDateTimeMS = 0;

	public IdLogic(int serverNum) {
		_serverNum = serverNum;

		GregorianCalendar userZeroCal = new GregorianCalendar(Locale.CHINA);
		userZeroCal.set(2013, 0, 1, 0, 0, 0);
		_userZeroDateTimeMS = (userZeroCal).getTimeInMillis();
	}

	public String getNewDataID() {
		synchronized (_lockForUserId) {
			long dataId = ((((long) _serverNum) << 48) & 0xFFFF000000000000L)
					| ((System.currentTimeMillis() - _userZeroDateTimeMS) & 0x0000FFFFFFFFFFFFL);

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}

			return HexUtil.toHexString(dataId);
		}
	}

	public static void main(String[] args) {
//		String newId = new IdLogic(100).getNewDataID();
//		System.out.println(newId);

		// 左移运算符, 相当于乘以2^48
//		long num1 = 100L << 48;
//		long num2 = num1 & 0xFFFF000000000000L;
//		System.out.println("num1: " + num1);
//		System.out.println("num2: " + num2);
//
//		GregorianCalendar userZeroCal = new GregorianCalendar(Locale.CHINA);
//		userZeroCal.set(2013, 0, 1, 0, 0, 0);
//		long time1 = (userZeroCal).getTimeInMillis();
//		long num3 = (System.currentTimeMillis() - time1);
//		long num4 = num3 & 0x0000FFFFFFFFFFFFL;
//		System.out.println("num3: " + num3);
//		System.out.println("num4: " + num4);
//
//		long dataId = num2 | num4;
//		System.out.println("dataId: " + dataId);


		//
		System.out.println(0xff >>> 7);
		System.out.println(1<<3);
		System.out.println(Integer.toBinaryString(255));

		System.out.println((((byte) 0xff) >>> 7));
//
//		System.out.println((byte) (((byte) 0xff) >>> 7));
	}
}
