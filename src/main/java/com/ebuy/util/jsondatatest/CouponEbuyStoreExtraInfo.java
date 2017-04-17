package com.ebuy.util.jsondatatest;

import org.json.JSONObject;

/**
 *
 *
 * @author 	lian
 * @date	2017年3月2日
 * @since	2.0
 */
public class CouponEbuyStoreExtraInfo extends ExtraInfo {

	/**
	 * 是否从ERP获取门店信息
	 * 0：否；1：是
	 */
	private int ebuy_store_flg = 0;

	public CouponEbuyStoreExtraInfo() {
		super();
	}

	public CouponEbuyStoreExtraInfo(JSONObject json) throws Exception {
		this();
		this.ebuy_store_flg = json.optInt("ebuy_store_flg");
	}

	public JSONObject toJSON() throws Exception {
		JSONObject json = new JSONObject();
		json.put("ebuy_store_flg", getEbuy_store_flg());

		return json;
	}

	public int getEbuy_store_flg() {
		return ebuy_store_flg;
	}

	public void setEbuy_store_flg(int ebuy_store_flg) {
		this.ebuy_store_flg = ebuy_store_flg;
	}
}
