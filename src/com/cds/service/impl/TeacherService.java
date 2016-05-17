package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Teacher;
import com.cds.service.ITeacherService;

/**
 * ΩÃ ¶service
 * 
 * @author PengChan
 *
 */
public class TeacherService implements ITeacherService {

	private DaoFactory daoFactory;

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void save(Teacher teacher) {
		daoFactory.getTeacherDao().save(teacher);
	}

	@Override
	public void update(Teacher teacher) {
		daoFactory.getTeacherDao().update(teacher);
	}

	@Override
	public void delete(Teacher teacher) {
		daoFactory.getTeacherDao().delete(teacher);
	}

	@Override
	public Teacher find(Teacher teacher) {
		return daoFactory.getTeacherDao().find(teacher);
	}

	@Override
	public List<Teacher> findAll() {
		return daoFactory.getTeacherDao().findAll();
	}

	@Override
	public Teacher findTeacherByAccId_z(int accId) {
		return daoFactory.getTeacherDao().findTeacherByAccId_z(accId);
	}

	@Override
	public Teacher findTeacherByWorkId_z(String teacherWorkId) {
		return daoFactory.getTeacherDao().findTeacherByWorkId_z(teacherWorkId);
	}

	@Override
	public List findTeachersByCollegeId_z(int collegeId) {
		return daoFactory.getTeacherDao().findTeachersByCollegeId_z(collegeId);
	}
	
	@Override
	public Teacher findTeacherByTeachWId_c(Teacher teacher) {
		return daoFactory.getTeacherDao().findTeacherByTeachWorkId_c(teacher);
	}
}
