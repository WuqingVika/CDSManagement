package com.cds.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * String的工具类
 * @author PengChan
 *
 */
public class StringUtils {
	/**
	 * 获取时间字符串
	 * @return
	 */
	public static String getStringTime(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	/**
	 * 判断字符串是否为空	
	 * @param s
	 * @return
	 */
	public static boolean validateString(String s){
		if(s!=null&&s.trim().length()>0){
			return true;
		}else{
			return false;
		}
	}
	
}
