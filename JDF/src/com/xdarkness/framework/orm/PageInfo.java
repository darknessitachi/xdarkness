package com.xdarkness.framework.orm;

import java.util.List;

import com.xdarkness.framework.orm.data.DataTable;

/**
 * 
 * @author Darkness Create on Jun 2, 2010 5:36:44 PM
 * @version 1.0
 * @since JDF1.0
 */
public class PageInfo {

	List<?> objectList = null;
	DataTable dataTable = null;
	int pageCount; // 总页数
	int rowCount; // 总行数
	int pageNum; // 当前页的页码
	int pageSize; // 每页显示的记录数

	private boolean PageFlag;// 是否分页

	public List<?> getObjectList() {
		return objectList;
	}

	public void setObjectList(List<?> objectList) {
		this.objectList = objectList;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
		if ((double) pageNum > Math.ceil(((double) rowCount * 1.0D)
				/ (double) getPageSize()))
			pageNum = (new Double(Math.floor(((double) rowCount * 1.0D)
					/ (double) getPageSize()))).intValue();
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public DataTable getDataTable() {
		return dataTable;
	}

	public void setDataTable(DataTable dataTable) {
		this.dataTable = dataTable;
	}

	public boolean isPageFlag() {
		return PageFlag;
	}

	public void setPageFlag(boolean pageFlag) {
		PageFlag = pageFlag;
	}
}
