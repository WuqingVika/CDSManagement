package com.cds.service;

import java.util.List;

import com.cds.entity.Cddesigntopics;
import com.cds.entity.Selfevaluation;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;

/**
 * 自评service
 * 
 * @author PengChan
 *
 */
public interface ISelfevaluationService {
	void save(Selfevaluation selfevaluation);// 添加数据操作

	void update(Selfevaluation selfevaluation);// 修改数据操作

	void delete(Selfevaluation selfevaluation);// 删除数据操作

	Selfevaluation find(Selfevaluation selfevaluation);// 查询数据操作

	List<Selfevaluation> findAll();// 查询所有
	
	/**
	 * 判断学生是否已经自评
	 * @param cddesigntopics
	 * @param student
	 * @return
	 */
	boolean studentHasEvaled_c(Cddesigntopics cddesigntopics,Student student);
	
	
	/**
	 * 查询
	 * @param cddesigntopics
	 * @param student
	 * @return
	 */
	List findAllStuGroupSelEv_c(Cddesigntopics cddesigntopics,Studentgroup studentgroup);
}
