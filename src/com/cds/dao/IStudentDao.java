package com.cds.dao;

import java.util.List;

import com.cds.entity.Cdplan;
import com.cds.entity.Classes;
import com.cds.entity.Student;

/**
 * ѧ��dao
 * 
 * @author PengChan
 *
 */
public interface IStudentDao {
	void save(Student student);// ������ݲ���

	void update(Student student);// �޸����ݲ���

	void delete(Student student);// ɾ�����ݲ���

	Student find(Student student);// ��ѯ���ݲ���

	List<Student> findAll();// ��ѯ�������ݲ���
	/**
	 * ͨ��ѧ����ѧ��������ѧ��
	 * @param studentId ѧ����ѧ��
	 * @return ����ѧ������
	 */
	Student findStudentByStudentId_z(String studentId);
	
	/**
	 * ���ݰ༶��ѯ����ͬѧ����Ϣ
	 * @param classes
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List findAllStuByClass_c(Classes classes);
	
	/**
	 * ��ѯ��ѧ���İ༶��������δ��ѡ��ĳ�γ���Ƽƻ���ͬѧ����Ϣ
	 * @param classes
	 * @param student
	 * @return
	 */
	List findNeedChStuByClass_c(Student student,Cdplan cdplan);
	
	/**
	 * ͨ��ѧ���ı�Ų�ѯ�û�����Ϣ
	 * @param student
	 * @return
	 */
	Student findSudentByStudentId_c(Student student);
}
