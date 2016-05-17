package com.cds.action.teacher;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;

import com.alibaba.fastjson.JSON;
import com.cds.entity.Account;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.PageBean;
import com.cds.entity.Processassshedule;
import com.cds.entity.Teacher;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

public class MakeProcessPlanAction extends ActionSupport implements RequestAware {
	private static final long serialVersionUID = 1L;
	private Processassshedule pcd;
	private HttpServletResponse response = ServletActionContext.getResponse();
	private HttpServletRequest req = ServletActionContext.getRequest();
	private int cdplanId = 0;
	private int teacherId;
	private int pno = 0;// 页码
	private ServiceFactory serviceFactory;
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
	 * 
	 * @throws IOException
	 */
	public String viewCdplanNameExs() throws IOException {
		BindCdplanName();
		return "viewNameMcdtNew";

	}
	/**
	 * 查看过程考核计划
	 *   cdplanId==0,查询全部；
	 *   否则，查询对应的过程考核计划；
	 * @return
	 */
	public String viewProcessPlan() {// 查看课程计划
		BindCdplanName();
		String whereSql = "";//附加的条件
		teacherId = getTeacher().getTeacherId();
		// 判断是否查询全部
		if (cdplanId == 0)
			whereSql += "";
		else
			whereSql += " and cg.cDPlanId=" + cdplanId;
		String sql = "select cp.cDPlanId,cp.cDPlanName,pcs.* from processassshedule "
				+ "pcs,cdteachergroup cg,cdplan cp where " + "cg.cDTeacherGroupId=pcs.cDTeacherGroupId"
				+ " and cp.cDPlanId=cg.cDPlanId " + whereSql + " and pcs.teacherId=" + teacherId
				+ " order by pcs.processAssShId";
		PageBean pageBean = new PageBean();
		pageBean = serviceFactory.getPageBeanService().getPageBean(sql, 2, pno);
		request.put("results", pageBean);
		return "viewProcessPlan";
	}
	/**
	 * 添加过程考核计划
	 * @return
	 * @throws IOException
	 */
	public String addProPlan() throws IOException {
		BindCdplanName();//绑定下拉框的计划名
		Teacher teacher = new Teacher();
		teacher.setTeacherId(getTeacher().getTeacherId());
		teacher = serviceFactory.getTeacherService().find(teacher);
		pcd.setTeacher(teacher);
		serviceFactory.getProcessasssheduleService().save(pcd);
		ShowMessage("添加成功！");
		return "addProPlan";
	}
	/**
	 * 删除过程考核计划
	 * @return
	 * @throws IOException 
	 */
	public String del() throws IOException {// 根据指定的考核计划id删除对应的过程考核计划
		//BindCdplanName();//绑定下拉框的计划名
		int processAssShId = Integer.parseInt(req.getParameter("processAssShId").toString());
		Processassshedule processassshedule = new Processassshedule();
		processassshedule.setProcessAssShId(processAssShId);
		// 找到该过程考核计划再删除
		processassshedule = serviceFactory.getProcessasssheduleService().find(processassshedule);
		serviceFactory.getProcessasssheduleService().delete(processassshedule);
		ShowMessage("删除成功！");
		BindCdplanName();
		return "del";
	}
	
	/**
	 * 修改过程考核计划
	 * @return
	 * @throws IOException 
	 */
	public String updateProPlan() throws IOException{
		BindCdplanName();//绑定下拉框的计划名
		Processassshedule pcd2=new Processassshedule();
		pcd2=serviceFactory.getProcessasssheduleService().find(pcd);
		pcd2.setProcessDescribtion(pcd.getProcessDescribtion());
		pcd2.setProcessName(pcd.getProcessName());
		pcd2.setStartTime(pcd.getStartTime());
		pcd2.setEndTime(pcd.getEndTime());
		serviceFactory.getProcessasssheduleService().update(pcd2);
		ShowMessage("修改成功！");
		return "updateProPlan";
	}
	/**
	 * 绑定计划名到下拉框
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void  BindCdplanName(){
		Teacher teacher=new Teacher();
		teacher.setTeacherId(getTeacher().getTeacherId());	
		Teacher te=serviceFactory.getTeacherService().find(teacher);
		
		String sqlNew="select cg.cdplanId,cg.cdteacherGroupID,"
				+ "cp.cdplanName from cdteachergroup cg,cdplan cp"
				+ " where cdteachergroupId in (select cdteacherGroupId"
				+ " from  cdgroupteachers where teacherId="+te.getTeacherId()
				+ ") and cp.cdplanId=cg.cdplanId and cp.isCurrent=1";
		List data3=serviceFactory.getPageBeanService().getQueryList(sqlNew);
		ServletActionContext.getRequest().setAttribute("Cdteachergroups",data3);	
	
	}
	/**
	 * 页面提示消息，message为要提示的消息内容；
	 * @param message
	 * @throws IOException
	 */
	private void ShowMessage(String message) throws IOException{
		response.setContentType("text/html;charset=utf-8");		  
		PrintWriter out =response.getWriter();
		out.println("<script>alert('"+message+"')</script>");
		out.flush();		
	}
	private Map<String, Object> request;

	@Override
	public void setRequest(Map<String, Object> req) {
		this.request = req;
	}
	public Processassshedule getPcd() {
		return pcd;
	}

	public void setPcd(Processassshedule pcd) {
		this.pcd = pcd;
	}
	public int getCdplanId() {
		return cdplanId;
	}

	public void setCdplanId(int cdplanId) {
		this.cdplanId = cdplanId;
	}
	public int getPno() {
		return pno;
	}

	public void setPno(int pno) {
		this.pno = pno;
	}
	// 注入serviceFactory
	public ServiceFactory getServiceFactory() {
			return serviceFactory;
	}
	public void setServiceFactory(ServiceFactory serviceFactory) {
			this.serviceFactory = serviceFactory;
	}
}
