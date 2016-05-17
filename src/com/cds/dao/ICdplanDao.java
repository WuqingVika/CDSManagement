package com.cds.dao;

import java.util.List;

import com.cds.entity.Cdplan;
import com.cds.entity.Teacher;

public interface ICdplanDao {
	void save(Cdplan cdplan);// ���

	void update(Cdplan cdplan);// �޸�

	void delete(Cdplan cdplan);// ɾ��

	Cdplan find(Cdplan cdplan);// ��ѯ

	List<Cdplan> findAll();// ��ѯ���е�����
	
	/**
	 * ��ѯĳ�γ�������鳤��ǰѧ�ڵ����еĿγ���Ƽƻ�
	 * @param teacher
	 * @return
	 */
	List findAllTeachCdPlan_c(Teacher teacher);
	
	/**
	 * ��ѯĳĳ���γ�����鳤��ǰѧ�ڵ�����(δ�������)�Ŀγ���Ƽƻ�
	 * ���cdplanΪ�գ���û������ɸѡ��������ټ���ɸѡ���������� cdpplan.cdplanId = ?
	 * @param teacher
	 * @return
	 */
	List findAllCdPlan_c(Teacher teacher,Cdplan cdplan);
	
	
	/**
	 * ��ѯĳ���γ�������鳤�µ�ǰѧ�ڵ�����(�Ѿ������)�γ���Ƽƻ�
	 * ���cdplanΪ�գ���û������ɸѡ��������ټ��ϵ�ɸѡ��������cdplan.cdplanId = ?
	 * @param teacher
	 * @param cdplan
	 * @return
	 */
	List findAllReplyedCd_c(Teacher teacher,Cdplan cdplan);
}
