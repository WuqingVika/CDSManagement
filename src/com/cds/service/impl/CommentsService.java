package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Comments;
import com.cds.service.ICommentsService;

public class CommentsService implements ICommentsService {
	//×¢ÈëDaoFactory
	private DaoFactory daoFactory;
	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void save(Comments comments) {
		daoFactory.getCommentsDao().save(comments);
	}

	@Override
	public void update(Comments comments) {
		daoFactory.getCommentsDao().update(comments);
	}

	@Override
	public void delete(Comments comments) {
		daoFactory.getCommentsDao().delete(comments);
	}

	@Override
	public Comments find(Comments comments) {
		return daoFactory.getCommentsDao().find(comments);
	}

	@Override
	public List<Comments> findAll() {
		return daoFactory.getCommentsDao().findAll();
	}

}
