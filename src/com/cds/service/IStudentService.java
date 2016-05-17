package com.cds.service;

import java.util.List;

import com.cds.entity.Cdplan;
import com.cds.entity.Classes;
import com.cds.entity.Student;

/**
 * ѧ��service
 * 
 * @author PengChan
 *
 */
public interface IStudentService {
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
	 * ͨ���༶��ѯ�ð༶��������е�ѧ��
	 * @param classes
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List findAllByClasses_c(Classes classes);
	
	/**
	 * ��ѯ���༶������Ҫѡ�ε�ѧ��
	 * @param student
	 * @param cdplan
	 * @return
	 */
	List findAllNeedChoosingStu_c(Student student,Cdplan cdplan);
	
	/**
	 * ͨ��ѧ����ѧ�Ų�ѯ�û�����Ϣ
	 * @param student
	 * @return
	 */
	Student findStuByStudentId_c(Student student);
}
