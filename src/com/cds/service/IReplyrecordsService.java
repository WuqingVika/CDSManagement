package com.cds.service;

import java.util.List;

import com.cds.entity.Replyrecords;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;

/**
 * ����¼����service�ӿ�
 * @author PengChan
 *
 */
public interface IReplyrecordsService {
	void save(Replyrecords replyrecords);//������ݲ���
	void update(Replyrecords replyrecords);//�޸����ݲ���
	void delete(Replyrecords replyrecords);//ɾ�����ݲ���
	Replyrecords find(Replyrecords replyrecords);//��ѯ���ݲ���
	List<Replyrecords> findAll();//��ѯ���е����ݲ���
	
	/**
	 * ��ѯѧ���Ĵ���¼
	 * @param student
	 * @return
	 */
	List findAllReplyRecords_c(Student student,Studentgroup studentgroup);
}
