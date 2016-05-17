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
	private int pno = 0;// ҳ��
	private ServiceFactory serviceFactory;
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
	 * �鿴��ʦ�����Ŀγ���Ƽƻ���
	 * 
	 * @throws IOException
	 */
	public String viewCdplanNameExs() throws IOException {
		BindCdplanName();
		return "viewNameMcdtNew";

	}
	/**
	 * �鿴���̿��˼ƻ�
	 *   cdplanId==0,��ѯȫ����
	 *   ���򣬲�ѯ��Ӧ�Ĺ��̿��˼ƻ���
	 * @return
	 */
	public String viewProcessPlan() {// �鿴�γ̼ƻ�
		BindCdplanName();
		String whereSql = "";//���ӵ�����
		teacherId = getTeacher().getTeacherId();
		// �ж��Ƿ��ѯȫ��
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
	 * ��ӹ��̿��˼ƻ�
	 * @return
	 * @throws IOException
	 */
	public String addProPlan() throws IOException {
		BindCdplanName();//��������ļƻ���
		Teacher teacher = new Teacher();
		teacher.setTeacherId(getTeacher().getTeacherId());
		teacher = serviceFactory.getTeacherService().find(teacher);
		pcd.setTeacher(teacher);
		serviceFactory.getProcessasssheduleService().save(pcd);
		ShowMessage("��ӳɹ���");
		return "addProPlan";
	}
	/**
	 * ɾ�����̿��˼ƻ�
	 * @return
	 * @throws IOException 
	 */
	public String del() throws IOException {// ����ָ���Ŀ��˼ƻ�idɾ����Ӧ�Ĺ��̿��˼ƻ�
		//BindCdplanName();//��������ļƻ���
		int processAssShId = Integer.parseInt(req.getParameter("processAssShId").toString());
		Processassshedule processassshedule = new Processassshedule();
		processassshedule.setProcessAssShId(processAssShId);
		// �ҵ��ù��̿��˼ƻ���ɾ��
		processassshedule = serviceFactory.getProcessasssheduleService().find(processassshedule);
		serviceFactory.getProcessasssheduleService().delete(processassshedule);
		ShowMessage("ɾ���ɹ���");
		BindCdplanName();
		return "del";
	}
	
	/**
	 * �޸Ĺ��̿��˼ƻ�
	 * @return
	 * @throws IOException 
	 */
	public String updateProPlan() throws IOException{
		BindCdplanName();//��������ļƻ���
		Processassshedule pcd2=new Processassshedule();
		pcd2=serviceFactory.getProcessasssheduleService().find(pcd);
		pcd2.setProcessDescribtion(pcd.getProcessDescribtion());
		pcd2.setProcessName(pcd.getProcessName());
		pcd2.setStartTime(pcd.getStartTime());
		pcd2.setEndTime(pcd.getEndTime());
		serviceFactory.getProcessasssheduleService().update(pcd2);
		ShowMessage("�޸ĳɹ���");
		return "updateProPlan";
	}
	/**
	 * �󶨼ƻ�����������
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
	 * ҳ����ʾ��Ϣ��messageΪҪ��ʾ����Ϣ���ݣ�
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
	// ע��serviceFactory
	public ServiceFactory getServiceFactory() {
			return serviceFactory;
	}
	public void setServiceFactory(ServiceFactory serviceFactory) {
			this.serviceFactory = serviceFactory;
	}
}
