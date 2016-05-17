package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Replygroup;
import com.cds.service.IReplygroupService;

public class ReplygroupService implements IReplygroupService{
	//×¢ÈëDaoFactory
	private DaoFactory daoFactory;
	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void save(Replygroup replygroup) {
		daoFactory.getReplygroupDao().save(replygroup);
	}

	@Override
	public void update(Replygroup replygroup) {
		daoFactory.getReplygroupDao().update(replygroup);
	}

	@Override
	public void delete(Replygroup replygroup) {
		daoFactory.getReplygroupDao().delete(replygroup);
	}

	@Override
	public Replygroup find(Replygroup replygroup) {
		return daoFactory.getReplygroupDao().find(replygroup);
	}

	@Override
	public List<Replygroup> findAll() {
		return daoFactory.getReplygroupDao().findAll();
	}
	

	@Override
	public List findAllTeachersByRpId_c(Replygroup replygroup) {		 
		return daoFactory.getReplygroupDao().findAllTeachersByRpId_c(replygroup);
	}
}
