package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.IAccountDao;
import com.cds.entity.Account;

public class AccoutDao implements IAccountDao {
	//×¢Èë
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void save(Account account) {
		sessionFactory.getCurrentSession().save(account);
	}

	@Override
	public void update(Account account) {
		sessionFactory.getCurrentSession().update(account);
		
	}

	@Override
	public void delete(Account account) {
		sessionFactory.getCurrentSession().delete(account);
	}

	@Override
	public Account find(Account account) {		
		return (Account)sessionFactory.getCurrentSession().get(Account.class, account.getAccId());
	}

	@Override
	public List<Account> findAll() {
		@SuppressWarnings("unchecked")
		List<Account> list=sessionFactory
				.getCurrentSession().createCriteria(Account.class).list();
		return list;
	}
	
	@Override
	public Account findByAccountId_c(Account account) {
		return (Account) sessionFactory.getCurrentSession().createQuery("from Account a where a.accountId=:accountId").setParameter("accountId", account.getAccountId()).uniqueResult();				
	}
}
