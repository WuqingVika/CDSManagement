package com.cds.action;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 上传文件的Action
 * @author PengChan
 *
 */
public class FileUploadAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private File uploadFile;// 上传的文件
	private String uploadFileFileName;// 上传的文件的名称

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

	@SuppressWarnings("deprecation")
	public String execute() throws IOException{
		//获取到文件的路径
		String targetDirectory = ServletActionContext.getRequest().getRealPath("/WEB-INF/upload");		
		//创建一个临时的file对象
		File target = new File(targetDirectory, uploadFileFileName);
		
		
		//将上传的文件拷贝到临时对象中，从而实现文件上传
		FileUtils.copyFile(uploadFile,target);
		return "upload";
	}
	
}
