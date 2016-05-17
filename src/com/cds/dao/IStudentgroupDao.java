package com.cds.dao;

import java.util.List;

import com.cds.entity.Student;
import com.cds.entity.Studentgroup;

/**
 * 学生小组数据操作
 * 
 * @author PengChan
 *
 */
public interface IStudentgroupDao {
	void save(Studentgroup studentgroup);// 添加数据操作

	void update(Studentgroup studentgroup);// 修改数据操作

	void delete(Studentgroup studentGroup);// 删除数据操作

	Studentgroup find(Studentgroup studentgroup);// 查询数据操作

	List<Studentgroup> findAll();// 查询所有数据操作
	
	/**
	 * 持久化类studentGroup
	 * @param studentgroup
	 */
	void persistStudentGroup_c(Studentgroup studentgroup);
	
	/**
	 * 向学生表和小组表关系表中插入数据
	 * @param studentgroup
	 */
	void addStudentMembers_c(Studentgroup studentgroup);
	
	/**
	 * 查询每一组有哪些成员
	 * @param studentgroup
	 * @return
	 */
	List findAllMembers_c(Studentgroup studentgroup);
	
	/**
	 * 为小组添加学生成员
	 * @param studentgroup
	 * @param student
	 */
	void addStuMembers_c(Studentgroup studentgroup,Student student);
	
	/**
	 * 移除小组的成员
	 * @param studentgroup
	 */
	void removeStuGroupInfo_c(Studentgroup studentgroup,Student student);
	
	/**
	 * 删除整个小组的选题
	 */
	void removeAllStuGroup_c(Studentgroup studentgroup);
}
