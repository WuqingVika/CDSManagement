package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Cdplan;
import com.cds.entity.Classes;
import com.cds.entity.College;
import com.cds.entity.Teacher;
import com.cds.service.ICollegeService;

public class CollegeService implements ICollegeService {
	//×¢ÈëDaoFactory
		private DaoFactory daoFactory;
		public void setDaoFactory(DaoFactory daoFactory) {
			this.daoFactory = daoFactory;
		}
		
	@Override
	public void save(College college) {
		daoFactory.getCollegeDao().save(college);
	}

	@Override
	public void update(College college) {
		daoFactory.getCollegeDao().update(college);
	}

	@Override
	public void delete(College college) {
		daoFactory.getCollegeDao().delete(college);

	}

	@Override
	public College find(College college) {
		
		return daoFactory.getCollegeDao().find(college);
	}

	@Override
	public List<College> findAll() {
		
		return daoFactory.getCollegeDao().findAll();
	}

	@Override
	public College findCollegeByName_z(String collegeName) {
		List<College> colleges = daoFactory.getCollegeDao().findCollegeByName_z(collegeName);
		if(colleges.size() > 0){
			return colleges.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<Classes> findClassesByCollegeId_z(int collegeId) {
		return daoFactory.getCollegeDao().findClassesByCollegeId_z(collegeId);
	}

	@Override
	public List findStudentsByCollegeId(int collegeId) {
		return daoFactory.getCollegeDao().findStudentsByCollegeId(collegeId);
	}

	@Override
	public List<Cdplan> findCdplansByCollegeId(int collegeId) {
		return daoFactory.getCollegeDao().findCdplansByCollegeId(collegeId);
	}
	
	@Override
	public List findAllTeacher_c(Teacher teacher) {
		return daoFactory.getCollegeDao().findAllTeacher_c(teacher);
	}
}
