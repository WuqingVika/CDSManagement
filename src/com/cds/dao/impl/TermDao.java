package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.ITermDao;
import com.cds.entity.Term;

/**
 * 学期访问的数据操作
 * 
 * @author PengChan
 *
 */
public class TermDao implements ITermDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(Term term) {
		sessionFactory.getCurrentSession().save(term);
	}

	@Override
	public void update(Term term) {
		sessionFactory.getCurrentSession().update(term);
	}

	@Override
	public void delete(Term term) {
		sessionFactory.getCurrentSession().delete(term);
	}

	@Override
	public Term find(Term term) {
		Term results = new Term();
		results = (Term) sessionFactory.getCurrentSession().get(Term.class, term.getTermId());
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Term> findAll() {
		List<Term> results = sessionFactory.getCurrentSession().createCriteria(Term.class).list();
		return results;
	}

	@Override
	public List<Term> findTermByName_z(String termName) {
		return sessionFactory.getCurrentSession().createQuery("from Term where termName=:termName")
				.setParameter("termName", termName)
				.list();
	}

}
