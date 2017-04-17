package com.ebuy.util.jsondatatest;

public class CouponDisplayInfoJson extends JSONBaseData{
	public static final String TYPE_QRCODE_PAGE_DISPLAY_INFO = "1";
	public static final String VERSION_1 = "1.0";
	
	private CouponDisplayInfoData data = null;

	public CouponDisplayInfoData getData() {
		return data;
	}

	public void setData(CouponDisplayInfoData data) {
		this.data = data;
	}
	
	
}
