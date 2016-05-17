package com.cds.service;

import java.util.List;

import com.cds.entity.Replygroup;

public interface IReplygroupService {
	void save(Replygroup replygroup);
	void update(Replygroup replygroup);
	void delete(Replygroup replygroup);
	Replygroup find(Replygroup replygroup);
	List<Replygroup> findAll();
	
	/**
	 * ��ѯС���µĳ�Ա
	 * 
	 * @param replygroup
	 *            ���С��
	 * @return
	 */
	List findAllTeachersByRpId_c(Replygroup replygroup);
}
