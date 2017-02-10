package com.ebuy.util.service.spd.ticket;


import com.ebuy.shop.api.client.ecoupon.data.ECouponConsts;
import com.ebuy.shop.api.client.ecoupon.data.sendcode.ThirdptySendCodeQuery;
import com.ebuy.shop.api.client.ecoupon.data.sendcode.ThirdptySendCodeQueryMessage;
import com.ebuy.shop.api.client.ecoupon.logic.ThirdptyQueryECouponLogic;

/**
 * $
 *
 * @author Lian
 * @date 2017/1/3
 * @since 1.0
 */
public class ThirdptyService {

	private final static String MERCHANT_ID = "000001588283df26005f7dfe80000002";
	private final static String MERCHANT_TOKEN = "000001591CE264B600C90F548000000B";
	private final static String MERCHANT_KEY = "000001591ce2649c007e120c80000001";
	private final static String MERCHANT_IV = "000001591ce264ab0021bafd8000000a";
	private final static String REQUEST_URL = "http://118.178.142.20:8080/shop_ticket_test/thidpty/1.0";

	public static void main(String[] args) {
		requestCode();

	}

	public static void requestCode() {

		String transcode = "20170105112640";

		// 消息体构造
		ThirdptySendCodeQueryMessage queryMessage = new ThirdptySendCodeQueryMessage();
		queryMessage.setTranscode(transcode);
		queryMessage.setActivityItemId("1");
		queryMessage.setResponseUrl(REQUEST_URL);
		queryMessage.setCertNo("41140319910808543X");

		// 头文件构造
		ThirdptySendCodeQuery query = new ThirdptySendCodeQuery();
		query.setMerchantId(MERCHANT_ID);
		query.setMethod(ECouponConsts.METHOD_QEY_SENDCODE);
		query.setTimestamp(String.valueOf(System.currentTimeMillis()));
		query.setVersion(ECouponConsts.VERSION_1_0);
		query.setMessage(queryMessage);

		// 发起请求
		String returnValue = new ThirdptyQueryECouponLogic(MERCHANT_TOKEN, MERCHANT_KEY, MERCHANT_IV).pushSendCode(null, REQUEST_URL, query);
		System.out.println("returnValue: " + returnValue);

	}
}
