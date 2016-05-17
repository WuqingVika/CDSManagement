package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.IReplyrecordsDao;
import com.cds.entity.Replyrecords;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;

/**
 * 答辩记录数据库操作
 * 
 * @author PengChan
 *
 */
public class ReplyrecordsDao implements IReplyrecordsDao {
	
	//注入sessionfactory
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(Replyrecords replyrecords) {
		sessionFactory.getCurrentSession().save(replyrecords);

	}

	@Override
	public void update(Replyrecords replyrecords) {
		sessionFactory.getCurrentSession().update(replyrecords);

	}

	@Override
	public void delete(Replyrecords replyrecords) {
		sessionFactory.getCurrentSession().delete(replyrecords);

	}

	@Override
	public Replyrecords find(Replyrecords replyrecords) {
		Replyrecords results = new Replyrecords();
		results = (Replyrecords) sessionFactory.getCurrentSession().load(Replyrecords.class, replyrecords.getReplyRecordId());
		return results;
	}
 
	@SuppressWarnings("unchecked")
	@Override
	public List<Replyrecords> findAll() {
		List<Replyrecords> results = sessionFactory.getCurrentSession().createCriteria(Replyrecords.class).list();		
		return results;
	}
	
	 
	@Override
	public List findStudentReplyRecords_c(Student student, Studentgroup studentgroup) {
		String hql = "select replyrecords.question,teacher.teacherName,replyrecords.answers,replyrecords.answerTime"
				+ " from student,replyrecords,replyplan,studentgroup,teacher"
				+ " where student.stuId = replyrecords.stuId"
				+ " and replyrecords.replyPlanId = replyplan.replyPlanId"
				+ " and replyplan.stuGroupId = studentgroup.stuGroupId"
				+ " and replyrecords.teacherId = teacher.teacherId"
				+ " and student.stuId ="+student.getStuId()
				+ " and studentgroup.stuGroupId ="+studentgroup.getStuGroupId();
		return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
	}

}
