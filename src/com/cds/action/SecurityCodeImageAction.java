package com.cds.action;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cds.common.SecurityCode;
import com.cds.common.SecurityCode.SecurityCodeLevel;
import com.cds.common.SecurityImage;
import com.opensymphony.xwork2.ActionSupport;

/**
 * ����ͼƬ��֤���Action
 * 
 * @author PengChan
 *
 */
public class SecurityCodeImageAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	// ͼƬ��
	private ByteArrayInputStream imageStream;
	// session��
	private HttpSession session = ServletActionContext.getRequest().getSession();

	public ByteArrayInputStream getImageStream() {
		return imageStream;
	}

	public void setImageStream(ByteArrayInputStream imageStream) {
		this.imageStream = imageStream;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public String execute() {
		String securityCode = SecurityCode.getSecutrityCode(4, SecurityCodeLevel.Hard, false);
		imageStream = SecurityImage.getImageAsInputStream(securityCode);
		// ����session��
		session.setAttribute("secturityCode", securityCode);
		return SUCCESS;
	}

}
