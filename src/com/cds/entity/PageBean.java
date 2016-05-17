package com.cds.entity;

import java.util.List;

/**
 * ��װ��ʵ����
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
	private List list;// ͨ��hql�����ݿ��ҳ��ѯ����
	private int allRows;// ������
	private int totalPage;// ��ҳ��
	private int currentPage;// ��ǰҳ

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
	 * �õ���ҳ��
	 * 
	 * @param pageSize
	 *            ÿҳ�ļ�¼��
	 * @param allRows
	 *            �ܼ�¼��
	 * @return ��ҳ��
	 */
	public int getTotalPages(int pageSize, int allRows) {
		int totalPage = (allRows % pageSize == 0) ? (allRows / pageSize) : (allRows / pageSize) + 1;
		return totalPage;
	}

	/**
	 * �õ���ǰ��ʼ��¼��
	 * 
	 * @param pageSize
	 *            ÿҳ��¼��
	 * @param currentPage
	 *            ��ǰҳ��
	 * @return ��ǰ��ʼ�ļ�¼��
	 */
	public int getCurrentPageOffset(int pageSize, int currentPage) {
		int offset = pageSize * (currentPage - 1);
		return offset;
	}

	/**
	 * �õ���ǰ��ҳ��,���Ϊ0����ʼ��һҳ������Ϊ��ǰҳ
	 * @param page
	 * @return
	 */
	public int getCurrentPage(int page) {
		int currentPage = (page == 0) ? 1 : page;
		return currentPage;
	}

}
