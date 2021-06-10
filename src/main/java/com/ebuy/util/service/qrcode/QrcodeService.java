package com.ebuy.util.service.qrcode;

import com.ebuy.util.util.ZXingUitl;

import java.util.ArrayList;


/**
 * @auth Lian
 * @date 16/9/12
 */
public class QrcodeService {

	private static String LOGO_PATH = "/Logo.jpeg";

	public static void main(String[] args) {
		// get pic from resources root
		String logoPath = QrcodeService.class.getResource(LOGO_PATH).getPath();
//		System.out.println(path);

//		ZXingUitl.createFinalQrCode("20160901", "/Users/xin/Downloads/20160901.png", logoPath);
//		ZXingUitl.createQrCode("20160901", "/Users/xin/Downloads/qrcode/20160901.png");

		ArrayList<String> snCodeList = new ArrayList<>();
		snCodeList.add("10010600187086855556");
		snCodeList.add("10010600083086861187");
		snCodeList.add("10010600086086862189");
		snCodeList.add("10010600087086859119");
		snCodeList.add("10010600089086859832");
		snCodeList.add("10010600180086859949");
		snCodeList.add("10010600180086860625");
		snCodeList.add("10010600180086863957");
		snCodeList.add("10010600180086866488");
		snCodeList.add("10010600180086869285");
		snCodeList.add("10010600181086862525");
		snCodeList.add("10010600181086865159");
		snCodeList.add("10010600181086867429");
		snCodeList.add("10010600181086869528");
		snCodeList.add("10010600181086870747");
		snCodeList.add("10010600182086856892");
		snCodeList.add("10010600182086862394");
		snCodeList.add("10010600182086868126");
		snCodeList.add("10010600182086870383");
		snCodeList.add("10010600183086863141");
		snCodeList.add("10010600183086868639");
		snCodeList.add("10010600184086866799");
		snCodeList.add("10010600184086867819");
		snCodeList.add("10010600184086869870");
		snCodeList.add("10010600184086870205");
		snCodeList.add("10010600185086857379");
		snCodeList.add("10010600185086860863");
		snCodeList.add("10010600185086868845");
		snCodeList.add("10010600185086871120");
		snCodeList.add("10010600186086861961");
		snCodeList.add("10010600186086866212");
		snCodeList.add("10010600186086867305");
		snCodeList.add("10010600186086871503");
		snCodeList.add("10010600187086856765");
		snCodeList.add("10010600187086861368");
		snCodeList.add("10010600187086863021");
		snCodeList.add("10010600187086864208");
		snCodeList.add("10010600187086866079");
		snCodeList.add("10010600187086866601");
		snCodeList.add("10010600187086867002");
		snCodeList.add("10010600187086868255");
		snCodeList.add("10010600188086855892");
		snCodeList.add("10010600188086856258");
		snCodeList.add("10010600188086857791");
		snCodeList.add("10010600188086859529");
		snCodeList.add("10010600188086860070");
		snCodeList.add("10010600188086867116");
		snCodeList.add("10010600188086867619");
		snCodeList.add("10010600188086869163");
		snCodeList.add("10010600188086869407");
		snCodeList.add("10010600188086871244");
		snCodeList.add("10010600189086857196");
		snCodeList.add("10010600189086857503");
		snCodeList.add("10010600189086867937");
		snCodeList.add("10010600280086861577");
		snCodeList.add("10010600280086862909");
		snCodeList.add("10010600281086858915");
		snCodeList.add("10010600281086865875");
		snCodeList.add("10010600282086858126");
		snCodeList.add("10010600282086863498");
		snCodeList.add("10010600282086869049");
		snCodeList.add("10010600283086857066");
		snCodeList.add("10010600283086861064");
		snCodeList.add("10010600283086861707");
		snCodeList.add("10010600283086863622");
		snCodeList.add("10010600283086864443");
		snCodeList.add("10010600284086866333");
		snCodeList.add("10010600284086869749");
		snCodeList.add("10010600284086870615");
		snCodeList.add("10010600285086858251");
		snCodeList.add("10010600285086864085");
		snCodeList.add("10010600285086864645");
		snCodeList.add("10010600287086859326");
		snCodeList.add("10010600289086870990");
		snCodeList.add("10010600383086856568");
		snCodeList.add("10010600383086861831");
		snCodeList.add("10010600383086864957");
		snCodeList.add("10010600386086860336");
		snCodeList.add("10010600480086870075");
		snCodeList.add("10010600485086857997");
		snCodeList.add("10010600488086857624");
		snCodeList.add("10010600488086871369");
		snCodeList.add("10010600588086865668");
		snCodeList.add("10010600681086870866");
		snCodeList.add("10010600686086856031");
		snCodeList.add("10010600688086860196");
		snCodeList.add("10010600783086863269");
		snCodeList.add("10010600784086858513");
		snCodeList.add("10010600786086862703");
		snCodeList.add("10010600787086865294");
		snCodeList.add("10010600789086859684");
		snCodeList.add("10010600880086865542");
		snCodeList.add("10010600882086870493");
		snCodeList.add("10010600883086863386");
		snCodeList.add("10010600887086865417");
		snCodeList.add("10010600983086864759");
		snCodeList.add("10010600986086858709");
		snCodeList.add("10010600986086868383");
		snCodeList.add("10010600987086863827");
		snCodeList.add("10010600987086869641");

		for(String snCode : snCodeList) {
			ZXingUitl.createQrCode(snCode, "/Users/xin/Downloads/qrcode2/" + snCode);
		}
	}


}
