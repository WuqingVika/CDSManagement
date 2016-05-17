package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.IReplyplanDao;
import com.cds.entity.Replyplan;

public class ReplyplanDao implements IReplyplanDao {
	//×¢Èë
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void save(Replyplan replyplan) {
		sessionFactory.getCurrentSession().save(replyplan);
	}

	@Override
	public void update(Replyplan replyplan) {
		sessionFactory.getCurrentSession().update(replyplan);
	}

	@Override
	public void delete(Replyplan replyplan) {
		sessionFactory.getCurrentSession().delete(replyplan);
	}

	@Override
	public Replyplan find(Replyplan replyplan) {
		return (Replyplan) sessionFactory.getCurrentSession().get(Replyplan.class, replyplan.getReplyPlanId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Replyplan> findAll() {
		return sessionFactory.getCurrentSession().createCriteria(Replyplan.class).list();
	}
	
	@Override
	public List findAllReplyGrop_c() {
		String hql = "select * from replygroup";
		return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
	}

}
