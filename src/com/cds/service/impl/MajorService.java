package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Major;
import com.cds.service.IMajorService;

public class MajorService implements IMajorService {
	//×¢ÈëDaoFactory
	private DaoFactory daoFactory;
	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void save(Major major) {
		daoFactory.getMajorDao().save(major);
	}

	@Override
	public void update(Major major) {
		daoFactory.getMajorDao().update(major);
	}

	@Override
	public void delete(Major major) {
		daoFactory.getMajorDao().delete(major);
	}

	@Override
	public Major find(Major major) {
		return daoFactory.getMajorDao().find(major);
	}

	@Override
	public List<Major> findAll() {
		return daoFactory.getMajorDao().findAll();
	}

	@Override
	public Major findMajorByName_z(String majorName) {
		return daoFactory.getMajorDao().findMajorByName_z(majorName);
	}

	@Override
	public List<Major> findMajorsByCollegeId_z(int collegeId) {
		return daoFactory.getMajorDao().findMajorsByCollegeId_z(collegeId);
	}

	@Override
	public List findMajorsByCollegeIdJSON_z(int collegeId) {		 
		return daoFactory.getMajorDao().findMajorsByCollegeIdJSON_z(collegeId);
	}
}
