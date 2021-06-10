package com.ebuy.util.test;


import com.beef.util.HexUtil;

/**
 * $
 *
 * @author Lian
 * @date 2017/4/1
 * @since 1.0
 */
public class HexUtilTest {

	public static void toHexStringTest() {

		byte byte1 = -128;

		byte byte2 = 127;

		System.out.println(Integer.toHexString(byte1));
		System.out.println(Integer.toHexString(byte2));
		System.out.println(HexUtil.toHexString(byte1));
		System.out.println(HexUtil.toHexString(byte2));

	}

	public static void main(String[] args) {
		toHexStringTest();
	}
}
