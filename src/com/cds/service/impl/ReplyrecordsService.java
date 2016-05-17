package com.cds.service.impl;

import java.util.List;

import com.cds.dao.facory.DaoFactory;
import com.cds.entity.Replyrecords;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;
import com.cds.service.IReplyrecordsService;

/**
 * ´ð±ç¼ÇÂ¼service
 * @author PengChan
 *
 */
public class ReplyrecordsService implements IReplyrecordsService {

	private DaoFactory daoFactory;

	// ×¢ÈëdaoFactory
	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void save(Replyrecords replyrecords) {
		 daoFactory.getReplyRecordsDao().save(replyrecords);
	}

	@Override
	public void update(Replyrecords replyrecords) {
		 daoFactory.getReplyRecordsDao().update(replyrecords);
	}

	@Override
	public void delete(Replyrecords replyrecords) {
		 daoFactory.getReplyRecordsDao().delete(replyrecords);
	}

	@Override
	public Replyrecords find(Replyrecords replyrecords) {		 
		return daoFactory.getReplyRecordsDao().find(replyrecords);
	}

	@Override
	public List<Replyrecords> findAll() {		 
		return daoFactory.getReplyRecordsDao().findAll();
	}
	
	@Override
	public List findAllReplyRecords_c(Student student,Studentgroup studentgroup) {		 
		return daoFactory.getReplyRecordsDao().findStudentReplyRecords_c(student, studentgroup);
	}

}
