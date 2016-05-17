package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.ICddesigntopicsDao;
import com.cds.entity.Cddesigntopics;
import com.cds.entity.Cdplan;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Classes;
import com.cds.entity.Student;

public class CddesigntopicsDao implements ICddesigntopicsDao {
	//×¢Èë
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		}
			
	@Override
	public void save(Cddesigntopics cddesigntopics) {
		sessionFactory.getCurrentSession().save(cddesigntopics);

	}

	@Override
	public void update(Cddesigntopics cddesigntopics) {
		sessionFactory.getCurrentSession().update(cddesigntopics);
	}

	@Override
	public void delete(Cddesigntopics cddesigntopics) {
		sessionFactory.getCurrentSession().delete(cddesigntopics);
	}

	@Override
	public Cddesigntopics find(Cddesigntopics cddesigntopics) {
		return (Cddesigntopics)sessionFactory.getCurrentSession().get(Cddesigntopics.class, cddesigntopics.getCddesignTopId());

	}

	@Override
	public List<Cddesigntopics> findAll() {
	
		@SuppressWarnings("unchecked")
		List<Cddesigntopics> list=sessionFactory
				.getCurrentSession().createCriteria(Cddesigntopics.class).list();
		return list;
	}
	
	@Override
	public List findTopicByCdGroupId_c(Cdteachergroup cdteachergroup) {
		String hql = "select ct.cDDesignTopId,ct.topics,ct.topicsDescribtion,"
				+ "t.teacherName from cddesigntopics ct,teacher t,cdgroupteachers cgt,cdteachergroup tg"
				+ " where ct.cDTeacherGroupId = cgt.cDTeacherGroupId"
				+ " and ct.teacherId = t.teacherId"
				+ " and t.teacherId = cgt.teacherId"
				+ " and cgt.cDTeacherGroupId = tg.cDTeacherGroupId and ct.isSelfChoosed=0"
				+ " and ct.cDTeacherGroupId ="+cdteachergroup.getCdteacherGroupId();
		return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
	}

	@Override
	public List findAllSelfChoosedCD_c(Student student) {
		String hql = "select distinct cddesigntopics.cDDesignTopId,studentgroup.stuGroupId,"
				+ "cdplan.cDPlanId,cddesigntopics.topics,"
				+ " cddesigntopics.topicsDescribtion,cdchoosingrecord.submitTime,"
				+ " cdchoosingrecord.isPassed,teacher.teacherName,cdchoosingrecord.tutorOpinion"
				+ " from cdplan,cdteachergroup,cdgroupteachers,teacher,"
				+ " cddesigntopics,cdchoosingrecord,"
				+ " studentgroup,stugroupmembers,student"
				+ " where cdplan.cDPlanId = cdteachergroup.cDPlanId"
				+ " and cdteachergroup.cDTeacherGroupId = cdgroupteachers.cDTeacherGroupId"
				+ " and cdteachergroup.teacherId = teacher.teacherId"
				+ " and cdteachergroup.cDTeacherGroupId = cddesigntopics.cDTeacherGroupId"
				+ " and cddesigntopics.cDDesignTopId = cdchoosingrecord.cDDesignTopId"
				+ " and studentgroup.cDDesignTopId = cddesigntopics.cDDesignTopId"
				+ " and studentgroup.stuGroupId = stugroupmembers.stuGroupId"
				+ " and stugroupmembers.stuId = student.stuId"
				+ " and cdplan.isCurrent = 1"
				+ " and student.stuId ="+student.getStuId();
		return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
	}

	@Override
	public List findAllChoosedThCD_c(Student student) {
		String hql = "select distinct cddesigntopics.cDDesignTopId,"
				+ " cdplan.cDPlanId,studentgroup.stuGroupId,cddesigntopics.topics,"
				+ "cddesigntopics.topicsDescribtion,teacher.teacherName"
				+ " from cdplan,cdteachergroup,cdgroupteachers,teacher,"
				+ " cddesigntopics, "
				+ " studentgroup,stugroupmembers,student"
				+ " where cdplan.cDPlanId = cdteachergroup.cDPlanId"
				+ " and cdteachergroup.cDTeacherGroupId = cdgroupteachers.cDTeacherGroupId"
				+ " and cdteachergroup.teacherId = teacher.teacherId"
				+ " and cdteachergroup.cDTeacherGroupId = cddesigntopics.cDTeacherGroupId"
				+ " and studentgroup.cDDesignTopId = cddesigntopics.cDDesignTopId"
				+ " and studentgroup.stuGroupId = stugroupmembers.stuGroupId"
				+ " and stugroupmembers.stuId = student.stuId"
				+ " and cddesigntopics.isSelfChoosed = 0"
				+ " and cdplan.isCurrent = 1"
				+ " and student.stuId ="+student.getStuId();
		return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
	}
	 
	@Override
	public List findAllChoosedCD_c(Student student) {
		String hql = "select distinct cddesigntopics.cDDesignTopId,"
				+ " cdplan.cDPlanId,studentgroup.stuGroupId,cddesigntopics.topics,"
				+ " cddesigntopics.topicsDescribtion,teacher.teacherName"
				+ " from cdplan,cdteachergroup,cdgroupteachers,teacher,"
				+ " cddesigntopics,"
				+ " studentgroup,stugroupmembers,student"
				+ " where cdplan.cDPlanId = cdteachergroup.cDPlanId"
				+ " and cdteachergroup.cDTeacherGroupId = cdgroupteachers.cDTeacherGroupId"
				+ " and cdteachergroup.teacherId = teacher.teacherId"
				+ " and cdteachergroup.cDTeacherGroupId = cddesigntopics.cDTeacherGroupId"
				+ " and studentgroup.cDDesignTopId = cddesigntopics.cDDesignTopId"
				+ " and studentgroup.stuGroupId = stugroupmembers.stuGroupId"
				+ " and stugroupmembers.stuId = student.stuId"
				+ " and cdplan.isCurrent = 1"
				+ " and student.stuId ="+student.getStuId();
		return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
	}

	@Override
	public List findCDByClassAndCDPlan_c(Classes classes, Cdplan cdplan) {
	    String hql = "select cddesigntopics.cDDesignTopId,cddesigntopics.topics,student.stuName,studentgroup.stuGroupId"
	    		+ " from cdplan,cdteachergroup,cddesigntopics,studentgroup,student,classes"
	    		+ " where cdplan.cDPlanId = cdteachergroup.cDPlanId"
	    		+ " and cdteachergroup.cDTeacherGroupId = cddesigntopics.cDTeacherGroupId"
	    		+ " and studentgroup.cDDesignTopId = cddesigntopics.cDDesignTopId"
	    		+ " and studentgroup.stuId = student.stuId"
	    		+ " and student.classId = classes.classId"
	    		+ " and classes.classId = "+classes.getClassId()
	    		+ " and cdplan.cDPlanId = "+cdplan.getCdplanId();
		return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
	}

	@Override
	public List findCdDesignTopic_c(Student student) {
		String hql = "select distinct "
				+ " cddesigntopics.cDDesignTopId,cddesigntopics.topics,teacher.teacherName,teacher.teacherId,cdteachergroup.cDTeacherGroupId"
				+ " from student,stugroupmembers,studentgroup,"
				+ " cddesigntopics,cdteachergroup,teacher,cdplan"
				+ " where student.stuId = stugroupmembers.stuId"
				+ " and stugroupmembers.stuGroupId = studentgroup.stuGroupId"
				+ " and studentgroup.cDDesignTopId = cddesigntopics.cDDesignTopId"
				+ " and cddesigntopics.cDTeacherGroupId = cdteachergroup.cDTeacherGroupId"
				+ " and cddesigntopics.teacherId = teacher.teacherId"
				+ " and cdplan.cDPlanId = cdteachergroup.cDPlanId"
				+ " and cdplan.isCurrent = 1"
				+ " and student.stuId ="+student.getStuId();
		return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
	}

}
