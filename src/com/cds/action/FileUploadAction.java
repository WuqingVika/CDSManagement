package com.cds.action;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * �ϴ��ļ���Action
 * @author PengChan
 *
 */
public class FileUploadAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
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

	@SuppressWarnings("deprecation")
	public String execute() throws IOException{
		//��ȡ���ļ���·��
		String targetDirectory = ServletActionContext.getRequest().getRealPath("/WEB-INF/upload");		
		//����һ����ʱ��file����
		File target = new File(targetDirectory, uploadFileFileName);
		
		
		//���ϴ����ļ���������ʱ�����У��Ӷ�ʵ���ļ��ϴ�
		FileUtils.copyFile(uploadFile,target);
		return "upload";
	}
	
}
