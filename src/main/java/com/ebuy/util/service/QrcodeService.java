package com.ebuy.util.service;

import com.ebuy.util.util.ZXingUitl;


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

		ZXingUitl.createFinalQrCode("20160901", "/Users/xingguliu/Desktop/qrcode/7.png", logoPath);
	}


}
