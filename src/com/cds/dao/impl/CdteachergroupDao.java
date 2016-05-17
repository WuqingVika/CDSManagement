package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.ICdteachergroupDao;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Teacher;

public class CdteachergroupDao implements ICdteachergroupDao {
	//×¢Èë
		private SessionFactory sessionFactory;
		public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
		}
		
	@Override
	public void save(Cdteachergroup cdteachergroup) {
		sessionFactory.getCurrentSession().save(cdteachergroup);
	}

	@Override
	public void update(Cdteachergroup cdteachergroup) {
		sessionFactory.getCurrentSession().update(cdteachergroup);
	}

	@Override
	public void delete(Cdteachergroup cdteachergroup) {
		sessionFactory.getCurrentSession().delete(cdteachergroup);
	}

	@Override
	public Cdteachergroup find(Cdteachergroup cdteachergroup) {
		return (Cdteachergroup)sessionFactory.getCurrentSession().get(Cdteachergroup.class, cdteachergroup.getCdteacherGroupId());

	}

	@Override
	public List<Cdteachergroup> findAll() {
		@SuppressWarnings("unchecked")
		List<Cdteachergroup> list=sessionFactory
				.getCurrentSession().createCriteria(Cdteachergroup.class).list();
		return list;
	}

	@Override
	public void addTeacherMember_z(int teacherId, int cdTeacherGroupId) {
		sessionFactory.getCurrentSession().createSQLQuery("insert into cdgroupteachers values (" + cdTeacherGroupId + "," + teacherId + ")")
		.executeUpdate();
	}

	@Override
	public void deleteMemberInfoByCdTeacherGroupId(int teacherGroupId) {
		sessionFactory.getCurrentSession().createSQLQuery("delete from cdgroupteachers where cDTeacherGroupId = " + teacherGroupId)
		.executeUpdate();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List findTeacherByTG_c(Cdteachergroup cdteachergroup) {
		String hql = "select t.teacherId,t.teacherName " + "from cdteachergroup tg,cdgroupteachers gt,"
				+ "teacher t where tg.cDTeacherGroupId = " + "gt.cDTeacherGroupId and gt.teacherId = t.teacherId "
				+ "and tg.cDTeacherGroupId =" + cdteachergroup.getCdteacherGroupId();
		List result = sessionFactory.getCurrentSession().createSQLQuery(hql).list();
		return result;
	}

	@Override
	public void addThGroupMember_c(Teacher teacher, Cdteachergroup cdteachergroup) {
		String hql = "insert into cdgroupteachers(teacherId,cDTeacherGroupId) values(" + teacher.getTeacherId() + ","
				+ cdteachergroup.getCdteacherGroupId() + ")";
		sessionFactory.getCurrentSession().createSQLQuery(hql).executeUpdate();
	}

	@Override
	public List findTeacherByTeachGroup_c(Cdteachergroup cdteachergroup) {
		String hql = "select teacher.teacherId,teacher.teacherWorkId,teacher.teacherName"
				+ " from teacher,cdgroupteachers,cdteachergroup"
				+ " where teacher.teacherId = cdgroupteachers.teacherId"
				+ " and cdgroupteachers.cDTeacherGroupId = cdteachergroup.cDTeacherGroupId"
				+ " and cdteachergroup.cDTeacherGroupId = " + cdteachergroup.getCdteacherGroupId()
				+ " and teacher.teacherId <> " + cdteachergroup.getTeacher().getTeacherId();
		return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
	}

	@Override
	public void deleteTeachGroupMem_c(Cdteachergroup cdteachergroup, Teacher teacher) {
		String hql = "delete from cdgroupteachers where cdgroupteachers.teacherId = " + teacher.getTeacherId()
				+ " and cdgroupteachers.cDTeacherGroupId =" + cdteachergroup.getCdteacherGroupId();
		try {
			sessionFactory.getCurrentSession().createSQLQuery(hql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
