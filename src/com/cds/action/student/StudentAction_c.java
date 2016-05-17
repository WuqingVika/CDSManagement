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
 * 学生的Action
 * 
 * @author PengChan
 *
 */
public class StudentAction_c extends ActionSupport {
	private static final long serialVersionUID = 1L;
	// 注入serviceFactory
	private ServiceFactory serviceFactory; // serviceFactory

	private String fileName;// 文档的名字

	private Account account;// 用户的信息

	private int pno = 0;// 分页的当前页

	private int scoreStandards;// 评分标准号

	private int cdTeacherGroupId;// 教师小组的编号

	private int cdPlanId;// 课程计划的编号

	private Cddesigntopics cddesigntopics;// 课程设计题目

	private Studentgroup studentgroup;// 学生小组

	private String studentId;// 学生的学号

	private int stuGroupId;// 学生小组的编号
	

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
	 * action默认执行的方法
	 */
	public String execute() {
		return SUCCESS;
	}

	/**
	 * 获取学生的信息
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
	 * 查询学生的信息
	 * 
	 * @return
	 */
	public String getStudentInfo() {
		ServletActionContext.getRequest().setAttribute("student", getStudent());
		return "studentInfo";
	}

	/**
	 * 将学生的信息导出为pdf文档
	 * 
	 * @return inputstream流
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public InputStream getStudentInfoPDF()
			throws UnsupportedEncodingException, FileNotFoundException, DocumentException {
		// 从数据库中读取数据
		Student student = getStudent();
		// 制定下载文件的名字
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
		String title = "个人信息表";
		Paragraph paragraph = new Paragraph(title, font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(paragraph);
		PdfPTable table = new PdfPTable(2);
		table.setSpacingBefore(30f);
		// 添加内容
		PdfPCell cell = new PdfPCell(new Paragraph("学生姓名:", new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph(student.getStuName(), new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph("学生学号:", new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph(student.getStudentId(), new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph("学院:", new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(
				new Paragraph(student.getClasses().getMajor().getCollege().getCollegeName(), new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph("专业:", new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph(student.getClasses().getMajor().getMajorName(), new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph("班级:", new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph(student.getClasses().getClassName(), new Font(bfChinese, 10)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		document.add(table);
		document.close();
		// 新建立一个文件输出流
		InputStream in = new FileInputStream(path);
		// 删除临时文件
		File file = new File("temp.pdf");
		if (file.isFile() && file.exists()) {
			file.delete();
		}
		return in;
	}

	/**
	 * 修改账户密码
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
	 * 查询需要选择的课程设计
	 * 
	 * @return
	 */
	public String searchCDChoosing() {
		// 获取当前学生的信息
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
	 * 查看课程设计小组的评分标准
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
	 * 通过教师小组的编号获取小组里面的所有教师的信息
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
	 * 根据班级获取所有的学生的信息
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
	 * 通过教师小组的编号，查询所有的课题的信息
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
	 * 课程设计自主选题
	 */
	public String selfSelectCDTopic() {
		// 封装实体类
		Student student = getStudent();
		// 添加小组组长
		studentgroup.setStudent(student);
		// 在小组成员中添加小组组长
		studentgroup.getStudents().add(student);
		if (serviceFactory.getCddesigntopicsService().selfSelectCDTopic_c(cddesigntopics, studentgroup)) {
			return "selfSelectCDTopic";
		} else {
			return "selfSelectError";
		}
	}

	/**
	 * 课程设计选择指导老师所定的题目
	 */
	public String selectCDTopic() {
		Student student = new Student();
		student = getStudent();
		// 设置组长
		this.studentgroup.setStudent(student);
		// 把组长添加到组员中
		this.studentgroup.getStudents().add(student);
		if (serviceFactory.getCddesigntopicsService().selectCDTopic_c(studentgroup)) {
			return "selectCDTopicSuccess";
		} else {
			return "selectCDTopicError";
		}
	}

	/**
	 * 查询所有的自己选择的题目
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
	 * 查询所有选择教师制定的题目
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
	 * 查询所有的选择的信息
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
	 * 根据小组编号查询用户的成员信息
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
	 * 向学生小组中添加信息
	 * 
	 */
	public void addStuMembers() {
		try {	
			System.out.println(studentId+","+stuGroupId);
			//定义一个学生的实体类
			Student student = new Student();
			student.setStudentId(this.studentId);
			student = serviceFactory.getStudentService().findStuByStudentId_c(student);
			//定义一个学生小组的实体类
			Studentgroup studentgroup = new Studentgroup();
			studentgroup.setStuGroupId(this.stuGroupId);
			//添加学生信息
			serviceFactory.getStudentGroupService().addStuGroupMembers_c(studentgroup, student);
			//查询已经添加到的学生的信息
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
	 * 查询班级选题情况
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
	 * 退出学生小组
	 * @throws IOException 
	 */
	public void exitStuGroup() throws IOException {
		//查询学生小组的信息
		Studentgroup studentgroup = new Studentgroup();
		studentgroup.setStuGroupId(this.stuGroupId);
		studentgroup = serviceFactory.getStudentGroupService().find(studentgroup);		
		//实例化学生类
		Student student = getStudent();
		//获取printwriter类
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		//判断是否为组长，如果不是组长可以退群，否则不能退群
		if(studentgroup.getStudent().getStuId().equals(student.getStuId())==false){
			try {
				serviceFactory.getStudentGroupService().removeStuGroupMem_c(studentgroup, student);
				ServletActionContext.getResponse().getWriter().print("<script>alert('退出小组成功!请注意及时重新选题！');</script>");
			} catch (Exception e) {
				e.printStackTrace();
				ServletActionContext.getResponse().getWriter().print("<script>alert('退出小组失败!');</script>");
			}			
		}else{
			try {
				//组长和组员一同删除选题的记录
				serviceFactory.getStudentGroupService().removeAllStuGroupMem_c(studentgroup);
				ServletActionContext.getResponse().getWriter().println("<script>alert('您是该组的组长，选题记录已经全部清空，请注意及时选题！');</script>");
			} catch (Exception e) {
				e.printStackTrace();
				ServletActionContext.getResponse().getWriter().println("<script>alert('对不起无法退出小组!');</script>");
			}			
		}	 
		ServletActionContext.getResponse().getWriter().println("<script>window.location.href='student_findAllSelfChoosed.action';</script>");		
		ServletActionContext.getResponse().getWriter().flush();	 	
		ServletActionContext.getResponse().getWriter().close();
	}
	
}
