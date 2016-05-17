package com.cds.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class DownLoadAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String fileName;// 文件的名称
		  
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getDownLoadFile() throws UnsupportedEncodingException, FileNotFoundException {	 
		String path = ServletActionContext.getServletContext().getRealPath("//WEB-INF//upload")+"\\"+fileName;
		System.out.println(path);
		int index = path.lastIndexOf('\\');
		this.setFileName(new String(path.substring(index + 1).getBytes("GBK"),"iso-8859-1"));
		InputStream in = new FileInputStream(path);
		return in;
	}
	 
	public String execute() {
		return SUCCESS;
	}

}
