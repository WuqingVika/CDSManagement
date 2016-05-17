package com.cds.service;

import java.util.List;

import com.cds.entity.Cdplan;
import com.cds.entity.Classes;
import com.cds.entity.Student;

/**
 * 学生service
 * 
 * @author PengChan
 *
 */
public interface IStudentService {
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
	 * 通过班级查询该班级下面的所有的学生
	 * @param classes
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List findAllByClasses_c(Classes classes);
	
	/**
	 * 查询本班级其余需要选课的学生
	 * @param student
	 * @param cdplan
	 * @return
	 */
	List findAllNeedChoosingStu_c(Student student,Cdplan cdplan);
	
	/**
	 * 通过学生的学号查询用户的信息
	 * @param student
	 * @return
	 */
	Student findStuByStudentId_c(Student student);
}
