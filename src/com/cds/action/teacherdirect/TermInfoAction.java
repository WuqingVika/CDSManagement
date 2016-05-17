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

import com.cds.entity.PageBean;
import com.cds.entity.Term;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

public class TermInfoAction extends ActionSupport implements RequestAware {
	private static final long serialVersionUID = 1L;
	//����request
	private Map<String, Object> request;
	@Override
	public void setRequest(Map<String, Object> req) {
		this.request = req;
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
	//���index�������ǻ�ȡTerm���������ݣ�����ҳ�������ʾ
	public String index(){
		PageBean terms = (PageBean) serviceFactory.getPageBeanService().getPageBean("select * from term", 10, pno);
		request.put("pageBean", terms);
		return SUCCESS;
	}
	
	//���ķ���������
	private Term term;
	public Term getTerm() {
		return term;
	}
	public void setTerm(Term term) {
		this.term = term;
	}
	//���ĵķ���
	public String updateTerm(){
		serviceFactory.getTermService().update(term);
		return SUCCESS;
	}
	
	//���ѧ�ڵķ���
	public String addTerm(){
		//����Ѿ���ѧԺ�ˣ��Ͳ����
		Term ter = serviceFactory.getTermService().findTermByName_z(term.getTermName());
		if(ter == null){
			serviceFactory.getTermService().save(term);//��ӵ���term����ter
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
	public InputStream getExportTerm() throws IOException {
		//ָ�������ļ�������
		setFileName(new String("ѧ����Ϣ��.xls".getBytes("GBK"),"iso-8859-1"));
		//������е�ѧ����Ϣ
		List<Term> terms = serviceFactory.getTermService().findAll();
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
		String[] title = { "���", "ѧ�ڱ��", "ѧ������"};
		// ������ͷ��
		row = sheet.createRow(2);
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(title[i]));
		}
		// ѭ���������
		for (int i = 0; i < terms.size(); i++) {
			// ȥ���������Լ�����
			row = sheet.createRow(3 + i);
			// ��ȡÿһ�е�����
			Term term = (Term) terms.get(i);
			// ��ÿһ�е����ݶ��ŵ�cell��
			cell = row.createCell(0);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(i + 1 + ""));
			cell = row.createCell(1);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(term.getTermId() + ""));
			cell = row.createCell(2);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(term.getTermName()));
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
	public String importTerm(){
		try{
			// ��ȡ���ϴ����ļ�
			FileInputStream fis = new FileInputStream(getUploadFile());
			XSSFWorkbook work = new XSSFWorkbook(fis);
			XSSFSheet sheet = work.getSheetAt(0);
			//ֱ�Ӵӵ�һ�п�ʼ���ͺ���
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				Term term = new Term();
				// ��ȡ��һ��
				XSSFRow row = sheet.getRow(i);
				XSSFCell cell = row.getCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				//����Term������
				term.setTermName(cell.getStringCellValue());
				//������û�����ѧԺ
				Term findTerm = serviceFactory.getTermService().findTermByName_z(term.getTermName());
				if(findTerm == null){
					//���û�и�ѧԺ�������
					serviceFactory.getTermService().save(term);
				}
			}
		}catch(Exception e){
			return ERROR;
		}		
		return SUCCESS;
	}

	//ɾ��Term����������AJAX����
	public String deleteTerm() throws IOException{
		//���ҵ�Ҫɾ�������ѧ��
		Term delTrm = serviceFactory.getTermService().find(term);
		// ����Ҫ���ص�����
		String flag;
		if(delTrm.getCdplans().size() == 0){
			//���û�пγ���Ϣ���Ϳ�ɾ����ѧ��
			serviceFactory.getTermService().delete(delTrm); //����һ��Ҫд�����delCol������дcollege����Ϊhibernate������ͬ�Ļ����ˡ�
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
