package com.cds.service;

import java.util.List;

import com.cds.entity.Cdplan;
import com.cds.entity.Classes;
import com.cds.entity.College;
import com.cds.entity.Teacher;

public interface ICollegeService {
	void save(College college);//���
	void update(College college);//�޸�
	void delete(College college);//ɾ��
	College find(College college);//��ѯ
	List<College> findAll();//��ѯ���е�����
	/**
	 * ����ѧԺ���ƻ�ø�ѧԺ��ʵ���ࡣÿ��ѧԺ��������Ψһ�ģ����Է���һ��ʵ�����
	 * @param collegeName ѧԺ����
	 * @return ��ѧԺʵ�����
	 */
	College findCollegeByName_z(String collegeName);
	/**
	 * ͨ��ѧԺ��Ų�ѯ���еİ༶
	 * @param collegeId ѧԺ���
	 * @return �༶�б�
	 */
	List<Classes> findClassesByCollegeId_z(int collegeId);
	/**
	 * ͨ��ѧԺ��Ų������б�ѧԺ��ѧ��
	 * @param collegeId
	 * @return
	 */
	List findStudentsByCollegeId(int collegeId);
	/**
	 * ͨ��ѧԺ�ҵ����еĿγ̼ƻ�
	 * @param collegeId
	 * @return
	 */
	List<Cdplan> findCdplansByCollegeId(int collegeId);
	
	/**
	 * �鿴ѧԺ�������еĽ�ʦ
	 * 
	 * @param college
	 * @return
	 */
	public List findAllTeacher_c(Teacher teacher);
}
