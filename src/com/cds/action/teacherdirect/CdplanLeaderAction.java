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
	//����request
	private Map<String, Object> request;
	@Override
	public void setRequest(Map<String, Object> req) {
		this.request = req;
	}
	//����session
	private Map<String, Object> session;
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;		
	}
	//Service����
	private ServiceFactory serviceFactory;
	public void setServiceFactory(ServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}
	//���index�������ǻ�ȡ�γ̼ƻ����������ݣ�����ҳ�������ʾ
	public String index(){
		//�õ�account���session����
		Account account = (Account)session.get("account");
		//����ǽ��������Σ���������¼��������ǽ��������Σ��Ͳ�������¼
		if(account.getRole() == 1){
			//���ǵ�¼���
			//���ҵ��ý��������Σ�Ȼ�󱣴�������������ε�session��
			Teacher teacher = serviceFactory.getTeacherService().findTeacherByAccId_z(account.getAccId());
			//��������������Ϣ������teacherDirectorSession�С�
			session.put("teacherDirectorSession", teacher);
			//���ظ�ѧԺ���еĿγ̼ƻ�
			List<Cdplan> cdplans = serviceFactory.getCollegeService().findCdplansByCollegeId(teacher.getCollege().getCollegeId());
			request.put("cdplans", cdplans);
			//��cdTeacherGroup����ת����List���ϱ�����request�������
			
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	//��ø�ѧԺ���еĽ�ʦ
	public String getTeachers() throws IOException {		
		List teachers = serviceFactory.getTeacherService().findTeachersByCollegeId_z(((Teacher)session.get("teacherDirectorSession")).getCollege().getCollegeId());
		String json = JSON.toJSONString(teachers);
		//��json����AJAX
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
		return null;
	}
	
	//�ϴ���ʦ�Ĺ��źͿγ̼ƻ��ı��
	//��Ҫ�����Ծ��ǽ�ʦ�Ϳγ̼ƻ�
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
	//��ӿγ̼ƻ����鳤
	public String addLeaderTeacher(){
		//��Ҫ֪���γ̼ƻ����鳤��˭
		//ָ���γ���Ƶ��鳤
		Cdplan cp = serviceFactory.getCdplanService().find(cdplan); //��ÿγ̼ƻ�
		Teacher rTeacher = serviceFactory.getTeacherService().findTeacherByWorkId_z(teacher.getTeacherWorkId()); //��ý�ʦ
		//�½�����׼�������鳤
		Cdteachergroup cdteachergroup = new Cdteachergroup();
		cdteachergroup.setTeacher(rTeacher);
		cdteachergroup.setCdplan(cp);
		serviceFactory.getCdteachergroupService().save(cdteachergroup);//����
		cdteachergroup = serviceFactory.getCdteachergroupService().find(cdteachergroup); //�ٻ�����cdteachergroup
		//�����ǳ�Ա����Ҫ���cdgroupTeachers�������ǽ�ʦ�Ϳγ̼ƻ���ʦ�ı��
		serviceFactory.getCdteachergroupService().addTeacherMember_z(rTeacher.getTeacherId(), cdteachergroup.getCdteacherGroupId());
		return SUCCESS;
	}
	
	//ɾ���γ�����鳤�ķ���
	public String deleteTeacher() throws IOException{
		//���巵�صı�־
		String flag = null;
		try{			
			Cdplan cp = serviceFactory.getCdplanService().find(cdplan); //��ÿγ̼ƻ�
			//����Cdteachergroup�ļƻ���Ȼ������ɾ����Ҳ����ɾ���鳤��
			Set<Cdteachergroup> cdteachergroups = cp.getCdteachergroups();
			for(Cdteachergroup cdteachergroup : cdteachergroups){
				int teacherGroupId = cdteachergroup.getCdteacherGroupId();
				//��ͨ�����С��ı�ţ�ɾ�����е���Ա��Ϣ
				serviceFactory.getCdteachergroupService().deleteMemberInfoByCdTeacherGroupId(teacherGroupId);
				//��ɾ�����С���鳤��
				serviceFactory.getCdteachergroupService().delete(cdteachergroup);
			}
			flag = "1";
		}catch(Exception e){
			flag = "0";
		}finally{
			//��finally���淵������Ҫ�����ݣ���������쳣���Ͳ���ɾ��
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write(flag);
			pw.close();		
		}		
		return null;
	}
}
