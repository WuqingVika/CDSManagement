package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.IMajorDao;
import com.cds.entity.Major;

public class MajorDao implements IMajorDao {
	//×¢ÈësessionFactory
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void save(Major major) {
		sessionFactory.getCurrentSession().save(major);
	}

	@Override
	public void update(Major major) {
		sessionFactory.getCurrentSession().update(major);
	}

	@Override
	public void delete(Major major) {
		sessionFactory.getCurrentSession().delete(major);
	}

	@Override
	public Major find(Major major) {
		return (Major) sessionFactory.getCurrentSession().get(Major.class, major.getMajorId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Major> findAll() {
		return sessionFactory.getCurrentSession().createCriteria(Major.class).list();
	}

	@Override
	public Major findMajorByName_z(String majorName) {
		return (Major) sessionFactory.getCurrentSession().createQuery("from Major where majorName=:majorName")
			.setParameter("majorName", majorName)
			.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Major> findMajorsByCollegeId_z(int collegeId) {
		return sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM major WHERE major.`collegeId` = "+collegeId).addEntity(Major.class).list();		 
	}

	@Override
	public List findMajorsByCollegeIdJSON_z(int collegeId) {		 
		return sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM major WHERE major.`collegeId` = "+collegeId).list();	
	}

}
