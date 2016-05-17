package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Replygroup;
import com.cds.entity.Replyplan;
import com.cds.service.IReplyplanService;

public class ReplyplanService implements IReplyplanService {
	//ע��DaoFactory
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
		//�ж��ύ�ķ�ʽ
		// �����ѡ��С��ķ�ʽ
		if(subType == 0){
			// ��Ӵ��ƻ�
			daoFactory.getReplyplanDao().save(replyplan);
		}else if(subType == 1){// ����ǹ���С��ķ�ʽ
			// 1.��Ӵ���С��		
			Replygroup replygroup = replyplan.getReplygroup();
			daoFactory.getReplygroupDao().save(replygroup);			 
			// 2.��Ӵ������*/
			replyplan.setReplygroup(replygroup);
			daoFactory.getReplyplanDao().save(replyplan);			
		}
	}

	@Override
	public List findAllReplyGrop_c() {	 
		return daoFactory.getReplyplanDao().findAllReplyGrop_c();
	}
}
