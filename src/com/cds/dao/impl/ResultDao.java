package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.IResultDao;

/**
 * 分页查询的实现类
 * @author PengChan
 *
 */
public class ResultDao implements IResultDao {
	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 通过hql语句以及offset和pageSize参数查询所有的数据
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
	 * 通过hql语句查询记录的所有的条数
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
