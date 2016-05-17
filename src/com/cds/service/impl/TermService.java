package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Term;
import com.cds.service.ITermService;

/**
 * Ñ§ÆÚservice
 * 
 * @author PengChan
 *
 */
public class TermService implements ITermService {

	// ×¢ÈëdaoFactory
	private DaoFactory daoFactory;

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void save(Term term) {
		daoFactory.getTermDao().save(term);
	}

	@Override
	public void update(Term term) {
		daoFactory.getTermDao().update(term);
	}

	@Override
	public void delete(Term term) {
		daoFactory.getTermDao().delete(term);
	}

	@Override
	public Term find(Term term) {
		return daoFactory.getTermDao().find(term);
	}

	@Override
	public List<Term> findAll() {
		return daoFactory.getTermDao().findAll();
	}

	@Override
	public Term findTermByName_z(String termName) {
		List<Term> terms = daoFactory.getTermDao().findTermByName_z(termName);
		if(terms.size() > 0){
			return terms.get(0);
		}else{
			return null;
		}
	}
}
