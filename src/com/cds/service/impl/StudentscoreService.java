package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Student;
import com.cds.entity.Studentscore;
import com.cds.service.IStudentscoreService;

/**
 * 学生成绩service
 * 
 * @author PengChan
 *
 */
public class StudentscoreService implements IStudentscoreService {

	//注入daofactory
	private DaoFactory daoFactory;

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void save(Studentscore studentscore) {
		daoFactory.getStudentScoreDao().save(studentscore);
	}

	@Override
	public void update(Studentscore studentscore) {
		daoFactory.getStudentScoreDao().update(studentscore);
	}

	@Override
	public void delete(Studentscore studentscore) {
		daoFactory.getStudentScoreDao().delete(studentscore);
	}

	@Override
	public Studentscore find(Studentscore studentscore) {
		return daoFactory.getStudentScoreDao().find(studentscore);
	}

	@Override
	public List<Studentscore> findAll() {
		return daoFactory.getStudentScoreDao().findAll();
	}
	
	@Override
	public List findStudentScore_c(Student student) {		 
		return daoFactory.getStudentScoreDao().findStudentScore_c(student);
	}

}
