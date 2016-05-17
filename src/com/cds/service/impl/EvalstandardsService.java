package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Evalstandards;
import com.cds.service.IEvalstandardsService;

public class EvalstandardsService implements IEvalstandardsService {
	//×¢ÈëDaoFactory
	private DaoFactory daoFactory;
	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void save(Evalstandards evalstandards) {
		daoFactory.getEvalstandardsDao().save(evalstandards);
	}

	@Override
	public void update(Evalstandards evalstandards) {
		daoFactory.getEvalstandardsDao().update(evalstandards);
	}

	@Override
	public void delete(Evalstandards evalstandards) {
		daoFactory.getEvalstandardsDao().delete(evalstandards);
	}

	@Override
	public Evalstandards find(Evalstandards evalstandards) {
		return daoFactory.getEvalstandardsDao().find(evalstandards);
	}

	@Override
	public List<Evalstandards> findAll() {
		return daoFactory.getEvalstandardsDao().findAll();
	}
}
