package com.cds.service;

import java.util.List;

import com.cds.entity.Replyrecords;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;

/**
 * 答辩记录服务service接口
 * @author PengChan
 *
 */
public interface IReplyrecordsService {
	void save(Replyrecords replyrecords);//添加数据操作
	void update(Replyrecords replyrecords);//修改数据操作
	void delete(Replyrecords replyrecords);//删除数据操作
	Replyrecords find(Replyrecords replyrecords);//查询数据操作
	List<Replyrecords> findAll();//查询所有的数据操作
	
	/**
	 * 查询学生的答辩记录
	 * @param student
	 * @return
	 */
	List findAllReplyRecords_c(Student student,Studentgroup studentgroup);
}
