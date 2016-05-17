package com.cds.action.teacherdirect;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;

import com.alibaba.fastjson.JSON;
import com.cds.entity.Account;
import com.cds.entity.College;
import com.cds.entity.PageBean;
import com.cds.entity.Teacher;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

public class TeacherDirectorInfoAction extends ActionSupport implements RequestAware {
	private static final long serialVersionUID = 1L;
	//����request
	private Map<String, Object> request;
	@Override
	public void setRequest(Map<String, Object> req) {
		this.request = req;
	}
	//Service����
	private ServiceFactory serviceFactory;
	//��ǰҳ����Ϊ1
	private int pno = 0;
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public void setServiceFactory(ServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}
	
	//��ѯȫ����action(��ʼ��action)
	//���index�������ǻ�ȡ���������ε��������ݣ�����ҳ�������ʾ
	public String index(){
		PageBean teacherdirectors = (PageBean) serviceFactory.getPageBeanService().getPageBean("SELECT account.accId, accountId, teacher.teacherId, teacherWorkId, teacherName, collegeName FROM account, teacher, college WHERE account.`role` = 1 AND account.`accId` = teacher.`accId` AND teacher.`collegeId` = college.`collegeId`", 10, pno);
		request.put("pageBean", teacherdirectors);
		return SUCCESS;
	}
	//��ý�ʦ������
	private String collegeName;
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	//��ý�ʦ�ķ���
	public String getTeacher() throws IOException {
		try{
			College college = serviceFactory.getCollegeService().findCollegeByName_z(collegeName);
			List teachers = serviceFactory.getTeacherService().findTeachersByCollegeId_z(college.getCollegeId());
			String json = JSON.toJSONString(teachers);
			//��json����AJAX
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write(json);
			pw.close();		
		}catch(Exception e){
			
		}			
		return null;
	}
	//��ӽ�ʦ������
	private String teacherWorkId;
	public String getTeacherWorkId() {
		return teacherWorkId;
	}
	public void setTeacherWorkId(String teacherWorkId) {
		this.teacherWorkId = teacherWorkId;
	}
	//��ӽ�ʦ�ķ���
	public String addTeacherDirector(){
		//�ҵ���ʦ
		Teacher teacher = serviceFactory.getTeacherService().findTeacherByWorkId_z(teacherWorkId);
		//�õ��˺�
		Account account = teacher.getAccount();
		account.setRole(1); //��������Ϊ����������
		//�����˺���Ϣ
		serviceFactory.getAccountService().update(account);
		return SUCCESS;
	}
	
	//ɾ��������
	private int accId;
	public int getAccId() {
		return accId;
	}
	public void setAccId(int accId) {
		this.accId = accId;
	}
	//ɾ���ķ���
	public String deleteTeacherDirector() throws IOException{
		Account account = new Account();
		account.setAccId(accId);
		account = serviceFactory.getAccountService().find(account);
		account.setRole(2);
		serviceFactory.getAccountService().update(account);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write("1");
		pw.close();	
		return null;
	}
}
