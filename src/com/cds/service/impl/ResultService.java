package com.cds.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.cds.dao.IResultDao;
import com.cds.entity.PageBean;
import com.cds.service.IResultService;

/**
 * ��ȡҳ��ʵ����ķ���ʵ��
 * 
 * @author PengChan
 *
 */
public class ResultService implements IResultService {

	private IResultDao resultDao;

	// ע��resultdao
	public void setResultDao(IResultDao resultDao) {
		this.resultDao = resultDao;
	}

	/**
	 * ��ȡҳ��ʵ��
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public PageBean getPageBean(String hql, int pageSize, int page) {
		System.out.println("pageSize:"+pageSize+",page="+page);
		PageBean pageBean = new PageBean();		
		int allRows = resultDao.getAllRowCount(hql);//��ȡ���е���
		int totalPage = pageBean.getTotalPages(pageSize, allRows);//��ȡ�����е�ҳ��
		int currentPage = pageBean.getCurrentPage(page);//��ȡ��ǰҳ��ҳ��
		int offset = pageBean.getCurrentPageOffset(pageSize, currentPage);//��ȡ��ʼ��ѯ�ı��
		List list = resultDao.queryByPage(hql, offset, pageSize);//��ѯ��¼
		System.out.println("resultservice��:"+JSON.toJSONString(pageBean.getList()));
		//���²���Ϊ�Ѳ����ŵ�pageBean��
		//pageBean.setUrl("teacherMakeProPlan_viewProcessPlan");
		pageBean.setList(list);
		pageBean.setAllRows(allRows);
		pageBean.setCurrentPage(currentPage);
		pageBean.setTotalPage(totalPage);
		return pageBean;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getQueryList(String sql) {
		// TODO Auto-generated method stub
		return resultDao.queryBysql(sql);
	}

}
