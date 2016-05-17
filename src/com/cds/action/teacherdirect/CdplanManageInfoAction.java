package com.cds.action.teacherdirect;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.cds.entity.Account;
import com.cds.entity.Cdplan;
import com.cds.entity.Major;
import com.cds.entity.PageBean;
import com.cds.entity.Teacher;
import com.cds.entity.Term;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

public class CdplanManageInfoAction extends ActionSupport implements RequestAware, SessionAware {
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
	public String index(){
		//�õ�account���session����
		Account account = (Account)session.get("account");
		//����ǽ��������Σ���������¼��������ǽ��������Σ��Ͳ�������¼
		System.out.println(account.getRole());
		if(account.getRole() == 1){
			//���ǵ�¼���
			//���ҵ��ý��������Σ�Ȼ�󱣴�������������ε�session��
			Teacher teacher = serviceFactory.getTeacherService().findTeacherByAccId_z(account.getAccId());
			//��������������Ϣ������teacherDirectorSession�С�
			session.put("teacherDirectorSession", teacher);
			//���ظ�ѧԺ���еĿγ̼ƻ�
			PageBean cdplans = (PageBean) serviceFactory.getPageBeanService().getPageBean("SELECT cDPlanId, cDPlanNum, cDPlanName, totalCredits, totalClassHour, major.`majorName`, term.`termName` FROM cdplan, major, college, term WHERE cdplan.`majorId` = major.`majorId` AND major.`collegeId` = college.`collegeId` AND cdplan.`termId` = term.`termId` AND college.`collegeId` = " + teacher.getCollege().getCollegeId(), 10, pno);
			request.put("pageBean", cdplans);
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	//����γ̼ƻ���Ϣ
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
	@Override
	public String execute() throws Exception {
		return super.execute();
	}
	//����ķ���	
	@SuppressWarnings("resource")
	public String importCdplan(){
		try{
			// ��ȡ���ϴ����ļ�
			FileInputStream fis = new FileInputStream(getUploadFile());
			XSSFWorkbook work = new XSSFWorkbook(fis);
			XSSFSheet sheet = work.getSheetAt(0);
			//ֱ�Ӵӵڶ��п�ʼ���ͺ���
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Cdplan cdplan = new Cdplan();
				XSSFRow row = sheet.getRow(i);				
				XSSFCell cell = row.getCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cdplan.setCdplanNum(cell.getStringCellValue()); //���ÿγ̺�
				
				XSSFCell cell1 = row.getCell(1);
				cell1.setCellType(Cell.CELL_TYPE_STRING);
				cdplan.setCdplanName(cell1.getStringCellValue()); //���ÿγ̼ƻ�����
				
				XSSFCell cell2 = row.getCell(2);
				cell2.setCellType(Cell.CELL_TYPE_STRING);
				cdplan.setTotalCredits(Float.parseFloat(cell2.getStringCellValue())); //������ѧ��

				XSSFCell cell3 = row.getCell(3);
				cell3.setCellType(Cell.CELL_TYPE_STRING);
				cdplan.setTotalClassHour((Float.parseFloat(cell3.getStringCellValue()))); //������ѧʱ
				
				XSSFCell cell4 = row.getCell(4);
				cell4.setCellType(Cell.CELL_TYPE_STRING);
				Major major = serviceFactory.getMajorService().findMajorByName_z(cell4.getStringCellValue());
				cdplan.setMajor(major); //����רҵ				
				
				cdplan.setIsCurrent(1); //�����ǵ�ǰѧ��
				//����ѧ��
				//��õ�ǰ������£��Լ���������
				Calendar calendar = Calendar.getInstance();
				int cYear = calendar.get(Calendar.YEAR);
				int cMonth = calendar.get(Calendar.MONTH) + 1;
				//8~12�����Ǹ��������ĵ�һѧ��
				//1-6������ȥ��͸���ĵڶ�ѧ��
				String termName;
				if(cMonth>=8 && cMonth <= 12){
					termName = cYear + "-" + (cYear + 1) + "���һѧ��";
				}else{
					termName = (cYear - 1) + "-" + cYear + "��ڶ�ѧ��";
				}
				//��ѧ�����Ʋ���ѧ��
				Term term = serviceFactory.getTermService().findTermByName_z(termName);
				if(term == null){
					//Ϊ�վ����
					term = new Term();
					term.setTermName(termName);
					serviceFactory.getTermService().save(term);
				}
				term = serviceFactory.getTermService().findTermByName_z(termName); //�ٴβ��ң���ο϶���
				//��������γ̼ƻ���ѧ��
				cdplan.setTerm(term);
				serviceFactory.getCdplanService().save(cdplan);
			}
		}catch(Exception e){
			return ERROR;
		}
		return SUCCESS;
	}
}
