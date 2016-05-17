package com.cds.dao;

import java.util.List;

import com.cds.entity.Cdteachergroup;
import com.cds.entity.Processassshedule;
import com.cds.entity.Student;
import com.cds.entity.Teacher;

/**
 * 过程考核计划数据操作
 * 
 * @author PengChan
 *
 */
public interface IProcessasssheduleDao {
	void save(Processassshedule processassshedule);

	void update(Processassshedule processassshedule);

	void delete(Processassshedule processassshedule);

	Processassshedule find(Processassshedule processassshedule);

	List<Processassshedule> findAll();
	
	/**
	 * 查询某教师制定的课程考核计划表
	 * @param teacher
	 * @param cdteachergroup
	 * @return
	 */
	List findAllProcessShe_c(Teacher teacher,Cdteachergroup cdteachergroup);
	
	/**
	 * 查询某教师制定的课程考核计划表，除去学生已经提交的课题
	 * @param teacher
	 * @param cdteachergroup
	 * @param student
	 * @return
	 */
	List findAllProcSheExcep_c(Teacher teacher,Cdteachergroup cdteachergroup,Student student);
}
