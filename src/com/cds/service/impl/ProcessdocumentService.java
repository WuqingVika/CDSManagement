package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Cddesigntopics;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Processdocument;
import com.cds.entity.Student;
import com.cds.entity.Teacher;
import com.cds.service.IProcessdocumentService;

public class ProcessdocumentService implements IProcessdocumentService {
	//×¢ÈëDaoFactory
	private DaoFactory daoFactory;
	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void save(Processdocument processdocument) {
		daoFactory.getProcessdocumentDao().save(processdocument);
	}

	@Override
	public void update(Processdocument processdocument) {
		daoFactory.getProcessdocumentDao().update(processdocument);
	}

	@Override
	public void delete(Processdocument processdocument) {
		daoFactory.getProcessdocumentDao().delete(processdocument);
	}

	@Override
	public Processdocument find(Processdocument processdocument) {
		return daoFactory.getProcessdocumentDao().find(processdocument);
	}

	@Override
	public List<Processdocument> findAll() {
		return daoFactory.getProcessdocumentDao().findAll();
	}
	
	@Override
	public List findAllSubmitted_c(Teacher teacher, Cdteachergroup cdteachergroup, Student student) {		 
		return daoFactory.getProcessdocumentDao().findAllSubmitted_c(teacher, cdteachergroup, student);
	}

	@Override
	public List findAllOtherSubmitted_c(Teacher teacher, Cdteachergroup cdteachergroup, Student student,
			Cddesigntopics cddesigntopics) {	 
		return daoFactory.getProcessdocumentDao().findAllOtherGroupMemb_c(teacher, cdteachergroup, student, cddesigntopics);
	}
}
