package com.cds.action.teacher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.SettingsDocumentImpl;

import com.alibaba.fastjson.JSON;
import com.cds.entity.Account;
import com.cds.entity.Cddesigntopics;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.MemberResult;
import com.cds.entity.PageBean;
import com.cds.entity.Processdocument;
import com.cds.entity.Replyplan;
import com.cds.entity.Replyrecords;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;
import com.cds.entity.Studentscore;
import com.cds.entity.Teacher;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

public class ViewProcessFileAction extends ActionSupport implements RequestAware{
	private static final long serialVersionUID = 1L;
	private Map<String, Object> request;
	private ServiceFactory serviceFactory;
	private HttpServletResponse response = ServletActionContext.getResponse();
	//private HttpServletRequest req = ServletActionContext.getRequest();
	private int processAssShId;//���˽׶α��
	private int pno;//��ǰҳ��
	private int processDocId;//�����ĵ����
	private String fileName;// �ĵ�������
	private int cdplanId;//�γ���Ƽƻ����
	private int cddesignTopId;//�����Ŀ���
	private int stugroupid;//ѧ��С���� 
	private int stuId;//ѧ��ѧ��
	private int cdteachergroupId;//��ʦС���ţ�
	private float procAccCoe;
	private float replyCoe;
	private float attendCoe;
	private float selfCoe;
	private float selfScore;
	private Studentscore ssc;//ѧ���ɼ�
	
	
	public Studentscore getSsc() {
		return ssc;
	}

	public void setSsc(Studentscore ssc) {
		this.ssc = ssc;
	}

	public float getSelfScore() {
		return selfScore;
	}

	public void setSelfScore(float selfScore) {
		this.selfScore = selfScore;
	}

	public float getProcAccCoe() {
		return procAccCoe;
	}

	public void setProcAccCoe(float procAccCoe) {
		this.procAccCoe = procAccCoe;
	}

	public float getReplyCoe() {
		return replyCoe;
	}

	public void setReplyCoe(float replyCoe) {
		this.replyCoe = replyCoe;
	}

	public float getAttendCoe() {
		return attendCoe;
	}

	public void setAttendCoe(float attendCoe) {
		this.attendCoe = attendCoe;
	}

	public float getSelfCoe() {
		return selfCoe;
	}

	public void setSelfCoe(float selfCoe) {
		this.selfCoe = selfCoe;
	}

	public int getCdteachergroupId() {
		return cdteachergroupId;
	}

	public void setCdteachergroupId(int cdteachergroupId) {
		this.cdteachergroupId = cdteachergroupId;
	}

	//���������޸�ѧ���ɼ���Ϣʱ����ŵ�ѧ���Ĳ�����Ϣ��
	private List<Processdocument> processdocuments=new ArrayList<Processdocument>();
	private int replyplanId;//���ƻ����
	private Replyrecords rec;//����¼
	
	public Replyrecords getRec() {
		return rec;
	}

	public void setRec(Replyrecords rec) {
		this.rec = rec;
	}

	

	public int getReplyplanId() {
		return replyplanId;
	}

	public void setReplyplanId(int replyplanId) {
		this.replyplanId = replyplanId;
	}
	
	private String question;
	private String answers;
	private Date answerTime;
	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswers() {
		return this.answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	public Date getAnswerTime() {
		return this.answerTime;
	}

	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
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
	 * ����ѧ��ĳ���׶εĹ��̿��˲��Ͻ�������
	 * ��ģ̬�򵯳�ѧ���Ĳ��������
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	public void viewProDoc() throws IOException{
		//ѧ��С����
		//int stugroupidJsp=Integer.parseInt(req.getParameter("stugroupidJsp"));
		//���˽׶α��
		//int processAssshIdJsp=Integer.parseInt(req.getParameter("processAssshIdJsp"));
		//�ӿ��˲��ϱ��в�ѯ���˲��ϱ�ţ�ѧ���������������ƣ�������ַ
		//�ȸ���С���ţ���ѯ����С�����Ա��ţ��ٸ��ݿ��˽׶α�Ų�ѯ����Щ��Ա�Ŀ��˲��ϱ��
		String sql="select c.processDocId,d.stuName,if(d.stuId=("
				+ "select stuId from studentgroup where stugroupId="+stugroupid
				+ "),'�鳤','��Ա') '״̬��Ϣ' ,c.documentName,c.documentUrl,"
				+ "c.upperTime,IF(c.isReaded=1,'�Ѳ���','δ����') as '�Ƿ����'"
				+ " from processdocument c,student d  "
				+ "where d.stuId=c.stuId and c.processassshid="+processAssShId
				+ " and (c.stuId,d.stuName) in (select a.stuId,b.stuName "
				+ " from stugroupmembers a,student b where a.stuId=b.stuId "
				+ "and a.stugroupId="+stugroupid +")";
		List data=serviceFactory.getPageBeanService().getQueryList(sql);
		sendJsonData(data);	
		
	}
	
	/**
	 * �鿴��ʦ�����Ŀγ���Ƽƻ���
	 * 
	 * @throws IOException
	 */
	public String getCdplanName() throws IOException {
		BindCdplanName();
		return "getCdplanName";

	}
	/**
	 * ������ѯ
	 * ĳ�γ���Ƽƻ�����Ӧ�Ŀγ������Ŀ
	 * @throws IOException 
	 */
	public void getTopic() throws IOException {
		    BindTopic();
			BindCdplanName();	
	}
	/**
	 * ����Ŀ��һ��˽�з���
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	private void BindTopic() throws IOException{
		//int cdPlanIdJSp = 0;
		//���ﻹ��д��teacherId;
		int teacherId = getTeacher().getTeacherId();
		//cdPlanIdJSp=Integer.parseInt(req.getParameter("cdPlanId"));
		String sql="select cDDesignTopId,topics from "
				+ "cddesigntopics ct ,cdplan cp,cdteachergroup cg "
				+ "where ct.cdteachergroupid=cg.cdteachergroupid "
				+ "and cp.cdplanId=cg.cdplanId and cp.cdplanId="+cdplanId
				+ " and ct.teacherid="+teacherId;
		List data=serviceFactory.getPageBeanService().getQueryList(sql);
		sendJsonData(data);
	}
	
	/**
	 * ������ѯ
	 * ��ȡ���˽׶�����
	 * @throws IOException 
	 */
	public void getProcessName() throws IOException{
		BindProcessName();
	}
	/**
	 * ��һ���õ����˽׶����Ƶķ���
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	private void BindProcessName() throws IOException{
		int teacherId = getTeacher().getTeacherId();
		//int cddesignTopIdJsp=0;//��Ŀ���
		//cddesignTopIdJsp=Integer.parseInt(req.getParameter("cddesignTopId"));
		//cdPlanIdJSp=Integer.parseInt(req.getParameter("cdPlanId"));
		String sql="select a.cdteachergroupid,processAssShId,processname,processDescribtion "
				+ "from processassshedule a,cdteacherGroup b, cddesigntopics c,cdplan d "
				+ "where a.cdteachergroupid=b.cdteachergroupid and b.cdplanid=d.cdplanid "
				+ "and c.cdDesignTopid="+cddesignTopId
				+ " and a.teacherid="+teacherId
				+ " and d.cdplanid="+cdplanId;
		List data=serviceFactory.getPageBeanService().getQueryList(sql);
		sendJsonData(data);
	}
	/**
	 * ��ʾѧ��С���ţ���Ƶ���Ŀ
	 * �Լ����̿��˵���Ϣ
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	public String getPFile() throws IOException{
		
		/*String sql="select processdocument.*  "
				+ "from processdocument natural join "
				+ "processassshedule"
				+ " where processassshid="+processAssShId;*/
		String sql="select stugroupid,topics,a.*  "
				+ "from processassshedule  a,studentgroup  c "
				+ ",cddesigntopics ct "
				+ " where c.cddesigntopId="+cddesignTopId
				+ " and c.cddesigntopid=ct.cddesigntopid "
				+ " and a.processassshid="+processAssShId
				+ " order by stugroupid";
		
		List results = serviceFactory.getPageBeanService().getQueryList(sql);
		request.put("results", results);
		
		BindCdplanName();
		BindTopic();
		BindProcessName();
		return "getPFile";
	}
	/**
	 * ����ѧ���Ľ׶ο��˲���
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 */
	public InputStream getDownLoadFile() throws UnsupportedEncodingException, FileNotFoundException {
		Processdocument processdocument = new Processdocument();
		processdocument.setProcessDocId(this.processDocId);
		processdocument = serviceFactory.getProcessdocumentService().find(processdocument);
		// ��ȡ���ļ�����ʵ·��
		String path = processdocument.getDocumentUrl();
		// �������ص�����
		this.setFileName(new String(processdocument.getDocumentName().toString().getBytes("GBK"), "iso-8859-1"));
		InputStream in = new FileInputStream(path);
		processdocument.setIsReaded(1);
		serviceFactory.getProcessdocumentService().update(processdocument);
		return in;
	}
	
	public String execute() {
		return SUCCESS;
	}

 
	
	/**
	 * ��ʼ��������󶨼ƻ���
	 */
	@SuppressWarnings("unchecked")
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
	 * ��JSON���ݴ�������
	 * @param data
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	private void sendJsonData(List data) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println(JSON.toJSONString(data));
		out.flush();	
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
	/**
	 * ��ʾѧ���ɼ�������
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public void showScore() throws IOException{
		String sql="select c.processDocId,d.stuName,"
				+ "if(d.stuId=(select stuId from studentgroup "
				+ "where stugroupId="+stugroupid
				+ "),'�鳤','��Ա') '״̬��Ϣ' ,"
				+ "if(c.tutoropinion is null,'��δ����',c.tutoropinion),c.score from processdocument c,"
				+ "student d where d.stuId=c.stuId and "
				+ "c.processassshid="+processAssShId
				+ " and (c.stuId,d.stuName) "
				+ "in (select a.stuId,b.stuName  from stugroupmembers a,"
				+ "student b where a.stuId=b.stuId "
				+ "and a.stugroupId="+stugroupid+ ")";
		List data=serviceFactory.getPageBeanService().getQueryList(sql);
		sendJsonData(data);	
	}
	/**
	 * ָ���޸�ѧ���ɼ� 
	 * @return
	 * @throws Exception
	 */
	public String upListScore() throws Exception{
		for(Processdocument pd:processdocuments){
			Processdocument pdhelp=new Processdocument();
			pdhelp=serviceFactory.getProcessdocumentService().find(pd);
			pdhelp.setTutorOpinion(pd.getTutorOpinion());
			pdhelp.setScore(pd.getScore());
			serviceFactory.getProcessdocumentService().update(pdhelp);
		}
		BindCdplanName();
		BindTopic();
		BindProcessName();
		ShowMessage("�޸ĳɹ���");
		
		return "upListScore";
	}
	//////////////////////////ѧ�����˸��׶γɼ��鿴ҳ��///////////////////////
	/**
	 * ��ѧ���ɼ�����󶨿γ���Ƽƻ���
	 * @return
	 */
	public String gradePName(){
		BindCdplanName();
		return "gradePName";
	}
	/**
	 * ������ʾ��Ŀ
	 * @throws IOException 
	 */
	public void gradeCName() throws IOException{
		BindTopic();
		BindCdplanName();
	}
	/**
	 * ��ʾ�������ĳ����Ŀ��ѧ����Ϣ
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public String showStuCdName() throws IOException{
		String sql="select a.stuId,b.stuName,a.stugroupId,c.cddesigntopid,c.topics "
				+ "from stugroupmembers a,student b,cddesigntopics c "
				+ "where a.stuId=b.stuID and c.cddesigntopid="+cddesignTopId
				+ " and stugroupid in (select stugroupId "
				+ "from studentgroup where cddesigntopid="+cddesignTopId
				+ ")  order by a.stugroupid";
		List stuCdNames=serviceFactory.getPageBeanService().getQueryList(sql);
		request.put("stuCdNames", stuCdNames);
		BindCdplanName();
		BindTopic();
		return "showStuCdName";
	}
	/**
	 * ��ʾ��һ��ѧ�������׶εĿ��˳ɼ� 
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	public void getListSco() throws IOException{
		int teacherid = getTeacher().getTeacherId();
		String sql="select a.processdocid,b.processName,score from processdocument a,processassshedule b"
				+ " where a.processassshid=b.processassshid and"
				+ " a.processassshid in(select processAssshid from"
				+ " processassshedule where teacherid="+teacherid
				+ " and cdteacherGroupId=(select cdteachergroupId from "
				+ "cddesigntopics where cddesigntopid="+cddesignTopId
				+ " and teacherId="+teacherid
				+ ")) and stuid="+stuId;
		List data=serviceFactory.getPageBeanService().getQueryList(sql);
		sendJsonData(data);	
	}
	public String upGradeScore() throws Exception{
		for(Processdocument pd:processdocuments){
			Processdocument pdhelp=new Processdocument();
			pdhelp=serviceFactory.getProcessdocumentService().find(pd);
			//pdhelp.setTutorOpinion(pd.getTutorOpinion());
			pdhelp.setScore(pd.getScore());
			serviceFactory.getProcessdocumentService().update(pdhelp);
		}
		BindCdplanName();
		BindTopic();
		ShowMessage("�޸ĳɹ�!");
		
		return "upGradeScore";
	}
//////////////////////////(end)ѧ�����˸��׶γɼ��鿴ҳ��///////////////////////

//////////////////////////ѧ���γ���ƴ��ҳ��///////////////////////
	/**
	 * �ڴ��γ���ƽ���󶨿γ���Ƽƻ���
	 * @return
	 */
	public String replyPName(){
		BindreplyName();
		return "replyPName";
	}
	/**
	 * ������ʾ��Ŀ
	 * @throws IOException 
	 */
	public void replyCName() throws IOException{
		
		BindreplyName();
		BindReplyTopic();
	}
	/**
	 * ������ѯҪ������Ŀ
	 * @throws IOException 
	 */
	private void BindReplyTopic() throws IOException {
		String sql="select st.cddesigntopid,"
				+ "ct.topics from studentgroup st,"
				+ "cddesigntopics ct  where  ct.cddesigntopid=st.cddesigntopid"
				+ " and st.stugroupid="+stugroupid;
		List data=serviceFactory.getPageBeanService().getQueryList(sql);
		sendJsonData(data);
		
	}

	/**
	 * ��ʾ�������ĳ����Ŀ��ѧ��С����Ϣ
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getAllGroup() throws IOException{
		/*Cddesigntopics cddesigntopics=new Cddesigntopics();
		cddesigntopics.setCddesignTopId(cddesignTopId);
		cddesigntopics=serviceFactory.getCddesigntopicsService().find(cddesigntopics);
		List<Studentgroup> studentgroups = new ArrayList(cddesigntopics.getStudentgroups());
		request.put("studentgroups", studentgroups);
		
		String sql="select stugroupId from studentgroup where"+
				" cddesigntopid="+cddesignTopId+
				" order by stugroupId";*/
		int teacherId = getTeacher().getTeacherId();
		String sql="select sg.stugroupId from studentgroup sg,"
				+ "replyplan rp where rp.stugroupid=sg.stugroupid and "
				+ " cddesigntopid="+cddesignTopId
                +"  and rp.stugroupid = (select stugroupid from replyPlan "
                +"where stugroupid= "+stugroupid
                + " and replygroupId in(select replyGroupId"
                + " from replymembers where teacherId="+teacherId+") and (select now() from dual) "
                		+ "between startTime and endTime)  order by sg.stugroupId ";
		String sql2="select rp.replyplanId from studentgroup sg,"
				+ "replyplan rp where rp.stugroupid=sg.stugroupid and "
				+ " cddesigntopid="+cddesignTopId
                +"  and rp.stugroupid = (select stugroupid from replyPlan "
                +"where stugroupid= "+stugroupid
                + " and  replygroupId in(select replyGroupId"
                + " from replymembers where teacherId="+teacherId+") and (select now() from dual) "
                		+ "between startTime and endTime)  order by sg.stugroupId ";
		
		List groups=serviceFactory.getPageBeanService().getQueryList(sql);
		List groups2=serviceFactory.getPageBeanService().getQueryList(sql2);
		
		request.put("groups",groups);
		List<MemberResult> mrs=new ArrayList<MemberResult>();
		
		for(int i=0;i<groups.size();i++){
			MemberResult mr=new MemberResult();
			
			mr.setReplyplanId(Integer.parseInt(groups2.get(i).toString()));
			mr.setStugroupId(Integer.parseInt(groups.get(i).toString()));
			System.out.println("�ƻ����:"+mr.getReplyplanId()+"С���ţ�"+mr.getStugroupId());
			sql="select a.stuId,b.stuName,if(a.stuId=(select stuId "
					+ "from studentgroup where stugroupId="+mr.getStugroupId()
					+ "),'�鳤','��Ա') from stugroupmembers a, student b "
					+ "where a.stuId=b.stuId and stuGroupId ="
					+ " (select stugroupId from studentgroup "
					+ "where cddesigntopid="+cddesignTopId
					+ " and stugroupId="+mr.getStugroupId()
					+ " order by stugroupId)";
			List data=serviceFactory.getPageBeanService().getQueryList(sql);
			mr.setMembers(data);
			mrs.add(mr);
		 }
		request.put("mrs", mrs);
		BindreplyName();
		BindReplyTopic();
		return "getAllGroup";
	}
	/**
	 * ��ʼ��������󶨴��ƻ���
	 */
	@SuppressWarnings("unchecked")
	private void  BindreplyName(){
		Teacher teacher=new Teacher();
		teacher.setTeacherId(getTeacher().getTeacherId());	
		teacher=serviceFactory.getTeacherService().find(teacher);
		String sql="select replyPlanId,replyName,"
				+ "stuGroupId from replyPlan "
				+ "where replygroupId in(select replyGroupId"
				+ " from replymembers where teacherId="+teacher.getTeacherId()
				+ ") and (select now() from dual) between startTime and endTime";
		@SuppressWarnings("rawtypes")
		List<Cdteachergroup> data2 = serviceFactory.getPageBeanService().getQueryList(sql);
		ServletActionContext.getRequest().setAttribute("replyNames",data2);
	
	}
	/**
	 * ����ѧ����ţ���ȡ�������Ĵ���¼
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public void getRecord() throws IOException{
		int teacherid = getTeacher().getTeacherId();
		String sql="select * from replyrecords where stuId="+stuId
				+ " and replyplanId="+replyplanId+""
						+ " and teacherId="+teacherid;
		List data=serviceFactory.getPageBeanService().getQueryList(sql);
		sendJsonData(data);
	}
	
	public String addRec() throws IOException{
		Replyplan rp=new Replyplan();
		rp.setReplyPlanId(replyplanId);
		rp=serviceFactory.getReplyplanService().find(rp);
		rec.setReplyplan(rp);
		Student st=new Student();
		st.setStuId(stuId);
		//st=serviceFactory.getStudentService().find(st);
		rec.setStudent(st);
		
		Teacher te=new Teacher();
		te.setTeacherId(getTeacher().getTeacherId());
		te=serviceFactory.getTeacherService().find(te);
		rec.setTeacher(te);
		
		serviceFactory.getReplyRecordsService().save(rec);
		BindreplyName();
		BindReplyTopic();
		ShowMessage("�ɹ���Ӵ���¼��");
		return "addRec";
	}
	/**
	 * ˽�з�������ʾδ��ӳɼ���ѧ���б�
	 */
	private void showScohelp(){
		int teacherId = getTeacher().getTeacherId();
		String sql="select s.stuId,cg.cdteacherGroupId,s.stuName,st.stugroupId,s.studentId,"
				+ "cp.cdplanId,cp.cdplanName,ct.cddesigntopid,ct.topics,"
				+ "sf.selfscore from studentgroup st,cdplan cp,cddesigntopics ct,"
				+ "cdteacherGroup cg,student s ,stugroupmembers gpm,selfevaluation sf "
				+ "where (s.stuId not in (select stuId from studentscore ) "
				+ "or (s.stuId in (select stuId from studentscore where "
				+ "cddesigntopid=ct.cddesigntopid and totalScore is null)))"
				+ " and sf.stuId=s.stuId and sf.cddesigntopId=ct.cddesigntopID and st.cddesignTopId=ct.cddesigntopId and cg.cdplanId=cp.cdplanID "
				+ "and ct.cdteacherGroupId=cg.cdteacherGroupID and st.cddesignTopId=ct.cddesigntopID "
				+ "and gpm.stuGroupId=st.stuGroupId and s.stuId=gpm.stuId and "
				+ "(st.stugroupId,cp.cdplanId,cp.cdPlanName,ct.cddesigntopid,ct.topics) in(select st.stugroupId,cp.cdplanId,cp.cdplanName,ct.cddesignTopId,ct.topics "
				+ "from studentgroup st,cdplan cp,cddesigntopics ct,cdteacherGroup cg where "
				+ "st.cddesignTopId=ct.cddesigntopId and cg.cdplanId=cp.cdplanID "
				+ "and ct.cdteacherGroupId=cg.cdteacherGroupID and st.cddesignTopId=ct.cddesigntopID and "
				+ "(cp.cdplanId,cp.cdplanName,ct.cddesignTopId, ct.topics) in "
				+ "(select cp.cdplanId,cp.cdplanName,cDDesignTopId,topics from "
				+ "cddesigntopics ct ,cdplan cp,cdteachergroup cg "
				+ "where ct.cdteachergroupid=cg.cdteachergroupid "
				+ "and cp.cdplanId=cg.cdplanId and ct.teacherid="+teacherId
				+ " and cp.cdplanId in(select cg.cdplanId from "
				+ "cdteachergroup cg,cdplan cp where cdteachergroupId in (select cdteacherGroupId "
				+ "from  cdgroupteachers where teacherId="+teacherId
				+ " ) "
				+ "and cp.cdplanId=cg.cdplanId and cp.isCurrent=1))) order by cp.cdplanId,ct.cddesigntopId";
		PageBean pageBean = new PageBean();
		pageBean=
				serviceFactory.getPageBeanService().getPageBean(sql, 2, pno);
		ServletActionContext.getRequest().setAttribute("pageBean", pageBean);
		//ServletActionContext.getRequest().setAttribute("addScoreList",data);
	
	}
	/**
	 * �鿴��ʦ�����γ���Ƶ�ѧ�������Ŀ��
	 * ������������Ŀ���ɼ������������
	 * @return
	 */
	public String findAddScore(){
		showScohelp();
		return "findAddScore";
	}
	/**
	 * ��ȡĳλѧ���Ĳ��ϼ�������� 
	 * Json���ݷ��ص�ǰ̨
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	public void getUsualFile() throws IOException{
		int teacherId = getTeacher().getTeacherId();
		String sql="select documentName,score,if(isReaded=0,'*δ����','') "
				+ "from processdocument where "
				+ "processassshid in(select processassshid"
				+ " from processassshedule where cdteachergroupid="+cdteachergroupId
				+ " and teacherId="+teacherId
				+ ") and stuId="+stuId;
		List data=serviceFactory.getPageBeanService().getQueryList(sql);
		sendJsonData(data);
	}
	
	@SuppressWarnings("rawtypes")
	/**
	 * �鿴��ǰѧ���Ŀ��������
	 * @throws IOException
	 */
	public void getreplyList() throws IOException{
		String sql="select question,answers,teacherName "
				+ "from replyrecords rrd,teacher  "
				+ "where replyplanId in (select replyplanId "
				+ "from replyplan where stugroupId="+stugroupid
				+ ") and stuId="+stuId
				+ " and rrd.teacherId=teacher.teacherId";
		List data =serviceFactory.getPageBeanService().getQueryList(sql);
		sendJsonData(data);
	}
	/**
	 * �õ� ���ֱ�׼
	 * @throws IOException
	 */
	public void getstandScore() throws IOException{
		String sql="select attendCoe,procAccCoe,replyCoe,selfCoe from evalstandards "
				+ "where cdteachergroupid="+cdteachergroupId;
		List data=serviceFactory.getPageBeanService().getQueryList(sql);
		
		sendJsonData(data);
	}
	
	/**
	 * ���ѧ���ɼ�
	 * @return
	 * @throws IOException 
	 */
	public String addListScore() throws IOException{
		Studentscore scwq=new Studentscore();
		Student wq=new Student();
		wq.setStuId(stuId);
		wq=serviceFactory.getStudentService().find(wq);
		scwq.setStudent(wq);
		Cddesigntopics cp=new Cddesigntopics();
		cp.setCddesignTopId(cddesignTopId);
		cp=serviceFactory.getCddesigntopicsService().find(cp);
		scwq.setCddesigntopics(cp);
		float a,b,c,d;
		a=ssc.getAttendanceScore()*attendCoe;
		b=ssc.getExamScore()*procAccCoe;
		c=ssc.getSelEvScore()*selfCoe;
		d=ssc.getReplyScore()*replyCoe;
		System.out.println(a+b+c+d);
		scwq.setAttendanceScore(a);
		scwq.setExamScore(b);
		scwq.setSelEvScore(c);
		scwq.setReplyScore(d);
		scwq.setTotalScore(a+b+c+d);
		serviceFactory.getStudentScoreService().save(scwq);
		ShowMessage("����ӳɼ�!");
		showScohelp();
		return "addListScore";
	}
	///////////////////////�޸�ѧ���ɼ� JSp//////////////////////////////////
	/**
	 * ��ʾȫ��ѧ���ɼ�
	 * @return
	 */
	public String viewTotalScore(){
		viewTotalScoreHelp();
		return "viewTotalScore";
	}
	
	private void viewTotalScoreHelp(){
		int teacherId = getTeacher().getTeacherId();
		String sql="select dsc.id, s.stuId,s.studentId,s.stuName,st.stugroupId,s.studentId,cp.cdplanName,cg.cdteachergroupid,ct.topics,sf.selfscore,dsc.attendancescore,dsc.examscore,"+
"dsc.selEvscore,dsc.replyScore,totalScore "+
"from studentgroup st,cdplan cp,cddesigntopics ct,cdteacherGroup cg,student s ,stugroupmembers gpm,studentscore dsc,"+
"selfevaluation sf where dsc.stuId=s.stuId and dsc.cddesigntopid=ct.cddesigntopid and sf.stuId=s.stuId and sf.cddesigntopId=ct.cddesigntopID and"+
" st.cddesignTopId=ct.cddesigntopId and cg.cdplanId=cp.cdplanID"+
" and ct.cdteacherGroupId=cg.cdteacherGroupID and st.cddesignTopId=ct.cddesigntopID"+
" and gpm.stuGroupId=st.stuGroupId and s.stuId=gpm.stuId "+
"and (st.stugroupId,cp.cdplanId,cp.cdPlanName,ct.cddesigntopid,ct.topics) in("+
"select st.stugroupId,cp.cdplanId,cp.cdplanName,ct.cddesignTopId,ct.topics"+
" from studentgroup st,cdplan cp,cddesigntopics ct,cdteacherGroup cg where "+
"st.cddesignTopId=ct.cddesigntopId and cg.cdplanId=cp.cdplanID"+
" and ct.cdteacherGroupId=cg.cdteacherGroupID and st.cddesignTopId=ct.cddesigntopID"+
" and  (cp.cdplanId,cp.cdplanName,ct.cddesignTopId, ct.topics) in("+
"select cp.cdplanId,cp.cdplanName,cDDesignTopId,topics from "+
"cddesigntopics ct ,cdplan cp,cdteachergroup cg "+
"where ct.cdteachergroupid=cg.cdteachergroupid"+
" and cp.cdplanId=cg.cdplanId "+
" and ct.teacherid="+teacherId
+ " and cp.cdplanId in(select cg.cdplanId from "+
"cdteachergroup cg,cdplan cp"+
" where cdteachergroupId in (select cdteacherGroupId"+
" from  cdgroupteachers where teacherId="+teacherId+
" )and cp.cdplanId=cg.cdplanId and cp.isCurrent=1)))";
		PageBean pageBean = new PageBean();
		pageBean=
				serviceFactory.getPageBeanService().getPageBean(sql, 2, pno);
		ServletActionContext.getRequest().setAttribute("pageBean", pageBean);
		
	}
	public String updateLiSSc() throws IOException{
		Studentscore scwq=new Studentscore();
		scwq=serviceFactory.getStudentScoreService().find(ssc);
		float a,b,c,d;
		a=ssc.getAttendanceScore()*attendCoe;
		b=ssc.getExamScore()*procAccCoe;
		//c=ssc.getSelEvScore()*selfCoe;
		d=ssc.getReplyScore()*replyCoe;
		scwq.setAttendanceScore(a);
		scwq.setExamScore(b);
		scwq.setReplyScore(d);
		scwq.setTotalScore(a+b+d+scwq.getSelEvScore());
		serviceFactory.getStudentScoreService().update(scwq);
		ShowMessage("�ɹ��޸�ѧ���ɼ���");
		viewTotalScoreHelp();
		return "updateLiSSc";
	}
	///////////////////////�޸�ѧ���ɼ� JSpEnd//////////////////////////////////

	// ע��serviceFactory
		public ServiceFactory getServiceFactory() {
				return serviceFactory;
		}
		public void setServiceFactory(ServiceFactory serviceFactory) {
				this.serviceFactory = serviceFactory;
		}
	
	@Override
	public void setRequest(Map<String, Object> req) {
		this.request = req;
	}
	public int getProcessAssShId() {
		return processAssShId;
	}
	public void setProcessAssShId(int processAssShId) {
		this.processAssShId = processAssShId;
	}
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public int getCddesignTopId() {
		return cddesignTopId;
	}
	public void setCddesignTopId(int cddesignTopId) {
		this.cddesignTopId = cddesignTopId;
	}
	public List<Processdocument> getProcessdocuments() {
		return processdocuments;
	}

	public void setProcessdocuments(List<Processdocument> processdocuments) {
		this.processdocuments = processdocuments;
	}
	public int getStugroupid() {
		return stugroupid;
	}

	public void setStugroupid(int stugroupid) {
		this.stugroupid = stugroupid;
	}

	public int getCdplanId() {
		return cdplanId;
	}

	public void setCdplanId(int cdplanId) {
		this.cdplanId = cdplanId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getProcessDocId() {
		return processDocId;
	}

	public void setProcessDocId(int processDocId) {
		this.processDocId = processDocId;
	}

	public int getStuId() {
		return stuId;
	}

	public void setStuId(int stuId) {
		this.stuId = stuId;
	}	
}
