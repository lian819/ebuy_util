package com.ebuy.util.test;

import MetoXML.XmlSerializer;
import org.apache.log4j.Logger;

/**
 * $
 *
 * @author Lian
 * @date 2016/12/22
 * @since 1.0
 */
public class QueryResult {


	private final static Logger logger = Logger.getLogger(QueryResult.class);
	private static final String OBJ_NAME = "QueryResult";

	private String resultCode = "";
	private String resultMsg = "";

	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	/**
	 * XML
	 * @return
	 */
	public String toXml() {
		try {
			String returnValue = XmlSerializer.objectToString(this, QueryResult.class, false);
			returnValue = returnValue.replaceAll(OBJ_NAME, "Result");
			return returnValue;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("",e);
			return null;
		}
	}

}
