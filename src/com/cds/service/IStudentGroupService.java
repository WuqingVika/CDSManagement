package com.cds.service;

import java.util.List;

import com.cds.entity.Student;
import com.cds.entity.Studentgroup;

public interface IStudentGroupService {
	void save(Studentgroup studentgroup);// 添加数据操作

	void update(Studentgroup studentgroup);// 修改数据操作

	void delete(Studentgroup studentGroup);// 删除数据操作

	Studentgroup find(Studentgroup studentgroup);// 查询数据操作

	List<Studentgroup> findAll();// 查询所有数据操作
	
	/**
	 * 持久化类StudentGroup
	 * @param studentgroup
	 */
	void persistStudentGroupInfo_c(Studentgroup studentgroup);
	
	/**
	 * 查询所有的小组的成员的信息
	 * @param studentgroup
	 * @return
	 */
	List searchAllStuGroupMembers_c(Studentgroup studentgroup);
	
	
	/**
	 * 为每一个小组添加组员
	 * @param studentgroup
	 * @param student
	 */
	void addStuGroupMembers_c(Studentgroup studentgroup,Student student);
	
	/**
	 * 移除某个小组的某个成员
	 * @param studentgroup
	 * @param student
	 */
	void removeStuGroupMem_c(Studentgroup studentgroup,Student student);
	
	/**
	 * 移除整个小组
	 * @param studentgroup
	 */
	void removeAllStuGroupMem_c(Studentgroup studentgroup);
}
