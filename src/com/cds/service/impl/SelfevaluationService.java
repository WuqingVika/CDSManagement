package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Cddesigntopics;
import com.cds.entity.Selfevaluation;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;
import com.cds.service.ISelfevaluationService;

/**
 * 自我评价service
 * 
 * @author PengChan
 *
 */
public class SelfevaluationService implements ISelfevaluationService {

	//注入daofactory
	private DaoFactory daoFactory;

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void save(Selfevaluation selfevaluation) {
		 daoFactory.getSelfEvaluationDao().save(selfevaluation);
	}

	@Override
	public void update(Selfevaluation selfevaluation) {
		 daoFactory.getSelfEvaluationDao().update(selfevaluation);
	}

	@Override
	public void delete(Selfevaluation selfevaluation) {
		 daoFactory.getSelfEvaluationDao().delete(selfevaluation);
	}

	@Override
	public Selfevaluation find(Selfevaluation selfevaluation) {		
		return daoFactory.getSelfEvaluationDao().find(selfevaluation);
	}

	@Override
	public List<Selfevaluation> findAll() {		 
		return daoFactory.getSelfEvaluationDao().findAll();
	}
	
	@Override
	public boolean studentHasEvaled_c(Cddesigntopics cddesigntopics, Student student) {		 
		return daoFactory.getSelfEvaluationDao().findSelEval_c(cddesigntopics, student)==null?false:true;
	}

	@Override
	public List findAllStuGroupSelEv_c(Cddesigntopics cddesigntopics, Studentgroup studentgroup) {		 
		return daoFactory.getSelfEvaluationDao().findStuGroupEval_c(cddesigntopics, studentgroup);
	}

}
