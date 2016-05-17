package com.cds.dao;

import java.util.List;

import com.cds.entity.Account;

/**
 * �˻������ݲ���
 * 
 *
 */
public interface IAccountDao {
	void save(Account account);// ���

	void update(Account account);// �޸�

	void delete(Account account);// ɾ��

	Account find(Account account);// ��ѯ

	List<Account> findAll();// ��ѯ���е�����

	/**
	 * ͨ���˺Ų�ѯ�û�����Ϣ
	 * @param account
	 * @return
	 */
	Account findByAccountId_c(Account account);
}
