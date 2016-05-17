package com.cds.dao;

import java.util.List;

import com.cds.entity.Cddesigntopics;
import com.cds.entity.Selfevaluation;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;

/**
 * 自我评价dao层
 * 
 * @author PengChan
 *
 */
public interface ISelfevaluationDao {
	void save(Selfevaluation selfevaluation);// 添加数据操作

	void update(Selfevaluation selfevaluation);// 修改数据操作

	void delete(Selfevaluation selfevaluation);// 删除数据操作

	Selfevaluation find(Selfevaluation selfevaluation);// 查询数据操作

	List<Selfevaluation> findAll();// 查询所有
	
	/**
	 * 根据学生的编号和课程设计题目的编号查询自评
	 * @param cddesigntopics
	 * @param student
	 * @return
	 */
	Selfevaluation findSelEval_c(Cddesigntopics cddesigntopics,Student student);
		
	/**
	 * 查看课程设计小组其他成员对于某一个个课程设计的所有的自我评价
	 * @param cddesigntopics
	 * @param studentgroup
	 * @return
	 */
	List findStuGroupEval_c(Cddesigntopics cddesigntopics,Studentgroup studentgroup);
}
