package com.cds.dao;

import java.util.List;

import com.cds.entity.Cdplan;
import com.cds.entity.Classes;
import com.cds.entity.Student;

/**
 * 学生dao
 * 
 * @author PengChan
 *
 */
public interface IStudentDao {
	void save(Student student);// 添加数据操作

	void update(Student student);// 修改数据操作

	void delete(Student student);// 删除数据操作

	Student find(Student student);// 查询数据操作

	List<Student> findAll();// 查询所有数据操作
	/**
	 * 通过学生的学号来查找学生
	 * @param studentId 学生的学号
	 * @return 返回学生对象
	 */
	Student findStudentByStudentId_z(String studentId);
	
	/**
	 * 根据班级查询所有同学的信息
	 * @param classes
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List findAllStuByClass_c(Classes classes);
	
	/**
	 * 查询该学生的班级上其他还未曾选择某课程设计计划的同学的信息
	 * @param classes
	 * @param student
	 * @return
	 */
	List findNeedChStuByClass_c(Student student,Cdplan cdplan);
	
	/**
	 * 通过学生的编号查询用户的信息
	 * @param student
	 * @return
	 */
	Student findSudentByStudentId_c(Student student);
}
