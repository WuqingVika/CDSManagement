package com.cds.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * String�Ĺ�����
 * @author PengChan
 *
 */
public class StringUtils {
	/**
	 * ��ȡʱ���ַ���
	 * @return
	 */
	public static String getStringTime(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	/**
	 * �ж��ַ����Ƿ�Ϊ��	
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
