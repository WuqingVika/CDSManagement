package com.cds.action;


import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cds.entity.Account;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * �û���¼��action
 * 
 * @author PengChan
 *
 */
public class LoginAction extends ActionSupport implements ModelDriven<Account> {
	private static final long serialVersionUID = 1L;
	private String message;
	private String securityCode;
	private Account account = new Account();

	// ע��serviceFactory
	private ServiceFactory serviceFactory;

	public void setServiceFactory(ServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * �û���¼�Ĺ���
	 * @return
	 */
	public String login() {
		// ��ȡsession
		HttpSession session = ServletActionContext.getRequest().getSession();
		// �ж���֤���Ƿ���ȷ
		if (session.getAttribute("secturityCode")== null) {
			setMessage("�Բ��� ��ʱ�޷���¼,������");
			return INPUT;
		} else {			 
			if (session.getAttribute("secturityCode").toString().toLowerCase().equals(this.getSecurityCode().toLowerCase())) {
				Account result = serviceFactory.getAccountService().findAccountByAccountId_c(account);
				if (result == null) {
					setMessage("�û���������������");
					ServletActionContext.getRequest().setAttribute("message", message);
					return INPUT;
				} else {
					if (result.getAccountId().equals(account.getAccountId())
							&& result.getPasswords().equals(account.getPasswords())) {
						ServletActionContext.getRequest().getSession().setAttribute("account",result);
						//���session����ֹ�ظ���¼
						session.setAttribute("secturityCode",null);
						// ��ȡ�û��Ľ�ɫ
						int role = result.getRole();
						switch (role) {
						case 1:
							return "teacherDir";
						case 2:
							return "teacher";							 
						case 3:
							return "student";							 
						default:
							setMessage("�Բ���������ڲ�������ʱ�޷���¼");
							return INPUT;
						}
					} else {
						setMessage("�û���������������");
						ServletActionContext.getRequest().setAttribute("message", message);
						return INPUT;
					}
				}
			} else {
				setMessage("��֤�����");
				ServletActionContext.getRequest().setAttribute("message", message);
				return INPUT;
			}
		}
	}
		

	@Override
	public Account getModel() {
		return this.account;
	}

}
