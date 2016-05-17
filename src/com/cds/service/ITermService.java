package com.cds.service;

import java.util.List;

import com.cds.entity.Term;

/**
 * 学期的service
 * 
 * @author PengChan
 *
 */
public interface ITermService {
	void save(Term term);// 添加数据

	void update(Term term);// 修改数据

	void delete(Term term);// 删除数据

	Term find(Term term);// 查询数据

	List<Term> findAll();// 查询所有的数据
	/**
	 * 根据学期名称获得该学期的实体类。每个学期的名称是唯一的，所以返回一个实体对象
	 * @param termName 学期名称
	 * @return 该学期实体对象
	 */
	Term findTermByName_z(String termName);
}
