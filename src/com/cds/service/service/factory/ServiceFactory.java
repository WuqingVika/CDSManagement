package com.cds.service.service.factory;

import com.cds.service.ICommentsService;
import com.cds.service.IEvalstandardsService;
import com.cds.service.IMajorService;
import com.cds.service.IProcessasssheduleService;
import com.cds.service.IProcessdocumentService;
import com.cds.service.IReplygroupService;
import com.cds.service.IReplyplanService;
import com.cds.service.IReplyrecordsService;
import com.cds.service.IResultService;
import com.cds.service.ISelfevaluationService;
import com.cds.service.IStudentGroupService;
import com.cds.service.IStudentService;
import com.cds.service.IStudentscoreService;
import com.cds.service.ITeacherService;
import com.cds.service.ITermService;
import com.cds.service.impl.AccountService;
import com.cds.service.impl.CdchoosingrecordService;
import com.cds.service.impl.CddesigntopicsService;
import com.cds.service.impl.CdplanService;
import com.cds.service.impl.CdteachergroupService;
import com.cds.service.impl.ClassesService;
import com.cds.service.impl.CollegeService;

/**
 * service工厂类
 *
 */
public class ServiceFactory {

	private IResultService pageBeanService;// pageBean实体类

	// wq的service部分
	private AccountService accountService;
	private CdchoosingrecordService cdchoosingrecordService;
	private CddesigntopicsService cddesigntopicsService;
	private CdplanService cdplanService;
	private ClassesService classesService;
	private CdteachergroupService cdteachergroupService;
	private CollegeService collegeService;

	// zmm的service部分
	private ICommentsService commentsService;
	private IEvalstandardsService evalstandardsService;
	private IMajorService majorService;
	private IProcessasssheduleService processasssheduleService;
	private IProcessdocumentService processdocumentService;
	private IReplygroupService replygroupService;
	private IReplyplanService replyplanService;

	// cp的service部分
	private IReplyrecordsService replyRecordsService;
	private ISelfevaluationService selfEvaluationService;
	private IStudentService studentService;
	private IStudentGroupService studentGroupService;
	private IStudentscoreService studentScoreService;
	private ITeacherService teacherService;
	private ITermService termService;

	public ICommentsService getCommentsService() {
		return commentsService;
	}

	public void setCommentsService(ICommentsService commentsService) {
		this.commentsService = commentsService;
	}

	public IEvalstandardsService getEvalstandardsService() {
		return evalstandardsService;
	}

	public void setEvalstandardsService(IEvalstandardsService evalstandardsService) {
		this.evalstandardsService = evalstandardsService;
	}

	public IMajorService getMajorService() {
		return majorService;
	}

	public void setMajorService(IMajorService majorService) {
		this.majorService = majorService;
	}

	public IProcessasssheduleService getProcessasssheduleService() {
		return processasssheduleService;
	}

	public void setProcessasssheduleService(IProcessasssheduleService processasssheduleService) {
		this.processasssheduleService = processasssheduleService;
	}

	public IProcessdocumentService getProcessdocumentService() {
		return processdocumentService;
	}

	public void setProcessdocumentService(IProcessdocumentService processdocumentService) {
		this.processdocumentService = processdocumentService;
	}

	public IReplygroupService getReplygroupService() {
		return replygroupService;
	}

	public void setReplygroupService(IReplygroupService replygroupService) {
		this.replygroupService = replygroupService;
	}

	public IReplyplanService getReplyplanService() {
		return replyplanService;
	}

	public void setReplyplanService(IReplyplanService replyplanService) {
		this.replyplanService = replyplanService;
	}

	public CdchoosingrecordService getCdchoosingrecordService() {
		return cdchoosingrecordService;
	}

	public void setCdchoosingrecordService(CdchoosingrecordService cdchoosingrecordService) {
		this.cdchoosingrecordService = cdchoosingrecordService;
	}

	public CddesigntopicsService getCddesigntopicsService() {
		return cddesigntopicsService;
	}

	public void setCddesigntopicsService(CddesigntopicsService cddesigntopicsService) {
		this.cddesigntopicsService = cddesigntopicsService;
	}

	public CdplanService getCdplanService() {
		return cdplanService;
	}

	public void setCdplanService(CdplanService cdplanService) {
		this.cdplanService = cdplanService;
	}

	public ClassesService getClassesService() {
		return classesService;
	}

	public void setClassesService(ClassesService classesService) {
		this.classesService = classesService;
	}

	public CdteachergroupService getCdteachergroupService() {
		return cdteachergroupService;
	}

	public void setCdteachergroupService(CdteachergroupService cdteachergroupService) {
		this.cdteachergroupService = cdteachergroupService;
	}

	public CollegeService getCollegeService() {
		return collegeService;
	}

	public void setCollegeService(CollegeService collegeService) {
		this.collegeService = collegeService;
	}

	public IReplyrecordsService getReplyRecordsService() {
		return replyRecordsService;
	}

	public void setReplyRecordsService(IReplyrecordsService replyRecordsService) {
		this.replyRecordsService = replyRecordsService;
	}

	public ISelfevaluationService getSelfEvaluationService() {
		return selfEvaluationService;
	}

	public void setSelfEvaluationService(ISelfevaluationService selfEvaluationService) {
		this.selfEvaluationService = selfEvaluationService;
	}

	public IStudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(IStudentService studentService) {
		this.studentService = studentService;
	}

	public IStudentGroupService getStudentGroupService() {
		return studentGroupService;
	}

	public void setStudentGroupService(IStudentGroupService studentGroupService) {
		this.studentGroupService = studentGroupService;
	}

	public IStudentscoreService getStudentScoreService() {
		return studentScoreService;
	}

	public void setStudentScoreService(IStudentscoreService studentScoreService) {
		this.studentScoreService = studentScoreService;
	}

	public ITeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(ITeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public ITermService getTermService() {
		return termService;
	}

	public void setTermService(ITermService termService) {
		this.termService = termService;
	}

	public IResultService getPageBeanService() {
		return pageBeanService;
	}

	public void setPageBeanService(IResultService pageBeanService) {
		this.pageBeanService = pageBeanService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public AccountService getAccountService() {
		return this.accountService;
	}
}
