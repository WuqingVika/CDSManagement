package com.cds.service;

import java.util.List;

import com.cds.entity.Cdchoosingrecord;

public interface ICdchoosingrecordService {
	void save(Cdchoosingrecord cdchoosingrecord);//���
	void update(Cdchoosingrecord cdchoosingrecord);//�޸�
	void delete(Cdchoosingrecord cdchoosingrecord);//ɾ��
	Cdchoosingrecord find(Cdchoosingrecord cdchoosingrecord);//��ѯ
	List<Cdchoosingrecord> findAll();//��ѯ���е�����
}
