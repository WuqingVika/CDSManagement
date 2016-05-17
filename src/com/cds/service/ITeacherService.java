package com.cds.service;

import java.util.List;

import com.cds.entity.Teacher;

/**
 * ��ʦservice
 * @author PengChan
 *
 */
public interface ITeacherService {
	void save(Teacher teacher);// �������

	void update(Teacher teacher);// �޸�����

	void delete(Teacher teacher);// ɾ������

	Teacher find(Teacher teacher);// ��ѯ����

	List<Teacher> findAll();// ��ѯ���е�����
	/**
	 * ͨ���˺ű��accId�ҵ������ʦ
	 * @param accId �˺ű��accId�����ֵ��Ӧ�Ľ�ɫ�����ǽ�ʦ���������
	 * @return ���������ʦ�����û�У�����null��
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
	Teacher findTeacherByTeachWId_c(Teacher teacher);
}
