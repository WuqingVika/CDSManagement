package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.IClassesDao;

import com.cds.entity.Classes;

public class ClassesDao implements IClassesDao {
	//×¢Èë
		private SessionFactory sessionFactory;
		public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}
	@Override
	public void save(Classes classes) {
		sessionFactory.getCurrentSession().save(classes);

	}

	@Override
	public void update(Classes classes) {
		sessionFactory.getCurrentSession().update(classes);
	}

	@Override
	public void delete(Classes classes) {
		sessionFactory.getCurrentSession().delete(classes);

	}

	@Override
	public Classes find(Classes classes) {
		return (Classes)sessionFactory.getCurrentSession().get(Classes.class, classes.getClassId());

	}

	@Override
	public List<Classes> findAll() {
		@SuppressWarnings("unchecked")
		List<Classes> list=sessionFactory
				.getCurrentSession().createCriteria(Classes.class).list();
		return list;
	}
	@Override
	public Classes findClassByClassName_z(String className) {
		return (Classes) sessionFactory.getCurrentSession().createQuery("from Classes where className=:className")
				.setParameter("className", className)
				.uniqueResult();
	}
	@Override
	public List findClassByCollegeId_z(int collegeId) {
		return sessionFactory.getCurrentSession().createSQLQuery("SELECT classId, className, majorName FROM classes, major, college WHERE classes.`majorId` = major.`majorId` AND college.`collegeId` = major.`collegeId` AND college.collegeId = " + collegeId)
				.list();
	}

}
