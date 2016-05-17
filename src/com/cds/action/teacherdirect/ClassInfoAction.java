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
	//这个index方法就是获取Class的所有数据，并且页面进行显示
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
			PageBean majors = (PageBean) serviceFactory.getPageBeanService().getPageBean("SELECT classId, className, majorName FROM classes, major, college WHERE classes.`majorId` = major.`majorId` AND college.`collegeId` = major.`collegeId` AND collegeName = '" + teacher.getCollege().getCollegeName() + "'", 10, pno);
			request.put("pageBean", majors);
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	//获得该学院所有专业的方法，返回的是JSON数据
	public String getMajor() throws IOException {
		int collegeId = ((Teacher)session.get("teacherDirectorSession")).getCollege().getCollegeId();
		List majors = serviceFactory.getMajorService().findMajorsByCollegeIdJSON_z(collegeId);
		String json = JSON.toJSONString(majors);
		//将json返回AJAX
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.close();
		return null;
	}
	//更改方法的属性
	private Classes classes;
	//更改的方法
	public Classes getClasses() {
		return classes;
	}
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	private String majorName; //上传上来的专业
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	public String updateClass(){
		//通过majorName拿到响应的专业
		Major major = serviceFactory.getMajorService().findMajorByName_z(majorName);
		//设置该班级的专业
		classes.setMajor(major);
		//进行更新班级
		serviceFactory.getClassesService().update(classes);
		return SUCCESS;
	}
	
	//添加专业的方法，这个和更改的方法基本是一样的。
	public String addClass(){
		//先判断这个班级是否存在了。
		Classes cla = serviceFactory.getClassesService().findClassByClassName_z(classes.getClassName());
		if(cla == null){
			//不存在就添加
			//通过majorName拿到响应的专业
			Major major = serviceFactory.getMajorService().findMajorByName_z(majorName);
			//设置该班级的专业
			classes.setMajor(major);
			//进行更新班级
			serviceFactory.getClassesService().save(classes);
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
	public InputStream getExportClass() throws IOException {
		//指定下载文件的名字
		setFileName(new String("班级信息表.xls".getBytes("GBK"),"iso-8859-1"));
		//获得指定学院的所有的专业信息
		Teacher teacher = (Teacher) session.get("teacherDirectorSession");
		List classList = serviceFactory.getClassesService().findClassByCollegeId_z(teacher.getCollege().getCollegeId());
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
		cell.setCellValue(new HSSFRichTextString("班级信息表"));
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
		String[] title = { "序号", "班级编号", "班级名称", "所属专业", "所属学院"};
		// 创建表头行
		row = sheet.createRow(2);
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(title[i]));
		}
		// 循环输出内容
		for (int i = 0; i < classList.size(); i++) {
			// 去掉表格标题以及列名
			row = sheet.createRow(3 + i);
			// 获取每一行的数据
			Object[] classes = (Object[]) classList.get(i);
			// 把每一行的数据都放到cell中
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
		//新建立一个文件输出流
		InputStream in = new FileInputStream(curTempFileName);
		//删除临时文件
		File file = new File(curTempFileName);
		if(file.isFile()&&file.exists()){
			file.delete();			
		}
		return in;
	}
	
	//删除Classes方法，返回AJAX数据
	public String deleteClass() throws IOException{
		//先找到要删除的这个学期
		Classes delcla = serviceFactory.getClassesService().find(classes);
		// 定义要返回的数据
		String flag;
		if(delcla.getStudents().size() == 0){
			//如果班级没有学生信息，就可删除该专业
			serviceFactory.getClassesService().delete(delcla); //这里一定要写查出的delcla
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
