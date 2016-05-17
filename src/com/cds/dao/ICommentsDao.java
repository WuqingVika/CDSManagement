package com.cds.dao;

import java.util.List;

import com.cds.entity.Comments;

/**
 * ��������ݲ���
 * 
 * @author PengChan
 *
 */
public interface ICommentsDao {
	void save(Comments comments); // ��������

	void update(Comments comments); // ��������

	void delete(Comments comments); // ɾ������

	Comments find(Comments comments); // ��������

	List<Comments> findAll(); // ������������
}
