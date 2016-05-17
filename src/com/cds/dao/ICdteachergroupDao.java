package com.cds.dao;

import java.util.List;

import com.cds.entity.Cdteachergroup;
import com.cds.entity.Teacher;

public interface ICdteachergroupDao {
	void save(Cdteachergroup cdteachergroup);// ���

	void update(Cdteachergroup cdteachergroup);// �޸�

	void delete(Cdteachergroup cdteachergroup);// ɾ��

	Cdteachergroup find(Cdteachergroup cdteachergroup);// ��ѯ

	List<Cdteachergroup> findAll();// ��ѯ���е�����
	/**
	 * ͨ����ʦ��źͽ�ʦ�γ̼ƻ������ӽ�ʦ�����Ա
	 * @param teacherId ��ʦ���
	 * @param teacherGroupId ��ʦ�γ̼ƻ����
	 */
	void addTeacherMember_z(int teacherId, int cdTeacherGroupId);
	/**
	 * ͨ��teacherGroupId��ɾ�����еĽ�ʦ��Ա��Ϣ
	 * @param teacherGroupId
	 */
	void deleteMemberInfoByCdTeacherGroupId(int teacherGroupId);
	
	/**
	 * ͨ����Ա��ѯ���еĽ�ʦ��Ϣ
	 * @param cdteachergroup
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List findTeacherByTG_c(Cdteachergroup cdteachergroup);
	
	/**
	 * ���ʦС���������Ա
	 * @param teacher ��ʦ
	 * @param cdteachergroup �γ����С��
	 */
	void addThGroupMember_c(Teacher teacher,Cdteachergroup cdteachergroup);
	
	
	/**
	 * ��ѯ���еĳ����鳤�ĳ�Ա��Ϣ
	 * @param cdteachergroup
	 * @return
	 */
	List findTeacherByTeachGroup_c(Cdteachergroup cdteachergroup);
	
	
	/**
	 * ɾ��С���Ա
	 * @param cdteachergroup ��ʦС��
	 * @param teacher ��ʦ
	 */
	void deleteTeachGroupMem_c(Cdteachergroup cdteachergroup,Teacher teacher);
}
