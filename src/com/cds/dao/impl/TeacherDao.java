package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.ITeacherDao;
import com.cds.entity.Teacher;

/**
 * 教师数据操作
 * 
 * @author PengChan
 *
 */
public class TeacherDao implements ITeacherDao {

	SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(Teacher teacher) {
		sessionFactory.getCurrentSession().save(teacher);
	}

	@Override
	public void update(Teacher teacher) {
		sessionFactory.getCurrentSession().update(teacher);
	}

	@Override
	public void delete(Teacher teacher) {
		sessionFactory.getCurrentSession().delete(teacher);
	}

	@Override
	public Teacher find(Teacher teacher) {
		Teacher results = new Teacher();
		results = (Teacher) sessionFactory.getCurrentSession().get(Teacher.class, teacher.getTeacherId());
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Teacher> findAll() {
		List<Teacher> results = sessionFactory.getCurrentSession().createCriteria(Teacher.class).list();
		return results;
	}

	@Override
	public Teacher findTeacherByAccId_z(int accId) {
		return (Teacher) sessionFactory.getCurrentSession().createQuery("from Teacher where accId=:accId")
			.setParameter("accId", accId)
			.uniqueResult();
	}

	@Override
	public Teacher findTeacherByWorkId_z(String teacherWorkId) {
		return (Teacher) sessionFactory.getCurrentSession().createQuery("from Teacher where teacherWorkId=:teacherWorkId")
				.setParameter("teacherWorkId", teacherWorkId)
				.uniqueResult();
	}

	@Override
	public List findTeachersByCollegeId_z(int collegeId) {
		return sessionFactory.getCurrentSession().createSQLQuery("SELECT accountId, teacherWorkId, teacherName, collegeName FROM account, teacher, college WHERE account.`accId` = teacher.`accId` AND teacher.`collegeId` = college.`collegeId` AND college.`collegeId` = " + collegeId)
				.list();
	}
	
	@Override
	public Teacher findTeacherByTeachWorkId_c(Teacher teacher) {
		String hql = "from Teacher t where t.teacherWorkId = " + teacher.getTeacherWorkId();
		teacher = (Teacher) sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
		return teacher;
	}
}
