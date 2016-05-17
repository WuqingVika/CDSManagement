package com.cds.common;

import java.time.chrono.IsoChronology;
import java.util.Arrays;

/**
 * ���������֤���ַ���
 * 
 * @author PengChan
 *
 */
public class SecurityCode {
	/**
	 * ��֤����Ѷȼ���
	 */
	public enum SecurityCodeLevel {
		Simple, Medium, Hard
	};

	
	/**
	 * ����Ĭ�ϵ���֤��
	 * @return
	 */
	public static String getSecturityCode(){
		return getSecutrityCode(4, SecurityCodeLevel.Medium, false);
	}
	
	
	/**
	 * 
	 * �������ⳤ�ȵ���֤��
	 * @param length ����
	 * @param level �Ѷȼ���
	 * @param isCanRepeat �Ƿ��ܹ������ظ����ַ�
	 * @return string ��֤��
	 */
	public static String getSecutrityCode(int length, SecurityCodeLevel level, boolean isCanRepeat) {
		// �����ȡlen���ַ�
		int len = length;
		// �ַ�����
		char[] codes = { '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
				'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		// ���ݲ�ͬ�ѶȽ�ȡ�ַ�����
		if (level == SecurityCodeLevel.Simple) {
			codes = Arrays.copyOfRange(codes, 0, 9);
		} else if (level == SecurityCodeLevel.Medium) {
			codes = Arrays.copyOfRange(codes, 0, 33);
		}

		// �ַ����ͳ���
		int n = codes.length;
		// �׳�����ʱ�쳣
		if (len > n && isCanRepeat == false) {
			throw new RuntimeException(String.format(
					"����SecurityCode.getSecurityCode(%1$s,%2$s,%3$s)�����쳣����isCanRepeatΪ%3$sʱ���������%1$s���ܴ���%4$s", len,
					level, isCanRepeat, n));
		}
		//��ų�ȡ�������ַ�
		char[] result = new char[len];
		//�ж��ܷ�����ظ����ַ�
		if(isCanRepeat){
			for(int i = 0;i<result.length;i++){
				int r = (int)(Math.random()*n);
				//����result
				result[i] = codes[r];
			}
		}else{
			for(int i=0;i<result.length;i++){
				int r = (int)(Math.random()*n);
				result[i] = codes[r];
				//Ϊȷ����ͬ���ַ��������һ���ַ��ŵ���λ���ϣ����ѳ��ȼ�ȥһ
				codes[r] = codes[n-1];
				n--;
			}
		}
		return String.valueOf(result);		
	}
}
