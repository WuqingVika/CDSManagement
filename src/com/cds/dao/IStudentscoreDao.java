package com.cds.dao;

import java.util.List;

import com.cds.entity.Student;
import com.cds.entity.Studentscore;

/**
 * ѧ���ɼ�dao��
 * 
 * @author PengChan
 *
 */
public interface IStudentscoreDao {
	void save(Studentscore studentscore);// ������ݲ���

	void update(Studentscore studentscore);// �޸����ݲ���

	void delete(Studentscore studentscore);// ɾ�����ݲ���

	Studentscore find(Studentscore studentscore);// ��ѯ���ݲ���

	List<Studentscore> findAll();// �����������ݲ���
	
	/**
	 * ��ѯĳѧ����ѧ�ڵ����еĿγ���Ƴɼ�����Ϣ
	 * @param student
	 * @return
	 */
	List findStudentScore_c(Student student);
}
