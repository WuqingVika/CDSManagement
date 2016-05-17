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
	 * 查询小组下的成员
	 * @param replygroup 答辩小组
	 * @return
	 */
	List findAllTeachersByRpId_c(Replygroup replygroup);
	
	/**
	 * 向答辩教师小组中添加组员
	 */
	void addReplyMembers_c(Teacher teacher,Replygroup replygroup);
}
