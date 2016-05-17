package com.cds.dao.facory;

import com.cds.dao.IAccountDao;
import com.cds.dao.ICdchoosingrecordDao;
import com.cds.dao.ICddesigntopicsDao;
import com.cds.dao.ICdplanDao;
import com.cds.dao.ICdteachergroupDao;
import com.cds.dao.IClassesDao;
import com.cds.dao.ICollegeDao;
import com.cds.dao.ICommentsDao;
import com.cds.dao.IEvalstandardsDao;
import com.cds.dao.IMajorDao;
import com.cds.dao.IProcessasssheduleDao;
import com.cds.dao.IProcessdocumentDao;
import com.cds.dao.IReplygroupDao;
import com.cds.dao.IReplyplanDao;
import com.cds.dao.IReplyrecordsDao;
import com.cds.dao.ISelfevaluationDao;
import com.cds.dao.IStudentDao;
import com.cds.dao.IStudentgroupDao;
import com.cds.dao.IStudentscoreDao;
import com.cds.dao.ITeacherDao;
import com.cds.dao.ITermDao;

/**
 * @author PengChan
 *
 */
public class DaoFactory {
	private IAccountDao accountDao;
	// wq
	private ICdchoosingrecordDao cdchoosingrecordDao;
	private ICddesigntopicsDao cddesigntopicsDao;
	private ICdplanDao cdplanDao;
	private ICdteachergroupDao cdteachergroupDao;
	private IClassesDao classesDao;
	private ICollegeDao collegeDao;

	// zmm
	private ICommentsDao commentsDao;
	private IEvalstandardsDao evalstandardsDao;
	private IMajorDao majorDao;
	private IProcessasssheduleDao processasssheduleDao;
	private IProcessdocumentDao processdocumentDao;
	private IReplygroupDao replygroupDao;
	private IReplyplanDao replyplanDao;

	// cp
	private IReplyrecordsDao replyRecordsDao;
	private ISelfevaluationDao selfEvaluationDao;
	private IStudentDao studentDao;
	private IStudentgroupDao studentGroupDao;
	private IStudentscoreDao studentScoreDao;
	private ITeacherDao teacherDao;
	private ITermDao termDao;

	public ICommentsDao getCommentsDao() {
		return commentsDao;
	}

	public void setCommentsDao(ICommentsDao commentsDao) {
		this.commentsDao = commentsDao;
	}

	public IEvalstandardsDao getEvalstandardsDao() {
		return evalstandardsDao;
	}

	public void setEvalstandardsDao(IEvalstandardsDao evalstandardsDao) {
		this.evalstandardsDao = evalstandardsDao;
	}

	public IMajorDao getMajorDao() {
		return majorDao;
	}

	public void setMajorDao(IMajorDao majorDao) {
		this.majorDao = majorDao;
	}

	public IProcessasssheduleDao getProcessasssheduleDao() {
		return processasssheduleDao;
	}

	public void setProcessasssheduleDao(IProcessasssheduleDao processasssheduleDao) {
		this.processasssheduleDao = processasssheduleDao;
	}

	public IProcessdocumentDao getProcessdocumentDao() {
		return processdocumentDao;
	}

	public void setProcessdocumentDao(IProcessdocumentDao processdocumentDao) {
		this.processdocumentDao = processdocumentDao;
	}

	public IReplygroupDao getReplygroupDao() {
		return replygroupDao;
	}

	public void setReplygroupDao(IReplygroupDao replygroupDao) {
		this.replygroupDao = replygroupDao;
	}

	public IReplyplanDao getReplyplanDao() {
		return replyplanDao;
	}

	public void setReplyplanDao(IReplyplanDao replyplanDao) {
		this.replyplanDao = replyplanDao;
	}

	public ICdchoosingrecordDao getCdchoosingrecordDao() {
		return cdchoosingrecordDao;
	}

	public void setCdchoosingrecordDao(ICdchoosingrecordDao cdchoosingrecordDao) {
		this.cdchoosingrecordDao = cdchoosingrecordDao;
	}

	public ICddesigntopicsDao getCddesigntopicsDao() {
		return cddesigntopicsDao;
	}

	public void setCddesigntopicsDao(ICddesigntopicsDao cddesigntopicsDao) {
		this.cddesigntopicsDao = cddesigntopicsDao;
	}

	public ICdplanDao getCdplanDao() {
		return cdplanDao;
	}

	public void setCdplanDao(ICdplanDao cdplanDao) {
		this.cdplanDao = cdplanDao;
	}

	public ICdteachergroupDao getCdteachergroupDao() {
		return cdteachergroupDao;
	}

	public void setCdteachergroupDao(ICdteachergroupDao cdteachergroupDao) {
		this.cdteachergroupDao = cdteachergroupDao;
	}

	public IClassesDao getClassesDao() {
		return classesDao;
	}

	public void setClassesDao(IClassesDao classesDao) {
		this.classesDao = classesDao;
	}

	public ICollegeDao getCollegeDao() {
		return collegeDao;
	}

	public void setCollegeDao(ICollegeDao collegeDao) {
		this.collegeDao = collegeDao;
	}

	public IReplyrecordsDao getReplyRecordsDao() {
		return replyRecordsDao;
	}

	public void setReplyRecordsDao(IReplyrecordsDao replyRecordsDao) {
		this.replyRecordsDao = replyRecordsDao;
	}

	public ISelfevaluationDao getSelfEvaluationDao() {
		return selfEvaluationDao;
	}

	public void setSelfEvaluationDao(ISelfevaluationDao selfEvaluationDao) {
		this.selfEvaluationDao = selfEvaluationDao;
	}

	public IStudentDao getStudentDao() {
		return studentDao;
	}

	public void setStudentDao(IStudentDao studentDao) {
		this.studentDao = studentDao;
	}

	public IStudentgroupDao getStudentGroupDao() {
		return studentGroupDao;
	}

	public void setStudentGroupDao(IStudentgroupDao studentGroupDao) {
		this.studentGroupDao = studentGroupDao;
	}

	public IStudentscoreDao getStudentScoreDao() {
		return studentScoreDao;
	}

	public void setStudentScoreDao(IStudentscoreDao studentScoreDao) {
		this.studentScoreDao = studentScoreDao;
	}

	public ITeacherDao getTeacherDao() {
		return teacherDao;
	}

	public void setTeacherDao(ITeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	public ITermDao getTermDao() {
		return termDao;
	}

	public void setTermDao(ITermDao termDao) {
		this.termDao = termDao;
	}

	public void setAccountDao(IAccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public IAccountDao getAccountDao() {
		return accountDao;
	}
}
