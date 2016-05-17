package com.cds.dao;

import java.util.List;

import com.cds.entity.Student;
import com.cds.entity.Studentgroup;

/**
 * ѧ��С�����ݲ���
 * 
 * @author PengChan
 *
 */
public interface IStudentgroupDao {
	void save(Studentgroup studentgroup);// ������ݲ���

	void update(Studentgroup studentgroup);// �޸����ݲ���

	void delete(Studentgroup studentGroup);// ɾ�����ݲ���

	Studentgroup find(Studentgroup studentgroup);// ��ѯ���ݲ���

	List<Studentgroup> findAll();// ��ѯ�������ݲ���
	
	/**
	 * �־û���studentGroup
	 * @param studentgroup
	 */
	void persistStudentGroup_c(Studentgroup studentgroup);
	
	/**
	 * ��ѧ�����С����ϵ���в�������
	 * @param studentgroup
	 */
	void addStudentMembers_c(Studentgroup studentgroup);
	
	/**
	 * ��ѯÿһ������Щ��Ա
	 * @param studentgroup
	 * @return
	 */
	List findAllMembers_c(Studentgroup studentgroup);
	
	/**
	 * ΪС�����ѧ����Ա
	 * @param studentgroup
	 * @param student
	 */
	void addStuMembers_c(Studentgroup studentgroup,Student student);
	
	/**
	 * �Ƴ�С��ĳ�Ա
	 * @param studentgroup
	 */
	void removeStuGroupInfo_c(Studentgroup studentgroup,Student student);
	
	/**
	 * ɾ������С���ѡ��
	 */
	void removeAllStuGroup_c(Studentgroup studentgroup);
}
