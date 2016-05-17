package com.cds.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cds.entity.Account;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

/**
 * excel�ĵ��빦�� *ʵ�ֵ�˼·�ǵ�һ���ļ����ϴ���Ȼ����ϴ����ļ����д���ɾ�����ļ�
 * 
 * @author PengChan
 *
 */
public class ExcelImportAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	// ע��serviceFactory
	private ServiceFactory serviceFactory;

	public void setServiceFactory(ServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	private File uploadFile;// �ϴ����ļ�
	private String uploadFileFileName;// �ϴ����ļ�������

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

	@SuppressWarnings("resource")
	public String execute() throws IOException {
		// ��ȡ���ϴ����ļ�
				
		FileInputStream fis = new FileInputStream(this.getUploadFile());
		XSSFWorkbook work = new XSSFWorkbook(fis);
		XSSFSheet sheet = work.getSheetAt(0);
		for (int i = 2; i <= sheet.getLastRowNum(); i++) {
			Account account = new Account();
			// ��ȡ��һ��
			XSSFRow row = sheet.getRow(i);
			XSSFCell cell2 = row.getCell(1);
			XSSFCell cell3 = row.getCell(2);
			XSSFCell cell4 = row.getCell(3);
			cell2.setCellType(Cell.CELL_TYPE_STRING);
			cell3.setCellType(Cell.CELL_TYPE_STRING);
			cell4.setCellType(Cell.CELL_TYPE_STRING);
			String accountId = cell2.getStringCellValue();
			String passwords = cell3.getStringCellValue();
			Integer role = new Integer(cell4.getStringCellValue());
			account.setAccountId(accountId);
			account.setPasswords(passwords);
			account.setRole(role);
			serviceFactory.getAccountService().save(account);
		}
		return SUCCESS;
	}
}
