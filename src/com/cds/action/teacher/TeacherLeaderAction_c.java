package com.cds.action.teacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.Servlet;

import org.apache.struts2.ServletActionContext;
import org.aspectj.weaver.ast.Var;
import org.hibernate.classic.Validatable;

import com.alibaba.fastjson.JSON;
import com.cds.entity.Account;
import com.cds.entity.Cdplan;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Evalstandards;
import com.cds.entity.PageBean;
import com.cds.entity.Replygroup;
import com.cds.entity.Replyplan;
import com.cds.entity.Studentgroup;
import com.cds.entity.Teacher;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

import javafx.scene.control.Alert;

public class TeacherLeaderAction_c extends ActionSupport {
	private static final long serialVersionUID = 1L;

	// ע��serviceFactory
	private ServiceFactory serviceFactory;

	public void setServiceFactory(ServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	private int pno = 0;// ҳ��

	private String teachersString;// ��ʦ*

	private int teacherGroupId;// ��ʦС����

	private int teacherId;// ��ʦ���

	private String teacherWorkId;// ��ʦ����

	private Evalstandards evalstandards; // ���ֱ�׼

	private int cdplanId = -1;// �γ���Ƽƻ����

	private int theReplyGroupId;// ���С����

	private Replygroup replygroup;// ���С��

	private Replyplan replyplan;// �������

	private int theReplyPlanId;// ���������

	private int stuGroupId;// ѧ��С����

	private int subType = -1;// �ύ�ķ�ʽ���û����ύ�������ʱ����Ҫ�ύʱ��ѡ�Ļ���������

	public int getTheReplyGroupId() {
		return theReplyGroupId;
	}

	public void setTheReplyGroupId(int theReplyGroupId) {
		this.theReplyGroupId = theReplyGroupId;
	}

	public int getTheReplyPlanId() {
		return theReplyPlanId;
	}

	public void setTheReplyPlanId(int theReplyPlanId) {
		this.theReplyPlanId = theReplyPlanId;
	}

	public Replyplan getReplyplan() {
		return replyplan;
	}

	public void setReplyplan(Replyplan replyplan) {
		this.replyplan = replyplan;
	}

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}

	public Replygroup getReplygroup() {
		return replygroup;
	}

	public void setReplygroup(Replygroup replygroup) {
		this.replygroup = replygroup;
	}

	public int getStuGroupId() {
		return stuGroupId;
	}

	public void setStuGroupId(int stuGroupId) {
		this.stuGroupId = stuGroupId;
	}

	public int getCdplanId() {
		return cdplanId;
	}

	public void setCdplanId(int cdplanId) {
		this.cdplanId = cdplanId;
	}

	public Evalstandards getEvalstandards() {
		return evalstandards;
	}

	public void setEvalstandards(Evalstandards evalstandards) {
		this.evalstandards = evalstandards;
	}

	public String getTeacherWorkId() {
		return teacherWorkId;
	}

	public void setTeacherWorkId(String teacherWorkId) {
		this.teacherWorkId = teacherWorkId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public int getTeacherGroupId() {
		return teacherGroupId;
	}

	public void setTeacherGroupId(int teacherGroupId) {
		this.teacherGroupId = teacherGroupId;
	}

	public String getTeachersString() {
		return teachersString;
	}

	public void setTeachersString(String teachersString) {
		this.teachersString = teachersString;
	}

	public int getPno() {
		return pno;
	}

	public void setPno(int pno) {
		this.pno = pno;
	}

	/**
	 * ��ȡ��ʦ����Ϣ
	 * 
	 * @return
	 */
	private Teacher getTeacher() {
		if (ServletActionContext.getRequest().getSession().getAttribute("account") != null) {
			Account account = (Account) ServletActionContext.getRequest().getSession().getAttribute("account");
			account = serviceFactory.getAccountService().find(account);
			Teacher teacher = new Teacher();
			for (Teacher teach : account.getTeachers()) {
				teacher = teach;
			}
			return teacher;
		} else {
			return null;
		}		
	}

	/**
	 * ��ѯ��ǰ��ʦ���еĻ�δ�������Ա�Ŀγ���Ƽƻ�����Щ
	 */
	public String findAllNeedAllowCD() {
		Teacher teacher = getTeacher();
		String hql = "select distinct cdplan.cDPlanId,cdplan.cDPlanNum,"
				+ " cdplan.cDPlanName,major.majorName,term.termName,"
				+ " cdplan.totalCredits,cdplan.totalClassHour,cdteachergroup.cDTeacherGroupId"
				+ " from teacher,cdteachergroup,cdplan,term,major"
				+ " where cdteachergroup.teacherId = teacher.teacherId"
				+ " and cdteachergroup.cDPlanId = cdplan.cDPlanId" + " and cdplan.termId = term.termId"
				+ " and cdplan.majorId = major.majorId" + " and cdplan.isCurrent = 1"
				+ " and cdteachergroup.teacherId =" + teacher.getTeacherId()
				+ " and cdteachergroup.cDTeacherGroupId in (" + "	select cdteachergroup.cDTeacherGroupId"
				+ "		from teacher,cdteachergroup,cdplan" + "		where teacher.teacherId = cdteachergroup.teacherId"
				+ "		and cdteachergroup.cDPlanId = cdplan.cDPlanId" + "		and cdplan.isCurrent = 1"
				+ "		and cdteachergroup.teacherId =" + teacher.getTeacherId()
				+ "		and cdteachergroup.cDTeacherGroupId not in("
				+ "				select distinct cdteachergroup.cDTeacherGroupId"
				+ "				from teacher,cdgroupteachers,cdteachergroup,cdplan"
				+ "				where teacher.teacherId = cdgroupteachers.teacherId"
				+ "				and cdgroupteachers.cDTeacherGroupId = cdteachergroup.cDTeacherGroupId"
				+ "				and cdteachergroup.cDPlanId = cdplan.cDPlanId"
				+ "				and cdplan.isCurrent = 1" + "				and teacher.teacherId<>"
				+ teacher.getTeacherId() + "				and cdteachergroup.teacherId =" + teacher.getTeacherId()
				+ "		)" + " )";
		ServletActionContext.getRequest().setAttribute("pageBean",
				serviceFactory.getPageBeanService().getPageBean(hql, 10, pno));
		return "findAllNeedAllowCD";
	}

	/**
	 * �鿴ĳ���γ�������鳤�Ѿ������Ա�Ŀγ̼ƻ���Ϣ
	 * 
	 * @return
	 */
	private PageBean getMyGroupMemBean(Teacher teacher) {
		PageBean pageBean = new PageBean();
		String hql = "select distinct cdplan.cDPlanId,cdplan.cDPlanNum,cdplan.cDPlanName,"
				+ " major.majorName,term.termName,cdplan.totalCredits,cdplan.totalClassHour,cdteachergroup.cDTeacherGroupId"
				+ " from cdteachergroup,teacher,cdplan,major,term,cdgroupteachers"
				+ " where cdteachergroup.teacherId = teacher.teacherId"
				+ " and cdteachergroup.cDPlanId = cdplan.cDPlanId" + " and cdplan.termId = term.termId"
				+ " and cdplan.majorId = major.majorId" + " and cdplan.isCurrent = 1"
				+ " and cdteachergroup.cDTeacherGroupId = cdgroupteachers.cDTeacherGroupId and cdgroupteachers.teacherId <>"
				+ teacher.getTeacherId() + " and cdteachergroup.teacherId=" + teacher.getTeacherId();
		pageBean = serviceFactory.getPageBeanService().getPageBean(hql, 10, pno);
		return pageBean;
	}

	/**
	 * �鿴ѧԺ�����еĽ�ʦ
	 * 
	 * @throws IOException
	 */
	public void findAllTeachers() throws IOException {
		Teacher teacher = getTeacher();
		List allTeachers = serviceFactory.getCollegeService().findAllTeacher_c(teacher);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(allTeachers));
		System.out.println(JSON.toJSONString(allTeachers));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * ��ӽ�ʦС��
	 * 
	 * @throws IOException
	 */
	public void addTeachers() throws IOException {		 
		String[] teachStr = teachersString.split(",");
		// ��ȡ�����еĽ�ʦ
		List<Teacher> teachers = new ArrayList<Teacher>();
		for (int i = 0; i < teachStr.length; i++) {
			Teacher teacher = new Teacher();
			teacher.setTeacherId(Integer.parseInt(teachStr[i].trim()));
			teachers.add(teacher);
		}
		// ��ȡ����ʦС��
		Cdteachergroup cdteachergroup = new Cdteachergroup();
		cdteachergroup.setCdteacherGroupId(this.teacherGroupId);		 
		// ��ӽ�ʦ��Ϣ
		serviceFactory.getCdteachergroupService().addThGroupMember_c(teachers, cdteachergroup);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString("success"));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * �鿴�����Ѿ�����С���Ա��
	 * 
	 * @return
	 */
	public String findAllAllowedMem() {
		ServletActionContext.getRequest().setAttribute("pageBean", getMyGroupMemBean(getTeacher()));
		return "findAllAllowedMem";
	}

	/**
	 * �鿴���е�С���½�ʦ����Ϣ
	 * 
	 * @throws IOException
	 */
	public void findTeachGroupMem() throws IOException {
		Teacher teacher = getTeacher();
		Cdteachergroup cdteachergroup = new Cdteachergroup();
		cdteachergroup.setTeacher(teacher);
		cdteachergroup.setCdteacherGroupId(this.teacherGroupId);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(
				JSON.toJSONString(serviceFactory.getCdteachergroupService().findTeacherByTeachGroup_c(cdteachergroup)));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * ɾ����ʦС���Ա
	 * 
	 * @throws IOException
	 */
	public void removeTeachGroupMem() throws IOException {
		// ��װʵ����
		Teacher teacher = new Teacher();
		teacher.setTeacherId(this.teacherId);
		Cdteachergroup cdteachergroup = new Cdteachergroup();
		cdteachergroup.setCdteacherGroupId(this.teacherGroupId);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		try {
			serviceFactory.getCdteachergroupService().deleteTeachGroupMem_c(cdteachergroup, teacher);
			ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString("success"));
		} catch (Exception e) {
			e.printStackTrace();
			ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString("error"));
		}
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * ��ӽ�ʦ��Ϣ
	 * 
	 * @throws IOException
	 */
	public void addTeacher() throws IOException {
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		try {
			// ����һ����ʦ��ʵ����
			Teacher teacher = new Teacher();
			teacher.setTeacherWorkId(this.teacherWorkId);
			// ͨ����ʦ�Ĺ��Ų�ѯ��ʦ����Ϣ
			teacher = serviceFactory.getTeacherService().findTeacherByTeachWId_c(teacher);
			// ����С��ʵ����
			Cdteachergroup cdteachergroup = new Cdteachergroup();
			cdteachergroup.setCdteacherGroupId(this.teacherGroupId);
			// ��ӽ�ʦС��
			serviceFactory.getCdteachergroupService().addTeacher_c(cdteachergroup, teacher);
			// ���ص�ǰ̨�Ľ��
			ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString("success"));
		} catch (Exception e) {
			// ���ص�ǰ̨����ʧ��
			ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString("error"));
		}
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * ��ѯ����δ������Ŀγ̼ƻ���Ϣ
	 * 
	 * @return
	 */
	public String findStillEvalStand() {
		Teacher teacher = getTeacher();
		String hql = "select cdplan.cDPlanId,cdplan.cDPlanNum,cdplan.cDPlanName,major.majorName,"
				+ " term.termName,cdplan.totalCredits,cdplan.totalClassHour,"
				+ " cdteachergroup.cDTeacherGroupId,cdteachergroup.teacherId" + " from cdplan,term,major,cdteachergroup"
				+ " where cdplan.termId = term.termId" + " and cdplan.majorId = major.majorId"
				+ " and cdplan.cDPlanId = cdteachergroup.cDPlanId" + " and cdplan.isCurrent = 1"
				+ " and cdteachergroup.teacherId = " + teacher.getTeacherId() + " and cdplan.cDPlanId not in("
				+ "	select distinct cdplan.cDPlanId" + "	from cdplan,cdteachergroup,evalstandards"
				+ "	where cdplan.cDPlanId = cdteachergroup.cDPlanId"
				+ "	and cdteachergroup.cDTeacherGroupId = evalstandards.cDTeacherGroupId"
				+ "	and cdteachergroup.teacherId = " + teacher.getTeacherId() + "	and cdplan.isCurrent = 1" + " )";
		ServletActionContext.getRequest().setAttribute("pageBean",
				serviceFactory.getPageBeanService().getPageBean(hql, 10, pno));
		return "findStillEvalStand";
	}

	/**
	 * ������ּ�¼
	 * 
	 * @return
	 */
	public String addEvalStandards() {
		try {
			serviceFactory.getEvalstandardsService().save(this.evalstandards);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "addEvalStandards";
	}

	/**
	 * �鿴�����Ѿ���������ֱ�׼����Ϣ
	 * 
	 * @return
	 */
	public String findAlladdedEvalStand() {
		Teacher teacher = getTeacher();
		String hql = "select cdplan.cDPlanId,cdplan.cDPlanNum,cdplan.cDPlanName,major.majorName,term.termName,"
				+ " cdplan.totalCredits,cdplan.totalClassHour,evalstandards.evalStandId"
				+ " from cdteachergroup,cdplan,term,major,evalstandards"
				+ " where cdteachergroup.cDPlanId = cdplan.cDPlanId" + " and cdplan.termId = term.termId"
				+ " and cdplan.majorId = major.majorId"
				+ " and cdteachergroup.cDTeacherGroupId = evalstandards.cDTeacherGroupId" + " and cdplan.isCurrent = 1"
				+ " and cdteachergroup.teacherId =" + teacher.getTeacherId();
		ServletActionContext.getRequest().setAttribute("pageBean",
				serviceFactory.getPageBeanService().getPageBean(hql, 10, pno));
		return "findAlladdedEvalStand";
	}

	/**
	 * �鿴���ֱ�׼
	 * 
	 * @throws IOException
	 */
	public void searchEvalStand() throws IOException {
		evalstandards = serviceFactory.getEvalstandardsService().find(evalstandards);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(evalstandards));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * ɾ�����ֱ�׼
	 * 
	 * @throws IOException
	 */
	public void deleteEvalStand() throws IOException {
		evalstandards = serviceFactory.getEvalstandardsService().find(evalstandards);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		// ִ��ɾ������
		try {
			serviceFactory.getEvalstandardsService().delete(evalstandards);
			ServletActionContext.getResponse().getWriter().printf("<script>alert('ɾ���ɹ�!');</script>");
		} catch (Exception e) {
			e.printStackTrace();
			ServletActionContext.getResponse().getWriter().printf("<script>alert('ɾ��ʧ��!');</script>");
		}
		ServletActionContext.getResponse().getWriter()
				.printf("<script>window.location.href='cdTechGroupLeC_findAlladdedEvalStand.action';</script>");
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * ��ѯĳ�γ�������鳤��ǰѧ�����еĿγ���Ƽƻ�
	 * 
	 * @throws IOException
	 */
	public void getAllCdPlan() throws IOException {
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter()
				.printf(JSON.toJSONString(serviceFactory.getCdplanService().findAllTeachCdPlan_c(getTeacher())));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * ��ѯ���е�δ��������ƻ��Ŀγ����
	 * 
	 * @throws IOException
	 */
	public void findAllNeedSupCD() throws IOException {
		List allNeedSupCD = null;
		Teacher teacher = getTeacher();
		// �ж��Ƿ���Ҫɸѡ
		if (this.cdplanId == -1) {
			allNeedSupCD = serviceFactory.getCdplanService().findAllCdPlan_c(teacher, null);
		} else {
			Cdplan cdplan = new Cdplan();
			cdplan.setCdplanId(this.cdplanId);
			allNeedSupCD = serviceFactory.getCdplanService().findAllCdPlan_c(teacher, cdplan);
		}
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(allNeedSupCD));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * ��ѯ���е��Ѿ�������ƻ��Ŀγ����
	 * 
	 * @throws IOException
	 */
	public void findAllSupedCD() throws IOException {
		List allSupedCD = null;
		Teacher teacher = getTeacher();
		// �ж��Ƿ���Ҫɸѡ
		if (this.cdplanId == -1) {
			allSupedCD = serviceFactory.getCdplanService().findAllReplyedCd_c(teacher, null);
		} else {
			Cdplan cdplan = new Cdplan();
			cdplan.setCdplanId(this.cdplanId);
			allSupedCD = serviceFactory.getCdplanService().findAllReplyedCd_c(teacher, cdplan);
		}
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(allSupedCD));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * ��ѯ���еĴ��С��
	 * 
	 * @throws IOException
	 * 
	 */
	public void findAllReplyGroups() throws IOException {
		List replygroups = serviceFactory.getReplyplanService().findAllReplyGrop_c();
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(replygroups));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * �鿴���С����������еĳ�Ա
	 * 
	 * @throws IOException
	 */
	public void findMemByReplyGroupId() throws IOException {
		Replygroup replygroup = new Replygroup();
		replygroup.setReplyGroupId(this.theReplyGroupId);
		List replyGroupMembers = serviceFactory.getReplygroupService().findAllTeachersByRpId_c(replygroup);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(replyGroupMembers));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * ���δ���������С��Ĵ������
	 */
	public void addUnCreReplyPlan() {
		// ��װʵ����
		// ѧ��С��
		Studentgroup studentgroup = new Studentgroup();
		studentgroup.setStuGroupId(this.stuGroupId);
		// ���С��
		Replygroup replygroup = this.replygroup;
		// �������
		Replyplan replyplan = this.replyplan;
		replyplan.setStudentgroup(studentgroup);
		replyplan.setReplygroup(replygroup);
		System.out.println(JSON.toJSONString(replyplan));
		try {
			serviceFactory.getReplyplanService().addReplyPlan_c(replyplan, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ӹ���С��Ĵ������
	 */
	private void addCreateReplyPlan() {
		// ѧ��С��
		Studentgroup studentgroup = new Studentgroup();
		studentgroup.setStuGroupId(this.stuGroupId);
		// ��ȡ���еĽ�ʦ
		Set<Teacher> teachers = new HashSet<Teacher>();
		String[] techStrs = this.teachersString.split(",");
		try {
			for (int i = 0; i < techStrs.length; i++) {
				Teacher teach = new Teacher();
				teach.setTeacherId(Integer.parseInt(techStrs[i].trim()));
				teachers.add(teach);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ����һ����ʦС��
		Replygroup replygroup = this.replygroup;
		replygroup.setTeachers(teachers);
		// ����һ�����ƻ���
		Replyplan replyplan = this.replyplan;
		replyplan.setStudentgroup(studentgroup);
		replyplan.setReplygroup(replygroup);
		System.out.println(JSON.toJSONString(replyplan));
		try {
			serviceFactory.getReplyplanService().addReplyPlan_c(replyplan, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��Ӵ��ƻ�
	 * 
	 * @throws IOException
	 */
	public void addReplyPlan() throws IOException {
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		if (this.subType == 0) {
			addUnCreReplyPlan();
		} else if (this.subType == 1) {
			addCreateReplyPlan();
		}
		ServletActionContext.getResponse().getWriter()
				.printf("<script>window.location.href='ReplyManage.jsp';</script>");
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * �鿴���ƻ�
	 * 
	 * @throws IOException
	 */
	public void searchReplyPlan() throws IOException {
		replyplan = serviceFactory.getReplyplanService().find(replyplan);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(replyplan));
		System.out.println(JSON.toJSONString(replyplan));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * ɾ�����ƻ�
	 * 
	 * @throws IOException
	 */
	public void delteReplyPlan() throws IOException {
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		try {
			Replyplan replyplan = new Replyplan();
			replyplan.setReplyPlanId(this.theReplyPlanId);
			serviceFactory.getReplyplanService().delete(replyplan);
			ServletActionContext.getResponse().getWriter().printf("<script>alert('ɾ���ɹ�!');</script>");
		} catch (Exception e) {
			e.printStackTrace();
			ServletActionContext.getResponse().getWriter().printf("<script>alert('�Բ���ÿγ���Ƽƻ����ܱ�ɾ��!');</script>");
		}
		ServletActionContext.getResponse().getWriter()
				.printf("<script>window.location.href='ReplyManage.jsp';</script>");
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}
}
