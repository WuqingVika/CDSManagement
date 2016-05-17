package com.cds.service;

import java.util.List;

import com.cds.entity.Cdchoosingrecord;

public interface ICdchoosingrecordService {
	void save(Cdchoosingrecord cdchoosingrecord);//添加
	void update(Cdchoosingrecord cdchoosingrecord);//修改
	void delete(Cdchoosingrecord cdchoosingrecord);//删除
	Cdchoosingrecord find(Cdchoosingrecord cdchoosingrecord);//查询
	List<Cdchoosingrecord> findAll();//查询所有的数据
}
