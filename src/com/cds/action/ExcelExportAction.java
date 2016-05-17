package com.cds.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.cds.entity.Account;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

/**
 * excel的导出功能(从数据库导出为excel)
 * 
 * @author PengChan
 *
 */
public class ExcelExportAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	// 注入serviceFactory
	private ServiceFactory serviceFactory;

	public void setServiceFactory(ServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	private String fileName;// 文档的名字

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@SuppressWarnings({ "rawtypes", "resource" })
	public InputStream getExcel() throws IOException {		 
		// 制定下载文件的名字
		this.setFileName(new String("账户信息表.xls".getBytes("GBK"),"iso-8859-1"));
		//从数据库中读取数据
		List list = serviceFactory.getAccountService().findAll();		 
		// 创建一个临时文件流		 
		FileOutputStream os = new FileOutputStream("temp.xls");
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
		cell.setCellValue(new HSSFRichTextString("账号信息表"));
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
		String[] title = { "编号", "账号", "密码","角色"};
		// 创建表头行
		row = sheet.createRow(2);
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(title[i]));
		}
		// 循环输出内容
		for (int i = 0; i < list.size(); i++) {
			// 去掉表格标题以及列名
			row = sheet.createRow(3 + i);
			// 获取每一行的数据
			Account rowInfo = (Account) list.get(i);
			// 把每一行的数据都放到cell中
			cell = row.createCell(0);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(rowInfo.getAccId().toString()));
			cell = row.createCell(1);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(rowInfo.getAccountId()));
			cell = row.createCell(2);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(rowInfo.getPasswords()));
			cell = row.createCell(3);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(rowInfo.getRole().toString()));
		}
		workBook.write(os);		 
		//新建立一个文件输出流
		InputStream in = new FileInputStream("temp.xls");
		//删除临时文件
		File file = new File("temp.xlsx");
		if(file.isFile()&&file.exists()){
			file.delete();			
		}		 
		return in;
	}

	public String execute() {
		return SUCCESS;
	}
}
