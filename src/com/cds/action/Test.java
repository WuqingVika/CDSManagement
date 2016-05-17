package com.cds.action;

import org.apache.struts2.ServletActionContext;

import com.cds.entity.PageBean;
import com.cds.service.service.factory.ServiceFactory;
import com.opensymphony.xwork2.ActionSupport;

public class Test extends ActionSupport {	 
	private static final long serialVersionUID = 1L;
	private ServiceFactory serviceFactory;
	private int pno = 0;// 页码

	public int getPno() {
		return pno;
	}

	public void setPno(int pno) {
		this.pno = pno;
	}

	public ServiceFactory getServiceFactory() {
		return serviceFactory;
	}

	public void setServiceFactory(ServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	public String mytest() {
		System.out.println(serviceFactory.getAccountService().findAll().size());
		return SUCCESS;
	}

	/**
	 * 测试分页功能的struts
	 * @return
	 */
	public String testPageBean() {
		PageBean pageBean = new PageBean();
		pageBean = serviceFactory.getPageBeanService().getPageBean("from Account", 2, pno);
		ServletActionContext.getRequest().setAttribute("pageBean", pageBean);
		return SUCCESS;
	}
}
