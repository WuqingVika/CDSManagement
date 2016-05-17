package com.cds.action.teacher;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;

import com.cds.entity.Account;
import com.cds.entity.Cddesigntopics;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Teacher;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

public class MadeCDTopicAction extends ActionSupport implements RequestAware {
	private static final long serialVersionUID = 1L;
	private String topics;
	private int cdteacherGroupId;
	private int isSelfChoosed;
	private String topicsDescribtion;
	
	public String getTopics() {
		return topics;
	}

	public void setTopics(String topics) {
		this.topics = topics;
	}

	public int getCdteacherGroupId() {
		return cdteacherGroupId;
	}

	public void setCdteacherGroupId(int cdteacherGroupId) {
		this.cdteacherGroupId = cdteacherGroupId;
	}

	public int getIsSelfChoosed() {
		return isSelfChoosed;
	}

	public void setIsSelfChoosed(int isSelfChoosed) {
		this.isSelfChoosed = isSelfChoosed;
	}

	public String getTopicsDescribtion() {
		return topicsDescribtion;
	}

	public void setTopicsDescribtion(String topicsDescribtion) {
		this.topicsDescribtion = topicsDescribtion;
	}

	private HttpServletResponse response=ServletActionContext.getResponse();
	// 注入serviceFactory
	private ServiceFactory serviceFactory;
	public ServiceFactory getServiceFactory() {
		return serviceFactory;
	}

	public void setServiceFactory(ServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
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
	 * 查看老师所带的课程设计计划名
	 * @throws IOException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String viewCdplanName() throws IOException{
		Teacher teacher=new Teacher();
		teacher.setTeacherId(getTeacher().getTeacherId());	
		Teacher te=serviceFactory.getTeacherService().find(teacher);
		
		String sqlNew="select cg.cdplanId,cg.cdteacherGroupID,"
				+ "cp.cdplanName from cdteachergroup cg,cdplan cp"
				+ " where cdteachergroupId in (select cdteacherGroupId"
				+ " from  cdgroupteachers where teacherId="+te.getTeacherId()
				+ ") and cp.cdplanId=cg.cdplanId and cp.isCurrent=1";
		List data3=serviceFactory.getPageBeanService().getQueryList(sqlNew);
		ServletActionContext.getRequest().setAttribute("cdPlanNames",data3);	
		return SUCCESS;
		
	}
	public String addCdTopic() throws IOException{
		Teacher teacher=new Teacher();
		teacher.setTeacherId(getTeacher().getTeacherId());		
		Teacher te=serviceFactory.getTeacherService().find(teacher);
		Cddesigntopics ctpadd=new Cddesigntopics();
		ctpadd.setTeacher(te);
		ctpadd.setTopics(topics);
		Cdteachergroup cdteachergroup=new Cdteachergroup();
		cdteachergroup.setCdteacherGroupId(cdteacherGroupId);		
	    cdteachergroup=serviceFactory.getCdteachergroupService().find(cdteachergroup);
		ctpadd.setCdteachergroup(cdteachergroup);
		ctpadd.setTopicsDescribtion(topicsDescribtion);
		ctpadd.setIsSelfChoosed(isSelfChoosed);
		serviceFactory.getCddesigntopicsService().save(ctpadd);
		response.setContentType("text/html;charset=utf-8");		  
		PrintWriter out =response.getWriter();
		out.println("<script>alert('添加成功')</script>");
		out.flush();	
		return SUCCESS;
	}
	
	private Map<String, Object> request;
	@Override
	public void setRequest(Map<String, Object> req) {
		this.request = 	req;
	}
	
}
