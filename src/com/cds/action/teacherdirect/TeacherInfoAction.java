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
import com.cds.entity.College;
import com.cds.entity.PageBean;
import com.cds.entity.Teacher;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

public class TeacherInfoAction extends ActionSupport implements RequestAware, SessionAware {
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
	//���index�������ǻ�ȡTeacher���������ݣ�����ҳ�������ʾ
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
			//�õ����б�רҵ�İ༶
			PageBean teachers = (PageBean) serviceFactory.getPageBeanService().getPageBean("SELECT teacher.`teacherId`, accountId, teacherWorkId, teacherName FROM account, teacher WHERE teacher.`accId` = account.`accId` AND teacher.`collegeId` = " + teacher.getCollege().getCollegeId(), 10, pno);
			request.put("pageBean", teachers);
			return SUCCESS;
		}else{
			return ERROR;
		}		
	}
	
	//���ѧУ����ѧԺ�ķ��������ص���JSON����
	public String getCollege() throws IOException {
		List<College> colleges = serviceFactory.getCollegeService().findAll();
		StringBuilder sb = new StringBuilder("{\"collegeName\":[");
		for(College college : colleges){
			sb.append("\"" + college.getCollegeName() + "\"" + ",");
		}
		
		String json = sb.toString();
		json = json.substring(0, json.length() - 1) + "]}";
		System.out.println(json);
		//��json����AJAX
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		System.out.println(json);
		pw.write(json);
		pw.close();
		return null;
	}
	
	//���ķ���������
	private Teacher teacher;
	private String collegeName; //�ϴ�������ѧԺ
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public String updateTeacher(){
		//ͨ��collegeName�õ���Ӧ��ѧԺ
		College college = serviceFactory.getCollegeService().findCollegeByName_z(collegeName);
		//�������ҵ����ݿ��еĽ�ʦ��ά��ԭ���˻���ϵ
		Teacher newTeacher = serviceFactory.getTeacherService().find(teacher);
		//���ý�ʦ��ѧԺ
		newTeacher.setCollege(college);
		//���ý�ʦ�Ĺ��ŵ�����
		newTeacher.setTeacherWorkId(teacher.getTeacherWorkId());
		newTeacher.setTeacherName(teacher.getTeacherName());
		//���и��½�ʦ
		serviceFactory.getTeacherService().update(newTeacher);
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
	public String addTeacher(){
		//��ͨ����ʦ���Ų��Ҹý�ʦ��������˸ý�ʦ��������˻��ͽ�ʦ�����û�иý�ʦ�����ٽ������
		Teacher nt = serviceFactory.getTeacherService().findTeacherByWorkId_z(teacher.getTeacherWorkId());
		if(nt == null){
			//����ǿվ����
			//�ȱ����˻�
			account.setPasswords(account.getAccountId());
			account.setRole(2);
			serviceFactory.getAccountService().save(account);
			//���õ�����˻��ı�ţ��Ա㱣���ʦ
			Account acc = serviceFactory.getAccountService().find(account);
			//�ٱ����ʦ
			teacher.setAccount(acc);		
			teacher.setCollege(((Teacher)session.get("teacherDirectorSession")).getCollege());
			serviceFactory.getTeacherService().save(teacher);
		}
		return SUCCESS;
	}
	
	//������ʦ��Ϣ�ķ�������
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
	public InputStream getExportTeacher() throws IOException {
		//ָ�������ļ�������
		setFileName(new String("��ʦ��Ϣ��.xls".getBytes("GBK"),"iso-8859-1"));
		Teacher sessionTeacher = (Teacher)session.get("teacherDirectorSession");
		List teachList = serviceFactory.getTeacherService().findTeachersByCollegeId_z(sessionTeacher.getCollege().getCollegeId());
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
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 4));
		HSSFRow row = null;
		HSSFCell cell = null;
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(titleStyle);
		// ����������excel���ı���
		cell.setCellValue(new HSSFRichTextString("��ʦ��Ϣ��"));
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
		String[] title = { "���", "��¼�˺�", "��ʦ����", "��ʦ����", "����ѧԺ"};
		// ������ͷ��
		row = sheet.createRow(2);
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(title[i]));
		}
		// ѭ���������
		for (int i = 0; i < teachList.size(); i++) {
			// ȥ���������Լ�����
			row = sheet.createRow(3 + i);
			// ��ȡÿһ�е�����
			Object[] teach = (Object[]) teachList.get(i);
			// ��ÿһ�е����ݶ��ŵ�cell��
			cell = row.createCell(0);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(i + 1 + ""));
			cell = row.createCell(1);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(teach[0] + ""));
			cell = row.createCell(2);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(teach[1].toString()));
			cell = row.createCell(3);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(teach[2].toString()));
			cell = row.createCell(4);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(sessionTeacher.getCollege().getCollegeName()));
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
	
	//�����ʦ��Ϣ
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
	public String importTeacher(){
		try{
			// ��ȡ���ϴ����ļ�
			FileInputStream fis = new FileInputStream(getUploadFile());
			XSSFWorkbook work = new XSSFWorkbook(fis);
			XSSFSheet sheet = work.getSheetAt(0);
			//ֱ�Ӵӵڶ��п�ʼ���ͺ���
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Teacher teacher = new Teacher();
				// �߻�ȡ��3�����ݣ����ǹ��ţ����ݹ����ж��ǲ����������ʦ
				XSSFRow row = sheet.getRow(i);
				XSSFCell cell = row.getCell(1);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				//���ý�ʦ����
				teacher.setTeacherWorkId(cell.getStringCellValue());
				//������û�������ʦ
				if(serviceFactory.getTeacherService().findTeacherByWorkId_z(teacher.getTeacherWorkId()) == null){
					//���û�иý�ʦ�������
					//������˺�
					Account ta = new Account();
					XSSFCell cell0 = row.getCell(0); //��õ�¼�˺�
					cell0.setCellType(Cell.CELL_TYPE_STRING);
					ta.setAccountId(cell0.getStringCellValue());
					ta.setPasswords(ta.getAccountId()); //������������û��˺�
					ta.setRole(2); //���ý�ɫ2�ǽ�ʦ
					serviceFactory.getAccountService().save(ta);//�����˺�
					//�ٱ����ʦ					
					teacher.setAccount(ta); //���ý�ʦ��Ӧ���˺�
					XSSFCell cell2 = row.getCell(2); //��ý�ʦ���Ƶ���
					cell2.setCellType(Cell.CELL_TYPE_STRING);
					teacher.setTeacherName(cell2.getStringCellValue());
					teacher.setCollege(((Teacher)session.get("teacherDirectorSession")).getCollege());//���ý�ʦ��ѧԺ
					serviceFactory.getTeacherService().save(teacher);
				}
			}
		}catch(Exception e){
			return ERROR;
		}		
		return SUCCESS;
	}
	
	//ɾ����ʦ����������AJAX����
	public String deleteTeacher() throws IOException{
		//���ҵ�Ҫɾ�������ʦ
		Teacher delTea = serviceFactory.getTeacherService().find(teacher);
		// ����Ҫ���ص�����
		String flag;
		if(delTea.getCdteachergroups_1().size() == 0){
			//�����ʦû�пγ̼ƻ���Ϣ���Ϳ�ɾ��
			//���ҵ��ý�ʦ���˻���׼��ɾ��
			Account teaAcc = delTea.getAccount();
			serviceFactory.getTeacherService().delete(delTea); //����һ��Ҫд�����delTea
			//Ȼ����ɾ��Account
			serviceFactory.getAccountService().delete(teaAcc);
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
