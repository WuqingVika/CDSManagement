package com.cds.dao;

import java.util.List;

import com.cds.entity.Student;
import com.cds.entity.Studentscore;

/**
 * 学生成绩dao层
 * 
 * @author PengChan
 *
 */
public interface IStudentscoreDao {
	void save(Studentscore studentscore);// 添加数据操作

	void update(Studentscore studentscore);// 修改数据操作

	void delete(Studentscore studentscore);// 删除数据操作

	Studentscore find(Studentscore studentscore);// 查询数据操作

	List<Studentscore> findAll();// 插叙所有数据操作
	
	/**
	 * 查询某学生本学期的所有的课程设计成绩的信息
	 * @param student
	 * @return
	 */
	List findStudentScore_c(Student student);
}
