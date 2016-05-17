package com.cds.service;

import java.util.List;

import com.cds.entity.Cddesigntopics;
import com.cds.entity.Cdplan;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Classes;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;

public interface ICddesigntopicsService {
	void save(Cddesigntopics cddesigntopics);//添加
	void update(Cddesigntopics cddesigntopics);//修改
	void delete(Cddesigntopics cddesigntopics);//删除
	Cddesigntopics find(Cddesigntopics cddesigntopics);//查询
	List<Cddesigntopics> findAll();//查询所有的数据
	
	/**
	 * 通过小组的编号，查询该小组下的所有的课题
	 * @param cdteachergroup
	 * @return
	 */
	List findAllTopicByTGpId_c(Cdteachergroup cdteachergroup);
	
	/**
	 * 学生自主选题
	 * @param cddesigntopics
	 * @param studentgroup
	 * @return
	 */
	boolean selfSelectCDTopic_c(Cddesigntopics cddesigntopics,Studentgroup studentgroup);
	
	/**
	 * 学生选择指导老师所定的题目
	 * @param studentgroup
	 * @return
	 */
	boolean selectCDTopic_c(Studentgroup studentgroup);
	
	/**
	 * 查询所有的自己选择的题目信息
	 * @param student
	 * @return
	 */
	List findSelfChoosedCD_c(Student student);
	
	/**
	 * 查询所有的已经选择的题目的信息
	 * @param student
	 * @return
	 */
	List findChoosedThCD_c(Student student);
	
	/**
	 * 查询所有已经选择的题目
	 * @return
	 */
	List findAllChoosedCD_c(Student student);
	
	/**
	 * 通过班级的编号和课程设计计划查询该班级下面所有的小组选题的信息
	 * @param classes
	 * @param cdplan
	 * @return
	 */  
	List findCDByClAndCdPlan_c(Classes classes,Cdplan cdplan);
	
	/**
	 * 查询某个学生在当前学期下面选择了哪些课程
	 * @param student
	 * @return
	 */
	List findDesignTopics_c(Student student);
}
