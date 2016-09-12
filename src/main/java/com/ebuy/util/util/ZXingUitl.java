package com.ebuy.util.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * ZXing二维码工具类
 *
 * @author 	Lian
 * @time	2016年8月1日
 *
 */
public class ZXingUitl {

	private static int DEFAULT_WIDTH = 500;
	private static int DEFAULT_HEIGHT = 500;
	private static String DEFAULT_FORMAT = "png";

	/**
	 * 生成普通二维码图片
	 *
	 * @param sncode			码券code
	 * @param code_save_path	二维码保存路径
	 */
	public static void createQrCode(String sncode, String code_save_path){
		createFinalQrCode(sncode, code_save_path, null);
	}

	/**
	 * 生成带有logo的二维码图片
	 *
	 * @param sncode			码券code
	 * @param code_save_path	二维码保存路径
	 * @param logo_path			logo路径
	 */
	public static void createQrCodeWithLogo(String sncode, String code_save_path, String logo_path) {
		createFinalQrCode(sncode, code_save_path, logo_path);

	}

	public static void createFinalQrCode(String sncode, String code_save_path, String logo_path) {
		try {
			Hashtable hints = new Hashtable();
			BitMatrix bitMatrix;
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); // 内容所使用字符集编码
			File file = new File(code_save_path + ".png");
			if(!file.exists()) {
				bitMatrix = new MultiFormatWriter().encode(sncode, BarcodeFormat.QR_CODE, DEFAULT_WIDTH, DEFAULT_HEIGHT, hints);
				MatrixToImageWriter.writeToFile(bitMatrix, DEFAULT_FORMAT, file, logo_path);
			}
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
