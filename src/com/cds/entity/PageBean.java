package com.cds.entity;

import java.util.List;

/**
 * 封装的实体类
 * 
 * @author PengChan
 *
 */
/**
 * @author PengChan
 *
 */
public class PageBean {
	@SuppressWarnings("rawtypes")
	private List list;// 通过hql从数据库分页查询出来
	private int allRows;// 总行数
	private int totalPage;// 总页数
	private int currentPage;// 当前页

	@SuppressWarnings("rawtypes")
	public List getList() {
		return list;
	}

	@SuppressWarnings("rawtypes")
	public void setList(List list) {
		this.list = list;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getAllRows() {
		return allRows;
	}

	public void setAllRows(int allRows) {
		this.allRows = allRows;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 得到总页数
	 * 
	 * @param pageSize
	 *            每页的记录数
	 * @param allRows
	 *            总记录数
	 * @return 总页数
	 */
	public int getTotalPages(int pageSize, int allRows) {
		int totalPage = (allRows % pageSize == 0) ? (allRows / pageSize) : (allRows / pageSize) + 1;
		return totalPage;
	}

	/**
	 * 得到当前开始记录号
	 * 
	 * @param pageSize
	 *            每页记录数
	 * @param currentPage
	 *            当前页面
	 * @return 当前开始的记录号
	 */
	public int getCurrentPageOffset(int pageSize, int currentPage) {
		int offset = pageSize * (currentPage - 1);
		return offset;
	}

	/**
	 * 得到当前的页面,如果为0，则开始第一页，否则为当前页
	 * @param page
	 * @return
	 */
	public int getCurrentPage(int page) {
		int currentPage = (page == 0) ? 1 : page;
		return currentPage;
	}

}
