package com.cds.service;

import java.util.List;

import com.cds.entity.Cdteachergroup;
import com.cds.entity.Teacher;

public interface ICdteachergroupService {
	void save(Cdteachergroup cdteachergroup);//���
	void update(Cdteachergroup cdteachergroup);//�޸�
	void delete(Cdteachergroup cdteachergroup);//ɾ��
	Cdteachergroup find(Cdteachergroup cdteachergroup);//��ѯ
	List<Cdteachergroup> findAll();//��ѯ���е�����
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
	 * ͨ��С���ѯС��ĳ�Ա��Ϣ
	 * 
	 * @return
	 */
	List<Teacher> findTeacherByGP_c(Cdteachergroup cdteachergroup);

	/**
	 * ��ӽ�ʦС���Ա
	 * 
	 * @param teachers
	 *            ���еĽ�ʦ
	 * @param cdteachergroup
	 *            ��ʦС��
	 */
	void addThGroupMember_c(List<Teacher> teachers, Cdteachergroup cdteachergroup);

	/**
	 * ��ѯ���еĳ����鳤�ĳ�Ա��Ϣ
	 * 
	 * @param cdteachergroup
	 * @return
	 */
	List findTeacherByTeachGroup_c(Cdteachergroup cdteachergroup);

	/**
	 * ɾ��С���Ա
	 * 
	 * @param cdteachergroup
	 *            ��ʦС��
	 * @param teacher
	 *            ��ʦ
	 */
	void deleteTeachGroupMem_c(Cdteachergroup cdteachergroup, Teacher teacher);
	
	/**
	 * ���ʦС������ӳ�Ա
	 * @param cdteachergroup ��ʦС��
	 * @param teacher ��ʦ
	 */
	void addTeacher_c(Cdteachergroup cdteachergroup,Teacher teacher);	 
}
