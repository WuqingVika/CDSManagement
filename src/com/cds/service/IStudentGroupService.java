package com.cds.service;

import java.util.List;

import com.cds.entity.Student;
import com.cds.entity.Studentgroup;

public interface IStudentGroupService {
	void save(Studentgroup studentgroup);// ������ݲ���

	void update(Studentgroup studentgroup);// �޸����ݲ���

	void delete(Studentgroup studentGroup);// ɾ�����ݲ���

	Studentgroup find(Studentgroup studentgroup);// ��ѯ���ݲ���

	List<Studentgroup> findAll();// ��ѯ�������ݲ���
	
	/**
	 * �־û���StudentGroup
	 * @param studentgroup
	 */
	void persistStudentGroupInfo_c(Studentgroup studentgroup);
	
	/**
	 * ��ѯ���е�С��ĳ�Ա����Ϣ
	 * @param studentgroup
	 * @return
	 */
	List searchAllStuGroupMembers_c(Studentgroup studentgroup);
	
	
	/**
	 * Ϊÿһ��С�������Ա
	 * @param studentgroup
	 * @param student
	 */
	void addStuGroupMembers_c(Studentgroup studentgroup,Student student);
	
	/**
	 * �Ƴ�ĳ��С���ĳ����Ա
	 * @param studentgroup
	 * @param student
	 */
	void removeStuGroupMem_c(Studentgroup studentgroup,Student student);
	
	/**
	 * �Ƴ�����С��
	 * @param studentgroup
	 */
	void removeAllStuGroupMem_c(Studentgroup studentgroup);
}
