package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Cdplan;
import com.cds.entity.Teacher;
import com.cds.service.ICdplanService;

public class CdplanService implements ICdplanService {
	//×¢ÈëDaoFactory
		private DaoFactory daoFactory;
		public void setDaoFactory(DaoFactory daoFactory) {
			this.daoFactory = daoFactory;
		}
	@Override
	public void save(Cdplan cdplan) {
		daoFactory.getCdplanDao().save(cdplan);

	}

	@Override
	public void update(Cdplan cdplan) {
		daoFactory.getCdplanDao().update(cdplan);

	}

	@Override
	public void delete(Cdplan cdplan) {
		daoFactory.getCdplanDao().delete(cdplan);

	}

	@Override
	public Cdplan find(Cdplan cdplan) {
		
		return daoFactory.getCdplanDao().find(cdplan);
	}

	@Override
	public List<Cdplan> findAll() {
		// TODO Auto-generated method stub
		return daoFactory.getCdplanDao().findAll();
	}
	
	@Override
	public List findAllTeachCdPlan_c(Teacher teacher) {		 
		return daoFactory.getCdplanDao().findAllTeachCdPlan_c(teacher);
	}
	@Override
	public List findAllCdPlan_c(Teacher teacher, Cdplan cdplan) {
		return daoFactory.getCdplanDao().findAllCdPlan_c(teacher, cdplan);
	}
	@Override
	public List findAllReplyedCd_c(Teacher teacher, Cdplan cdplan) {
		return daoFactory.getCdplanDao().findAllReplyedCd_c(teacher, cdplan);
	}

}
