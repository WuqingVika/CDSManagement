package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Cdplan;
import com.cds.entity.Classes;
import com.cds.entity.Student;
import com.cds.service.IStudentService;

/**
 * Ñ§ÉúService
 * 
 * @author PengChan
 *
 */
public class StudentService implements IStudentService {

	private DaoFactory daoFactory;

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void save(Student student) {
		daoFactory.getStudentDao().save(student);
	}

	@Override
	public void update(Student student) {
		daoFactory.getStudentDao().update(student);
	}

	@Override
	public void delete(Student student) {
		daoFactory.getStudentDao().delete(student);
	}

	@Override
	public Student find(Student student) {		
		return daoFactory.getStudentDao().find(student);
	}

	@Override
	public List<Student> findAll() {
		return daoFactory.getStudentDao().findAll();
	}

	@Override
	public Student findStudentByStudentId_z(String studentId) {
		return daoFactory.getStudentDao().findStudentByStudentId_z(studentId);
	}
	
	@Override
	public List findAllByClasses_c(Classes classes) {
		return daoFactory.getStudentDao().findAllStuByClass_c(classes);
	}

	@Override
	public List findAllNeedChoosingStu_c(Student student, Cdplan cdplan) {		 
		return daoFactory.getStudentDao().findNeedChStuByClass_c(student, cdplan);
	}

	@Override
	public Student findStuByStudentId_c(Student student) {		 
		return daoFactory.getStudentDao().findSudentByStudentId_c(student);
	}	 
}
