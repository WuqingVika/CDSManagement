package com.cds.service;

import java.util.List;

import com.cds.entity.Cdteachergroup;
import com.cds.entity.Processassshedule;
import com.cds.entity.Student;
import com.cds.entity.Teacher;

public interface IProcessasssheduleService {
	void save(Processassshedule processassshedule);
	void update(Processassshedule processassshedule);
	void delete(Processassshedule processassshedule);
	Processassshedule find(Processassshedule processassshedule);
	List<Processassshedule> findAll();
	
	/**
	 * 查看某一个教师对于某一个课程设计计划所制定的课程设计过程考核计划
	 * @param teacher
	 * @param cdteachergroup
	 * @return
	 */
	List findAllProcessShe_c(Teacher teacher,Cdteachergroup cdteachergroup);
	
	/**
	 * 查看某一个教师对于某一个课程设计计划所制定的课程设计过程考核计划，除去学生已经提交的课题
	 * @param teacher
	 * @param cdteachergroup
	 * @param student
	 * @return
	 */
	List findAllProcExpSubmited_c(Teacher teacher,Cdteachergroup cdteachergroup,Student student);
}
