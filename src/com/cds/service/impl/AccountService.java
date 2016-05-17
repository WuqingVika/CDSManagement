package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Account;
import com.cds.service.IAccountService;

public class AccountService implements IAccountService {
	//×¢ÈëDaoFactory
	private DaoFactory daoFactory;
	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void save(Account account) {
		daoFactory.getAccountDao().save(account);
	}

	@Override
	public void update(Account account) {
		daoFactory.getAccountDao().update(account);
		
	}

	@Override
	public void delete(Account account) {
		daoFactory.getAccountDao().delete(account);
	}

	@Override
	public Account find(Account account) {
		return daoFactory.getAccountDao().find(account);
		 
	}

	@Override
	public List<Account> findAll() {
		
		return daoFactory.getAccountDao().findAll();
	}
	
	@Override
	public Account findAccountByAccountId_c(Account account) {		
		return daoFactory.getAccountDao().findByAccountId_c(account);
	}
}
