package com.cds.action;


import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cds.entity.Account;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 用户登录的action
 * 
 * @author PengChan
 *
 */
public class LoginAction extends ActionSupport implements ModelDriven<Account> {
	private static final long serialVersionUID = 1L;
	private String message;
	private String securityCode;
	private Account account = new Account();

	// 注入serviceFactory
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
	 * 用户登录的功能
	 * @return
	 */
	public String login() {
		// 获取session
		HttpSession session = ServletActionContext.getRequest().getSession();
		// 判断验证码是否正确
		if (session.getAttribute("secturityCode")== null) {
			setMessage("对不起 暂时无法登录,请重试");
			return INPUT;
		} else {			 
			if (session.getAttribute("secturityCode").toString().toLowerCase().equals(this.getSecurityCode().toLowerCase())) {
				Account result = serviceFactory.getAccountService().findAccountByAccountId_c(account);
				if (result == null) {
					setMessage("用户名或者密码有误");
					ServletActionContext.getRequest().setAttribute("message", message);
					return INPUT;
				} else {
					if (result.getAccountId().equals(account.getAccountId())
							&& result.getPasswords().equals(account.getPasswords())) {
						ServletActionContext.getRequest().getSession().setAttribute("account",result);
						//清空session，防止重复登录
						session.setAttribute("secturityCode",null);
						// 获取用户的角色
						int role = result.getRole();
						switch (role) {
						case 1:
							return "teacherDir";
						case 2:
							return "teacher";							 
						case 3:
							return "student";							 
						default:
							setMessage("对不起服务器内部错误暂时无法登录");
							return INPUT;
						}
					} else {
						setMessage("用户名或者密码有误");
						ServletActionContext.getRequest().setAttribute("message", message);
						return INPUT;
					}
				}
			} else {
				setMessage("验证码错误");
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
