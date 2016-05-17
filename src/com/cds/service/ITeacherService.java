package com.cds.service;

import java.util.List;

import com.cds.entity.Teacher;

/**
 * 教师service
 * @author PengChan
 *
 */
public interface ITeacherService {
	void save(Teacher teacher);// 添加数据

	void update(Teacher teacher);// 修改数据

	void delete(Teacher teacher);// 删除数据

	Teacher find(Teacher teacher);// 查询数据

	List<Teacher> findAll();// 查询所有的数据
	/**
	 * 通过账号表的accId找到这个教师
	 * @param accId 账号表的accId。这个值对应的角色必须是教师，否则出错
	 * @return 返回这个教师。如果没有，返回null。
	 */
	Teacher findTeacherByAccId_z(int accId);
	/**
	 * 通过教师的工号查找教师
	 * @param teacherWorkId 教师工号
	 * @return 返回教师对象
	 */
	Teacher findTeacherByWorkId_z(String teacherWorkId);
	/**
	 * 通过学院查找到所有的教师
	 * @param collegeId 学院的编号
	 * @return 教师的集合
	 */
	List findTeachersByCollegeId_z(int collegeId);
	
	/**
	 * 更具教师的工号查询教师的信息
	 * 
	 * @param teacher
	 * @return
	 */
	Teacher findTeacherByTeachWId_c(Teacher teacher);
}
