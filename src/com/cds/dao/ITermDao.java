package com.cds.dao;

import java.util.List;

import com.cds.entity.Term;

/**
 * ѧ�ڱ�dao��
 * 
 * @author PengChan
 *
 */
public interface ITermDao {
	void save(Term term);// �������

	void update(Term term);// �޸�����

	void delete(Term term);// ɾ������

	Term find(Term term);// ��ѯ����

	List<Term> findAll();// ��ѯ���е�����
	/**
	 * ����ѧ�����ƻ�ø�ѧ�ڵ�ʵ���ࡣÿ��ѧ�ڵ�������Ψһ�ģ����ﷵ��һ��List����Service�ж���û������
	 * @param termName ѧ������
	 * @return ѧ�ڵ�ʵ����
	 */
	List<Term> findTermByName_z(String termName);
}
