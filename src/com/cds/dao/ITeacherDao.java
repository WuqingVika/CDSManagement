package com.cds.dao;

import java.util.List;

import com.cds.entity.Teacher;

/**
 * ��ʦ��dao��
 * 
 * @author PengChan
 *
 */
public interface ITeacherDao {
	void save(Teacher teacher);// �������

	void update(Teacher teacher);// �޸�����

	void delete(Teacher teacher);// ɾ������

	Teacher find(Teacher teacher);// ��ѯ����

	List<Teacher> findAll();// ��ѯ���е�����
	
	/**
	 * ͨ���˺ű��accId�ҵ������ʦ
	 * @param accId �˺ű��accId�����ֵ��Ӧ�Ľ�ɫ�����ǽ�ʦ���������
	 * @return ���������ʦ��
	 */
	Teacher findTeacherByAccId_z(int accId);
	/**
	 * ͨ����ʦ�Ĺ��Ų��ҽ�ʦ
	 * @param teacherWorkId ��ʦ����
	 * @return ���ؽ�ʦ����
	 */
	Teacher findTeacherByWorkId_z(String teacherWorkId);
	/**
	 * ͨ��ѧԺ���ҵ����еĽ�ʦ
	 * @param collegeId ѧԺ�ı��
	 * @return ��ʦ�ļ���
	 */
	List findTeachersByCollegeId_z(int collegeId);
	
	/**
	 * ���߽�ʦ�Ĺ��Ų�ѯ��ʦ����Ϣ
	 * 
	 * @param teacher
	 * @return
	 */
	Teacher findTeacherByTeachWorkId_c(Teacher teacher);
}
