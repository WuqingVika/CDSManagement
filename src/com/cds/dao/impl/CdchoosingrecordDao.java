package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.ICdchoosingrecordDao;

import com.cds.entity.Cdchoosingrecord;

public class CdchoosingrecordDao implements ICdchoosingrecordDao {
	//×¢Èë
		private SessionFactory sessionFactory;
		public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}
		
	@Override
	public void save(Cdchoosingrecord cdchoosingrecord) {
		sessionFactory.getCurrentSession().save(cdchoosingrecord);

	}

	@Override
	public void update(Cdchoosingrecord cdchoosingrecord) {
		sessionFactory.getCurrentSession().update(cdchoosingrecord);

	}

	@Override
	public void delete(Cdchoosingrecord cdchoosingrecord) {
		sessionFactory.getCurrentSession().delete(cdchoosingrecord);

	}

	@Override
	public Cdchoosingrecord find(Cdchoosingrecord cdchoosingrecord) {
		return (Cdchoosingrecord)sessionFactory.getCurrentSession().get(Cdchoosingrecord.class, cdchoosingrecord.getCdchoosingRecId());

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cdchoosingrecord> findAll() {
		// TODO Auto-generated method stub
		List<Cdchoosingrecord> list=sessionFactory
				.getCurrentSession().createCriteria(Cdchoosingrecord.class).list();
		return list;
	}

}
