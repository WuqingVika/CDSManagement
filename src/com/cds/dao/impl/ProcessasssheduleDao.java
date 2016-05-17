package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.IProcessasssheduleDao;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Processassshedule;
import com.cds.entity.Student;
import com.cds.entity.Teacher;

public class ProcessasssheduleDao implements IProcessasssheduleDao {
	//×¢Èë
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void save(Processassshedule processassshedule) {
		sessionFactory.getCurrentSession().save(processassshedule);
	}

	@Override
	public void update(Processassshedule processassshedule) {
		sessionFactory.getCurrentSession().update(processassshedule);
	}

	@Override
	public void delete(Processassshedule processassshedule) {
		sessionFactory.getCurrentSession().delete(processassshedule);
	}

	@Override
	public Processassshedule find(Processassshedule processassshedule) {
		return (Processassshedule) sessionFactory.getCurrentSession().get(Processassshedule.class, processassshedule.getProcessAssShId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Processassshedule> findAll() {
		return sessionFactory.getCurrentSession().createCriteria(Processassshedule.class).list();
	}
	
	@Override
	public List findAllProcessShe_c(Teacher teacher, Cdteachergroup cdteachergroup) {
		String hql = "select processassshedule.ProcessName,processassshedule.ProcessDescribtion,"
				+ " processassshedule.startTime,processassshedule.endTime,processassshedule.processAssShId"
				+ " from processassshedule" + " where processassshedule.cDTeacherGroupId ="
				+ cdteachergroup.getCdteacherGroupId() + " and processassshedule.teacherId = " + teacher.getTeacherId();
		return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
	}

	@Override
	public List findAllProcSheExcep_c(Teacher teacher, Cdteachergroup cdteachergroup, Student student) {
		String hql = "select processassshedule.ProcessName,processassshedule.ProcessDescribtion,"
				+ " processassshedule.startTime,processassshedule.endTime,processassshedule.processAssShId"
				+ " from processassshedule" + " where processassshedule.cDTeacherGroupId ="
				+ cdteachergroup.getCdteacherGroupId() + " and processassshedule.teacherId = " + teacher.getTeacherId()
				+ " and processassshedule.processAssShId not in(" + "select processassshedule.processAssShId"
				+ " from processdocument,processassshedule"
				+ " where processdocument.processAssShId = processassshedule.processAssShId"
				+ " and processdocument.stuId = " + student.getStuId() + ")";
		return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
	}
}
