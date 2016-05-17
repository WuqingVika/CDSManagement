package com.cds.dao;

import java.util.List;

import com.cds.entity.Replyplan;

public interface IReplyplanDao {
	void save(Replyplan replyplan);

	void update(Replyplan replyplan);

	void delete(Replyplan replyplan);

	Replyplan find(Replyplan replyplan);

	List<Replyplan> findAll();
	
	/**
	 * 查看所有的答辩小组
	 * @param replyplan
	 * @return
	 */
	List findAllReplyGrop_c();
}
