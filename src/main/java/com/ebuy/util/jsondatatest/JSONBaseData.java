package com.ebuy.util.jsondatatest;

import org.json.JSONObject;

public class JSONBaseData {

	public static final String TYPE_COMMON = "common";
	/** 淘票票券相关配置信息 */
	public static final String TYPE_TAOPIAOPIAO = "taopiaopiao";
	/** 从ERP获取门店信息 */
	public static final String TYPE_EBUY_STORE = "ebuystore";
	/** 线上取臻客码券相关配置信息 */
	public static final String TYPE_ZHENKE = "zhenke";

	public static final String VERSION_10 = "1.0";

	private String type = "";
	private String version = "";

	private JSONObject baseJsondata = null;

	public JSONBaseData() {

	}

	public JSONBaseData(String strInput) throws Exception {
		JSONObject json = new JSONObject(strInput);
		this.type = json.optString("type");
		this.version = json.optString("version");
		baseJsondata = json.optJSONObject("baseJsondata");
	}

	public JSONObject toJSON() throws Exception {
		JSONObject json = new JSONObject();
		json.put("type", getType());
		json.put("version", getVersion());
		json.put("baseJsondata", getBaseJsondata());

		return json;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public JSONObject getBaseJsondata() {
		return baseJsondata;
	}

	public void setBaseJsondata(JSONObject baseJsondata) {
		this.baseJsondata = baseJsondata;
	}

	/**
	 * 取得扩展信息（券扩展信息）
	 * @return
	 * @throws Exception
	 */
	public ExtraInfo getExtraInfo() throws Exception {
		if (getType().equals(TYPE_TAOPIAOPIAO) && getVersion().equals(VERSION_10)) {
			return new CouponTaopiaopiaoExtraInfo(this.baseJsondata);
		} else if (getType().equals(TYPE_ZHENKE) && getVersion().equals(VERSION_10)) {
			return new CouponZhenKeExtraInfo(this.baseJsondata);
		} else if (getType().equals(TYPE_EBUY_STORE) && getVersion().equals(VERSION_10)) {
			return new CouponEbuyStoreExtraInfo(this.baseJsondata);
		}
		return null;
	}

}
