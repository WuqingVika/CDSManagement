package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Replygroup;
import com.cds.entity.Replyplan;
import com.cds.service.IReplyplanService;

public class ReplyplanService implements IReplyplanService {
	//注入DaoFactory
	private DaoFactory daoFactory;
	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	@Override
	public void save(Replyplan replyplan) {
		daoFactory.getReplyplanDao().save(replyplan);
	}

	@Override
	public void update(Replyplan replyplan) {
		daoFactory.getReplyplanDao().update(replyplan);
	}
	

	@Override
	public void delete(Replyplan replyplan) {
		daoFactory.getReplyplanDao().delete(replyplan);
	}

	@Override
	public Replyplan find(Replyplan replyplan) {
		return daoFactory.getReplyplanDao().find(replyplan);
	}

	@Override
	public List<Replyplan> findAll() {
		return daoFactory.getReplyplanDao().findAll();
	}
	
	@Override
	public void addReplyPlan_c(Replyplan replyplan, int subType) {
		//判断提交的方式
		// 如果是选择小组的方式
		if(subType == 0){
			// 添加答辩计划
			daoFactory.getReplyplanDao().save(replyplan);
		}else if(subType == 1){// 如果是构建小组的方式
			// 1.添加代办小组		
			Replygroup replygroup = replyplan.getReplygroup();
			daoFactory.getReplygroupDao().save(replygroup);			 
			// 2.添加答辩任务*/
			replyplan.setReplygroup(replygroup);
			daoFactory.getReplyplanDao().save(replyplan);			
		}
	}

	@Override
	public List findAllReplyGrop_c() {	 
		return daoFactory.getReplyplanDao().findAllReplyGrop_c();
	}
}
