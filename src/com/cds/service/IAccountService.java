package com.cds.service;

import java.util.List;

import com.cds.entity.Account;

public interface IAccountService {
	void save(Account account);
	void update(Account account);//修改
	void delete(Account account);//删除
	Account find(Account account);//查询
	List<Account> findAll();//查询所有的数据
	
	/**
	 * 通过编号查询账号的信息
	 * @param account
	 * @return
	 */
	Account findAccountByAccountId_c(Account account);
}
