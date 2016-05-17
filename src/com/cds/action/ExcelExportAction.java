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
 * excel�ĵ�������(�����ݿ⵼��Ϊexcel)
 * 
 * @author PengChan
 *
 */
public class ExcelExportAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	// ע��serviceFactory
	private ServiceFactory serviceFactory;

	public void setServiceFactory(ServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	private String fileName;// �ĵ�������

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@SuppressWarnings({ "rawtypes", "resource" })
	public InputStream getExcel() throws IOException {		 
		// �ƶ������ļ�������
		this.setFileName(new String("�˻���Ϣ��.xls".getBytes("GBK"),"iso-8859-1"));
		//�����ݿ��ж�ȡ����
		List list = serviceFactory.getAccountService().findAll();		 
		// ����һ����ʱ�ļ���		 
		FileOutputStream os = new FileOutputStream("temp.xls");
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
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 3));
		HSSFRow row = null;
		HSSFCell cell = null;
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(titleStyle);
		// ����������excel���ı���
		cell.setCellValue(new HSSFRichTextString("�˺���Ϣ��"));
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
		String[] title = { "���", "�˺�", "����","��ɫ"};
		// ������ͷ��
		row = sheet.createRow(2);
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellStyle(tableStyle);
			cell.setCellValue(new HSSFRichTextString(title[i]));
		}
		// ѭ���������
		for (int i = 0; i < list.size(); i++) {
			// ȥ���������Լ�����
			row = sheet.createRow(3 + i);
			// ��ȡÿһ�е�����
			Account rowInfo = (Account) list.get(i);
			// ��ÿһ�е����ݶ��ŵ�cell��
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
		//�½���һ���ļ������
		InputStream in = new FileInputStream("temp.xls");
		//ɾ����ʱ�ļ�
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
