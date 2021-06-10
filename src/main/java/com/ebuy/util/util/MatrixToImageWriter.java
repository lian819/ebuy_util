package com.ebuy.util.util;

import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * ZXing生成二维码需要用到的类
 *
 * @auth Lian
 * @date 16/9/12
 */
public class MatrixToImageWriter {

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	private MatrixToImageWriter() {
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}


	/**
	 * @param matrix   二维码矩阵相关
	 * @param format   二维码图片格式
	 * @param file     二维码图片文件
	 * @param logoPath logo路径
	 * @throws IOException
	 */
	public static void writeToFile(BitMatrix matrix, String format, File file, String logoPath) throws IOException {
		BufferedImage image = toBufferedImage(matrix);

		if (logoPath != null) {
			Graphics2D gs = image.createGraphics();
			// 载入logo
			Image img = ImageIO.read(new File(logoPath));
			int width = (matrix.getWidth() -100) / 2;
			int height = (matrix.getHeight() -100) / 2;
			gs.drawImage(img, width, height, null);
			gs.dispose();
			img.flush();
		}

		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an ebuy of format " + format + " to " + file);
		}

	}

	/**
	 * 最终调用该方法生成普通二维码图片
	 *
	 * @param matrix 二维码矩阵相关
	 * @param format 二维码图片格式
	 * @param file   二维码图片文件
	 * @throws IOException
	 */
	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		writeToFile(matrix, format, file, null);
	}

	public static BufferedImage writeToBufferedImage(BitMatrix matrix, String format) throws IOException {
		return toBufferedImage(matrix);
	}

	public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an ebuy of format " + format);
		}
	}

}
