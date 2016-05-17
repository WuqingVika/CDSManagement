package com.cds.dao;

import java.util.List;

import com.cds.entity.Term;

/**
 * 学期表dao层
 * 
 * @author PengChan
 *
 */
public interface ITermDao {
	void save(Term term);// 添加数据

	void update(Term term);// 修改数据

	void delete(Term term);// 删除数据

	Term find(Term term);// 查询数据

	List<Term> findAll();// 查询所有的数据
	/**
	 * 根据学期名称获得该学期的实体类。每个学期的名称是唯一的，这里返回一个List，由Service判断有没有数据
	 * @param termName 学期名称
	 * @return 学期的实体类
	 */
	List<Term> findTermByName_z(String termName);
}
