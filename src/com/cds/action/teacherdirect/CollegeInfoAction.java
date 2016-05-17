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
 * 本类是学院信息管理的Action类。
 * 主要功能有：添加学院信息、导入导出学院信息和删除修改学院信息
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
	
	//这个index方法就是获取College的所有数据，并且页面进行显示
	public String index(){
		PageBean colleges = (PageBean) serviceFactory.getPageBeanService().getPageBean("select * from college", 10, pno);
		request.put("pageBean", colleges);
		return SUCCESS;
	}
	
	//更改的方法的属性
	private College college;
	public College getCollege() {
		return college;
	}
	public void setCollege(College college) {
		this.college = college;
	}
	//更改的方法
	public String updateCollege(){
		serviceFactory.getCollegeService().update(college);
		return SUCCESS;
	}
	//添加学院的方法
	public String addCollege(){
		//如果已经有学院了，就不添加
		College col = serviceFactory.getCollegeService().findCollegeByName_z(college.getCollegeName());
		if(col == null){
			serviceFactory.getCollegeService().save(college);
		}
		return SUCCESS;
	}
	//导出学院信息的方法属性
	private String fileName; //文档的名字
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	//导出学院必须要execute方法
	public String execute(){
		return SUCCESS;
	}
	//导出学院信息的方法
	public InputStream getExportCollege() throws IOException {
		//指定下载文件的名字
		setFileName(new String("学院信息表.xls".getBytes("GBK"),"iso-8859-1"));
		//获得所有的学院信息
		List<College> colleges = serviceFactory.getCollegeService().findAll();
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
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 2));
		HSSFRow row = null;
		HSSFCell cell = null;
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(titleStyle);
		// 这里是设置excel表格的标题
		cell.setCellValue(new HSSFRichTextString("学院信息表"));
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
		String[] title = { "序号", "学院编号", "学院名称"};
		// 创建表头行
		row = sheet.createRow(2);
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(title[i]));
		}
		// 循环输出内容
		for (int i = 0; i < colleges.size(); i++) {
			// 去掉表格标题以及列名
			row = sheet.createRow(3 + i);
			// 获取每一行的数据
			College college = (College) colleges.get(i);
			// 把每一行的数据都放到cell中
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
		//新建立一个文件输出流
		InputStream in = new FileInputStream(curTempFileName);
		//删除临时文件
		File file = new File(curTempFileName);
		if(file.isFile()&&file.exists()){
			file.delete();			
		}		 
		return in;
	}
		
	//导入学院信息
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
	public String importCollege(){
		try{
			// 获取到上传的文件
			FileInputStream fis = new FileInputStream(getUploadFile());
			XSSFWorkbook work = new XSSFWorkbook(fis);
			XSSFSheet sheet = work.getSheetAt(0);
			//直接从第一行开始读就好了
			for (int i = 0; i <= sheet.getLastRowNum(); i++) {
				College college = new College();
				// 获取第一行
				XSSFRow row = sheet.getRow(i);
				XSSFCell cell = row.getCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				//设置College的名称
				college.setCollegeName(cell.getStringCellValue());
				//查找有没有这个学院
				College findCollege = serviceFactory.getCollegeService().findCollegeByName_z(college.getCollegeName());
				if(findCollege == null){
					//如果没有该学院，就添加
					serviceFactory.getCollegeService().save(college);
				}
			}
		}catch(Exception e){
			return ERROR;
		}		
		return SUCCESS;
	}
	
	//删除College方法，返回AJAX数据
	public String deleteCollege() throws IOException{
		//先找到要删除的这个学院
		College delCol = serviceFactory.getCollegeService().find(college);
		// 定义要返回的数据
		String flag;
		if(delCol.getMajors().size() == 0){
			//如果没有专业信息，就可删除该学院
			serviceFactory.getCollegeService().delete(delCol); //这里一定要写查出的delCol，不能写college，因为hibernate有两相同的缓存了。
			
			flag = "1"; //1表示删除成功
		}else{
			//不可删除该学院
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
