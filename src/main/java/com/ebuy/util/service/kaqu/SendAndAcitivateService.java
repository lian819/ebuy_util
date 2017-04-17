package com.ebuy.util.service.kaqu;

import com.salama.easyhttp.client.HttpClientUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * $
 *
 * @author Lian
 * @date 2017/4/17
 * @since 1.0
 */
public class SendAndAcitivateService {

	private final static String APPKEY = "5410ac199d8de0da";
	private final static String APPSECRET="300f71c7eac195390d9a66eac72d7eb6";

	private final static String URL_PREFIX = "https://sboxapi.cardqu.com";

	public static void main(String[] args) {

		HttpPost httpPost = new HttpPost(URL_PREFIX + "/api/1.0/prepay/coupon/delivery.json");
		HttpResponse response = null;
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("appkey", APPKEY);
			paramMap.put("prepay_type", "1");
			paramMap.put("count", "1");
			paramMap.put("extra_info", "");

			String sign = new MakeSignLogic().buildSign(paramMap,APPSECRET);
			System.out.println("sign : " + sign);

			List<BasicNameValuePair> paramList = new ArrayList<BasicNameValuePair>();
			paramList.add(new BasicNameValuePair("appkey", APPKEY));
			paramList.add(new BasicNameValuePair("prepay_type", "1"));
			paramList.add(new BasicNameValuePair("count", "1"));
			paramList.add(new BasicNameValuePair("extra_info", ""));
			paramList.add(new BasicNameValuePair("sign", "sign"));

			httpPost.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
			response = HttpClientUtil.getHttpClient().execute(httpPost);
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			HttpClientUtil.getResponseContent(response.getEntity(), output);
			String getResult = new String(output.toByteArray(), "utf-8");
			System.out.println(getResult);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



}
