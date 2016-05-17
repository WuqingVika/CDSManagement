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
	//上传的班级编号
	private int classId;
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
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
			//返回该学院所有的班级
			List<Classes> allClass = serviceFactory.getCollegeService().findClassesByCollegeId_z(teacher.getCollege().getCollegeId());
			//把所有班级保存在request域中
			request.put("allClass", allClass);
			PageBean students = null;
			if(classId <= 0){
				//得到所有本专业的班级
				students = (PageBean) serviceFactory.getPageBeanService().getPageBean("SELECT account.accId, stuId, account.accountId, studentId, stuName, className, majorName FROM account, student, college, classes, major WHERE account.`accId` = student.`accId` AND student.`classId` = classes.`classId` AND classes.`majorId` = major.`majorId` AND college.`collegeId` = major.`collegeId` AND college.`collegeId` = " + teacher.getCollege().getCollegeId(), 10, pno);
			}else{
				//查到某班级对应的所有学生
				students = (PageBean) serviceFactory.getPageBeanService().getPageBean("SELECT account.accId, stuId, account.accountId, studentId, stuName, className, majorName FROM account, student, college, classes, major WHERE account.`accId` = student.`accId` AND student.`classId` = classes.`classId` AND classes.`majorId` = major.`majorId` AND college.`collegeId` = major.`collegeId` AND college.`collegeId` = " + teacher.getCollege().getCollegeId() + " AND classes.`classId` = " + classId, 10, pno);
				//并且返回这个classId。
				request.put("currentClass", classId);
			}
			request.put("pageBean", students); //返回pageBean
			return SUCCESS;
		}else{
			return ERROR;
		}		
	}
	
	//更改方法的属性
	private Student student;
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String updateStudent(){
		//通过classId拿到相应的班级
		Classes cla = new Classes();
		cla.setClassId(classId);
		cla = serviceFactory.getClassesService().find(cla); //找到这个classes
		//必须先找到数据库中的教师，维持原本账户关系
		Student newStu = serviceFactory.getStudentService().find(student);
		//设置学生的班级
		newStu.setClasses(cla);
		//设置学生的学号和姓名
		newStu.setStudentId(student.getStudentId());
		newStu.setStuName(student.getStuName());
		//进行更新教师
		serviceFactory.getStudentService().update(newStu);
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
	public String addStudent(){
		//先通过学号查找该学生，如果有了该学生，则不添加账户和学生表；如果没有该学生，则再进行添加
		Student ns = serviceFactory.getStudentService().findStudentByStudentId_z(student.getStudentId());
		if(ns == null){
			//如果是空就添加
			//先保存账户
			account.setPasswords(account.getAccountId());
			account.setRole(3);
			serviceFactory.getAccountService().save(account);
			//再拿到这个账户的编号，以便保存学生
			Account acc = serviceFactory.getAccountService().find(account);
			//再保存教师
			student.setAccount(acc);
			//找到学生的班级
			Classes cls = new Classes();
			cls.setClassId(classId);
			serviceFactory.getClassesService().find(cls);
			student.setClasses(cls); //设置学生的班级
			serviceFactory.getStudentService().save(student);
		}
		return SUCCESS;
	}
	
	//导出学生信息的方法属性
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
	public InputStream getExportStudent() throws IOException {
		//指定下载文件的名字
		setFileName(new String("学生信息表.xls".getBytes("GBK"),"iso-8859-1"));
		Teacher sessionTeacher = (Teacher)session.get("teacherDirectorSession");
		//查找到学院所有的学生
		List stuList = serviceFactory.getCollegeService().findStudentsByCollegeId(sessionTeacher.getCollege().getCollegeId());
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
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 5));
		HSSFRow row = null;
		HSSFCell cell = null;
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(titleStyle);
		// 这里是设置excel表格的标题
		cell.setCellValue(new HSSFRichTextString("学生信息表"));
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
		String[] title = { "序号", "登录账号", "学生学号", "学生姓名", "所属班级", "所属学院"};
		// 创建表头行
		row = sheet.createRow(2);
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(title[i]));
		}
		// 循环输出内容
		for (int i = 0; i < stuList.size(); i++) {
			// 去掉表格标题以及列名
			row = sheet.createRow(3 + i);
			// 获取每一行的数据
			Object[] stu = (Object[]) stuList.get(i);
			// 把每一行的数据都放到cell中
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
		//新建立一个文件输出流
		InputStream in = new FileInputStream(curTempFileName);
		//删除临时文件
		File file = new File(curTempFileName);
		if(file.isFile()&&file.exists()){
			file.delete();			
		}
		return in;
	}
	
	//导入学生信息
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
	public String importStudent(){
		try{
			// 获取到上传的文件
			FileInputStream fis = new FileInputStream(getUploadFile());
			XSSFWorkbook work = new XSSFWorkbook(fis);
			XSSFSheet sheet = work.getSheetAt(0);
			//直接从第二行开始读就好了
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Student stu = new Student();
				// 线获取第2列数据，就是学号，根据学号判断是不是有这个学生
				XSSFRow row = sheet.getRow(i);
				XSSFCell cell = row.getCell(1);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				//设置教师工号
				stu.setStudentId(cell.getStringCellValue());
				//查找有没有这个学生
				if(serviceFactory.getStudentService().findStudentByStudentId_z(stu.getStudentId()) == null){
					//如果没有该学生，就添加
					//先添加账号
					Account ta = new Account();
					XSSFCell cell0 = row.getCell(0); //获得登录账号
					cell0.setCellType(Cell.CELL_TYPE_STRING);
					ta.setAccountId(cell0.getStringCellValue());
					ta.setPasswords(ta.getAccountId()); //设置密码就是用户账号
					ta.setRole(3); //设置是学生
					serviceFactory.getAccountService().save(ta);//保存账号
					//再保存学生			
					stu.setAccount(ta); //设置学生对应的账号
					XSSFCell cell2 = row.getCell(2); //获得学生名称的列
					cell2.setCellType(Cell.CELL_TYPE_STRING);
					stu.setStuName(cell2.getStringCellValue());
					//设置学生的班级
					XSSFCell cell3 = row.getCell(3); //获得学生班级的列
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
	
	//删除学生的方法，返回AJAX数据
	public String deleteStudent() throws IOException{
		//先找到要删除的这学生
		Student delStu = serviceFactory.getStudentService().find(student);
		// 定义要返回的数据
		String flag;
		if(delStu.getStudentgroups_1().size() == 0){
			//如果学生没有课程计划信息，就可删除
			//先找到该学生的账户，准备删除
			Account stuAcc = delStu.getAccount();
			serviceFactory.getStudentService().delete(delStu); //这里一定要写查出的delStu
			//然后再删除Account
			serviceFactory.getAccountService().delete(stuAcc);
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
