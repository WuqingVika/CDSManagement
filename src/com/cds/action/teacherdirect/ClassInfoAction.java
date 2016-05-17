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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.alibaba.fastjson.JSON;
import com.cds.entity.Account;
import com.cds.entity.Classes;
import com.cds.entity.Major;
import com.cds.entity.PageBean;
import com.cds.entity.Teacher;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

public class ClassInfoAction extends ActionSupport implements RequestAware, SessionAware {
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
	//���index�������ǻ�ȡClass���������ݣ�����ҳ�������ʾ
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
			PageBean majors = (PageBean) serviceFactory.getPageBeanService().getPageBean("SELECT classId, className, majorName FROM classes, major, college WHERE classes.`majorId` = major.`majorId` AND college.`collegeId` = major.`collegeId` AND collegeName = '" + teacher.getCollege().getCollegeName() + "'", 10, pno);
			request.put("pageBean", majors);
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	//��ø�ѧԺ����רҵ�ķ��������ص���JSON����
	public String getMajor() throws IOException {
		int collegeId = ((Teacher)session.get("teacherDirectorSession")).getCollege().getCollegeId();
		List majors = serviceFactory.getMajorService().findMajorsByCollegeIdJSON_z(collegeId);
		String json = JSON.toJSONString(majors);
		//��json����AJAX
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
		return null;
	}
	//���ķ���������
	private Classes classes;
	//���ĵķ���
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	private String majorName; //�ϴ�������רҵ
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	public String updateClass(){
		//ͨ��majorName�õ���Ӧ��רҵ
		Major major = serviceFactory.getMajorService().findMajorByName_z(majorName);
		//���øð༶��רҵ
		classes.setMajor(major);
		//���и��°༶
		serviceFactory.getClassesService().update(classes);
		return SUCCESS;
	}
	
	//���רҵ�ķ���������͸��ĵķ���������һ���ġ�
	public String addClass(){
		//���ж�����༶�Ƿ�����ˡ�
		Classes cla = serviceFactory.getClassesService().findClassByClassName_z(classes.getClassName());
		if(cla == null){
			//�����ھ����
			//ͨ��majorName�õ���Ӧ��רҵ
			Major major = serviceFactory.getMajorService().findMajorByName_z(majorName);
			//���øð༶��רҵ
			classes.setMajor(major);
			//���и��°༶
			serviceFactory.getClassesService().save(classes);
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
	public InputStream getExportClass() throws IOException {
		//ָ�������ļ�������
		setFileName(new String("�༶��Ϣ��.xls".getBytes("GBK"),"iso-8859-1"));
		//���ָ��ѧԺ�����е�רҵ��Ϣ
		Teacher teacher = (Teacher) session.get("teacherDirectorSession");
		List classList = serviceFactory.getClassesService().findClassByCollegeId_z(teacher.getCollege().getCollegeId());
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
		cell.setCellValue(new HSSFRichTextString("�༶��Ϣ��"));
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
		String[] title = { "���", "�༶���", "�༶����", "����רҵ", "����ѧԺ"};
		// ������ͷ��
		row = sheet.createRow(2);
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(title[i]));
		}
		// ѭ���������
		for (int i = 0; i < classList.size(); i++) {
			// ȥ���������Լ�����
			row = sheet.createRow(3 + i);
			// ��ȡÿһ�е�����
			Object[] classes = (Object[]) classList.get(i);
			// ��ÿһ�е����ݶ��ŵ�cell��
			cell = row.createCell(0);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(i + 1 + ""));
			cell = row.createCell(1);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(classes[0] + ""));
			cell = row.createCell(2);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(classes[1].toString()));
			cell = row.createCell(3);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(classes[2].toString()));
			cell = row.createCell(4);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(teacher.getCollege().getCollegeName()));
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
	
	//ɾ��Classes����������AJAX����
	public String deleteClass() throws IOException{
		//���ҵ�Ҫɾ�������ѧ��
		Classes delcla = serviceFactory.getClassesService().find(classes);
		// ����Ҫ���ص�����
		String flag;
		if(delcla.getStudents().size() == 0){
			//����༶û��ѧ����Ϣ���Ϳ�ɾ����רҵ
			serviceFactory.getClassesService().delete(delcla); //����һ��Ҫд�����delcla
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
