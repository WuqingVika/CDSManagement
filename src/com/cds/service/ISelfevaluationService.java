package com.cds.service;

import java.util.List;

import com.cds.entity.Cddesigntopics;
import com.cds.entity.Selfevaluation;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;

/**
 * ����service
 * 
 * @author PengChan
 *
 */
public interface ISelfevaluationService {
	void save(Selfevaluation selfevaluation);// ������ݲ���

	void update(Selfevaluation selfevaluation);// �޸����ݲ���

	void delete(Selfevaluation selfevaluation);// ɾ�����ݲ���

	Selfevaluation find(Selfevaluation selfevaluation);// ��ѯ���ݲ���

	List<Selfevaluation> findAll();// ��ѯ����
	
	/**
	 * �ж�ѧ���Ƿ��Ѿ�����
	 * @param cddesigntopics
	 * @param student
	 * @return
	 */
	boolean studentHasEvaled_c(Cddesigntopics cddesigntopics,Student student);
	
	
	/**
	 * ��ѯ
	 * @param cddesigntopics
	 * @param student
	 * @return
	 */
	List findAllStuGroupSelEv_c(Cddesigntopics cddesigntopics,Studentgroup studentgroup);
}
