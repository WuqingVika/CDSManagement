package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.ICdplanDao;
import com.cds.entity.Cdplan;
import com.cds.entity.Teacher;

public class CdplanDao implements ICdplanDao {
	//注入
		private SessionFactory sessionFactory;
		public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}
		
	@Override
	public void save(Cdplan cdplan) {
		sessionFactory.getCurrentSession().save(cdplan);
	}

	@Override
	public void update(Cdplan cdplan) {
		sessionFactory.getCurrentSession().update(cdplan);

	}

	@Override
	public void delete(Cdplan cdplan) {
		sessionFactory.getCurrentSession().delete(cdplan);

	}

	@Override
	public Cdplan find(Cdplan cdplan) {
		return (Cdplan)sessionFactory.getCurrentSession().get(Cdplan.class, cdplan.getCdplanId());

	}

	@Override
	public List<Cdplan> findAll() {
		
		@SuppressWarnings("unchecked")
		List<Cdplan> list=sessionFactory
				.getCurrentSession().createCriteria(Cdplan.class).list();
		return list;
	}
	
	@Override
	public List findAllTeachCdPlan_c(Teacher teacher) {
		String hql = "select distinct cdplan.cDPlanId,cdplan.cDPlanName"
				+ " from cdplan,cdteachergroup"
				+ " where cdplan.cDPlanId = cdteachergroup.cDPlanId"
				+ " and cdplan.isCurrent = 1"
				+ " and cdteachergroup.teacherId ="+teacher.getTeacherId();
		return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
	}

	@Override
	public List findAllCdPlan_c(Teacher teacher,Cdplan cdplan) {
		String hql = "select cdplan.cDPlanName,cddesigntopics.topics,teacher.teacherName,"
				+ " student.stuName,major.majorName,term.termName,studentgroup.stuGroupId "
				+ " from cdplan,term,major,cdteachergroup,cddesigntopics,teacher,studentgroup,student"
				+ " where cdplan.termId = term.termId"
				+ " and cdplan.majorId = major.majorId"
				+ " and cdplan.cDPlanId = cdteachergroup.cDPlanId"
				+ " and cdteachergroup.cDTeacherGroupId = cddesigntopics.cDTeacherGroupId"
				+ " and cddesigntopics.teacherId = teacher.teacherId"
				+ " and cddesigntopics.cDDesignTopId = studentgroup.cDDesignTopId"
				+ " and studentgroup.stuId = student.stuId"
				+ " and cdplan.isCurrent = 1"
				+ " and cdteachergroup.teacherId = "+teacher.getTeacherId()
				+ " and studentgroup.stuGroupId not in("
				+ "	select studentgroup.stuGroupId"
				+ "	from cdplan,term,major,cdteachergroup,cddesigntopics,teacher,studentgroup,student,replyplan"
				+ "	where cdplan.termId = term.termId"
				+ "	and cdplan.majorId = major.majorId"
				+ "	and cdplan.cDPlanId = cdteachergroup.cDPlanId"
				+ "	and cdteachergroup.cDTeacherGroupId = cddesigntopics.cDTeacherGroupId"
				+ "	and cddesigntopics.teacherId = teacher.teacherId"
				+ "	and cddesigntopics.cDDesignTopId = studentgroup.cDDesignTopId"
				+ "	and studentgroup.stuId = student.stuId"
				+ "	and replyplan.stuGroupId = studentgroup.stuGroupId"
				+ "	and cdplan.isCurrent = 1"
				+ "	and cdteachergroup.teacherId ="+teacher.getTeacherId()
				+ " )";	 
		// 如果课程设计计划为空
		if(cdplan == null){
			return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
		}else{
			hql += " and cdplan.cDPlanId ="+cdplan.getCdplanId();
			return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
		}		 
	}

	@Override
	public List findAllReplyedCd_c(Teacher teacher, Cdplan cdplan) {
		String hql = "select cdplan.cDPlanName,cddesigntopics.topics,teacher.teacherName,"
				+ " student.stuName,major.majorName,term.termName,studentgroup.stuGroupId,replyPlan.replyPlanId,replyplan.replyGroupId"
				+ " from cdplan,term,major,cdteachergroup,cddesigntopics,teacher,studentgroup,student,replyplan"
				+ " where cdplan.termId = term.termId"
				+ " and cdplan.majorId = major.majorId"
				+ " and cdplan.cDPlanId = cdteachergroup.cDPlanId"
				+ " and cdteachergroup.cDTeacherGroupId = cddesigntopics.cDTeacherGroupId"
				+ " and cddesigntopics.teacherId = teacher.teacherId"
				+ " and cddesigntopics.cDDesignTopId = studentgroup.cDDesignTopId"
				+ " and studentgroup.stuId = student.stuId"
				+ " and replyplan.stuGroupId = studentgroup.stuGroupId"
				+ " and cdplan.isCurrent = 1"
				+ " and cdteachergroup.teacherId ="+teacher.getTeacherId();
		// 如果cdplan为空
		if(cdplan == null){
			return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
		}else{
			hql += " and cdplan.cDPlanId ="+cdplan.getCdplanId();
			return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
		}		 
	}
}
