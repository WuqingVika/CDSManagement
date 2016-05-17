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

import com.cds.entity.College;
import com.cds.entity.PageBean;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;
/**
 * ������ѧԺ��Ϣ�����Action�ࡣ
 * ��Ҫ�����У����ѧԺ��Ϣ�����뵼��ѧԺ��Ϣ��ɾ���޸�ѧԺ��Ϣ
 * @author NOM
 *
 */
public class CollegeInfoAction extends ActionSupport implements RequestAware {
	private static final long serialVersionUID = 1L;
	private ServiceFactory serviceFactory;
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
	private Map<String, Object> request;
	@Override
	public void setRequest(Map<String, Object> req) {
		this.request = req;
	}
	
	//���index�������ǻ�ȡCollege���������ݣ�����ҳ�������ʾ
	public String index(){
		PageBean colleges = (PageBean) serviceFactory.getPageBeanService().getPageBean("select * from college", 10, pno);
		request.put("pageBean", colleges);
		return SUCCESS;
	}
	
	//���ĵķ���������
	private College college;
	public College getCollege() {
		return college;
	}
	public void setCollege(College college) {
		this.college = college;
	}
	//���ĵķ���
	public String updateCollege(){
		serviceFactory.getCollegeService().update(college);
		return SUCCESS;
	}
	//���ѧԺ�ķ���
	public String addCollege(){
		//����Ѿ���ѧԺ�ˣ��Ͳ����
		College col = serviceFactory.getCollegeService().findCollegeByName_z(college.getCollegeName());
		if(col == null){
			serviceFactory.getCollegeService().save(college);
		}
		return SUCCESS;
	}
	//����ѧԺ��Ϣ�ķ�������
	private String fileName; //�ĵ�������
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	//����ѧԺ����Ҫexecute����
	public String execute(){
		return SUCCESS;
	}
	//����ѧԺ��Ϣ�ķ���
	public InputStream getExportCollege() throws IOException {
		//ָ�������ļ�������
		setFileName(new String("ѧԺ��Ϣ��.xls".getBytes("GBK"),"iso-8859-1"));
		//������е�ѧԺ��Ϣ
		List<College> colleges = serviceFactory.getCollegeService().findAll();
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
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 2));
		HSSFRow row = null;
		HSSFCell cell = null;
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(titleStyle);
		// ����������excel���ı���
		cell.setCellValue(new HSSFRichTextString("ѧԺ��Ϣ��"));
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
		String[] title = { "���", "ѧԺ���", "ѧԺ����"};
		// ������ͷ��
		row = sheet.createRow(2);
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(title[i]));
		}
		// ѭ���������
		for (int i = 0; i < colleges.size(); i++) {
			// ȥ���������Լ�����
			row = sheet.createRow(3 + i);
			// ��ȡÿһ�е�����
			College college = (College) colleges.get(i);
			// ��ÿһ�е����ݶ��ŵ�cell��
			cell = row.createCell(0);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(i + 1 + ""));
			cell = row.createCell(1);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(college.getCollegeId() + ""));
			cell = row.createCell(2);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(college.getCollegeName()));
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
		
	//����ѧԺ��Ϣ
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
	public String importCollege(){
		try{
			// ��ȡ���ϴ����ļ�
			FileInputStream fis = new FileInputStream(getUploadFile());
			XSSFWorkbook work = new XSSFWorkbook(fis);
			XSSFSheet sheet = work.getSheetAt(0);
			//ֱ�Ӵӵ�һ�п�ʼ���ͺ���
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				College college = new College();
				// ��ȡ��һ��
				XSSFRow row = sheet.getRow(i);
				XSSFCell cell = row.getCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				//����College������
				college.setCollegeName(cell.getStringCellValue());
				//������û�����ѧԺ
				College findCollege = serviceFactory.getCollegeService().findCollegeByName_z(college.getCollegeName());
				if(findCollege == null){
					//���û�и�ѧԺ�������
					serviceFactory.getCollegeService().save(college);
				}
			}
		}catch(Exception e){
			return ERROR;
		}		
		return SUCCESS;
	}
	
	//ɾ��College����������AJAX����
	public String deleteCollege() throws IOException{
		//���ҵ�Ҫɾ�������ѧԺ
		College delCol = serviceFactory.getCollegeService().find(college);
		// ����Ҫ���ص�����
		String flag;
		if(delCol.getMajors().size() == 0){
			//���û��רҵ��Ϣ���Ϳ�ɾ����ѧԺ
			serviceFactory.getCollegeService().delete(delCol); //����һ��Ҫд�����delCol������дcollege����Ϊhibernate������ͬ�Ļ����ˡ�
			
			flag = "1"; //1��ʾɾ���ɹ�
		}else{
			//����ɾ����ѧԺ
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
