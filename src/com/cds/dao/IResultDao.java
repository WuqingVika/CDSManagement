package com.cds.dao;

import java.util.List;

/**
 * 实现分页的Dao层
 * 
 * @author PengChan
 *
 */
public interface IResultDao {
	/**
	 * 通过hql语句以及offset参数和pagesize参数查询结果
	 * 
	 * @param hql
	 *            hql语句
	 * 
	 * @param offset
	 *            从哪个位置开始查询
	 * 
	 * @param pageSize
	 *            设定每一个页面的大小
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryByPage(String hql, int offset, int pageSize);

	/**
	 * 获取查询的数据条数
	 * 
	 * @param hql
	 * @return
	 */
	public int getAllRowCount(String hql);
	/**
	 * 不采用分页根据hql查询
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List queryBysql(String sql);
}
