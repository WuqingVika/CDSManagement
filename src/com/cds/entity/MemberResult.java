package com.cds.entity;

import java.util.List;

public class MemberResult {
    private int stugroupId;//ѧ��С���ţ�
    private int replyplanId;//���ƻ����
    public int getReplyplanId() {
		return replyplanId;
	}
	public void setReplyplanId(int replyplanId) {
		this.replyplanId = replyplanId;
	}
	@SuppressWarnings("rawtypes")
	private List members;
	public int getStugroupId() {
		return stugroupId;
	}
	public void setStugroupId(int stugroupId) {
		this.stugroupId = stugroupId;
	}
	@SuppressWarnings("rawtypes")
	public List getMembers() {
		return members;
	}
	@SuppressWarnings("rawtypes")
	public void setMembers(List members) {
		this.members = members;
	}
    
    
    
}
