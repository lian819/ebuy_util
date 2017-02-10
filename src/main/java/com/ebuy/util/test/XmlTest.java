package com.ebuy.util.test;

import MetoXML.XmlDeserializer;

/**
 * $
 *
 * @author Lian
 * @date 2016/12/22
 * @since 1.0
 */
public class XmlTest {

	public static void main(String[] args) {
		String result = "<Result><resultCode>000000</resultCode><resultMsg>操作成功</resultMsg></Result>";

		try {


			QueryResult returnResult = (QueryResult) XmlDeserializer.stringToObject(result, QueryResult.class);
			System.out.println(returnResult);
			System.out.println(returnResult.getResultCode());
			System.out.println(returnResult.getResultMsg());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
