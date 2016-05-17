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
	 * ��Ӵ��ƻ������subtypes�ύ������ֵΪ0��ֱ��ѡ��С�鷽ʽ;1:����С��
	 * @param replyplan ���ƻ�
	 * @param replygroup ���С��
	 * @param subType �ύ�ķ�ʽ
	 */
	void addReplyPlan_c(Replyplan replyplan,int subType);
	
	/**
	 * �鿴���еĴ��С��
	 * @param replyplan
	 * @return
	 */
	List findAllReplyGrop_c();
}
