package com.cds.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.cds.dao.ICommentsDao;
import com.cds.entity.Comments;

public class CommentsDao implements ICommentsDao {
	//×¢ÈësessionFactory
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void save(Comments comments) {
		sessionFactory.getCurrentSession().save(comments);
	}
	
	@Override
	public void delete(Comments comments) {
		sessionFactory.getCurrentSession().delete(comments);
	}

	@Override
	public void update(Comments comments) {
		sessionFactory.getCurrentSession().update(comments);
	}

	@Override
	public Comments find(Comments comments) {
		return (Comments) sessionFactory.getCurrentSession().get(Comments.class, comments.getCommentId());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comments> findAll() {
		return sessionFactory.getCurrentSession().createCriteria(Comments.class).list();
	}
}
