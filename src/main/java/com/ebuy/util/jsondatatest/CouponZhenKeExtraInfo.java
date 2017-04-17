package com.ebuy.util.jsondatatest;

import org.json.JSONObject;

public class CouponZhenKeExtraInfo extends ExtraInfo{
	
	private int activity_batch_id = 0;//活动批次ID 
	
	public CouponZhenKeExtraInfo() {
		super();
	}
	public CouponZhenKeExtraInfo(JSONObject json) throws Exception {
		this();
		this.activity_batch_id = json.optInt("activity_batch_id");
	}
	
	public JSONObject toJSON() throws Exception {
		JSONObject json = new JSONObject();
		json.put("activity_batch_id", getActivity_batch_id());
		
		return json;
	}
	public int getActivity_batch_id() {
		return activity_batch_id;
	}
	public void setActivity_batch_id(int activity_batch_id) {
		this.activity_batch_id = activity_batch_id;
	}
	
	
}
