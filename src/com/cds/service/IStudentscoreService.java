package com.cds.service;

import java.util.List;

import com.cds.entity.Student;
import com.cds.entity.Studentscore;

/**
 * 学生成绩service
 * 
 * @author PengChan
 *
 */
public interface IStudentscoreService {
	void save(Studentscore studentscore);// 添加数据操作

	void update(Studentscore studentscore);// 修改数据操作

	void delete(Studentscore studentscore);// 删除数据操作

	Studentscore find(Studentscore studentscore);// 查询数据操作

	List<Studentscore> findAll();// 插叙所有数据操作
	
	/**
	 * 查看学生的所有的当前学期下面的所有的课程设计题目的成绩
	 * @param student
	 * @return
	 */
	List findStudentScore_c(Student student);
}
