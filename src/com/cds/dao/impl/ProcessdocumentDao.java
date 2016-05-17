package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.IProcessdocumentDao;
import com.cds.entity.Cddesigntopics;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Processdocument;
import com.cds.entity.Student;
import com.cds.entity.Teacher;

public class ProcessdocumentDao implements IProcessdocumentDao {
	//×¢Èë
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void save(Processdocument processdocument) {
		sessionFactory.getCurrentSession().save(processdocument);
	}

	@Override
	public void update(Processdocument processdocument) {
		sessionFactory.getCurrentSession().update(processdocument);
	}

	@Override
	public void delete(Processdocument processdocument) {
		sessionFactory.getCurrentSession().delete(processdocument);
	}

	@Override
	public Processdocument find(Processdocument processdocument) {
		return (Processdocument) sessionFactory.getCurrentSession().get(Processdocument.class, processdocument.getProcessDocId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Processdocument> findAll() {
		return sessionFactory.getCurrentSession().createCriteria(Processdocument.class).list();
	}
	
	@Override
	public List findAllSubmitted_c(Teacher teacher, Cdteachergroup cdteachergroup, Student student) {
		String hql = "select processdocument.processDocId,processdocument.documentName,"
				+ " processdocument.upperTime,processdocument.score,"
				+ " processdocument.tutorOpinion,processdocument.documentUrl"
				+ " from student,processdocument,processassshedule,teacher,cdteachergroup"
				+ " where student.stuId = processdocument.stuId"
				+ " and processdocument.processAssShId = processassshedule.processAssShId"
				+ " and processassshedule.teacherId = teacher.teacherId"
				+ " and processassshedule.cDTeacherGroupId = cdteachergroup.cDTeacherGroupId"
				+ " and teacher.teacherId ="+teacher.getTeacherId()
				+ " and cdteachergroup.cDTeacherGroupId ="+cdteachergroup.getCdteacherGroupId()
				+ " and student.stuId = "+student.getStuId();
		List result = sessionFactory.getCurrentSession().createSQLQuery(hql).list();
		return result;
	}

	@Override
	public List findAllOtherGroupMemb_c(Teacher teacher, Cdteachergroup cdteachergroup, Student student,Cddesigntopics cddesigntopics) {
		String hql = "select processdocument.processDocId,processdocument.documentName,student.stuName,"
				+ " processdocument.upperTime,processdocument.documentUrl"
				+ " from student,processdocument,processassshedule,teacher,cdteachergroup"
				+ " where student.stuId = processdocument.stuId"
				+ " and processdocument.processAssShId = processassshedule.processAssShId"
				+ " and processassshedule.teacherId = teacher.teacherId"
				+ " and processassshedule.cDTeacherGroupId = cdteachergroup.cDTeacherGroupId"
				+ " and teacher.teacherId = "+teacher.getTeacherId()
				+ " and cdteachergroup.cDTeacherGroupId ="+cdteachergroup.getCdteacherGroupId()
				+ " and student.stuId in("
				+ "	select student.stuId "
				+ " from student,stugroupmembers,studentgroup"
				+ "	where student.stuId = stugroupmembers.stuId"
				+ " and stugroupmembers.stuGroupId = studentgroup.stuGroupId"
				+ "	and studentgroup.stuGroupId = ("
				+ " select studentgroup.stuGroupId"
				+ "	from student,stugroupmembers,studentgroup,cddesigntopics"
				+ "	where student.stuId = stugroupmembers.stuId"
				+ "	and stugroupmembers.stuGroupId = studentgroup.stuGroupId"
				+ "	and studentgroup.cDDesignTopId = cddesigntopics.cDDesignTopId"
				+ "	and student.stuId ="+student.getStuId()
				+ "	and cddesigntopics.cDDesignTopId ="+cddesigntopics.getCddesignTopId()
				+ ") and student.stuId <> "+student.getStuId()
				+ " )";
		return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
	}
}
