package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;
import com.cds.service.IStudentGroupService;

public class StudentgroupService implements IStudentGroupService {

	private DaoFactory daoFactory;

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void save(Studentgroup studentgroup) {
		daoFactory.getStudentGroupDao().save(studentgroup);
	}

	@Override
	public void update(Studentgroup studentgroup) {
		daoFactory.getStudentGroupDao().update(studentgroup);
	}

	@Override
	public void delete(Studentgroup studentGroup) {
		daoFactory.getStudentGroupDao().delete(studentGroup);
	}

	@Override
	public Studentgroup find(Studentgroup studentgroup) {
		return daoFactory.getStudentGroupDao().find(studentgroup);
	}

	@Override
	public List<Studentgroup> findAll() {
		return daoFactory.getStudentGroupDao().findAll();
	}
	
	@Override
	public void persistStudentGroupInfo_c(Studentgroup studentgroup) {
		daoFactory.getStudentGroupDao().persistStudentGroup_c(studentgroup);
	}

	@Override
	public List searchAllStuGroupMembers_c(Studentgroup studentgroup) {	 
		return daoFactory.getStudentGroupDao().findAllMembers_c(studentgroup);
	}

	@Override
	public void addStuGroupMembers_c(Studentgroup studentgroup, Student student) {
		daoFactory.getStudentGroupDao().addStuMembers_c(studentgroup, student);		
	}

	@Override
	public void removeStuGroupMem_c(Studentgroup studentgroup, Student student) {
		daoFactory.getStudentGroupDao().removeStuGroupInfo_c(studentgroup, student);
	}

	@Override
	public void removeAllStuGroupMem_c(Studentgroup studentgroup) {
		daoFactory.getStudentGroupDao().removeAllStuGroup_c(studentgroup);
	}

}
