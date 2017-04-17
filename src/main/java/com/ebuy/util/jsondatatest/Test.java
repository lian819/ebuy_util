package com.ebuy.util.jsondatatest;

import org.json.JSONObject;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

/**
 * $
 *
 * @author Lian
 * @date 2017/2/28
 * @since 1.0
 */
public class Test {

	public static void main(String[] args) throws InvocationTargetException, IntrospectionException, InstantiationException, ParseException, IllegalAccessException {
		testConvert();
	}


	public static void testConvert() throws InvocationTargetException, IntrospectionException, InstantiationException, ParseException, IllegalAccessException {
		JSONBaseData baseData = new JSONBaseData();
		baseData.setType(JSONBaseData.TYPE_ZHENKE);
		baseData.setVersion(JSONBaseData.VERSION_10);

		JSONObject json = new JSONObject();
		json.put("activity_batch_id", 22);

		baseData.setBaseJsondata(json);

		System.out.println(baseData.getBaseJsondata());

		try {
			System.out.println(baseData.toJSON());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
