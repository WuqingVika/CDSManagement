package com.cds.dao;

import java.util.List;

/**
 * ʵ�ַ�ҳ��Dao��
 * 
 * @author PengChan
 *
 */
public interface IResultDao {
	/**
	 * ͨ��hql����Լ�offset������pagesize������ѯ���
	 * 
	 * @param hql
	 *            hql���
	 * 
	 * @param offset
	 *            ���ĸ�λ�ÿ�ʼ��ѯ
	 * 
	 * @param pageSize
	 *            �趨ÿһ��ҳ��Ĵ�С
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryByPage(String hql, int offset, int pageSize);

	/**
	 * ��ȡ��ѯ����������
	 * 
	 * @param hql
	 * @return
	 */
	public int getAllRowCount(String hql);
	/**
	 * �����÷�ҳ����hql��ѯ
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryBysql(String sql);
}
