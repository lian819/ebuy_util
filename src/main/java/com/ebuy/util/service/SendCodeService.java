package com.ebuy.util.service;

import MetoXML.Base.XmlParseException;
import com.epointchina.o2o.ecoupon2.thirdpty.data.sendcode.SendCodeQuery;
import com.epointchina.o2o.ecoupon2.thirdpty.data.sendcode.SendCodeQueryMessage;
import com.epointchina.o2o.ecoupon2.thirdpty.logic.QueryEcouponLogic;
import org.json.JSONException;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

/**
 * $
 *
 * @auth Lian
 * @date 2016/10/9
 * @since 1.0
 */
public class SendCodeService {

	public final static String _queryO2OThirdptyUrl = "http://121.40.210.198:8080/o2o_ecoupon2_thirdpty_test/thrid_party/1.0";
	public final static String _format = "xml";
	public final static String _msgtype_request_msg = "request_msg";
	public final static String _msgtype_notify_msg = "notify_msg";
	public final static String _version = "1.0";

	/** 粒上皇产品券 */
	public static String COUPON_ID = "0000001bb6b7c6d5";
	/** 粒上皇APP_ID */
	public static String APP_ID = "000000185fcb9a91";
	public static String TOKEN = "82E0D77033C4A7A64E9C89FC6AD5BF6F";
	public static String KEY = "0000000100000154517c7fa300000001";
	public static String IV = "000000185fcb9a91";

	public static void  main(String[] args) throws IOException, XmlParseException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException, IntrospectionException, JSONException, ParseException {

		String returnValue = "";

		String timestamp = String.valueOf(System.currentTimeMillis());

		//测试请求发码
		SendCodeQueryMessage queryMessage = new SendCodeQueryMessage();
		queryMessage.setCouponId(COUPON_ID);
		queryMessage.setOutOrderNo("20161010000");
		queryMessage.setTransCode("20161010000");
		queryMessage.setUserNumber("13681662710");
		queryMessage.setUserType("1");
		SendCodeQuery query = new SendCodeQuery();
		query.setAppId(APP_ID);
		query.setFormat(_format);
		query.setMessage(queryMessage);
		query.setMethod("10");
		query.setMsgtype(_msgtype_request_msg);
		query.setTimestamp(timestamp);
		query.setVersion(_version);
		returnValue = new QueryEcouponLogic(TOKEN, KEY, IV).pushSendCode(null, _queryO2OThirdptyUrl, query);

		System.out.println("[returnValue]" + returnValue);

	}

}
