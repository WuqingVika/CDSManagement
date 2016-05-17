package com.cds.service;

import java.util.List;

import com.cds.entity.Replyplan;

public interface IReplyplanService {
	void save(Replyplan replyplan);
	void update(Replyplan replyplan);
	void delete(Replyplan replyplan);
	Replyplan find(Replyplan replyplan);
	List<Replyplan> findAll();
	
	/**
	 * 添加答辩计划，如果subtypes提交的类型值为0：直接选择小组方式;1:构建小组
	 * @param replyplan 答辩计划
	 * @param replygroup 答辩小组
	 * @param subType 提交的方式
	 */
	void addReplyPlan_c(Replyplan replyplan,int subType);
	
	/**
	 * 查看所有的答辩小组
	 * @param replyplan
	 * @return
	 */
	List findAllReplyGrop_c();
}
