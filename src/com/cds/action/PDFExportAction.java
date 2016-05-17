package com.cds.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.cds.entity.Account;
import com.cds.service.service.factory.ServiceFactory;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionSupport;

public class PDFExportAction extends ActionSupport {
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

	@SuppressWarnings("rawtypes")
	public InputStream getPDF() throws DocumentException, IOException {
		// 制定下载文件的名字
		this.setFileName(new String("账户信息表.pdf".getBytes("GBK"), "iso-8859-1"));
		// 从数据库中读取数据
		List list = serviceFactory.getAccountService().findAll();
		BaseFont bfChinese = null;
		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
		Font font = new Font(bfChinese, 12, Font.BOLD);
		font.setColor(255, 0, 0);
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, new FileOutputStream("temp.pdf"));
		document.open();
		String title = "账户信息表";
		Paragraph paragraph = new Paragraph(title, font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(paragraph);
		PdfPTable table = new PdfPTable(4);
		table.setSpacingBefore(30f);
		String[] tableTitle = { "编号", "账号", "密码", "角色" };
		// 添加标题
		for (int i = 0; i < tableTitle.length; i++) {
			paragraph = new Paragraph(tableTitle[i], new Font(bfChinese, 10, Font.BOLD));
			PdfPCell cell = new PdfPCell(paragraph);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
		}
		// 添加内容
		for (int i = 0; i < list.size(); i++) {
			Account rowInfo = (Account) list.get(i);
			PdfPCell cell = new PdfPCell(new Paragraph(rowInfo.getAccId().toString(), new Font(bfChinese, 10)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(rowInfo.getAccountId(), new Font(bfChinese, 10)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(rowInfo.getPasswords(), new Font(bfChinese, 10)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
			cell = new PdfPCell(new Paragraph(rowInfo.getRole().toString(), new Font(bfChinese, 10)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
		}
		document.add(table);
		document.close();
		// 新建立一个文件输出流
		InputStream in = new FileInputStream("temp.pdf");
		// 删除临时文件
		File file = new File("temp.pdf");
		if (file.isFile() && file.exists()) {
			file.delete();
		}
		return in;
	}

	public String execute() {
		return SUCCESS;
	}
}
