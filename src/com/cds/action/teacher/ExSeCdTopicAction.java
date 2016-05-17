package com.cds.action.teacher;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import com.alibaba.fastjson.JSON;
import com.cds.entity.Account;
import com.cds.entity.Cdchoosingrecord;
import com.cds.entity.Comments;
import com.cds.entity.PageBean;
import com.cds.entity.Teacher;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

public class ExSeCdTopicAction extends ActionSupport implements RequestAware{
	
	private static final long serialVersionUID = 1L;
		private int cdplanId;
		private int pno= 0;// 页码
		private int stugroupId;
		private Cdchoosingrecord ccr;
		private int ifPass;//审核是否通过
		private String PassState;//具体情况
		private int isAdd;//是否添加新评论
		
		public int getIsAdd() {
			return isAdd;
		}

		public void setIsAdd(int isAdd) {
			this.isAdd = isAdd;
		}

		public String getPassState() {
			return PassState;
		}

		public void setPassState(String passState) {
			PassState = passState;
		}

		public int getIfPass() {
			return ifPass;
		}

		public void setIfPass(int ifPass) {
			this.ifPass = ifPass;
		}

		public Cdchoosingrecord getCcr() {
			return ccr;
		}

		public void setCcr(Cdchoosingrecord ccr) {
			this.ccr = ccr;
		}

		public int getStugroupId() {
			return stugroupId;
		}

		public void setStugroupId(int stugroupId) {
			this.stugroupId = stugroupId;
		}

		public int getPno() {
			return pno;
		}

		public void setPno(int pno) {
			this.pno = pno;
		}

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
		public String viewCdplanNameExs() throws IOException{
			bindcdPlanName();
			return SUCCESS;
			
		}
		public void getNopass() throws IOException{
			int teacherId = getTeacher().getTeacherId();
			String sql="select * from comments where teacherId="+teacherId;
			List comments=serviceFactory.getPageBeanService().getQueryList(sql);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println(JSON.toJSONString(comments));
			out.flush();	
		}
		/**
		 * 定义私有方法。绑定下拉框计划名。
		 */
		@SuppressWarnings("unchecked")
		private void bindcdPlanName(){
			Teacher teacher=new Teacher();
			teacher.setTeacherId(getTeacher().getTeacherId());	
			Teacher te=serviceFactory.getTeacherService().find(teacher);
			@SuppressWarnings("rawtypes")
			//List<Cdteachergroup> data2 = new ArrayList(te.getCdteachergroups());
			//查询老师当前学期的课程设计名，课程 小组名
			
			String sqlNew="select cg.cdplanId,cg.cdteacherGroupID,"
					+ "cp.cdplanName from cdteachergroup cg,cdplan cp"
					+ " where cdteachergroupId in (select cdteacherGroupId"
					+ " from  cdgroupteachers where teacherId="+te.getTeacherId()
					+ ") and cp.cdplanId=cg.cdplanId and cp.isCurrent=1";
			List data3=serviceFactory.getPageBeanService().getQueryList(sqlNew);
			ServletActionContext.getRequest().setAttribute("Cdteachergroups",data3);	
			//ServletActionContext.getRequest().setAttribute("Cdteachergroups",data2);
		}
		/*
		 * 查看该老师未审核的课程设计题目
		 */
		@SuppressWarnings("rawtypes")
		public String FindExTopic(){
			//注意：这里有两处写死cDPlanId，还有teacherId;
			Teacher teacher=new Teacher();
			teacher.setTeacherId(getTeacher().getTeacherId());	
			teacher=serviceFactory.getTeacherService().find(teacher);
			System.out.println(cdplanId+"jihuaid");
			String sql="select st.stuGroupId,cp.*,cr.*,ct.*  "
					+ "from cddesigntopics cp,"
					+ "cdchoosingrecord cr,"
					+ "cdteachergroup ct,"
					+ "studentgroup st "
					+ "where cp.cdteachergroupid=ct.cdteacherGroupid "
					+ "and cr.cddesigntopid=cp.cddesigntopid and "
					+ "st.cddesigntopid=cr.cddesigntopid and "
					+ "cr.isPassed=0 and ct.cDPlanId="+cdplanId
					//+ "where cr.isPassed=0 and ct.cDPlanId=3"
					+ " and cp.teacherId=" + getTeacher().getTeacherId()
					+ " and cp.isSelfChoosed=1";
			PageBean pageBean = new PageBean();
			pageBean = serviceFactory.getPageBeanService().getPageBean(sql, 2, pno);
			//System.out.println(JSON.toJSONString(cdExtopics));
			request.put("pageBean", pageBean);
			bindcdPlanName();
			return "extopics";
		}
		
		private HttpServletResponse response=ServletActionContext.getResponse();
		private HttpServletRequest req=	ServletActionContext.getRequest();
		/**
		 * 根据小组编号，显示该小组成员信息；
		 */
		@SuppressWarnings("rawtypes")
		public void showGroupMembers() throws IOException{			
			int stuGroupId=Integer.parseInt(req.getParameter("stuGroupId").toString());
			String sql="select stuId,studentId,stuName,className,collegeName,majorName from account natural join student natural join classes natural join major natural join college"
					+ " where stuId in (select sm.stuId from studentgroup sg"
					+ ",stugroupmembers sm where "
					+ "sm.stuGroupId=sg.stuGroupId and sg.stuGroupId="+stuGroupId+")";
			List GroupMembers=serviceFactory.getPageBeanService().getQueryList(sql);
			System.out.println(JSON.toJSONString(GroupMembers));
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.println(JSON.toJSONString(GroupMembers));
			out.flush();	
		}
		/*
		 * 对学生自己拟定的题目进行审核
		 */
		public String ShenHe(){
			int teacherid = getTeacher().getTeacherId();
			String sql="select cdchoosingrecId from cdchoosingrecord "
					+ "where  cddesigntopid=(select cddesigntopid "
					+ "from studentgroup where stugroupid="+stugroupId
					+ ") and ispassed=0";
			
			List data= serviceFactory.getPageBeanService().getQueryList(sql);
			if(ifPass==0){//通过是0
				ifPass=1;
				PassState="题目可行，通过!";
			}else{
				ifPass=-1;//代表不通过,且不显示。在页面。因为老师已经查看过这课题
			}
			for(int i=0;i<data.size();i++){
				Cdchoosingrecord chelp=new Cdchoosingrecord();
				chelp.setCdchoosingRecId(Integer.parseInt(data.get(i).toString()));
				chelp=serviceFactory.getCdchoosingrecordService().find(chelp);
				chelp.setIsPassed(ifPass);
				chelp.setTutorOpinion(PassState);
				if(ifPass==1)//通过了才能设置通过时间
				chelp.setPassTime(new Date());
				
				if(isAdd==1){
					Comments ct=new Comments();
					Teacher t=new Teacher();
					t.setTeacherId(teacherid);
					t=serviceFactory.getTeacherService().find(t);
					ct.setComments(PassState);
					ct.setTeacher(t);
					serviceFactory.getCommentsService().save(ct);
				}
				serviceFactory.getCdchoosingrecordService().update(chelp);
			}
			bindcdPlanName();
			return "ShenHe";
		}
		public int getCdplanId() {
			return cdplanId;
		}

		public void setCdplanId(int cdplanId) {
			this.cdplanId = cdplanId;
		}
		private Map<String, Object> request;
		@Override
		public void setRequest(Map<String, Object> req) {
			this.request = 	req;
		}
}
