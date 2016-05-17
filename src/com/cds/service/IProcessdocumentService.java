package com.cds.service;

import java.util.List;

import com.cds.entity.Cddesigntopics;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Processdocument;
import com.cds.entity.Student;
import com.cds.entity.Teacher;

public interface IProcessdocumentService {
	void save(Processdocument processdocument);
	void update(Processdocument processdocument);
	void delete(Processdocument processdocument);
	Processdocument find(Processdocument processdocument);
	List<Processdocument> findAll();
	
	/**
	 * ��ѯ�Ѿ��ύ�Ŀγ���ƵĲ���
	 * @param teacher
	 * @param cdteachergroup
	 * @param student
	 * @return
	 */
	List findAllSubmitted_c(Teacher teacher,Cdteachergroup cdteachergroup,Student student);
	
	/**
	 * ��ѯ�γ��������С���Ա�γ�����ϴ��Ĳ���
	 * @param teacher
	 * @param cdteachergroup
	 * @param student
	 * @param cddesigntopics
	 * @return
	 */
	List findAllOtherSubmitted_c(Teacher teacher,Cdteachergroup cdteachergroup,Student student,Cddesigntopics cddesigntopics);
}
