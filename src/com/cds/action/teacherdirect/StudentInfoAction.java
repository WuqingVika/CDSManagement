package com.cds.action.teacherdirect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.cds.entity.Account;
import com.cds.entity.Classes;
import com.cds.entity.PageBean;
import com.cds.entity.Student;
import com.cds.entity.Teacher;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

public class StudentInfoAction extends ActionSupport implements RequestAware, SessionAware {
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
	//���index�������ǻ�ȡStudent���������ݣ�����ҳ�������ʾ
	//�ϴ��İ༶���
	private int classId;
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
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
			//���ظ�ѧԺ���еİ༶
			List<Classes> allClass = serviceFactory.getCollegeService().findClassesByCollegeId_z(teacher.getCollege().getCollegeId());
			//�����а༶������request����
			request.put("allClass", allClass);
			PageBean students = null;
			if(classId <= 0){
				//�õ����б�רҵ�İ༶
				students = (PageBean) serviceFactory.getPageBeanService().getPageBean("SELECT account.accId, stuId, account.accountId, studentId, stuName, className, majorName FROM account, student, college, classes, major WHERE account.`accId` = student.`accId` AND student.`classId` = classes.`classId` AND classes.`majorId` = major.`majorId` AND college.`collegeId` = major.`collegeId` AND college.`collegeId` = " + teacher.getCollege().getCollegeId(), 10, pno);
			}else{
				//�鵽ĳ�༶��Ӧ������ѧ��
				students = (PageBean) serviceFactory.getPageBeanService().getPageBean("SELECT account.accId, stuId, account.accountId, studentId, stuName, className, majorName FROM account, student, college, classes, major WHERE account.`accId` = student.`accId` AND student.`classId` = classes.`classId` AND classes.`majorId` = major.`majorId` AND college.`collegeId` = major.`collegeId` AND college.`collegeId` = " + teacher.getCollege().getCollegeId() + " AND classes.`classId` = " + classId, 10, pno);
				//���ҷ������classId��
				request.put("currentClass", classId);
			}
			request.put("pageBean", students); //����pageBean
			return SUCCESS;
		}else{
			return ERROR;
		}		
	}
	
	//���ķ���������
	private Student student;
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String updateStudent(){
		//ͨ��classId�õ���Ӧ�İ༶
		Classes cla = new Classes();
		cla.setClassId(classId);
		cla = serviceFactory.getClassesService().find(cla); //�ҵ����classes
		//�������ҵ����ݿ��еĽ�ʦ��ά��ԭ���˻���ϵ
		Student newStu = serviceFactory.getStudentService().find(student);
		//����ѧ���İ༶
		newStu.setClasses(cla);
		//����ѧ����ѧ�ź�����
		newStu.setStudentId(student.getStudentId());
		newStu.setStuName(student.getStuName());
		//���и��½�ʦ
		serviceFactory.getStudentService().update(newStu);
		return SUCCESS;
	}

	//�ϴ���account
	private Account account;
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	//��ӽ�ʦ�ķ���
	public String addStudent(){
		//��ͨ��ѧ�Ų��Ҹ�ѧ����������˸�ѧ����������˻���ѧ�������û�и�ѧ�������ٽ������
		Student ns = serviceFactory.getStudentService().findStudentByStudentId_z(student.getStudentId());
		if(ns == null){
			//����ǿվ����
			//�ȱ����˻�
			account.setPasswords(account.getAccountId());
			account.setRole(3);
			serviceFactory.getAccountService().save(account);
			//���õ�����˻��ı�ţ��Ա㱣��ѧ��
			Account acc = serviceFactory.getAccountService().find(account);
			//�ٱ����ʦ
			student.setAccount(acc);
			//�ҵ�ѧ���İ༶
			Classes cls = new Classes();
			cls.setClassId(classId);
			serviceFactory.getClassesService().find(cls);
			student.setClasses(cls); //����ѧ���İ༶
			serviceFactory.getStudentService().save(student);
		}
		return SUCCESS;
	}
	
	//����ѧ����Ϣ�ķ�������
	private String fileName; //�ĵ�������
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	//��������Ҫexecute����
	public String execute(){
		return SUCCESS;
	}
	//����ѧ����Ϣ�ķ���
	public InputStream getExportStudent() throws IOException {
		//ָ�������ļ�������
		setFileName(new String("ѧ����Ϣ��.xls".getBytes("GBK"),"iso-8859-1"));
		Teacher sessionTeacher = (Teacher)session.get("teacherDirectorSession");
		//���ҵ�ѧԺ���е�ѧ��
		List stuList = serviceFactory.getCollegeService().findStudentsByCollegeId(sessionTeacher.getCollege().getCollegeId());
		// ����һ����ʱ�ļ���	
		String curTempFileName = "temp" + System.currentTimeMillis() + ".xlsx";
		
		FileOutputStream os = new FileOutputStream(curTempFileName);
		// ����һ��Excel�ĵ�����
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
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 5));
		HSSFRow row = null;
		HSSFCell cell = null;
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(titleStyle);
		// ����������excel���ı���
		cell.setCellValue(new HSSFRichTextString("ѧ����Ϣ��"));
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
		String[] title = { "���", "��¼�˺�", "ѧ��ѧ��", "ѧ������", "�����༶", "����ѧԺ"};
		// ������ͷ��
		row = sheet.createRow(2);
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(title[i]));
		}
		// ѭ���������
		for (int i = 0; i < stuList.size(); i++) {
			// ȥ���������Լ�����
			row = sheet.createRow(3 + i);
			// ��ȡÿһ�е�����
			Object[] stu = (Object[]) stuList.get(i);
			// ��ÿһ�е����ݶ��ŵ�cell��
			cell = row.createCell(0);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(i + 1 + ""));
			cell = row.createCell(1);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(stu[0] + ""));
			cell = row.createCell(2);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(stu[1].toString()));
			cell = row.createCell(3);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(stu[2].toString()));
			cell = row.createCell(4);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(stu[3].toString()));
			cell = row.createCell(5);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(stu[4].toString()));
		}
		workBook.write(os);	
		workBook.close();
		//�½���һ���ļ������
		InputStream in = new FileInputStream(curTempFileName);
		//ɾ����ʱ�ļ�
		File file = new File(curTempFileName);
		if(file.isFile()&&file.exists()){
			file.delete();			
		}
		return in;
	}
	
	//����ѧ����Ϣ
	private File uploadFile;
	private String uploadFileFileName;
	private String uploadFileContentType;
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
	public String getUploadFileContentType() {
		return uploadFileContentType;
	}
	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}
	//����ķ���	
	@SuppressWarnings("resource")
	public String importStudent(){
		try{
			// ��ȡ���ϴ����ļ�
			FileInputStream fis = new FileInputStream(getUploadFile());
			XSSFWorkbook work = new XSSFWorkbook(fis);
			XSSFSheet sheet = work.getSheetAt(0);
			//ֱ�Ӵӵڶ��п�ʼ���ͺ���
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Student stu = new Student();
				// �߻�ȡ��2�����ݣ�����ѧ�ţ�����ѧ���ж��ǲ��������ѧ��
				XSSFRow row = sheet.getRow(i);
				XSSFCell cell = row.getCell(1);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				//���ý�ʦ����
				stu.setStudentId(cell.getStringCellValue());
				//������û�����ѧ��
				if(serviceFactory.getStudentService().findStudentByStudentId_z(stu.getStudentId()) == null){
					//���û�и�ѧ���������
					//������˺�
					Account ta = new Account();
					XSSFCell cell0 = row.getCell(0); //��õ�¼�˺�
					cell0.setCellType(Cell.CELL_TYPE_STRING);
					ta.setAccountId(cell0.getStringCellValue());
					ta.setPasswords(ta.getAccountId()); //������������û��˺�
					ta.setRole(3); //������ѧ��
					serviceFactory.getAccountService().save(ta);//�����˺�
					//�ٱ���ѧ��			
					stu.setAccount(ta); //����ѧ����Ӧ���˺�
					XSSFCell cell2 = row.getCell(2); //���ѧ�����Ƶ���
					cell2.setCellType(Cell.CELL_TYPE_STRING);
					stu.setStuName(cell2.getStringCellValue());
					//����ѧ���İ༶
					XSSFCell cell3 = row.getCell(3); //���ѧ���༶����
					cell3.setCellType(Cell.CELL_TYPE_STRING);
					Classes cla = serviceFactory.getClassesService().findClassByClassName_z(cell3.getStringCellValue());
					stu.setClasses(cla);
					serviceFactory.getStudentService().save(stu);
				}
			}
		}catch(Exception e){
			return ERROR;
		}		
		return SUCCESS;
	}
	
	//ɾ��ѧ���ķ���������AJAX����
	public String deleteStudent() throws IOException{
		//���ҵ�Ҫɾ������ѧ��
		Student delStu = serviceFactory.getStudentService().find(student);
		// ����Ҫ���ص�����
		String flag;
		if(delStu.getStudentgroups_1().size() == 0){
			//���ѧ��û�пγ̼ƻ���Ϣ���Ϳ�ɾ��
			//���ҵ���ѧ�����˻���׼��ɾ��
			Account stuAcc = delStu.getAccount();
			serviceFactory.getStudentService().delete(delStu); //����һ��Ҫд�����delStu
			//Ȼ����ɾ��Account
			serviceFactory.getAccountService().delete(stuAcc);
			flag = "1"; //1��ʾɾ���ɹ�
		}else{
			//����ɾ����ѧ��
			flag = "0"; //0������ɾ��
		}
		//��flag����AJAX
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(flag);
		pw.close();
		return null;
	}
}
