package com.cds.service;

import java.util.List;

import com.cds.entity.Term;

/**
 * ѧ�ڵ�service
 * 
 * @author PengChan
 *
 */
public interface ITermService {
	void save(Term term);// �������

	void update(Term term);// �޸�����

	void delete(Term term);// ɾ������

	Term find(Term term);// ��ѯ����

	List<Term> findAll();// ��ѯ���е�����
	/**
	 * ����ѧ�����ƻ�ø�ѧ�ڵ�ʵ���ࡣÿ��ѧ�ڵ�������Ψһ�ģ����Է���һ��ʵ�����
	 * @param termName ѧ������
	 * @return ��ѧ��ʵ�����
	 */
	Term findTermByName_z(String termName);
}
