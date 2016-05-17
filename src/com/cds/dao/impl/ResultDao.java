package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.IResultDao;

/**
 * ��ҳ��ѯ��ʵ����
 * @author PengChan
 *
 */
public class ResultDao implements IResultDao {
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * ͨ��hql����Լ�offset��pageSize������ѯ���е�����
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List queryByPage(String hql, int offset, int pageSize) {
		List results = sessionFactory.getCurrentSession()
				.createSQLQuery(hql)
				.setFirstResult(offset)
				.setMaxResults(pageSize).list();		
		return results;
	}

	/**
	 * ͨ��hql����ѯ��¼�����е�����
	 */
	@Override
	public int getAllRowCount(String hql) {
		int rowCount = sessionFactory.getCurrentSession().createSQLQuery(hql).list().size();
		return rowCount;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List queryBysql(String hql) {
		List results = sessionFactory.getCurrentSession()
				.createSQLQuery(hql)
				
				.list();	
		return results;
	}
	


}
