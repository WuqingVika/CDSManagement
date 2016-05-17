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
	//设置request
	private Map<String, Object> request;
	@Override
	public void setRequest(Map<String, Object> req) {
		this.request = req;
	}
	//设置session
	private Map<String, Object> session;
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;		
	}
	//Service工厂
	private ServiceFactory serviceFactory;
	//当前页设置为1
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
	
	//查询全部的action(初始的action)
	//这个index方法就是获取Student的所有数据，并且页面进行显示
	public String index(){
		//拿到account这个session对象
		Account account = (Account)session.get("account");
		//如果是教研室主任，就让他登录，如果不是教研室主任，就不让他登录
		System.out.println(account.getRole());
		if(account.getRole() == 1){
			//这是登录后的
			//查找到该教研室主任，然后保存这个教研室主任到session中
			Teacher teacher = serviceFactory.getTeacherService().findTeacherByAccId_z(account.getAccId());
			//将教研室主任信息保存在teacherDirectorSession中。
			session.put("teacherDirectorSession", teacher);
			//返回该学院所有的课程计划
			PageBean cdplans = (PageBean) serviceFactory.getPageBeanService().getPageBean("SELECT cDPlanId, cDPlanNum, cDPlanName, totalCredits, totalClassHour, major.`majorName`, term.`termName` FROM cdplan, major, college, term WHERE cdplan.`majorId` = major.`majorId` AND major.`collegeId` = college.`collegeId` AND cdplan.`termId` = term.`termId` AND college.`collegeId` = " + teacher.getCollege().getCollegeId(), 10, pno);
			request.put("pageBean", cdplans);
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	//导入课程计划信息
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
	//导入的方法	
	@SuppressWarnings("resource")
	public String importCdplan(){
		try{
			// 获取到上传的文件
			FileInputStream fis = new FileInputStream(getUploadFile());
			XSSFWorkbook work = new XSSFWorkbook(fis);
			XSSFSheet sheet = work.getSheetAt(0);
			//直接从第二行开始读就好了
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Cdplan cdplan = new Cdplan();
				XSSFRow row = sheet.getRow(i);				
				XSSFCell cell = row.getCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cdplan.setCdplanNum(cell.getStringCellValue()); //设置课程号
				
				XSSFCell cell1 = row.getCell(1);
				cell1.setCellType(Cell.CELL_TYPE_STRING);
				cdplan.setCdplanName(cell1.getStringCellValue()); //设置课程计划名称
				
				XSSFCell cell2 = row.getCell(2);
				cell2.setCellType(Cell.CELL_TYPE_STRING);
				cdplan.setTotalCredits(Float.parseFloat(cell2.getStringCellValue())); //设置总学分

				XSSFCell cell3 = row.getCell(3);
				cell3.setCellType(Cell.CELL_TYPE_STRING);
				cdplan.setTotalClassHour((Float.parseFloat(cell3.getStringCellValue()))); //设置总学时
				
				XSSFCell cell4 = row.getCell(4);
				cell4.setCellType(Cell.CELL_TYPE_STRING);
				Major major = serviceFactory.getMajorService().findMajorByName_z(cell4.getStringCellValue());
				cdplan.setMajor(major); //设置专业				
				
				cdplan.setIsCurrent(1); //设置是当前学期
				//设置学期
				//获得当前的年和月，自己构造日期
				Calendar calendar = Calendar.getInstance();
				int cYear = calendar.get(Calendar.YEAR);
				int cMonth = calendar.get(Calendar.MONTH) + 1;
				//8~12月算是该年和下年的第一学期
				//1-6月算是去年和该年的第二学期
				String termName;
				if(cMonth>=8 && cMonth <= 12){
					termName = cYear + "-" + (cYear + 1) + "年第一学期";
				}else{
					termName = (cYear - 1) + "-" + cYear + "年第二学期";
				}
				//按学期名称查找学期
				Term term = serviceFactory.getTermService().findTermByName_z(termName);
				if(term == null){
					//为空就添加
					term = new Term();
					term.setTermName(termName);
					serviceFactory.getTermService().save(term);
				}
				term = serviceFactory.getTermService().findTermByName_z(termName); //再次查找，这次肯定有
				//保存这个课程计划的学期
				cdplan.setTerm(term);
				serviceFactory.getCdplanService().save(cdplan);
			}
		}catch(Exception e){
			return ERROR;
		}
		return SUCCESS;
	}
}
