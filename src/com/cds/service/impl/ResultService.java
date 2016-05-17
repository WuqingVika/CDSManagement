package com.cds.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.cds.dao.IResultDao;
import com.cds.entity.PageBean;
import com.cds.service.IResultService;

/**
 * 获取页面实体类的服务实现
 * 
 * @author PengChan
 *
 */
public class ResultService implements IResultService {

	private IResultDao resultDao;

	// 注入resultdao
	public void setResultDao(IResultDao resultDao) {
		this.resultDao = resultDao;
	}

	/**
	 * 获取页面实体
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public PageBean getPageBean(String hql, int pageSize, int page) {
		System.out.println("pageSize:"+pageSize+",page="+page);
		PageBean pageBean = new PageBean();		
		int allRows = resultDao.getAllRowCount(hql);//获取所有的行
		int totalPage = pageBean.getTotalPages(pageSize, allRows);//获取的所有的页数
		int currentPage = pageBean.getCurrentPage(page);//获取当前页的页号
		int offset = pageBean.getCurrentPageOffset(pageSize, currentPage);//获取开始查询的编号
		List list = resultDao.queryByPage(hql, offset, pageSize);//查询记录
		System.out.println("resultservice中:"+JSON.toJSONString(pageBean.getList()));
		//以下部分为把参数放到pageBean中
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
