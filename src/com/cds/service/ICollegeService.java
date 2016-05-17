package com.cds.service;

import java.util.List;

import com.cds.entity.Cdplan;
import com.cds.entity.Classes;
import com.cds.entity.College;
import com.cds.entity.Teacher;

public interface ICollegeService {
	void save(College college);//添加
	void update(College college);//修改
	void delete(College college);//删除
	College find(College college);//查询
	List<College> findAll();//查询所有的数据
	/**
	 * 根据学院名称获得该学院的实体类。每个学院的名称是唯一的，所以返回一个实体对象
	 * @param collegeName 学院名称
	 * @return 该学院实体对象
	 */
	College findCollegeByName_z(String collegeName);
	/**
	 * 通过学院编号查询所有的班级
	 * @param collegeId 学院编号
	 * @return 班级列表
	 */
	List<Classes> findClassesByCollegeId_z(int collegeId);
	/**
	 * 通过学院编号查找所有本学院的学生
	 * @param collegeId
	 * @return
	 */
	List findStudentsByCollegeId(int collegeId);
	/**
	 * 通过学院找到所有的课程计划
	 * @param collegeId
	 * @return
	 */
	List<Cdplan> findCdplansByCollegeId(int collegeId);
	
	/**
	 * 查看学院下面所有的教师
	 * 
	 * @param college
	 * @return
	 */
	public List findAllTeacher_c(Teacher teacher);
}
