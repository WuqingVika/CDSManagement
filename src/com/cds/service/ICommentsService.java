package com.cds.service;

import java.util.List;

import com.cds.entity.Comments;

public interface ICommentsService {
	void save(Comments comments);	//��������
	void update(Comments comments); //��������
	void delete(Comments comments);	//ɾ������
	Comments find(Comments comments); //��������
	List<Comments> findAll();		//������������
}
