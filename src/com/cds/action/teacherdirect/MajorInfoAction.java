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
import com.cds.entity.Major;
import com.cds.entity.PageBean;
import com.cds.entity.Teacher;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

public class MajorInfoAction extends ActionSupport implements RequestAware, SessionAware {
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
	//���index�������ǻ�ȡMajor���������ݣ�����ҳ�������ʾ
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
			PageBean majors = (PageBean) serviceFactory.getPageBeanService().getPageBean("SELECT majorId, majorName, collegeName FROM major, college WHERE major.`collegeId` = college.`collegeId` HAVING collegeName = '" + teacher.getCollege().getCollegeName() + "'", 10, pno);
			request.put("pageBean", majors);
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	//���ķ���������
	private Major major;
	public Major getMajor() {
		return major;
	}
	public void setMajor(Major major) {
		this.major = major;
	}
	//���ĵķ���
	public String updateMajor(){
		//�����ø�רҵ����ѧԺ�ı�ţ���Ϊ�ϴ�ʱû���ϴ�����ʱ��session�л�ȡ�������������ڵ�ѧԺ�ı�ż���
		major.setCollege(((Teacher)session.get("teacherDirectorSession")).getCollege());
		serviceFactory.getMajorService().update(major);
		return SUCCESS;
	}

	//���רҵ�ķ���
	public String addMajor(){
		//�����ø�רҵ����ѧԺ�ı�ţ���Ϊ�ϴ�ʱû���ϴ�����ʱ��session�л�ȡ�������������ڵ�ѧԺ�ı�ż���
		major.setCollege(((Teacher)session.get("teacherDirectorSession")).getCollege());
		//����Ѿ���רҵ�ˣ��Ͳ����
		Major mar = serviceFactory.getMajorService().findMajorByName_z(major.getMajorName());
		if(mar == null){
			serviceFactory.getMajorService().save(major);//��ӵ���major����mar
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
	public InputStream getExportMajor() throws IOException {
		//ָ�������ļ�������
		setFileName(new String("רҵ��Ϣ��.xls".getBytes("GBK"),"iso-8859-1"));
		//���ָ��ѧԺ�����е�רҵ��Ϣ
		Teacher teacher = (Teacher) session.get("teacherDirectorSession");
		List<Major> majors = serviceFactory.getMajorService().findMajorsByCollegeId_z(teacher.getCollege().getCollegeId());
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
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 3));
		HSSFRow row = null;
		HSSFCell cell = null;
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(titleStyle);
		// ����������excel���ı���
		cell.setCellValue(new HSSFRichTextString("רҵ��Ϣ��"));
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
		String[] title = { "���", "רҵ���", "רҵ����", "����ѧԺ"};
		// ������ͷ��
		row = sheet.createRow(2);
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(title[i]));
		}
		// ѭ���������
		for (int i = 0; i < majors.size(); i++) {
			// ȥ���������Լ�����
			row = sheet.createRow(3 + i);
			// ��ȡÿһ�е�����
			Major major = (Major) majors.get(i);
			// ��ÿһ�е����ݶ��ŵ�cell��
			cell = row.createCell(0);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(i + 1 + ""));
			cell = row.createCell(1);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(major.getMajorId() + ""));
			cell = row.createCell(2);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(major.getMajorName()));
			cell = row.createCell(3);
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
	
	//����רҵ��Ϣ
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
	public String importMajor(){
		try{
			// ��ȡ���ϴ����ļ�
			FileInputStream fis = new FileInputStream(getUploadFile());
			XSSFWorkbook work = new XSSFWorkbook(fis);
			XSSFSheet sheet = work.getSheetAt(0);
			//ֱ�Ӵӵ�һ�п�ʼ���ͺ���
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				Major major = new Major();
				// ��ȡ��һ��
				XSSFRow row = sheet.getRow(i);
				XSSFCell cell = row.getCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				//����Major������
				major.setMajorName(cell.getStringCellValue());
				//������û�����ѧԺ
				Major findMajor = serviceFactory.getMajorService().findMajorByName_z(major.getMajorName());
				if(findMajor == null){
					//���û�и�ѧԺ�������
					//���֮ǰ����major���ڵ�ѧԺ
					major.setCollege(((Teacher)session.get("teacherDirectorSession")).getCollege());
					serviceFactory.getMajorService().save(major);
				}
			}
		}catch(Exception e){
			return ERROR;
		}		
		return SUCCESS;
	}
	
	//ɾ��Major����������AJAX����
	public String deleteMajor() throws IOException{
		//���ҵ�Ҫɾ�������ѧ��
		Major delMaj = serviceFactory.getMajorService().find(major);
		// ����Ҫ���ص�����
		String flag;
		if(delMaj.getClasseses().size() == 0){
			//���רҵû�пγ���Ϣ���Ϳ�ɾ����רҵ
			serviceFactory.getMajorService().delete(delMaj); //����һ��Ҫд�����delMaj������дmajor����Ϊhibernate������ͬ�Ļ����ˡ�
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
