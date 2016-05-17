package com.cds.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

/**
 * md5加密算法
 * @author PengChan
 *
 */
public class MD5 {
	/**
	 * md5加密
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
	 * 匹配str1经过md5加密后是否与数据库中的数据相匹配
	 * @param str1 未加密的字符串
	 * @param str2 加密后的字符串
	 * @return 是否匹配
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
