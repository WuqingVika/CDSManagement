package com.cds.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import com.itextpdf.text.pdf.qrcode.ByteArray;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.javafx.image.IntPixelGetter;

/**
 * ������֤���ͼƬ
 * 
 * @author PengChan
 *
 */
public class SecurityImage {

	/**
	 * ������֤���ͼƬ�ĸ�ʽ
	 * @param secturityCode ��֤���ַ�
	 * @return ����ͼƬ
	 */
	public static BufferedImage createImage(String secturityCode) {
		// ��֤��ĳ���
		int codeLength = secturityCode.length();
		// ����Ĵ�С
		int fSize = 15;
		int fWidth = fSize + 1;
		// ͼƬ�Ŀ��
		int width = codeLength * fWidth + 6;
		// ͼƬ�ĸ߶�
		int height = fSize * 2 + 1;
		// ͼƬ
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		java.awt.Graphics g = image.createGraphics();
		// ���ñ�����ɫ
		g.setColor(Color.WHITE);
		// �������
		g.fillRect(0, 0, width, height);
		// ���ñ߿���ɫ
		g.setColor(Color.LIGHT_GRAY);
		// ����������ʽ
		g.setFont(new Font("Arial", Font.BOLD, height - 2));
		// ���Ʊ߿�
		g.drawRect(0, 0, width - 1, height - 1);
		// �������
		Random rand = new Random();
		// ���������ɫ
		g.setColor(Color.LIGHT_GRAY);
		for (int i = 0; i < codeLength * 6; i++) {
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			// ����1*1��С�ľ���
			g.drawRect(x, y, 1, 1);
		}
		// ������֤��
		int codeY = height - 10;
		// ����������ɫ����ʽ
		g.setColor(new Color(19, 148, 246));
		g.setFont(new Font("Georgia", Font.BOLD, fSize));
		for (int i = 0; i < codeLength; i++) {
			g.drawString(String.valueOf(secturityCode.charAt(i)), i * 16 + 5, codeY);
		}
		// �ر���Դ
		g.dispose();
		return image;
	}

	/**
	 * ������֤���ͼƬ���ĸ�ʽ
	 * @param securityCode ��֤��
	 * @return ͼƬ��
	 */
	public static ByteArrayInputStream getImageAsInputStream(String securityCode){
		BufferedImage image = createImage(securityCode);
		return converImageToInputStream(image);
	}
	
	/**
	 * ��BufferedImageת��ΪByteArrayInputStream
	 * @param image ͼƬ
	 * @return ����һ��ByteArrayInputStream��
	 */
	public static ByteArrayInputStream converImageToInputStream(BufferedImage image){
		ByteArrayInputStream inputStream = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		JPEGImageEncoder jpeg = JPEGCodec.createJPEGEncoder(bos);
		try {
			jpeg.encode(image);
			byte[] bts = bos.toByteArray();
			inputStream = new ByteArrayInputStream(bts);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;		
	}
}
