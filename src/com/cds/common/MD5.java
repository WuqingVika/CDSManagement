package com.cds.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

/**
 * md5�����㷨
 * @author PengChan
 *
 */
public class MD5 {
	/**
	 * md5����
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String encodyByMD5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		BASE64Encoder base64en = new BASE64Encoder();
		String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		return newstr;
	}
	
	/**
	 * ƥ��str1����md5���ܺ��Ƿ������ݿ��е�������ƥ��
	 * @param str1 δ���ܵ��ַ���
	 * @param str2 ���ܺ���ַ���
	 * @return �Ƿ�ƥ��
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean checkSame(String str1,String str2) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		if(encodyByMD5(str1).equals(str2)){
			return true;
		}else{
			return false;
		}
	}
		
}
