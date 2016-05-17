package com.cds.service;

import java.util.List;

import com.cds.entity.PageBean;

/**
 * ��ҳ���ʵķ���
 * @author PengChan
 *
 */
public interface IResultService {
	/**
	 * ��ȡҳ���ʵ�������
	 * @param hql
	 * @param pageSize
	 * @param page
	 * @return
	 */
	PageBean getPageBean(String hql,int pageSize,int page);
	List getQueryList(String sql);
}
