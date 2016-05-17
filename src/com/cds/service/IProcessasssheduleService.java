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
	 * �鿴ĳһ����ʦ����ĳһ���γ���Ƽƻ����ƶ��Ŀγ���ƹ��̿��˼ƻ�
	 * @param teacher
	 * @param cdteachergroup
	 * @return
	 */
	List findAllProcessShe_c(Teacher teacher,Cdteachergroup cdteachergroup);
	
	/**
	 * �鿴ĳһ����ʦ����ĳһ���γ���Ƽƻ����ƶ��Ŀγ���ƹ��̿��˼ƻ�����ȥѧ���Ѿ��ύ�Ŀ���
	 * @param teacher
	 * @param cdteachergroup
	 * @param student
	 * @return
	 */
	List findAllProcExpSubmited_c(Teacher teacher,Cdteachergroup cdteachergroup,Student student);
}
