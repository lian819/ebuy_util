package com.ebuy.util.service.simplejson;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by macmini4 on 2017/5/27.
 */
public class SimpleJsonUtil {

    private final static String str = "{\"errcode\":0,\"errmsg\":\"ok\",\"fail_code\":[\"100050280173\"]}";

    public static void main(String[] args) {
        orgJson(str);

    }

    public static void orgJson(String str) {

        JSONObject resultObj = new JSONObject(str);
        System.out.println(resultObj.toString());


        JSONArray failArr = resultObj.getJSONArray("fail_code");
        System.out.println(failArr.length());
        System.out.println(failArr.toString());

        String code = failArr.getString(0);
        System.out.println(code);

//        JSONObject json = failArr.getJSONObject(0);
//        System.out.println(json);
//        System.out.println(json.toString());

    }

    public static void simpleJsonData(String str) {

    }
}
