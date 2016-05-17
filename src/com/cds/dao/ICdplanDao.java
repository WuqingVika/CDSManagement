package com.cds.dao;

import java.util.List;

import com.cds.entity.Cdplan;
import com.cds.entity.Teacher;

public interface ICdplanDao {
	void save(Cdplan cdplan);// 添加

	void update(Cdplan cdplan);// 修改

	void delete(Cdplan cdplan);// 删除

	Cdplan find(Cdplan cdplan);// 查询

	List<Cdplan> findAll();// 查询所有的数据
	
	/**
	 * 查询某课程设计组组长当前学期的所有的课程设计计划
	 * @param teacher
	 * @return
	 */
	List findAllTeachCdPlan_c(Teacher teacher);
	
	/**
	 * 查询某某个课程设计组长当前学期的所有(未曾分配的)的课程设计计划
	 * 如果cdplan为空，则没有条件筛选，如果有再加上筛选的条件就是 cdpplan.cdplanId = ?
	 * @param teacher
	 * @return
	 */
	List findAllCdPlan_c(Teacher teacher,Cdplan cdplan);
	
	
	/**
	 * 查询某个课程设计组组长下当前学期的所有(已经分配的)课程设计计划
	 * 如果cdplan为空，则没有条件筛选，如果有再加上的筛选条件就是cdplan.cdplanId = ?
	 * @param teacher
	 * @param cdplan
	 * @return
	 */
	List findAllReplyedCd_c(Teacher teacher,Cdplan cdplan);
}
