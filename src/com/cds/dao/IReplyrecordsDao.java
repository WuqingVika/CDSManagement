package com.cds.dao;

import java.util.List;

import com.cds.entity.Replyrecords;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;

/**
 * ����¼dao
 * 
 * @author PengChan
 *
 */
public interface IReplyrecordsDao {
	void save(Replyrecords replyrecords);// ������ݲ���

	void update(Replyrecords replyrecords);// �޸����ݲ���

	void delete(Replyrecords replyrecords);// ɾ�����ݲ���

	Replyrecords find(Replyrecords replyrecords);// ��ѯ���ݲ���

	List<Replyrecords> findAll();// ��ѯ���е����ݲ���
	
	/**
	 * ��ѯѧ���Ĵ���¼
	 * @param student
	 * @param studentgroup
	 * @return
	 */
	List findStudentReplyRecords_c(Student student,Studentgroup studentgroup);
}
