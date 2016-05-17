package com.cds.action.student;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.Servlet;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.cds.entity.Account;
import com.cds.entity.Cddesigntopics;
import com.cds.entity.Cdteachergroup;
import com.cds.entity.PageBean;
import com.cds.entity.Processassshedule;
import com.cds.entity.Processdocument;
import com.cds.entity.Selfevaluation;
import com.cds.entity.Student;
import com.cds.entity.Studentgroup;
import com.cds.entity.Teacher;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.media.jfxmedia.control.VideoDataBuffer;

/**
 * 课程设计任务的action
 * 
 * @author PengChan
 *
 */
public class CDDesignTopicTask_c extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private int pno = 0;// 分页的页数

	private int teacherId;// 教师的编号

	private int cdTeacherGroupId;// 教师小组的编号

	private String fileName;// 文档的名字

	private String taskName;// 任务的名称

	private File uploadFile;// 上传的文件

	private String uploadFileFileName;// 上传的文件的名称

	private int processSheId;// 过程考核计划编号

	private int cdDesignTopicId;// 设计课题编号

	private int processDocId;// 过程考核文档编号

	private int stuGroupId;// 学生小组的编号

	private Selfevaluation selfevaluation;// 自评的类

	public Selfevaluation getSelfevaluation() {
		return selfevaluation;
	}

	public void setSelfevaluation(Selfevaluation selfevaluation) {
		this.selfevaluation = selfevaluation;
	}

	public int getStuGroupId() {
		return stuGroupId;
	}

	public void setStuGroupId(int stuGroupId) {
		this.stuGroupId = stuGroupId;
	}

	public int getProcessDocId() {
		return processDocId;
	}

	public void setProcessDocId(int processDocId) {
		this.processDocId = processDocId;
	}

	public int getCdDesignTopicId() {
		return cdDesignTopicId;
	}

	public void setCdDesignTopicId(int cdDesignTopicId) {
		this.cdDesignTopicId = cdDesignTopicId;
	}

	public int getProcessSheId() {
		return processSheId;
	}

	public void setProcessSheId(int processSheId) {
		this.processSheId = processSheId;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		System.out.println(teacherId);
		this.teacherId = teacherId;
	}

	public int getCdTeacherGroupId() {
		return cdTeacherGroupId;
	}

	public void setCdTeacherGroupId(int cdTeacherGroupId) {
		System.out.println(cdTeacherGroupId);
		this.cdTeacherGroupId = cdTeacherGroupId;
	}

	public int getPno() {
		return pno;
	}

	public void setPno(int pno) {
		this.pno = pno;
	}

	// 注入serviceFactory
	private ServiceFactory serviceFactory;

	public void setServiceFactory(ServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	/**
	 * 获取学生的信息
	 * 
	 * @return
	 */
	private Student getStudent() {
		if (ServletActionContext.getRequest().getSession().getAttribute("account") != null) {
			Account account = (Account) ServletActionContext.getRequest().getSession().getAttribute("account");
			System.out.println(account.getAccId());
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
	 * 查看学生选择了哪些课程设计
	 * 
	 * @param student
	 * @return
	 */
	private PageBean getChoosedCd(Student student) {
		PageBean pageBean = new PageBean();
		String hql = "select distinct "
				+ " cddesigntopics.cDDesignTopId,cddesigntopics.topics,teacher.teacherName,teacher.teacherId,cdteachergroup.cDTeacherGroupId,studentgroup.stuGroupId"
				+ " from student,stugroupmembers,studentgroup," + " cddesigntopics,cdteachergroup,teacher,cdplan"
				+ " where student.stuId = stugroupmembers.stuId"
				+ " and stugroupmembers.stuGroupId = studentgroup.stuGroupId"
				+ " and studentgroup.cDDesignTopId = cddesigntopics.cDDesignTopId"
				+ " and cddesigntopics.cDTeacherGroupId = cdteachergroup.cDTeacherGroupId"
				+ " and cddesigntopics.teacherId = teacher.teacherId" + " and cdplan.cDPlanId = cdteachergroup.cDPlanId"
				+ " and cdplan.isCurrent = 1" + " and student.stuId =" + student.getStuId();
		pageBean = serviceFactory.getPageBeanService().getPageBean(hql, 4, pno);
		return pageBean;
	}

	/**
	 * 查询某学生当前学期下面选择了哪些课程
	 * 
	 * @return
	 */
	public String searchAllCD() {
		ServletActionContext.getRequest().setAttribute("pageBean", getChoosedCd(getStudent()));
		return "searchAllCD";
	}

	/**
	 * 上传文件的材料页面的所有的课程设计的信息
	 * 
	 * @return
	 */
	public String uploadCDTask() {
		ServletActionContext.getRequest().setAttribute("pageBean", getChoosedCd(getStudent()));
		return "uploadCDTask";
	}

	/**
	 * 查询已经提交了哪些材料
	 * 
	 * @return
	 */
	public String searchCDTask() {
		ServletActionContext.getRequest().setAttribute("pageBean", getChoosedCd(getStudent()));
		return "searchCDTask";
	}

	/**
	 * 查看某一个老师对于某一个课程设计制定的课程设计过程考核计划
	 * 
	 * @throws IOException
	 */
	public void findAllProcessShe() throws IOException {
		// 教师
		Teacher teacher = new Teacher();
		teacher.setTeacherId(teacherId);
		// 教室小组
		Cdteachergroup cdteachergroup = new Cdteachergroup();
		cdteachergroup.setCdteacherGroupId(cdTeacherGroupId);
		// 查询结果
		List result = serviceFactory.getProcessasssheduleService().findAllProcExpSubmited_c(teacher, cdteachergroup,
				getStudent());
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(result));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * 查看所有的课程设计计划
	 * 
	 * @throws IOException
	 */
	public void findAllProcShedulePlan() throws IOException {
		// 教师
		Teacher teacher = new Teacher();
		teacher.setTeacherId(teacherId);
		// 教室小组
		Cdteachergroup cdteachergroup = new Cdteachergroup();
		cdteachergroup.setCdteacherGroupId(cdTeacherGroupId);
		// 查询结果
		List list = serviceFactory.getProcessasssheduleService().findAllProcessShe_c(teacher, cdteachergroup);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(list));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * 把任务导出为excel文档
	 */
	@SuppressWarnings("rawtypes")
	public InputStream getExcel() throws IOException {
		System.out.println("hello");
		// 制定下载文件的名字
		this.setFileName(new String((this.getTaskName() + "_过程任务计划表.xls").getBytes("GBK"), "iso-8859-1"));
		// 从数据库中读取数据
		// 教师
		Teacher teacher = new Teacher();
		teacher.setTeacherId(teacherId);
		// 教室小组
		Cdteachergroup cdteachergroup = new Cdteachergroup();
		cdteachergroup.setCdteacherGroupId(cdTeacherGroupId);
		// 查询结果
		List list = serviceFactory.getProcessasssheduleService().findAllProcessShe_c(teacher, cdteachergroup);
		String path = "temp" + System.currentTimeMillis() + ".xls";
		// 创建一个临时文件流
		FileOutputStream os = new FileOutputStream(path);
		// 创建一个Excel文档对象
		@SuppressWarnings("resource")
		HSSFWorkbook workBook = new HSSFWorkbook();
		// 创建一个工作簿对象
		HSSFSheet sheet = workBook.createSheet();
		// 设置样式
		HSSFCellStyle titleStyle = workBook.createCellStyle();
		// 水平居中
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 设置字体
		HSSFFont titleFont = workBook.createFont();
		titleFont.setFontHeightInPoints((short) 18);
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleFont.setFontName("黑体");
		titleStyle.setFont(titleFont);
		// 合并单元格操作(四个参数的意思分别为(要合并单元的起始行号，要合并单元的结束行号，要合并单元的起始列号，要合并单元的结束列号))
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 3));
		HSSFRow row = null;
		HSSFCell cell = null;
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(titleStyle);
		// 这里是设置excel表格的标题
		cell.setCellValue(new HSSFRichTextString("过程任务表"));
		// 设置表文样式
		HSSFCellStyle tableStyle = workBook.createCellStyle();
		tableStyle.setBorderBottom((short) 1);// 设置底部的样式
		tableStyle.setBorderTop((short) 1);// 设置顶部的样式
		tableStyle.setBorderLeft((short) 1);// 设置左边的样式
		tableStyle.setBorderRight((short) 1);// 设置右边的样式
		tableStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置表为字体
		HSSFFont tableFont = workBook.createFont();
		tableFont.setFontHeightInPoints((short) 12);
		tableFont.setFontName("宋体");
		tableStyle.setFont(tableFont);
		// 设定表头（*******************************）
		String[] title = { "过程考核名称", "过程考核详细", "开始时间", "结束时间" };
		// 创建表头行
		row = sheet.createRow(2);
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(title[i]));
			// 设置宽度
			sheet.setColumnWidth(i, 10000);
		}
		// 循环输出内容
		for (int i = 0; i < list.size(); i++) {
			// 去掉表格标题以及列名
			row = sheet.createRow(3 + i);
			Object[] rowInfo = (Object[]) list.get(i);
			// 把每一行的数据都放到cell中
			cell = row.createCell(0);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(rowInfo[0].toString()));
			cell = row.createCell(1);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(rowInfo[1].toString()));
			cell = row.createCell(2);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(rowInfo[2].toString()));
			cell = row.createCell(3);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(rowInfo[3].toString()));
		}
		workBook.write(os);
		// 新建立一个文件输出流
		InputStream in = new FileInputStream(path);
		// 删除临时文件
		File file = new File(path);
		if (file.isFile() && file.exists()) {
			file.delete();
		}
		return in;
	}

	/**
	 * 执行默认的方法
	 */
	public String execute() {
		return SUCCESS;
	}

	/**
	 * 学生上传过程材料
	 */
	public void upLoadFile() throws IOException {
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		if (this.uploadFile != null) {
			String destPath = "C:\\myUploadFiles";
			String fileName = getStudent().getStuId() + "_" + System.currentTimeMillis() + "";
			// 获取到文件的后缀名
			String prefix = this.uploadFileFileName.substring(this.uploadFileFileName.lastIndexOf(".") + 1);
			fileName += "." + prefix;
			File savedFile = new File(destPath, fileName);
			this.uploadFile.renameTo(savedFile);
			// 以下为添加纪录
			Processdocument processdocument = new Processdocument();
			processdocument.setStudent(getStudent());
			// 设置过程考核计划
			Processassshedule processassshedule = new Processassshedule();
			processassshedule.setProcessAssShId(processSheId);
			processdocument.setProcessassshedule(processassshedule);
			processdocument.setDocumentName(this.uploadFileFileName);
			processdocument.setDocumentUrl(destPath + "\\" + fileName);
			processdocument.setTutorOpinion(null);
			processdocument.setUpperDescribtion(null);
			processdocument.setUpperTime(new Date());
			processdocument.setIsReaded(0);
			processdocument.setScore(0f);
			serviceFactory.getProcessdocumentService().save(processdocument);
			ServletActionContext.getResponse().getWriter().println("<script>alert('文件上传成功!');</script>");
		} else {
			ServletActionContext.getResponse().getWriter().println("<script>alert('文件上传失败！');</script>");
		}
		// 页面跳转
		ServletActionContext.getResponse().getWriter()
				.println("<script>window.location.href='cddesigntask_uploadCDTask.action';</script>");
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * 查看个人已经上传的资料
	 * 
	 * @throws IOException
	 */
	public void findAllSubmited() throws IOException {
		// 获取学生类
		Student student = getStudent();
		// 教师类
		Teacher teacher = new Teacher();
		teacher.setTeacherId(this.teacherId);
		// 教师小组
		Cdteachergroup cdteachergroup = new Cdteachergroup();
		cdteachergroup.setCdteacherGroupId(this.cdTeacherGroupId);
		// 查询结果
		List result = serviceFactory.getProcessdocumentService().findAllSubmitted_c(teacher, cdteachergroup, student);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(result));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * 查看小组其他同学上传的材料
	 * 
	 * @throws IOException
	 */
	public void findAllStuGroupOtherSub() throws IOException {
		// 获取学生类
		Student student = getStudent();
		// 教师类
		Teacher teacher = new Teacher();
		teacher.setTeacherId(this.teacherId);
		// 教师小组
		Cdteachergroup cdteachergroup = new Cdteachergroup();
		cdteachergroup.setCdteacherGroupId(this.cdTeacherGroupId);
		// 课题
		Cddesigntopics cddesigntopics = new Cddesigntopics();
		cddesigntopics.setCddesignTopId(this.cdDesignTopicId);
		// 查询
		List result = serviceFactory.getProcessdocumentService().findAllOtherSubmitted_c(teacher, cdteachergroup,
				student, cddesigntopics);
		// 显示到前台
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(result));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * 下载过程文档
	 */
	public InputStream getDownLoadFile() throws UnsupportedEncodingException, FileNotFoundException {
		Processdocument processdocument = new Processdocument();
		processdocument.setProcessDocId(this.processDocId);
		processdocument = serviceFactory.getProcessdocumentService().find(processdocument);
		// 获取到文件的真实路径
		String path = processdocument.getDocumentUrl();
		// 设置下载的名称
		this.setFileName(new String(processdocument.getDocumentName().getBytes("GBK"), "iso-8859-1"));
		InputStream in = new FileInputStream(path);
		return in;
	}

	/**
	 * 删除文档
	 * 
	 * @throws IOException
	 */
	public void deleteDoc() throws IOException {
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		// 过程文档
		Processdocument processdocument = new Processdocument();
		processdocument.setProcessDocId(this.processDocId);
		// 查询文档
		processdocument = serviceFactory.getProcessdocumentService().find(processdocument);
		// 判断文档是否已经被批阅，如果已经批阅则不能删除
		if (processdocument.getIsReaded() == 0) {
			// 执行删除操作
			try {
				String realPath = processdocument.getDocumentUrl();
				System.out.println(realPath);
				File file = new File(realPath);
				// 删除文件
				if (file.exists()) {
					file.delete();
				}
				serviceFactory.getProcessdocumentService().delete(processdocument);
				ServletActionContext.getResponse().getWriter()
						.printf(JSON.toJSONString(processdocument.getDocumentName()));
			} catch (Exception e) {
				e.printStackTrace();
				ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString("innerError"));
			}
		} else {
			// 提示用户不能删除
			ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString("opError"));
		}
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * 跳转到个人自评页面
	 */
	public String toSelfEval() {
		PageBean pageBean = getChoosedCd(getStudent());
		ServletActionContext.getRequest().setAttribute("pageBean", pageBean);
		return "toSelfEval";
	}

	/**
	 * 添加课程设计自我评价
	 * 
	 * @throws IOException
	 */
	public void addSelfEvaluation() throws IOException {
		// 学生类
		Student student = getStudent();
		// 设计课题类
		Cddesigntopics cddesigntopics = new Cddesigntopics();
		cddesigntopics.setCddesignTopId(this.cdDesignTopicId);
		// 定义自评类
		this.selfevaluation.setStudent(student);
		this.selfevaluation.setCddesigntopics(cddesigntopics);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		// 判断是否已经评价过
		if (serviceFactory.getSelfEvaluationService().studentHasEvaled_c(cddesigntopics, student)) {
			ServletActionContext.getResponse().getWriter().printf("<script>alert('对不起您已经自评不能重复提交!');</script>");
		} else {
			//如果没有自评则添加自评记录
			try {
				// 保存自评实体类
				serviceFactory.getSelfEvaluationService().save(selfevaluation);
				ServletActionContext.getResponse().getWriter().printf("<script>alert('添加自评成功!');</script>");
			} catch (Exception e) {
				e.printStackTrace();
				ServletActionContext.getResponse().getWriter().printf("<script>alert('添加自评失败!');</script>");
			}
		}
		ServletActionContext.getResponse().getWriter()
				.printf("<script>window.location.href='cddesigntask_toSelfEval.action';</script>");
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	
	/**
	 * 查看组员的自评情况
	 * @throws IOException 
	 */
	public void searchStuGroupEval() throws IOException{
		//学生小组类
		Studentgroup studentgroup = new Studentgroup();
		studentgroup.setStuGroupId(this.stuGroupId);
		//课程设计
		Cddesigntopics cddesigntopics = new Cddesigntopics();
		cddesigntopics.setCddesignTopId(this.cdDesignTopicId);
		//查询结果
		List result = serviceFactory.getSelfEvaluationService().findAllStuGroupSelEv_c(cddesigntopics, studentgroup);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(result));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}
	
	/**
	 * 查看学生的成绩
	 */
	public String findStudentScore(){
		Student student = getStudent();
		String hql = "select cddesigntopics.cDDesignTopId,cdplan.cDPlanName,cddesigntopics.topics,teacher.teacherName,"
				+ " studentscore.attendanceScore,studentscore.replyScore,studentscore.examScore,studentscore.selEvScore,"
				+ " studentscore.totalScore,studentgroup.stuGroupId"
				+ " from student,stugroupmembers,studentgroup,cddesigntopics,cdteachergroup,cdplan,teacher,studentscore"
				+ " where student.stuId = stugroupmembers.stuId"
				+ " and stugroupmembers.stuGroupId = studentgroup.stuGroupId"
				+ " and studentgroup.cDDesignTopId = cddesigntopics.cDDesignTopId"
				+ " and cddesigntopics.cDTeacherGroupId = cdteachergroup.cDTeacherGroupId"
				+ " and cdteachergroup.cDPlanId = cdplan.cDPlanId"
				+ " and cddesigntopics.teacherId = teacher.teacherId"
				+ " and studentscore.cDDesignTopId = cddesigntopics.cDDesignTopId"
				+ " and studentscore.stuId = student.stuId"
				+ " and cdplan.isCurrent = 1"
				+ " and student.stuId = "+student.getStuId();
		PageBean pageBean = serviceFactory.getPageBeanService().getPageBean(hql, 3, pno);
		ServletActionContext.getRequest().setAttribute("pageBean", pageBean);
		return "findStudentScore";
	}

	/**
	 * 查看学生答辩情况
	 * @throws IOException 
	 */
	public void findReplyRecords() throws IOException{
		//学生
		Student student = new Student();
		student = getStudent();
		//学生小组编号
		Studentgroup studentgroup = new Studentgroup();
		studentgroup.setStuGroupId(this.stuGroupId);
		List result = serviceFactory.getReplyRecordsService().findAllReplyRecords_c(student, studentgroup);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(result));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}
}
