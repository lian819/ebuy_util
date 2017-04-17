package com.ebuy.util.jsondatatest;

import org.json.JSONObject;

public class CouponTaopiaopiaoExtraInfo extends ExtraInfo{
	
	private long lottery_id = -1L;//可选 活动id 
	private String channel = "";//必须 合作渠道代码
	private long platform = -1L;//必须 平台代码
	private String mix_id = "";//可选 混淆活动id
	
	public CouponTaopiaopiaoExtraInfo() {
		super();
	}
	public CouponTaopiaopiaoExtraInfo(JSONObject json) throws Exception {
		this();
		this.lottery_id = json.optLong("lottery_id");
		this.channel = json.optString("channel");
		this.platform = json.optLong("platform");
		this.mix_id = json.optString("mix_id");
	}
	
	public JSONObject toJSON() throws Exception {
		JSONObject json = new JSONObject();
		json.put("lottery_id", getLottery_id());
		json.put("channel", getChannel());
		json.put("platform", getPlatform());
		json.put("mix_id", getMix_id());
		
		return json;
	}
	public long getLottery_id() {
		return lottery_id;
	}
	public void setLottery_id(long lottery_id) {
		this.lottery_id = lottery_id;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public long getPlatform() {
		return platform;
	}
	public void setPlatform(long platform) {
		this.platform = platform;
	}
	public String getMix_id() {
		return mix_id;
	}
	public void setMix_id(String mix_id) {
		this.mix_id = mix_id;
	}
	
}
