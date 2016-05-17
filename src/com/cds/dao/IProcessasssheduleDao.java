package com.cds.dao;

import java.util.List;

import com.cds.entity.Cdteachergroup;
import com.cds.entity.Processassshedule;
import com.cds.entity.Student;
import com.cds.entity.Teacher;

/**
 * ���̿��˼ƻ����ݲ���
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
	 * ��ѯĳ��ʦ�ƶ��Ŀγ̿��˼ƻ���
	 * @param teacher
	 * @param cdteachergroup
	 * @return
	 */
	List findAllProcessShe_c(Teacher teacher,Cdteachergroup cdteachergroup);
	
	/**
	 * ��ѯĳ��ʦ�ƶ��Ŀγ̿��˼ƻ�����ȥѧ���Ѿ��ύ�Ŀ���
	 * @param teacher
	 * @param cdteachergroup
	 * @param student
	 * @return
	 */
	List findAllProcSheExcep_c(Teacher teacher,Cdteachergroup cdteachergroup,Student student);
}
