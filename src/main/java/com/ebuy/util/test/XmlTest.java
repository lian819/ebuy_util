package com.ebuy.util.test;

import MetoXML.Base.XmlParseException;
import MetoXML.XmlDeserializer;
import MetoXML.XmlSerializer;
import com.ebuy.util.data.FormBrand;
import com.ebuy.util.data.SearchBrand;
import org.jcp.xml.dsig.internal.dom.XMLDSigRI;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * $
 *
 * @author Lian
 * @date 2016/12/22
 * @since 1.0
 */
public class XmlTest {

	public static void main(String[] args) {
		xmlTransferTest();
	}

	public static void resultTest() {
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

	public static void xmlTransferTest() {

		try {

			FormBrand formBrand = new FormBrand();
			formBrand.setBrand_id("111");
			formBrand.setBrand_name_cn("brand_name");

			String formBrandXml = XmlSerializer.objectToString(formBrand, FormBrand.class);

			SearchBrand searchBrand = (SearchBrand) XmlDeserializer.stringToObject(formBrandXml, SearchBrand.class);

			System.out.println(searchBrand.getBrand_id() + "  " + searchBrand.getBrand_name_cn());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
