package com.cds.service;

import java.util.List;

import com.cds.entity.Cddesigntopics;
import com.cds.entity.Cdplan;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Classes;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;

public interface ICddesigntopicsService {
	void save(Cddesigntopics cddesigntopics);//���
	void update(Cddesigntopics cddesigntopics);//�޸�
	void delete(Cddesigntopics cddesigntopics);//ɾ��
	Cddesigntopics find(Cddesigntopics cddesigntopics);//��ѯ
	List<Cddesigntopics> findAll();//��ѯ���е�����
	
	/**
	 * ͨ��С��ı�ţ���ѯ��С���µ����еĿ���
	 * @param cdteachergroup
	 * @return
	 */
	List findAllTopicByTGpId_c(Cdteachergroup cdteachergroup);
	
	/**
	 * ѧ������ѡ��
	 * @param cddesigntopics
	 * @param studentgroup
	 * @return
	 */
	boolean selfSelectCDTopic_c(Cddesigntopics cddesigntopics,Studentgroup studentgroup);
	
	/**
	 * ѧ��ѡ��ָ����ʦ��������Ŀ
	 * @param studentgroup
	 * @return
	 */
	boolean selectCDTopic_c(Studentgroup studentgroup);
	
	/**
	 * ��ѯ���е��Լ�ѡ�����Ŀ��Ϣ
	 * @param student
	 * @return
	 */
	List findSelfChoosedCD_c(Student student);
	
	/**
	 * ��ѯ���е��Ѿ�ѡ�����Ŀ����Ϣ
	 * @param student
	 * @return
	 */
	List findChoosedThCD_c(Student student);
	
	/**
	 * ��ѯ�����Ѿ�ѡ�����Ŀ
	 * @return
	 */
	List findAllChoosedCD_c(Student student);
	
	/**
	 * ͨ���༶�ı�źͿγ���Ƽƻ���ѯ�ð༶�������е�С��ѡ�����Ϣ
	 * @param classes
	 * @param cdplan
	 * @return
	 */  
	List findCDByClAndCdPlan_c(Classes classes,Cdplan cdplan);
	
	/**
	 * ��ѯĳ��ѧ���ڵ�ǰѧ������ѡ������Щ�γ�
	 * @param student
	 * @return
	 */
	List findDesignTopics_c(Student student);
}
