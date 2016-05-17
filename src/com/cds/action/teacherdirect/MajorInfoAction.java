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
	//这个index方法就是获取Major的所有数据，并且页面进行显示
	public String index(){
		//拿到account这个session对象
		Account account = (Account)session.get("account");
		//如果是教研室主任，就让他登录，如果不是教研室主任，就不让他登录
		if(account.getRole() == 1){
			//这是登录后的
			//查找到该教研室主任，然后保存这个教研室主任到session中
			Teacher teacher = serviceFactory.getTeacherService().findTeacherByAccId_z(account.getAccId());
			//将教研室主任信息保存在teacherDirectorSession中。
			session.put("teacherDirectorSession", teacher);
			PageBean majors = (PageBean) serviceFactory.getPageBeanService().getPageBean("SELECT majorId, majorName, collegeName FROM major, college WHERE major.`collegeId` = college.`collegeId` HAVING collegeName = '" + teacher.getCollege().getCollegeName() + "'", 10, pno);
			request.put("pageBean", majors);
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	//更改方法的属性
	private Major major;
	public Major getMajor() {
		return major;
	}
	public void setMajor(Major major) {
		this.major = major;
	}
	//更改的方法
	public String updateMajor(){
		//先设置该专业所在学院的编号，因为上传时没有上传。这时从session中获取教研室主任所在的学院的编号即可
		major.setCollege(((Teacher)session.get("teacherDirectorSession")).getCollege());
		serviceFactory.getMajorService().update(major);
		return SUCCESS;
	}

	//添加专业的方法
	public String addMajor(){
		//先设置该专业所在学院的编号，因为上传时没有上传。这时从session中获取教研室主任所在的学院的编号即可
		major.setCollege(((Teacher)session.get("teacherDirectorSession")).getCollege());
		//如果已经有专业了，就不添加
		Major mar = serviceFactory.getMajorService().findMajorByName_z(major.getMajorName());
		if(mar == null){
			serviceFactory.getMajorService().save(major);//添加的是major不是mar
		}
		return SUCCESS;
	}
	
	//导出学期信息的方法属性
	private String fileName; //文档的名字
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	//导出必须要execute方法
	public String execute(){
		return SUCCESS;
	}
	//导出学期信息的方法
	public InputStream getExportMajor() throws IOException {
		//指定下载文件的名字
		setFileName(new String("专业信息表.xls".getBytes("GBK"),"iso-8859-1"));
		//获得指定学院的所有的专业信息
		Teacher teacher = (Teacher) session.get("teacherDirectorSession");
		List<Major> majors = serviceFactory.getMajorService().findMajorsByCollegeId_z(teacher.getCollege().getCollegeId());
		// 创建一个临时文件流	
		String curTempFileName = "temp" + System.currentTimeMillis() + ".xlsx";
		
		FileOutputStream os = new FileOutputStream(curTempFileName);
		// 创建一个Excel文档对象
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
		cell.setCellValue(new HSSFRichTextString("专业信息表"));
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
		String[] title = { "序号", "专业编号", "专业名称", "所属学院"};
		// 创建表头行
		row = sheet.createRow(2);
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(title[i]));
		}
		// 循环输出内容
		for (int i = 0; i < majors.size(); i++) {
			// 去掉表格标题以及列名
			row = sheet.createRow(3 + i);
			// 获取每一行的数据
			Major major = (Major) majors.get(i);
			// 把每一行的数据都放到cell中
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
		//新建立一个文件输出流
		InputStream in = new FileInputStream(curTempFileName);
		//删除临时文件
		File file = new File(curTempFileName);
		if(file.isFile()&&file.exists()){
			file.delete();			
		}
		return in;
	}
	
	//导入专业信息
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
	//导入的方法	
	@SuppressWarnings("resource")
	public String importMajor(){
		try{
			// 获取到上传的文件
			FileInputStream fis = new FileInputStream(getUploadFile());
			XSSFWorkbook work = new XSSFWorkbook(fis);
			XSSFSheet sheet = work.getSheetAt(0);
			//直接从第一行开始读就好了
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				Major major = new Major();
				// 获取第一行
				XSSFRow row = sheet.getRow(i);
				XSSFCell cell = row.getCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				//设置Major的名称
				major.setMajorName(cell.getStringCellValue());
				//查找有没有这个学院
				Major findMajor = serviceFactory.getMajorService().findMajorByName_z(major.getMajorName());
				if(findMajor == null){
					//如果没有该学院，就添加
					//添加之前设置major所在的学院
					major.setCollege(((Teacher)session.get("teacherDirectorSession")).getCollege());
					serviceFactory.getMajorService().save(major);
				}
			}
		}catch(Exception e){
			return ERROR;
		}		
		return SUCCESS;
	}
	
	//删除Major方法，返回AJAX数据
	public String deleteMajor() throws IOException{
		//先找到要删除的这个学期
		Major delMaj = serviceFactory.getMajorService().find(major);
		// 定义要返回的数据
		String flag;
		if(delMaj.getClasseses().size() == 0){
			//如果专业没有课程信息，就可删除该专业
			serviceFactory.getMajorService().delete(delMaj); //这里一定要写查出的delMaj，不能写major，因为hibernate有两相同的缓存了。
			flag = "1"; //1表示删除成功
		}else{
			//不可删除该学期
			flag = "0"; //0代表不能删除
		}
		//将flag返回AJAX
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(flag);
		pw.close();
		return null;
	}
}
