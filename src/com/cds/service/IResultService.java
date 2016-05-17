package com.cds.service;

import java.util.List;

import com.cds.entity.PageBean;

/**
 * 分页访问的服务
 * @author PengChan
 *
 */
public interface IResultService {
	/**
	 * 获取页面的实体类服务
	 * @param hql
	 * @param pageSize
	 * @param page
	 * @return
	 */
	PageBean getPageBean(String hql,int pageSize,int page);
	List getQueryList(String sql);
}
