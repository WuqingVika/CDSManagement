package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.ISelfevaluationDao;
import com.cds.entity.Cddesigntopics;
import com.cds.entity.Selfevaluation;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;

/**
 * 自我评价数据操作
 * @author PengChan
 *
 */
public class SelfevaluationDao implements ISelfevaluationDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void save(Selfevaluation selfevaluation) {
		 sessionFactory.getCurrentSession().save(selfevaluation);
	}

	@Override
	public void update(Selfevaluation selfevaluation) {
		 sessionFactory.getCurrentSession().update(selfevaluation);
	}

	@Override
	public void delete(Selfevaluation selfevaluation) {
		 sessionFactory.getCurrentSession().delete(selfevaluation);
	}

	@Override
	public Selfevaluation find(Selfevaluation selfevaluation) {
		Selfevaluation results = new Selfevaluation();
		results = (Selfevaluation) sessionFactory.getCurrentSession().get(Selfevaluation.class,selfevaluation);
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Selfevaluation> findAll() {
		List<Selfevaluation> results = sessionFactory.getCurrentSession().createCriteria(Selfevaluation.class).list();		
		return results;
	}
	
	@Override
	public Selfevaluation findSelEval_c(Cddesigntopics cddesigntopics, Student student) {
		String hql = "from Selfevaluation s where s.student.stuId=:stuId and s.cddesigntopics.cddesignTopId=:cddesignTopicId";
		Selfevaluation selfevaluation =  (Selfevaluation) sessionFactory.getCurrentSession().createQuery(hql).setParameter("stuId", student.getStuId()).setParameter("cddesignTopicId", cddesigntopics.getCddesignTopId()).uniqueResult();		
		return selfevaluation;
	}

	@Override
	public List findStuGroupEval_c(Cddesigntopics cddesigntopics, Studentgroup studentgroup) {
		String hql = "select student.studentId,student.stuName,"
				+ " selfevaluation.selfScore,selfevaluation.selfDescribtion"
				+ " from student,stugroupmembers,studentgroup,cddesigntopics,selfevaluation"
				+ " where student.stuId = stugroupmembers.stuId"
				+ " and stugroupmembers.stuGroupId = studentgroup.stuGroupId"
				+ " and studentgroup.cDDesignTopId = cddesigntopics.cDDesignTopId"
				+ " and cddesigntopics.cDDesignTopId = selfevaluation.cDDesignTopId"
				+ " and selfevaluation.stuId = student.stuId"
				+ " and studentgroup.stuGroupId ="+studentgroup.getStuGroupId()
				+ " and cddesigntopics.cDDesignTopId ="+cddesigntopics.getCddesignTopId();
		return sessionFactory.getCurrentSession().createSQLQuery(hql).list();
	}

}
