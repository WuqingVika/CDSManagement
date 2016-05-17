package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.ICollegeDao;
import com.cds.entity.Cdplan;
import com.cds.entity.Classes;
import com.cds.entity.College;
import com.cds.entity.Teacher;

public class CollegeDao implements ICollegeDao {
	//×¢Èë
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Override
	public void save(College college) {
		sessionFactory.getCurrentSession().save(college);

	}

	@Override
	public void update(College college) {
		sessionFactory.getCurrentSession().update(college);
	}

	@Override
	public void delete(College college) {
		sessionFactory.getCurrentSession().delete(college);

	}

	@Override
	public College find(College college) {
		return (College)sessionFactory.getCurrentSession().get(College.class, college.getCollegeId());

	}

	@Override
	public List<College> findAll() {
		@SuppressWarnings("unchecked")
		List<College> list=sessionFactory
				.getCurrentSession().createCriteria(College.class).list();
		return list;
	}

	@Override
	public List<College> findCollegeByName_z(String collegeName) {
		return sessionFactory.getCurrentSession().createQuery("from College where collegeName=:collegeName")
			.setParameter("collegeName", collegeName)
			.list();
	}
	@Override
	public List<Classes> findClassesByCollegeId_z(int collegeId) {
		return sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM classes, major, college WHERE classes.`majorId` = major.`majorId` AND college.`collegeId` = major.`collegeId` AND college.`collegeId` = " + collegeId)
				.addEntity(Classes.class)
				.list();
	}
	@Override
	public List findStudentsByCollegeId(int collegeId) {
		return sessionFactory.getCurrentSession().createSQLQuery("SELECT account.accountId, studentId, stuName, className, majorName FROM account, student, college, classes, major WHERE account.`accId` = student.`accId` AND student.`classId` = classes.`classId` AND classes.`majorId` = major.`majorId` AND college.`collegeId` = major.`collegeId` AND college.`collegeId` = " + collegeId)
				.list();
	}
	@Override
	public List<Cdplan> findCdplansByCollegeId(int collegeId) {
		return sessionFactory.getCurrentSession().createSQLQuery("SELECT * FROM cdplan, major, college, term WHERE cdplan.`majorId` = major.`majorId` AND major.`collegeId` = college.`collegeId` AND cdplan.`termId` = term.`termId` AND college.`collegeId` = " + collegeId).addEntity(Cdplan.class)
				.list();
	}
	
	@Override
	public List findAllTeacher_c(Teacher teacher) {
		String hql = "select * from teacher where teacher.collegeId ="+teacher.getCollege().getCollegeId()+" and teacher.teacherId <>"+teacher.getTeacherId();		
		return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
	}
}
