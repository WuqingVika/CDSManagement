package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.IReplygroupDao;
import com.cds.entity.Replygroup;
import com.cds.entity.Teacher;

public class ReplygroupDao implements IReplygroupDao {
	//×¢Èë
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void save(Replygroup replygroup) {
		sessionFactory.getCurrentSession().save(replygroup);
	}

	@Override
	public void update(Replygroup replygroup) {
		sessionFactory.getCurrentSession().update(replygroup);
	}

	@Override
	public void delete(Replygroup replygroup) {
		sessionFactory.getCurrentSession().delete(replygroup);
	}

	@Override
	public Replygroup find(Replygroup replygroup) {
		return (Replygroup) sessionFactory.getCurrentSession().get(Replygroup.class, replygroup.getReplyGroupId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Replygroup> findAll() {
		return sessionFactory.getCurrentSession().createCriteria(Replygroup.class).list();
	}
	
	@Override
	public List findAllTeachersByRpId_c(Replygroup replygroup) {
		String hql = "select teacher.teacherId,teacher.teacherWorkId,teacher.teacherName"
				+ " from replygroup,replymembers,teacher"
				+ " where replygroup.replyGroupId = replymembers.replyGroupId"
				+ " and replymembers.teacherId = teacher.teacherId"
				+ " and replygroup.replyGroupId = "+replygroup.getReplyGroupId();		
		return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
	}

	@Override
	public void addReplyMembers_c(Teacher teacher, Replygroup replygroup) {
		 String hql = "insert into replymembers values("+replygroup.getReplyGroupId()+","+teacher.getTeacherId()+")";
		 sessionFactory.getCurrentSession().createSQLQuery(hql).executeUpdate();
	}
}
