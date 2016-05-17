package com.cds.dao;

import java.util.List;

import com.cds.entity.Cddesigntopics;
import com.cds.entity.Cdplan;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Classes;
import com.cds.entity.Student;

public interface ICddesigntopicsDao {
	void save(Cddesigntopics cddesigntopics);// 添加

	void update(Cddesigntopics cddesigntopics);// 修改

	void delete(Cddesigntopics cddesigntopics);// 删除

	Cddesigntopics find(Cddesigntopics cddesigntopics);// 查询

	List<Cddesigntopics> findAll();// 查询所有的数据
	
	/**
	 * 通过教师的小组的编号，查询该小组下的所有的课题信息
	 * @param cdteachergroup
	 * @return
	 */
	List findTopicByCdGroupId_c(Cdteachergroup cdteachergroup);
	
	
	/**
	 * 查询所有的当前学期的所有的自选的题目的信息
	 * @param student
	 * @return
	 */
	List findAllSelfChoosedCD_c(Student student);
	
	/**
	 * 查询所有的当前学期的选择教师题目的信息
	 * @param student
	 * @return
	 */
	List findAllChoosedThCD_c(Student student);
	
	/**
	 * 查询所当前学期已经选择的课题的信息
	 * @param student
	 * @return
	 */
	List findAllChoosedCD_c(Student student);
	
	/**
	 * 查询一个班级下面的一个课程设计计划下面有哪些课题
	 * @param classes
	 * @param cdplan
	 * @return
	 */
	List findCDByClassAndCDPlan_c(Classes classes,Cdplan cdplan);
	
	
	/**
	 * 查询某一个学生当前学期下面选择了哪些课程
	 * @param student
	 * @return
	 */
	List findCdDesignTopic_c(Student student);
}
