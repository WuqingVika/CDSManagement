package com.cds.service;

import java.util.List;

import com.cds.entity.Account;

public interface IAccountService {
	void save(Account account);
	void update(Account account);//�޸�
	void delete(Account account);//ɾ��
	Account find(Account account);//��ѯ
	List<Account> findAll();//��ѯ���е�����
	
	/**
	 * ͨ����Ų�ѯ�˺ŵ���Ϣ
	 * @param account
	 * @return
	 */
	Account findAccountByAccountId_c(Account account);
}
