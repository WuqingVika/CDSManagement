package com.cds.dao;

import java.util.List;

import com.cds.entity.Account;

/**
 * 账户的数据操作
 * 
 *
 */
public interface IAccountDao {
	void save(Account account);// 添加

	void update(Account account);// 修改

	void delete(Account account);// 删除

	Account find(Account account);// 查询

	List<Account> findAll();// 查询所有的数据

	/**
	 * 通过账号查询用户的信息
	 * @param account
	 * @return
	 */
	Account findByAccountId_c(Account account);
}
