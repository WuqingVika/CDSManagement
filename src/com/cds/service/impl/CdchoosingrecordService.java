package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Cdchoosingrecord;
import com.cds.service.ICdchoosingrecordService;

public class CdchoosingrecordService implements ICdchoosingrecordService {
	//×¢ÈëDaoFactory
		private DaoFactory daoFactory;
		public void setDaoFactory(DaoFactory daoFactory) {
			this.daoFactory = daoFactory;
		}
	@Override
	public void save(Cdchoosingrecord cdchoosingrecord) {
		daoFactory.getCdchoosingrecordDao().save(cdchoosingrecord);
		
	}

	@Override
	public void update(Cdchoosingrecord cdchoosingrecord) {
		daoFactory.getCdchoosingrecordDao().update(cdchoosingrecord);
		
	}

	@Override
	public void delete(Cdchoosingrecord cdchoosingrecord) {
		daoFactory.getCdchoosingrecordDao().delete(cdchoosingrecord);
		
	}

	@Override
	public Cdchoosingrecord find(Cdchoosingrecord cdchoosingrecord) {
		// TODO Auto-generated method stub
		return daoFactory.getCdchoosingrecordDao().find(cdchoosingrecord);
	}

	@Override
	public List<Cdchoosingrecord> findAll() {
		return daoFactory.getCdchoosingrecordDao().findAll();
	}

}
