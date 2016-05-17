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
 * �γ���������action
 * 
 * @author PengChan
 *
 */
public class CDDesignTopicTask_c extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private int pno = 0;// ��ҳ��ҳ��

	private int teacherId;// ��ʦ�ı��

	private int cdTeacherGroupId;// ��ʦС��ı��

	private String fileName;// �ĵ�������

	private String taskName;// ���������

	private File uploadFile;// �ϴ����ļ�

	private String uploadFileFileName;// �ϴ����ļ�������

	private int processSheId;// ���̿��˼ƻ����

	private int cdDesignTopicId;// ��ƿ�����

	private int processDocId;// ���̿����ĵ����

	private int stuGroupId;// ѧ��С��ı��

	private Selfevaluation selfevaluation;// ��������

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

	// ע��serviceFactory
	private ServiceFactory serviceFactory;

	public void setServiceFactory(ServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	/**
	 * ��ȡѧ������Ϣ
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
	 * �鿴ѧ��ѡ������Щ�γ����
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
	 * ��ѯĳѧ����ǰѧ������ѡ������Щ�γ�
	 * 
	 * @return
	 */
	public String searchAllCD() {
		ServletActionContext.getRequest().setAttribute("pageBean", getChoosedCd(getStudent()));
		return "searchAllCD";
	}

	/**
	 * �ϴ��ļ��Ĳ���ҳ������еĿγ���Ƶ���Ϣ
	 * 
	 * @return
	 */
	public String uploadCDTask() {
		ServletActionContext.getRequest().setAttribute("pageBean", getChoosedCd(getStudent()));
		return "uploadCDTask";
	}

	/**
	 * ��ѯ�Ѿ��ύ����Щ����
	 * 
	 * @return
	 */
	public String searchCDTask() {
		ServletActionContext.getRequest().setAttribute("pageBean", getChoosedCd(getStudent()));
		return "searchCDTask";
	}

	/**
	 * �鿴ĳһ����ʦ����ĳһ���γ�����ƶ��Ŀγ���ƹ��̿��˼ƻ�
	 * 
	 * @throws IOException
	 */
	public void findAllProcessShe() throws IOException {
		// ��ʦ
		Teacher teacher = new Teacher();
		teacher.setTeacherId(teacherId);
		// ����С��
		Cdteachergroup cdteachergroup = new Cdteachergroup();
		cdteachergroup.setCdteacherGroupId(cdTeacherGroupId);
		// ��ѯ���
		List result = serviceFactory.getProcessasssheduleService().findAllProcExpSubmited_c(teacher, cdteachergroup,
				getStudent());
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(result));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * �鿴���еĿγ���Ƽƻ�
	 * 
	 * @throws IOException
	 */
	public void findAllProcShedulePlan() throws IOException {
		// ��ʦ
		Teacher teacher = new Teacher();
		teacher.setTeacherId(teacherId);
		// ����С��
		Cdteachergroup cdteachergroup = new Cdteachergroup();
		cdteachergroup.setCdteacherGroupId(cdTeacherGroupId);
		// ��ѯ���
		List list = serviceFactory.getProcessasssheduleService().findAllProcessShe_c(teacher, cdteachergroup);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(list));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * �����񵼳�Ϊexcel�ĵ�
	 */
	@SuppressWarnings("rawtypes")
	public InputStream getExcel() throws IOException {
		System.out.println("hello");
		// �ƶ������ļ�������
		this.setFileName(new String((this.getTaskName() + "_��������ƻ���.xls").getBytes("GBK"), "iso-8859-1"));
		// �����ݿ��ж�ȡ����
		// ��ʦ
		Teacher teacher = new Teacher();
		teacher.setTeacherId(teacherId);
		// ����С��
		Cdteachergroup cdteachergroup = new Cdteachergroup();
		cdteachergroup.setCdteacherGroupId(cdTeacherGroupId);
		// ��ѯ���
		List list = serviceFactory.getProcessasssheduleService().findAllProcessShe_c(teacher, cdteachergroup);
		String path = "temp" + System.currentTimeMillis() + ".xls";
		// ����һ����ʱ�ļ���
		FileOutputStream os = new FileOutputStream(path);
		// ����һ��Excel�ĵ�����
		@SuppressWarnings("resource")
		HSSFWorkbook workBook = new HSSFWorkbook();
		// ����һ������������
		HSSFSheet sheet = workBook.createSheet();
		// ������ʽ
		HSSFCellStyle titleStyle = workBook.createCellStyle();
		// ˮƽ����
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// ��������
		HSSFFont titleFont = workBook.createFont();
		titleFont.setFontHeightInPoints((short) 18);
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		titleFont.setFontName("����");
		titleStyle.setFont(titleFont);
		// �ϲ���Ԫ�����(�ĸ���������˼�ֱ�Ϊ(Ҫ�ϲ���Ԫ����ʼ�кţ�Ҫ�ϲ���Ԫ�Ľ����кţ�Ҫ�ϲ���Ԫ����ʼ�кţ�Ҫ�ϲ���Ԫ�Ľ����к�))
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 3));
		HSSFRow row = null;
		HSSFCell cell = null;
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(titleStyle);
		// ����������excel���ı���
		cell.setCellValue(new HSSFRichTextString("���������"));
		// ���ñ�����ʽ
		HSSFCellStyle tableStyle = workBook.createCellStyle();
		tableStyle.setBorderBottom((short) 1);// ���õײ�����ʽ
		tableStyle.setBorderTop((short) 1);// ���ö�������ʽ
		tableStyle.setBorderLeft((short) 1);// ������ߵ���ʽ
		tableStyle.setBorderRight((short) 1);// �����ұߵ���ʽ
		tableStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// ���ñ�Ϊ����
		HSSFFont tableFont = workBook.createFont();
		tableFont.setFontHeightInPoints((short) 12);
		tableFont.setFontName("����");
		tableStyle.setFont(tableFont);
		// �趨��ͷ��*******************************��
		String[] title = { "���̿�������", "���̿�����ϸ", "��ʼʱ��", "����ʱ��" };
		// ������ͷ��
		row = sheet.createRow(2);
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(title[i]));
			// ���ÿ��
			sheet.setColumnWidth(i, 10000);
		}
		// ѭ���������
		for (int i = 0; i < list.size(); i++) {
			// ȥ���������Լ�����
			row = sheet.createRow(3 + i);
			Object[] rowInfo = (Object[]) list.get(i);
			// ��ÿһ�е����ݶ��ŵ�cell��
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
		// �½���һ���ļ������
		InputStream in = new FileInputStream(path);
		// ɾ����ʱ�ļ�
		File file = new File(path);
		if (file.isFile() && file.exists()) {
			file.delete();
		}
		return in;
	}

	/**
	 * ִ��Ĭ�ϵķ���
	 */
	public String execute() {
		return SUCCESS;
	}

	/**
	 * ѧ���ϴ����̲���
	 */
	public void upLoadFile() throws IOException {
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		if (this.uploadFile != null) {
			String destPath = "C:\\myUploadFiles";
			String fileName = getStudent().getStuId() + "_" + System.currentTimeMillis() + "";
			// ��ȡ���ļ��ĺ�׺��
			String prefix = this.uploadFileFileName.substring(this.uploadFileFileName.lastIndexOf(".") + 1);
			fileName += "." + prefix;
			File savedFile = new File(destPath, fileName);
			this.uploadFile.renameTo(savedFile);
			// ����Ϊ��Ӽ�¼
			Processdocument processdocument = new Processdocument();
			processdocument.setStudent(getStudent());
			// ���ù��̿��˼ƻ�
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
			ServletActionContext.getResponse().getWriter().println("<script>alert('�ļ��ϴ��ɹ�!');</script>");
		} else {
			ServletActionContext.getResponse().getWriter().println("<script>alert('�ļ��ϴ�ʧ�ܣ�');</script>");
		}
		// ҳ����ת
		ServletActionContext.getResponse().getWriter()
				.println("<script>window.location.href='cddesigntask_uploadCDTask.action';</script>");
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * �鿴�����Ѿ��ϴ�������
	 * 
	 * @throws IOException
	 */
	public void findAllSubmited() throws IOException {
		// ��ȡѧ����
		Student student = getStudent();
		// ��ʦ��
		Teacher teacher = new Teacher();
		teacher.setTeacherId(this.teacherId);
		// ��ʦС��
		Cdteachergroup cdteachergroup = new Cdteachergroup();
		cdteachergroup.setCdteacherGroupId(this.cdTeacherGroupId);
		// ��ѯ���
		List result = serviceFactory.getProcessdocumentService().findAllSubmitted_c(teacher, cdteachergroup, student);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(result));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * �鿴С������ͬѧ�ϴ��Ĳ���
	 * 
	 * @throws IOException
	 */
	public void findAllStuGroupOtherSub() throws IOException {
		// ��ȡѧ����
		Student student = getStudent();
		// ��ʦ��
		Teacher teacher = new Teacher();
		teacher.setTeacherId(this.teacherId);
		// ��ʦС��
		Cdteachergroup cdteachergroup = new Cdteachergroup();
		cdteachergroup.setCdteacherGroupId(this.cdTeacherGroupId);
		// ����
		Cddesigntopics cddesigntopics = new Cddesigntopics();
		cddesigntopics.setCddesignTopId(this.cdDesignTopicId);
		// ��ѯ
		List result = serviceFactory.getProcessdocumentService().findAllOtherSubmitted_c(teacher, cdteachergroup,
				student, cddesigntopics);
		// ��ʾ��ǰ̨
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(result));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * ���ع����ĵ�
	 */
	public InputStream getDownLoadFile() throws UnsupportedEncodingException, FileNotFoundException {
		Processdocument processdocument = new Processdocument();
		processdocument.setProcessDocId(this.processDocId);
		processdocument = serviceFactory.getProcessdocumentService().find(processdocument);
		// ��ȡ���ļ�����ʵ·��
		String path = processdocument.getDocumentUrl();
		// �������ص�����
		this.setFileName(new String(processdocument.getDocumentName().getBytes("GBK"), "iso-8859-1"));
		InputStream in = new FileInputStream(path);
		return in;
	}

	/**
	 * ɾ���ĵ�
	 * 
	 * @throws IOException
	 */
	public void deleteDoc() throws IOException {
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		// �����ĵ�
		Processdocument processdocument = new Processdocument();
		processdocument.setProcessDocId(this.processDocId);
		// ��ѯ�ĵ�
		processdocument = serviceFactory.getProcessdocumentService().find(processdocument);
		// �ж��ĵ��Ƿ��Ѿ������ģ�����Ѿ���������ɾ��
		if (processdocument.getIsReaded() == 0) {
			// ִ��ɾ������
			try {
				String realPath = processdocument.getDocumentUrl();
				System.out.println(realPath);
				File file = new File(realPath);
				// ɾ���ļ�
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
			// ��ʾ�û�����ɾ��
			ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString("opError"));
		}
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	/**
	 * ��ת����������ҳ��
	 */
	public String toSelfEval() {
		PageBean pageBean = getChoosedCd(getStudent());
		ServletActionContext.getRequest().setAttribute("pageBean", pageBean);
		return "toSelfEval";
	}

	/**
	 * ��ӿγ������������
	 * 
	 * @throws IOException
	 */
	public void addSelfEvaluation() throws IOException {
		// ѧ����
		Student student = getStudent();
		// ��ƿ�����
		Cddesigntopics cddesigntopics = new Cddesigntopics();
		cddesigntopics.setCddesignTopId(this.cdDesignTopicId);
		// ����������
		this.selfevaluation.setStudent(student);
		this.selfevaluation.setCddesigntopics(cddesigntopics);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		// �ж��Ƿ��Ѿ����۹�
		if (serviceFactory.getSelfEvaluationService().studentHasEvaled_c(cddesigntopics, student)) {
			ServletActionContext.getResponse().getWriter().printf("<script>alert('�Բ������Ѿ����������ظ��ύ!');</script>");
		} else {
			//���û�����������������¼
			try {
				// ��������ʵ����
				serviceFactory.getSelfEvaluationService().save(selfevaluation);
				ServletActionContext.getResponse().getWriter().printf("<script>alert('��������ɹ�!');</script>");
			} catch (Exception e) {
				e.printStackTrace();
				ServletActionContext.getResponse().getWriter().printf("<script>alert('�������ʧ��!');</script>");
			}
		}
		ServletActionContext.getResponse().getWriter()
				.printf("<script>window.location.href='cddesigntask_toSelfEval.action';</script>");
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}

	
	/**
	 * �鿴��Ա���������
	 * @throws IOException 
	 */
	public void searchStuGroupEval() throws IOException{
		//ѧ��С����
		Studentgroup studentgroup = new Studentgroup();
		studentgroup.setStuGroupId(this.stuGroupId);
		//�γ����
		Cddesigntopics cddesigntopics = new Cddesigntopics();
		cddesigntopics.setCddesignTopId(this.cdDesignTopicId);
		//��ѯ���
		List result = serviceFactory.getSelfEvaluationService().findAllStuGroupSelEv_c(cddesigntopics, studentgroup);
		ServletActionContext.getResponse().setContentType("text/html");
		ServletActionContext.getResponse().setCharacterEncoding("utf-8");
		ServletActionContext.getResponse().getWriter().printf(JSON.toJSONString(result));
		ServletActionContext.getResponse().getWriter().flush();
		ServletActionContext.getResponse().getWriter().close();
	}
	
	/**
	 * �鿴ѧ���ĳɼ�
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
	 * �鿴ѧ��������
	 * @throws IOException 
	 */
	public void findReplyRecords() throws IOException{
		//ѧ��
		Student student = new Student();
		student = getStudent();
		//ѧ��С����
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
