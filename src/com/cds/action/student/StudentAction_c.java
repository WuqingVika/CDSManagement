package com.cds.action.student;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.aspectj.weaver.AjAttribute.PrivilegedAttribute;

import com.alibaba.fastjson.JSON;
import com.cds.dao.facory.DaoFactory;
import com.cds.dao.impl.CddesigntopicsDao;
import com.cds.entity.Account;
import com.cds.entity.Cddesigntopics;
import com.cds.entity.Cdplan;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.Classes;
import com.cds.entity.Evalstandards;
import com.cds.entity.PageBean;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;
import com.cds.entity.Teacher;
import com.cds.service.service.factory.ServiceFactory;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionSupport;

/**
 * ѧ����Action
 * 
 * @author PengChan
 *
 */
public class StudentAction_c extends ActionSupport {
	private static final long serialVersionUID = 1L;
	// ע��serviceFactory
	private ServiceFactory serviceFactory; // serviceFactory

	private String fileName;// �ĵ�������

	private Account account;// �û�����Ϣ

	private int pno = 0;// ��ҳ�ĵ�ǰҳ

	private int scoreStandards;// ���ֱ�׼��

	private int cdTeacherGroupId;// ��ʦС��ı��

	private int cdPlanId;// �γ̼ƻ��ı��

	private Cddesigntopics cddesigntopics;// �γ������Ŀ

	private Studentgroup studentgroup;// ѧ��С��

	private String studentId;// ѧ����ѧ��

	private int stuGroupId;// ѧ��С��ı��
	

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public int getStuGroupId() {
		return stuGroupId;
	}

	public void setStuGroupId(Integer stuGroupId) {
		this.stuGroupId = stuGroupId;
	}

	public Cddesigntopics getCddesigntopics() {
		return cddesigntopics;
	}

	public void setCddesigntopics(Cddesigntopics cddesigntopics) {
		this.cddesigntopics = cddesigntopics;
	}

	public Studentgroup getStudentgroup() {
		return studentgroup;
	}

	public void setStudentgroup(Studentgroup studentgroup) {
		this.studentgroup = studentgroup;
	}

	public int getCdPlanId() {
		return cdPlanId;
	}

	public void setCdPlanId(int cdPlanId) {
		this.cdPlanId = cdPlanId;
	}

	public int getCdTeacherGroupId() {
		return cdTeacherGroupId;
	}

	public void setCdTeacherGroupId(int cdTeacherGroupId) {
		this.cdTeacherGroupId = cdTeacherGroupId;
	}

	public int getScoreStandards() {
		return scoreStandards;
	}

	public void setScoreStandards(int scoreStandards) {
		this.scoreStandards = scoreStandards;
	}

	public int getPno() {
		return pno;
	}

	public void setPno(int pno) {
		this.pno = pno;
	}

	public void setServiceFactory(ServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * actionĬ��ִ�еķ���
	 */
	public String execute() {
		return SUCCESS;
	}

	/**
	 * ��ȡѧ������Ϣ
	 * 
	 * @return
	 */
	private Student getStudent() {
		if (ServletActionContext.getRequest().getSession().getAttribute("account") != null) {
			Account account = (Account) ServletActionContext.getRequest().getSession().getAttribute("account");			 
			account = serviceFactory.getAccountService().find(account);
			Student student = new Student();
			for (Student stu : account.getStudents()) {
				student = stu;
			}
			return student;
		} else {
			return null;
		}
	}

	/**
	 * ��ѯѧ������Ϣ
	 * 
	 * @return
	 */
	public String getStudentInfo() {
		ServletActionContext.getRequest().setAttribute("student", getStudent());
		return "studentInfo";
	}

	/**
	 * ��ѧ������Ϣ����Ϊpdf�ĵ�
	 * 
	 * @return inputstream��
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public InputStream getStudentInfoPDF()
			throws UnsupportedEncodingException, FileNotFoundException, DocumentException {
		// �����ݿ��ж�ȡ����
		Student student = getStudent();
		// �ƶ������ļ�������
		this.setFileName(new String((student.getStuName() + ".pdf").getBytes("GBK"), "iso-8859-1"));

		BaseFont bfChinese = null;
		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		Font font = new Font(bfChinese, 12, Font.BOLD);
		font.setColor(255, 0, 0);
		Document document = new Document(PageSize.A4);
		String path = "temp" + System.currentTimeMillis() + ".pdf";
		PdfWriter.getInstance(document, new FileOutputStream(path));
		document.open();
		String title = "������Ϣ��";
		Paragraph paragraph = new Paragraph(title, font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(paragraph);
		PdfPTable table = new PdfPTable(2);
		table.setSpacingBefore(30f);
		// �������
		PdfPCell cell = new PdfPCell(new Paragraph("ѧ������:", new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph(student.getStuName(), new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph("ѧ��ѧ��:", new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph(student.getStudentId(), new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph("ѧԺ:", new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(
				new Paragraph(student.getClasses().getMajor().getCollege().getCollegeName(), new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph("רҵ:", new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph(student.getClasses().getMajor().getMajorName(), new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph("�༶:", new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph(student.getClasses().getClassName(), new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		document.add(table);
		document.close();
		// �½���һ���ļ������
		InputStream in = new FileInputStream(path);
		// ɾ����ʱ�ļ�
		File file = new File("temp.pdf");
		if (file.isFile() && file.exists()) {
			file.delete();
		}
		return in;
	}

	/**
	 * �޸��˻�����
	 * 
	 * @return
	 */
	public String modifyAccountPass() {
		Account session = (Account) ServletActionContext.getRequest().getSession().getAttribute("account");
		session.setPasswords(account.getPasswords());
		serviceFactory.getAccountService().update(session);
		return "modifypass";
	}

	/**
	 * ��ѯ��Ҫѡ��Ŀγ����
	 * 
	 * @return
	 */
	public String searchCDChoosing() {
		// ��ȡ��ǰѧ������Ϣ
		Student student = getStudent();
		String hql = "select cp.cDPlanId,cp.cDPlanName,m.majorName,tm.termName,"
				+ "cp.totalCredits,cp.totalClassHour,th.teacherName,evl.evalStandId,tg.cDTeacherGroupId from cdplan cp"
				+ " natural join term tm natural join major m "
				+ "natural join cdteachergroup ctg natural join evalstandards evl" + " natural join cdteachergroup tg "
				+ " natural join teacher th where cp.isCurrent = 1 and m.majorId ="
				+ student.getClasses().getMajor().getMajorId() + " and cp.cDPlanId not in("
				+ "select distinct cdplan.cDPlanId"
				+ " from student,stugroupmembers,studentgroup,cddesigntopics,cdteachergroup,cdplan"
				+ " where student.stuId = stugroupmembers.stuId"
				+ " and stugroupmembers.stuGroupId = studentgroup.stuGroupId"
				+ " and studentgroup.cDDesignTopId = cddesigntopics.cDDesignTopId"
				+ " and cddesigntopics.cDTeacherGroupId = cdteachergroup.cDTeacherGroupId"
				+ " and cdteachergroup.cDPlanId = cdplan.cDPlanId" + " and student.stuId = " + student.getStuId() + ")";
		PageBean pageBean = serviceFactory.getPageBeanService().getPageBean(hql, 2, pno);
		ServletActionContext.getRequest().setAttribute("pageBean", pageBean);
		return "needChoosing";
	}

	/**
	 * �鿴�γ����С������ֱ�׼
	 * 
	 * @throws IOException
	 */
	public void getEvalStandards() throws IOException {
		Evalstandards evalstandards = new Evalstandards();
		evalstandards.setEvalStandId(this.scoreStandards);
		evalstandards = serviceFactory.getEvalstandardsService().find(evalstandards);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(evalstandards));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * ͨ����ʦС��ı�Ż�ȡС����������н�ʦ����Ϣ
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public void getAllGroupTutors() throws IOException {
		Cdteachergroup cdteachergroup = new Cdteachergroup();
		cdteachergroup.setCdteacherGroupId(cdTeacherGroupId);
		List result = serviceFactory.getCdteachergroupService().findTeacherByGP_c(cdteachergroup);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(result));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * ���ݰ༶��ȡ���е�ѧ������Ϣ
	 * 
	 * @throws IOException
	 */
	public void getAllStudetnJSON() throws IOException {
		Student student = getStudent();
		Cdplan cdplan = new Cdplan();
		cdplan.setCdplanId(this.cdPlanId);
		List<?> results = serviceFactory.getStudentService().findAllNeedChoosingStu_c(student, cdplan);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(results));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * ͨ����ʦС��ı�ţ���ѯ���еĿ������Ϣ
	 * 
	 * @throws IOException
	 */
	public void getAllTopicsByTGId() throws IOException {
		Cdteachergroup cdteachergroup = new Cdteachergroup();
		cdteachergroup.setCdteacherGroupId(this.cdTeacherGroupId);
		List<?> results = serviceFactory.getCddesigntopicsService().findAllTopicByTGpId_c(cdteachergroup);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(results));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * �γ��������ѡ��
	 */
	public String selfSelectCDTopic() {
		// ��װʵ����
		Student student = getStudent();
		// ���С���鳤
		studentgroup.setStudent(student);
		// ��С���Ա�����С���鳤
		studentgroup.getStudents().add(student);
		if (serviceFactory.getCddesigntopicsService().selfSelectCDTopic_c(cddesigntopics, studentgroup)) {
			return "selfSelectCDTopic";
		} else {
			return "selfSelectError";
		}
	}

	/**
	 * �γ����ѡ��ָ����ʦ��������Ŀ
	 */
	public String selectCDTopic() {
		Student student = new Student();
		student = getStudent();
		// �����鳤
		this.studentgroup.setStudent(student);
		// ���鳤��ӵ���Ա��
		this.studentgroup.getStudents().add(student);
		if (serviceFactory.getCddesigntopicsService().selectCDTopic_c(studentgroup)) {
			return "selectCDTopicSuccess";
		} else {
			return "selectCDTopicError";
		}
	}

	/**
	 * ��ѯ���е��Լ�ѡ�����Ŀ
	 */
	public String findAllSelfChoosed() {
		Student student = getStudent();
		String hql = "select distinct cddesigntopics.cDDesignTopId,"
				+ "cddesigntopics.topics,cddesigntopics.topicsDescribtion, "
				+ "cdchoosingrecord.submitTime, cdchoosingrecord.isPassed,"
				+ "teacher.teacherName,cdchoosingrecord.tutorOpinion,studentgroup.stuGroupId,cdplan.cDPlanId"
				+ " from cdplan,cdteachergroup,cdgroupteachers,teacher," + " cddesigntopics,cdchoosingrecord,"
				+ " studentgroup,stugroupmembers,student" + " where cdplan.cDPlanId = cdteachergroup.cDPlanId"
				+ " and cdteachergroup.cDTeacherGroupId = cdgroupteachers.cDTeacherGroupId"
				+ " and cdteachergroup.teacherId = teacher.teacherId"
				+ " and cdteachergroup.cDTeacherGroupId = cddesigntopics.cDTeacherGroupId"
				+ " and cddesigntopics.cDDesignTopId = cdchoosingrecord.cDDesignTopId"
				+ " and studentgroup.cDDesignTopId = cddesigntopics.cDDesignTopId"
				+ " and studentgroup.stuGroupId = stugroupmembers.stuGroupId"
				+ " and stugroupmembers.stuId = student.stuId" + " and cdplan.isCurrent = 1" + " and student.stuId ="
				+ student.getStuId();
		PageBean pageBean = serviceFactory.getPageBeanService().getPageBean(hql, 3, pno);
		ServletActionContext.getRequest().setAttribute("pageBean", pageBean);
		return "selectChoosingCD_1";
	}

	/**
	 * ��ѯ����ѡ���ʦ�ƶ�����Ŀ
	 * 
	 * @return
	 */
	public String findAllChooseThCD() {
		Student student = getStudent();
		String hql = "select distinct cddesigntopics.cDDesignTopId,cddesigntopics.topics,"
				+ "cddesigntopics.topicsDescribtion,teacher.teacherName,"
				+ "studentgroup.stuGroupId,cdplan.cDPlanId"			 
				+ " from cdplan,cdteachergroup,cdgroupteachers,teacher," + " cddesigntopics, "
				+ " studentgroup,stugroupmembers,student" + " where cdplan.cDPlanId = cdteachergroup.cDPlanId"
				+ " and cdteachergroup.cDTeacherGroupId = cdgroupteachers.cDTeacherGroupId"
				+ " and cdteachergroup.teacherId = teacher.teacherId"
				+ " and cdteachergroup.cDTeacherGroupId = cddesigntopics.cDTeacherGroupId"
				+ " and studentgroup.cDDesignTopId = cddesigntopics.cDDesignTopId"
				+ " and studentgroup.stuGroupId = stugroupmembers.stuGroupId"
				+ " and stugroupmembers.stuId = student.stuId" + " and cddesigntopics.isSelfChoosed = 0"
				+ " and cdplan.isCurrent = 1" + " and student.stuId =" + student.getStuId();
		PageBean pageBean = serviceFactory.getPageBeanService().getPageBean(hql, 3, pno);
		ServletActionContext.getRequest().setAttribute("pageBean", pageBean);
		;
		return "selectChoosingCD_2";
	}

	/**
	 * ��ѯ���е�ѡ�����Ϣ
	 * 
	 * @return
	 */
	public String findAllChoosedCD() {
		Student student = getStudent();
		String hql = "select distinct cddesigntopics.cDDesignTopId,cddesigntopics.topics,"
				+ "cddesigntopics.topicsDescribtion,teacher.teacherName,"
				+ "studentgroup.stuGroupId,cdplan.cDPlanId"	
				+ " from cdplan,cdteachergroup,cdgroupteachers,teacher," + " cddesigntopics,"
				+ " studentgroup,stugroupmembers,student" + " where cdplan.cDPlanId = cdteachergroup.cDPlanId"
				+ " and cdteachergroup.cDTeacherGroupId = cdgroupteachers.cDTeacherGroupId"
				+ " and cdteachergroup.teacherId = teacher.teacherId"
				+ " and cdteachergroup.cDTeacherGroupId = cddesigntopics.cDTeacherGroupId"
				+ " and studentgroup.cDDesignTopId = cddesigntopics.cDDesignTopId"
				+ " and studentgroup.stuGroupId = stugroupmembers.stuGroupId"
				+ " and stugroupmembers.stuId = student.stuId" + " and cdplan.isCurrent = 1" + " and student.stuId ="
				+ student.getStuId();
		PageBean pageBean = serviceFactory.getPageBeanService().getPageBean(hql, 3, pno);
		ServletActionContext.getRequest().setAttribute("pageBean", pageBean);
		return "selectChoosingCD_3";
	}

	/**
	 * ����С���Ų�ѯ�û��ĳ�Ա��Ϣ
	 * 
	 * @throws IOException
	 */
	public void findAllGroupMembers() throws IOException {
		List results = serviceFactory.getStudentGroupService().searchAllStuGroupMembers_c(this.studentgroup);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(results));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * ��ѧ��С���������Ϣ
	 * 
	 */
	public void addStuMembers() {
		try {	
			System.out.println(studentId+","+stuGroupId);
			//����һ��ѧ����ʵ����
			Student student = new Student();
			student.setStudentId(this.studentId);
			student = serviceFactory.getStudentService().findStuByStudentId_c(student);
			//����һ��ѧ��С���ʵ����
			Studentgroup studentgroup = new Studentgroup();
			studentgroup.setStuGroupId(this.stuGroupId);
			//���ѧ����Ϣ
			serviceFactory.getStudentGroupService().addStuGroupMembers_c(studentgroup, student);
			//��ѯ�Ѿ���ӵ���ѧ������Ϣ
			List results = serviceFactory.getStudentGroupService().searchAllStuGroupMembers_c(studentgroup);
			ServletActionContext.getResponse().setContentType("text/html");
			ServletActionContext.getResponse().setCharacterEncoding("utf-8");
			ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(results));
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ѯ�༶ѡ�����
	 * @throws IOException 
	 */
	public void findAllCDByClaAndCD() throws IOException{
		Classes classes = getStudent().getClasses();
		Cdplan cdplan = new Cdplan();
		cdplan.setCdplanId(cdPlanId);;
		List results = serviceFactory.getCddesigntopicsService().findCDByClAndCdPlan_c(classes, cdplan);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(results));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}
	
	/**
	 * �˳�ѧ��С��
	 * @throws IOException 
	 */
	public void exitStuGroup() throws IOException {
		//��ѯѧ��С�����Ϣ
		Studentgroup studentgroup = new Studentgroup();
		studentgroup.setStuGroupId(this.stuGroupId);
		studentgroup = serviceFactory.getStudentGroupService().find(studentgroup);		
		//ʵ����ѧ����
		Student student = getStudent();
		//��ȡprintwriter��
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		//�ж��Ƿ�Ϊ�鳤����������鳤������Ⱥ����������Ⱥ
		if(studentgroup.getStudent().getStuId().equals(student.getStuId())==false){
			try {
				serviceFactory.getStudentGroupService().removeStuGroupMem_c(studentgroup, student);
				ServletActionContext.getResponse().getWriter().print("<script>alert('�˳�С��ɹ�!��ע�⼰ʱ����ѡ�⣡');</script>");
			} catch (Exception e) {
				e.printStackTrace();
				ServletActionContext.getResponse().getWriter().print("<script>alert('�˳�С��ʧ��!');</script>");
			}			
		}else{
			try {
				//�鳤����Աһͬɾ��ѡ��ļ�¼
				serviceFactory.getStudentGroupService().removeAllStuGroupMem_c(studentgroup);
				ServletActionContext.getResponse().getWriter().println("<script>alert('���Ǹ�����鳤��ѡ���¼�Ѿ�ȫ����գ���ע�⼰ʱѡ�⣡');</script>");
			} catch (Exception e) {
				e.printStackTrace();
				ServletActionContext.getResponse().getWriter().println("<script>alert('�Բ����޷��˳�С��!');</script>");
			}			
		}	 
		ServletActionContext.getResponse().getWriter().println("<script>window.location.href='student_findAllSelfChoosed.action';</script>");		
		ServletActionContext.getResponse().getWriter().flush();	 	
		ServletActionContext.getResponse().getWriter().close();
	}
	
}
