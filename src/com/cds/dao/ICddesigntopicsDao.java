package com.cds.dao;

import java.util.List;

import com.cds.entity.Cddesigntopics;
import com.cds.entity.Cdplan;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Classes;
import com.cds.entity.Student;

public interface ICddesigntopicsDao {
	void save(Cddesigntopics cddesigntopics);// ���

	void update(Cddesigntopics cddesigntopics);// �޸�

	void delete(Cddesigntopics cddesigntopics);// ɾ��

	Cddesigntopics find(Cddesigntopics cddesigntopics);// ��ѯ

	List<Cddesigntopics> findAll();// ��ѯ���е�����
	
	/**
	 * ͨ����ʦ��С��ı�ţ���ѯ��С���µ����еĿ�����Ϣ
	 * @param cdteachergroup
	 * @return
	 */
	List findTopicByCdGroupId_c(Cdteachergroup cdteachergroup);
	
	
	/**
	 * ��ѯ���еĵ�ǰѧ�ڵ����е���ѡ����Ŀ����Ϣ
	 * @param student
	 * @return
	 */
	List findAllSelfChoosedCD_c(Student student);
	
	/**
	 * ��ѯ���еĵ�ǰѧ�ڵ�ѡ���ʦ��Ŀ����Ϣ
	 * @param student
	 * @return
	 */
	List findAllChoosedThCD_c(Student student);
	
	/**
	 * ��ѯ����ǰѧ���Ѿ�ѡ��Ŀ������Ϣ
	 * @param student
	 * @return
	 */
	List findAllChoosedCD_c(Student student);
	
	/**
	 * ��ѯһ���༶�����һ���γ���Ƽƻ���������Щ����
	 * @param classes
	 * @param cdplan
	 * @return
	 */
	List findCDByClassAndCDPlan_c(Classes classes,Cdplan cdplan);
	
	
	/**
	 * ��ѯĳһ��ѧ����ǰѧ������ѡ������Щ�γ�
	 * @param student
	 * @return
	 */
	List findCdDesignTopic_c(Student student);
}
