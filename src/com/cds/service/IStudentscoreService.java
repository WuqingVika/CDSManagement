package com.cds.service;

import java.util.List;

import com.cds.entity.Student;
import com.cds.entity.Studentscore;

/**
 * ѧ���ɼ�service
 * 
 * @author PengChan
 *
 */
public interface IStudentscoreService {
	void save(Studentscore studentscore);// ������ݲ���

	void update(Studentscore studentscore);// �޸����ݲ���

	void delete(Studentscore studentscore);// ɾ�����ݲ���

	Studentscore find(Studentscore studentscore);// ��ѯ���ݲ���

	List<Studentscore> findAll();// �����������ݲ���
	
	/**
	 * �鿴ѧ�������еĵ�ǰѧ����������еĿγ������Ŀ�ĳɼ�
	 * @param student
	 * @return
	 */
	List findStudentScore_c(Student student);
}
