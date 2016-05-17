package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Classes;
import com.cds.service.IClassesService;

public class ClassesService implements IClassesService {
	//×¢ÈëDaoFactory
		private DaoFactory daoFactory;
		public void setDaoFactory(DaoFactory daoFactory) {
			this.daoFactory = daoFactory;
		}
	@Override
	public void save(Classes classes) {
		daoFactory.getClassesDao().save(classes);

	}

	@Override
	public void update(Classes classes) {
		daoFactory.getClassesDao().update(classes);

	}

	@Override
	public void delete(Classes classes) {
		daoFactory.getClassesDao().delete(classes);

	}

	@Override
	public Classes find(Classes classes) {
		
		return daoFactory.getClassesDao().find(classes);
	}

	@Override
	public List<Classes> findAll() {
		return daoFactory.getClassesDao().findAll();
	}
	@Override
	public Classes findClassByClassName_z(String className) {
		return daoFactory.getClassesDao().findClassByClassName_z(className);
	}
	@Override
	public List findClassByCollegeId_z(int collegeId) {
		return daoFactory.getClassesDao().findClassByCollegeId_z(collegeId);
	}

}
