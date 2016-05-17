package com.cds.dao;

import java.util.List;

import com.cds.entity.Cddesigntopics;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Processdocument;
import com.cds.entity.Student;
import com.cds.entity.Teacher;

/**
 * 
 * 过程文档数据库操作
 *
 */
public interface IProcessdocumentDao {
	void save(Processdocument processdocument);

	void update(Processdocument processdocument);

	void delete(Processdocument processdocument);

	Processdocument find(Processdocument processdocument);

	List<Processdocument> findAll();
	
	/**
	 * 查询学生对于某一个课程设计已经提交的材料的信息
	 * @param teacher
	 * @param cdteachergroup
	 * @return
	 */
	List findAllSubmitted_c(Teacher teacher,Cdteachergroup cdteachergroup,Student student);
	
	/**
	 * 查看小组其他成员已经提交的课题的信息
	 * @param teacher
	 * @param cdteachergroup
	 * @param student
	 * @return
	 */
	List findAllOtherGroupMemb_c(Teacher teacher,Cdteachergroup cdteachergroup,Student student,Cddesigntopics cddesigntopics);
}
