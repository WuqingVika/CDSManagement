package com.cds.dao;

import java.util.List;

import com.cds.entity.Cddesigntopics;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Processdocument;
import com.cds.entity.Student;
import com.cds.entity.Teacher;

/**
 * 
 * �����ĵ����ݿ����
 *
 */
public interface IProcessdocumentDao {
	void save(Processdocument processdocument);

	void update(Processdocument processdocument);

	void delete(Processdocument processdocument);

	Processdocument find(Processdocument processdocument);

	List<Processdocument> findAll();
	
	/**
	 * ��ѯѧ������ĳһ���γ�����Ѿ��ύ�Ĳ��ϵ���Ϣ
	 * @param teacher
	 * @param cdteachergroup
	 * @return
	 */
	List findAllSubmitted_c(Teacher teacher,Cdteachergroup cdteachergroup,Student student);
	
	/**
	 * �鿴С��������Ա�Ѿ��ύ�Ŀ������Ϣ
	 * @param teacher
	 * @param cdteachergroup
	 * @param student
	 * @return
	 */
	List findAllOtherGroupMemb_c(Teacher teacher,Cdteachergroup cdteachergroup,Student student,Cddesigntopics cddesigntopics);
}
