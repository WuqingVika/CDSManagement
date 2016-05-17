package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.IEvalstandardsDao;
import com.cds.entity.Evalstandards;

public class EvalstandardDao implements IEvalstandardsDao {
	//×¢Èë
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Override
	public void save(Evalstandards evalstandards) {
		sessionFactory.getCurrentSession().save(evalstandards);
	}

	@Override
	public void update(Evalstandards evalstandards) {
		sessionFactory.getCurrentSession().update(evalstandards);
	}

	@Override
	public void delete(Evalstandards evalstandards) {
		sessionFactory.getCurrentSession().delete(evalstandards);
	}

	@Override
	public Evalstandards find(Evalstandards evalstandards) {
		return (Evalstandards) sessionFactory.getCurrentSession().get(Evalstandards.class, evalstandards.getEvalStandId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Evalstandards> findAll() {
		return sessionFactory.getCurrentSession().createCriteria(Evalstandards.class).list();
	}

}
