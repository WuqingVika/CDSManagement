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
	//这个index方法就是获取Teacher的所有数据，并且页面进行显示
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
			//得到所有本专业的班级
			PageBean teachers = (PageBean) serviceFactory.getPageBeanService().getPageBean("SELECT teacher.`teacherId`, accountId, teacherWorkId, teacherName FROM account, teacher WHERE teacher.`accId` = account.`accId` AND teacher.`collegeId` = " + teacher.getCollege().getCollegeId(), 10, pno);
			request.put("pageBean", teachers);
			return SUCCESS;
		}else{
			return ERROR;
		}		
	}
	
	//获得学校所有学院的方法，返回的是JSON数据
	public String getCollege() throws IOException {
		List<College> colleges = serviceFactory.getCollegeService().findAll();
		StringBuilder sb = new StringBuilder("{\"collegeName\":[");
		for(College college : colleges){
			sb.append("\"" + college.getCollegeName() + "\"" + ",");
		}
		
		String json = sb.toString();
		json = json.substring(0, json.length() - 1) + "]}";
		System.out.println(json);
		//将json返回AJAX
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		System.out.println(json);
		pw.write(json);
		pw.close();
		return null;
	}
	
	//更改方法的属性
	private Teacher teacher;
	private String collegeName; //上传上来的学院
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
		//通过collegeName拿到相应的学院
		College college = serviceFactory.getCollegeService().findCollegeByName_z(collegeName);
		//必须先找到数据库中的教师，维持原本账户关系
		Teacher newTeacher = serviceFactory.getTeacherService().find(teacher);
		//设置教师的学院
		newTeacher.setCollege(college);
		//设置教师的工号的姓名
		newTeacher.setTeacherWorkId(teacher.getTeacherWorkId());
		newTeacher.setTeacherName(teacher.getTeacherName());
		//进行更新教师
		serviceFactory.getTeacherService().update(newTeacher);
		return SUCCESS;
	}
	//上传的account
	private Account account;
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	//添加教师的方法
	public String addTeacher(){
		//先通过教师工号查找该教师，如果有了该教师，则不添加账户和教师表；如果没有该教师，则再进行添加
		Teacher nt = serviceFactory.getTeacherService().findTeacherByWorkId_z(teacher.getTeacherWorkId());
		if(nt == null){
			//如果是空就添加
			//先保存账户
			account.setPasswords(account.getAccountId());
			account.setRole(2);
			serviceFactory.getAccountService().save(account);
			//再拿到这个账户的编号，以便保存教师
			Account acc = serviceFactory.getAccountService().find(account);
			//再保存教师
			teacher.setAccount(acc);		
			teacher.setCollege(((Teacher)session.get("teacherDirectorSession")).getCollege());
			serviceFactory.getTeacherService().save(teacher);
		}
		return SUCCESS;
	}
	
	//导出教师信息的方法属性
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
	public InputStream getExportTeacher() throws IOException {
		//指定下载文件的名字
		setFileName(new String("教师信息表.xls".getBytes("GBK"),"iso-8859-1"));
		Teacher sessionTeacher = (Teacher)session.get("teacherDirectorSession");
		List teachList = serviceFactory.getTeacherService().findTeachersByCollegeId_z(sessionTeacher.getCollege().getCollegeId());
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
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 4));
		HSSFRow row = null;
		HSSFCell cell = null;
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(titleStyle);
		// 这里是设置excel表格的标题
		cell.setCellValue(new HSSFRichTextString("教师信息表"));
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
		String[] title = { "序号", "登录账号", "教师工号", "教师名称", "所属学院"};
		// 创建表头行
		row = sheet.createRow(2);
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(title[i]));
		}
		// 循环输出内容
		for (int i = 0; i < teachList.size(); i++) {
			// 去掉表格标题以及列名
			row = sheet.createRow(3 + i);
			// 获取每一行的数据
			Object[] teach = (Object[]) teachList.get(i);
			// 把每一行的数据都放到cell中
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
		//新建立一个文件输出流
		InputStream in = new FileInputStream(curTempFileName);
		//删除临时文件
		File file = new File(curTempFileName);
		if(file.isFile()&&file.exists()){
			file.delete();			
		}
		return in;
	}
	
	//导入教师信息
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
	public String importTeacher(){
		try{
			// 获取到上传的文件
			FileInputStream fis = new FileInputStream(getUploadFile());
			XSSFWorkbook work = new XSSFWorkbook(fis);
			XSSFSheet sheet = work.getSheetAt(0);
			//直接从第二行开始读就好了
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Teacher teacher = new Teacher();
				// 线获取第3行数据，就是工号，根据工号判断是不是有这个教师
				XSSFRow row = sheet.getRow(i);
				XSSFCell cell = row.getCell(1);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				//设置教师工号
				teacher.setTeacherWorkId(cell.getStringCellValue());
				//查找有没有这个教师
				if(serviceFactory.getTeacherService().findTeacherByWorkId_z(teacher.getTeacherWorkId()) == null){
					//如果没有该教师，就添加
					//先添加账号
					Account ta = new Account();
					XSSFCell cell0 = row.getCell(0); //获得登录账号
					cell0.setCellType(Cell.CELL_TYPE_STRING);
					ta.setAccountId(cell0.getStringCellValue());
					ta.setPasswords(ta.getAccountId()); //设置密码就是用户账号
					ta.setRole(2); //设置角色2是教师
					serviceFactory.getAccountService().save(ta);//保存账号
					//再保存教师					
					teacher.setAccount(ta); //设置教师对应的账号
					XSSFCell cell2 = row.getCell(2); //获得教师名称的列
					cell2.setCellType(Cell.CELL_TYPE_STRING);
					teacher.setTeacherName(cell2.getStringCellValue());
					teacher.setCollege(((Teacher)session.get("teacherDirectorSession")).getCollege());//设置教师的学院
					serviceFactory.getTeacherService().save(teacher);
				}
			}
		}catch(Exception e){
			return ERROR;
		}		
		return SUCCESS;
	}
	
	//删除教师方法，返回AJAX数据
	public String deleteTeacher() throws IOException{
		//先找到要删除的这教师
		Teacher delTea = serviceFactory.getTeacherService().find(teacher);
		// 定义要返回的数据
		String flag;
		if(delTea.getCdteachergroups_1().size() == 0){
			//如果教师没有课程计划信息，就可删除
			//先找到该教师的账户，准备删除
			Account teaAcc = delTea.getAccount();
			serviceFactory.getTeacherService().delete(delTea); //这里一定要写查出的delTea
			//然后再删除Account
			serviceFactory.getAccountService().delete(teaAcc);
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
