package com.cds.action.teacherdirect;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.alibaba.fastjson.JSON;
import com.cds.entity.Account;
import com.cds.entity.Cdplan;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Teacher;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

public class CdplanLeaderAction extends ActionSupport implements RequestAware, SessionAware {
	private static final long serialVersionUID = 1L;
	//设置request
	private Map<String, Object> request;
	@Override
	public void setRequest(Map<String, Object> req) {
		this.request = req;
	}
	//设置session
	private Map<String, Object> session;
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;		
	}
	//Service工厂
	private ServiceFactory serviceFactory;
	public void setServiceFactory(ServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}
	//这个index方法就是获取课程计划的所有数据，并且页面进行显示
	public String index(){
		//拿到account这个session对象
		Account account = (Account)session.get("account");
		//如果是教研室主任，就让他登录，如果不是教研室主任，就不让他登录
		if(account.getRole() == 1){
			//这是登录后的
			//查找到该教研室主任，然后保存这个教研室主任到session中
			Teacher teacher = serviceFactory.getTeacherService().findTeacherByAccId_z(account.getAccId());
			//将教研室主任信息保存在teacherDirectorSession中。
			session.put("teacherDirectorSession", teacher);
			//返回该学院所有的课程计划
			List<Cdplan> cdplans = serviceFactory.getCollegeService().findCdplansByCollegeId(teacher.getCollege().getCollegeId());
			request.put("cdplans", cdplans);
			//将cdTeacherGroup属性转换成List集合保存在request域中输出
			
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	//获得该学院所有的教师
	public String getTeachers() throws IOException {		
		List teachers = serviceFactory.getTeacherService().findTeachersByCollegeId_z(((Teacher)session.get("teacherDirectorSession")).getCollege().getCollegeId());
		String json = JSON.toJSONString(teachers);
		//将json返回AJAX
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
		return null;
	}
	
	//上传教师的工号和课程计划的编号
	//需要的属性就是教师和课程计划
	private Teacher teacher;
	private Cdplan cdplan;
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public Cdplan getCdplan() {
		return cdplan;
	}
	public void setCdplan(Cdplan cdplan) {
		this.cdplan = cdplan;
	}
	//添加课程计划的组长
	public String addLeaderTeacher(){
		//需要知道课程计划和组长是谁
		//指定课程设计的组长
		Cdplan cp = serviceFactory.getCdplanService().find(cdplan); //获得课程计划
		Teacher rTeacher = serviceFactory.getTeacherService().findTeacherByWorkId_z(teacher.getTeacherWorkId()); //获得教师
		//新建对象，准备保存组长
		Cdteachergroup cdteachergroup = new Cdteachergroup();
		cdteachergroup.setTeacher(rTeacher);
		cdteachergroup.setCdplan(cp);
		serviceFactory.getCdteachergroupService().save(cdteachergroup);//保存
		cdteachergroup = serviceFactory.getCdteachergroupService().find(cdteachergroup); //再获得这个cdteachergroup
		//他还是成员，还要添加cdgroupTeachers表，属性是教师和课程计划教师的编号
		serviceFactory.getCdteachergroupService().addTeacherMember_z(rTeacher.getTeacherId(), cdteachergroup.getCdteacherGroupId());
		return SUCCESS;
	}
	
	//删除课程设计组长的方法
	public String deleteTeacher() throws IOException{
		//定义返回的标志
		String flag = null;
		try{			
			Cdplan cp = serviceFactory.getCdplanService().find(cdplan); //获得课程计划
			//设置Cdteachergroup的计划，然后把这个删除，也就是删除组长了
			Set<Cdteachergroup> cdteachergroups = cp.getCdteachergroups();
			for(Cdteachergroup cdteachergroup : cdteachergroups){
				int teacherGroupId = cdteachergroup.getCdteacherGroupId();
				//先通过这个小组的编号，删除所有的组员信息
				serviceFactory.getCdteachergroupService().deleteMemberInfoByCdTeacherGroupId(teacherGroupId);
				//再删除这个小组组长表
				serviceFactory.getCdteachergroupService().delete(cdteachergroup);
			}
			flag = "1";
		}catch(Exception e){
			flag = "0";
		}finally{
			//在finally里面返回所需要的数据，如果出现异常，就不能删除
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write(flag);
			pw.close();		
		}		
		return null;
	}
}
