package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Processassshedule;
import com.cds.entity.Student;
import com.cds.entity.Teacher;
import com.cds.service.IProcessasssheduleService;

public class ProcessasssheduleService implements IProcessasssheduleService {
	//×¢ÈëDaoFactory
	private DaoFactory daoFactory;
	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void save(Processassshedule processassshedule) {
		daoFactory.getProcessasssheduleDao().save(processassshedule);
	}

	@Override
	public void update(Processassshedule processassshedule) {
		daoFactory.getProcessasssheduleDao().update(processassshedule);
	}

	@Override
	public void delete(Processassshedule processassshedule) {
		daoFactory.getProcessasssheduleDao().delete(processassshedule);
	}

	@Override
	public Processassshedule find(Processassshedule processassshedule) {
		return daoFactory.getProcessasssheduleDao().find(processassshedule);
	}

	@Override
	public List<Processassshedule> findAll() {
		return daoFactory.getProcessasssheduleDao().findAll();
	}
	
	@Override
	public List findAllProcessShe_c(Teacher teacher, Cdteachergroup cdteachergroup) {	    
		return daoFactory.getProcessasssheduleDao().findAllProcessShe_c(teacher, cdteachergroup);
	}

	@Override
	public List findAllProcExpSubmited_c(Teacher teacher, Cdteachergroup cdteachergroup, Student student) {		 
		return daoFactory.getProcessasssheduleDao().findAllProcSheExcep_c(teacher, cdteachergroup, student);
	}
}
