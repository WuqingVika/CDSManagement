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

	// 注入serviceFactory
	private ServiceFactory serviceFactory;

	public void setServiceFactory(ServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	private int pno = 0;// 页码

	private String teachersString;// 教师*

	private int teacherGroupId;// 教师小组编号

	private int teacherId;// 教师编号

	private String teacherWorkId;// 教师工号

	private Evalstandards evalstandards; // 评分标准

	private int cdplanId = -1;// 课程设计计划编号

	private int theReplyGroupId;// 答辩小组编号

	private Replygroup replygroup;// 答辩小组

	private Replyplan replyplan;// 答辩任务

	private int theReplyPlanId;// 答辩任务编号

	private int stuGroupId;// 学生小组编号

	private int subType = -1;// 提交的方式，用户在提交答辩任务时候需要提交时自选的还是新增的

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
	 * 获取教师的信息
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
	 * 查询当前教师所有的还未曾添加组员的课程设计计划有哪些
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
	 * 查看某个课程设计组组长已经添加组员的课程计划信息
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
	 * 查看学院下所有的教师
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
	 * 添加教师小组
	 * 
	 * @throws IOException
	 */
	public void addTeachers() throws IOException {		 
		String[] teachStr = teachersString.split(",");
		// 获取到所有的教师
		List<Teacher> teachers = new ArrayList<Teacher>();
		for (int i = 0; i < teachStr.length; i++) {
			Teacher teacher = new Teacher();
			teacher.setTeacherId(Integer.parseInt(teachStr[i].trim()));
			teachers.add(teacher);
		}
		// 获取到教师小组
		Cdteachergroup cdteachergroup = new Cdteachergroup();
		cdteachergroup.setCdteacherGroupId(this.teacherGroupId);		 
		// 添加教师信息
		serviceFactory.getCdteachergroupService().addThGroupMember_c(teachers, cdteachergroup);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString("success"));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * 查看所有已经分配小组成员的
	 * 
	 * @return
	 */
	public String findAllAllowedMem() {
		ServletActionContext.getRequest().setAttribute("pageBean", getMyGroupMemBean(getTeacher()));
		return "findAllAllowedMem";
	}

	/**
	 * 查看所有的小组下教师的信息
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
	 * 删除教师小组成员
	 * 
	 * @throws IOException
	 */
	public void removeTeachGroupMem() throws IOException {
		// 封装实体类
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
	 * 添加教师信息
	 * 
	 * @throws IOException
	 */
	public void addTeacher() throws IOException {
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		try {
			// 定义一个教师的实体类
			Teacher teacher = new Teacher();
			teacher.setTeacherWorkId(this.teacherWorkId);
			// 通过教师的工号查询教师的信息
			teacher = serviceFactory.getTeacherService().findTeacherByTeachWId_c(teacher);
			// 定义小组实体类
			Cdteachergroup cdteachergroup = new Cdteachergroup();
			cdteachergroup.setCdteacherGroupId(this.teacherGroupId);
			// 添加教师小组
			serviceFactory.getCdteachergroupService().addTeacher_c(cdteachergroup, teacher);
			// 返回到前台的结果
			ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString("success"));
		} catch (Exception e) {
			// 返回到前台操作失败
			ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString("error"));
		}
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * 查询所有未曾分配的课程计划信息
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
	 * 添加评分记录
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
	 * 查看所有已经添加了评分标准的信息
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
	 * 查看评分标准
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
	 * 删除评分标准
	 * 
	 * @throws IOException
	 */
	public void deleteEvalStand() throws IOException {
		evalstandards = serviceFactory.getEvalstandardsService().find(evalstandards);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		// 执行删除操作
		try {
			serviceFactory.getEvalstandardsService().delete(evalstandards);
			ServletActionContext.getResponse().getWriter().printf("<script>alert('删除成功!');</script>");
		} catch (Exception e) {
			e.printStackTrace();
			ServletActionContext.getResponse().getWriter().printf("<script>alert('删除失败!');</script>");
		}
		ServletActionContext.getResponse().getWriter()
				.printf("<script>window.location.href='cdTechGroupLeC_findAlladdedEvalStand.action';</script>");
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * 查询某课程设计组组长当前学期所有的课程设计计划
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
	 * 查询所有的未曾分配答辩计划的课程设计
	 * 
	 * @throws IOException
	 */
	public void findAllNeedSupCD() throws IOException {
		List allNeedSupCD = null;
		Teacher teacher = getTeacher();
		// 判断是否需要筛选
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
	 * 查询所有的已经分配答辩计划的课程设计
	 * 
	 * @throws IOException
	 */
	public void findAllSupedCD() throws IOException {
		List allSupedCD = null;
		Teacher teacher = getTeacher();
		// 判断是否需要筛选
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
	 * 查询所有的答辩小组
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
	 * 查看答辩小组下面的所有的成员
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
	 * 添加未曾构建答辩小组的答辩任务
	 */
	public void addUnCreReplyPlan() {
		// 分装实体类
		// 学生小组
		Studentgroup studentgroup = new Studentgroup();
		studentgroup.setStuGroupId(this.stuGroupId);
		// 答辩小组
		Replygroup replygroup = this.replygroup;
		// 答辩任务
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
	 * 添加构建小组的答辩任务
	 */
	private void addCreateReplyPlan() {
		// 学生小组
		Studentgroup studentgroup = new Studentgroup();
		studentgroup.setStuGroupId(this.stuGroupId);
		// 获取所有的教师
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
		// 定义一个教师小组
		Replygroup replygroup = this.replygroup;
		replygroup.setTeachers(teachers);
		// 定义一个答辩计划类
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
	 * 添加答辩计划
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
	 * 查看答辩计划
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
	 * 删除答辩计划
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
			ServletActionContext.getResponse().getWriter().printf("<script>alert('删除成功!');</script>");
		} catch (Exception e) {
			e.printStackTrace();
			ServletActionContext.getResponse().getWriter().printf("<script>alert('对不起该课程设计计划不能被删除!');</script>");
		}
		ServletActionContext.getResponse().getWriter()
				.printf("<script>window.location.href='ReplyManage.jsp';</script>");
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}
}
