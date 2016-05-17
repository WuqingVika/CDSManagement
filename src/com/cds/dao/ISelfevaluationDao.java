package com.cds.dao;

import java.util.List;

import com.cds.entity.Cddesigntopics;
import com.cds.entity.Selfevaluation;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;

/**
 * ��������dao��
 * 
 * @author PengChan
 *
 */
public interface ISelfevaluationDao {
	void save(Selfevaluation selfevaluation);// ������ݲ���

	void update(Selfevaluation selfevaluation);// �޸����ݲ���

	void delete(Selfevaluation selfevaluation);// ɾ�����ݲ���

	Selfevaluation find(Selfevaluation selfevaluation);// ��ѯ���ݲ���

	List<Selfevaluation> findAll();// ��ѯ����
	
	/**
	 * ����ѧ���ı�źͿγ������Ŀ�ı�Ų�ѯ����
	 * @param cddesigntopics
	 * @param student
	 * @return
	 */
	Selfevaluation findSelEval_c(Cddesigntopics cddesigntopics,Student student);
		
	/**
	 * �鿴�γ����С��������Ա����ĳһ�����γ���Ƶ����е���������
	 * @param cddesigntopics
	 * @param studentgroup
	 * @return
	 */
	List findStuGroupEval_c(Cddesigntopics cddesigntopics,Studentgroup studentgroup);
}
