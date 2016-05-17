package com.cds.dao;

import java.util.List;

import com.cds.entity.Replygroup;
import com.cds.entity.Teacher;

public interface IReplygroupDao {
	void save(Replygroup replygroup);

	void update(Replygroup replygroup);

	void delete(Replygroup replygroup);

	Replygroup find(Replygroup replygroup);

	List<Replygroup> findAll();
	
	/**
	 * ��ѯС���µĳ�Ա
	 * @param replygroup ���С��
	 * @return
	 */
	List findAllTeachersByRpId_c(Replygroup replygroup);
	
	/**
	 * �����ʦС���������Ա
	 */
	void addReplyMembers_c(Teacher teacher,Replygroup replygroup);
}
