package com.abigdreamer.java.net.sql;

import java.sql.SQLException;
import java.util.ArrayList;

import com.abigdreamer.java.net.ICallbackStatement;
import com.abigdreamer.java.net.connection.DBTypes;
import com.abigdreamer.java.net.connection.XConnectionPoolManager;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.util.LogUtil;

public class QueryBuilder {

	private StringBuffer sql = new StringBuffer();

	private ArrayList<Object> params;

	private boolean batchMode;
	private ArrayList<ArrayList<Object>> batches;

	// private boolean TotalFlag = false;
	// private boolean pageFlag = false;
	private boolean isPagedQuery = false;
	private String pagedSql;
	private int pageSize;
	private int pageIndex;

	public QueryBuilder() {
		params = new ArrayList<Object>();
		this.sql = null;
	}

	public QueryBuilder(String sql) {
		this();
		setSQL(sql);
	}

	public QueryBuilder(String sql, Object... params) {
		this(sql);
		add(params);
	}

	public void setBatchMode(boolean batchMode) {
		if ((batchMode) && (this.batches == null)) {
			this.batches = new ArrayList<ArrayList<Object>>();
		}
		this.batchMode = batchMode;
	}

	public void addBatch() {
		if (!this.batchMode) {
			throw new RuntimeException("非批处理模式下不能使用addBatch()");
		}
		this.batches.add(this.params);
		this.params = new ArrayList<Object>();
	}

	public QueryBuilder add(Object... paramValues) {
		for (int i = 0; i < paramValues.length; i++) {
			params.add(paramValues[i]);
		}
		return this;
	}

	public QueryBuilder add(Object paramValue) {
		params.add(paramValue);
		return this;
	}

	public void set(int index, Object param) {
		this.params.set(index, param);
	}

	public void setSQL(String sql) {
		this.sql = new StringBuffer(sql);
	}

	public QueryBuilder append(String sqlPart) {
		this.sql.append(sqlPart);
		return this;
	}

	public QueryBuilder append(String sqlPart, Object... param) {
		this.sql.append(sqlPart);
		add(param);
		return this;
	}

	public DataTable executeDataTable() {
		IDataAccess da = new DataAccess();
		DataTable dt = null;
		try {
			dt = da.executeDataTable(getSQL(), params);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dt;
	}

	public DataTable executePagedDataTable() {
		IDataAccess da = new DataAccess();
		DataTable dt = null;
		try {
			dt = da.executeDataTable(getSQL(), params);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dt;
	}

	public Object executeOneValue() {
		IDataAccess da = new DataAccess();
		Object t = null;
		try {
			t = da.executeOneValue(getSQL(), params);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return t;
	}

	public Object execute(ICallbackStatement iCallbackStatement) {
		IDataAccess da = new DataAccess();
		Object t = null;
		try {
			t = da.executeQuery(getSQL(), params, iCallbackStatement);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return t;
	}

	public String executeString() {
		Object o = executeOneValue();
		if (o == null) {
			return null;
		}
		return o.toString();
	}

	public int executeInt() {
		Object o = executeOneValue();
		if (o == null) {
			return 0;
		}
		return Integer.parseInt(o.toString());
	}

	public long executeLong() {
		Object o = executeOneValue();
		if (o == null) {
			return 0L;
		}
		return Long.parseLong(o.toString());
	}

	public int executeNoQueryWithException() throws SQLException {
		IDataAccess da = new DataAccess();
		int t = -1;
		try {
			if (batchMode) {
				t = da.executeNoQuery(getSQL(), batches, true);
			} else {
				t = da.executeNoQuery(getSQL(), params);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return t;
	}

	public int executeNoQuery() throws SQLException {
		try {
			return executeNoQueryWithException();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}

	}

	public QueryBuilder setPagedQuery(boolean ispaged) {
		this.isPagedQuery = ispaged;
		return this;
	}

	public String getSQL() {
		if (this.isPagedQuery) {
			checkPaged(XConnectionPoolManager.getDBConnConfig().DBType);
			return this.pagedSql;
		}
		return this.sql.toString();
	}

	public void clearBatches() {
		if (this.batchMode) {
			if (this.batches != null) {
				this.batches.clear();
			}
			this.batches = new ArrayList<ArrayList<Object>>();
		}
	}

	public boolean checkParams() {
		char[] arr = this.sql.toString().toCharArray();
		boolean StringCharFlag = false;
		int count = 0;
		for (int i = 0; i < arr.length; i++) {
			char c = arr[i];
			if (c == '\'') {
				StringCharFlag = !StringCharFlag;
			} else if ((c == '?') && (!StringCharFlag)) {
				count++;
			}
		}

		if (count != this.params.size()) {
			throw new RuntimeException("SQL中含有" + count + "个参数，但有"
					+ this.params.size() + "个参数置值!");
		}
		return true;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.sql);
		sb.append("\t{");
		for (int i = 0; i < this.params.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			Object o = this.params.get(i);
			if (o == null) {
				sb.append("null");
			} else {
				String str = this.params.get(i).toString();
				if (str.length() > 40) {
					str = str.substring(0, 37);
					sb.append(str);
					sb.append("...");
				} else {
					sb.append(str);
				}
			}
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 获取PageInfo
	 * 
	 * @param PageSize
	 * @param PageIndex
	 * @return
	 */
	// public PageInfo executePageInfo() {
	//
	// PageInfo pageInfo = new PageInfo();
	//
	// if (pageFlag) {
	// if (!TotalFlag)
	// pageInfo.setRowCount(getCount());
	// pageInfo.setDataTable(this.executePagedDataTable());
	// } else {
	// pageInfo.setDataTable(this.executeDataTable());
	// }
	//
	// return pageInfo;
	// }
	public void checkPaged(DBTypes dbType) {
		if (this.isPagedQuery) {// 采取分页查询
			initPagedSQL(dbType);
		}
	}

	/**
	 * 获取分页sql
	 * 
	 * @param dbType
	 * @param qb
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	public void initPagedSQL(DBTypes dbType) {
		StringBuffer sb = new StringBuffer();
		int start = pageIndex * pageSize;
		int end = (pageIndex + 1) * pageSize;
		if (dbType == DBTypes.ORACLE) {
			sb.append("select * from (select rs.*,rownum rnm from (");
			sb.append(this.sql.toString());
			sb.append(") rs where rownum <= ?) rss where rnm > ?");
			this.add(end);
			this.add(start);
		} else if (dbType == DBTypes.DB2) {
			sb
					.append("select * from (select rs.*,rownumber() OVER () rnm from (");
			sb.append(this.sql.toString());
			sb.append(") rs) rss WHERE rnm BETWEEN ? and ?");
			this.add(start + 1);
			this.add(end);
		} else if (dbType == DBTypes.MSSQL) {
			String sql = this.sql.toString();
			SelectSQLParser sp = new SelectSQLParser();
			try {
				sp.setSQL(sql);
				sp.parse();
			} catch (Exception e) {
				e.printStackTrace();
			}
			sb.append(sp.getMSSQLPagedSQL());
			this.add(start + 1);
			this.add(end);
		} else if (dbType == DBTypes.MSSQL2000)
			sb.append(this.sql.toString());
		else if (dbType == DBTypes.MYSQL) {
			sb.append(this.sql.toString());
			sb.append(" limit ?,?");
			this.add(start);
			this.add(pageSize);
		}

		String sqlString = sb.toString();
		LogUtil.debug(sqlString + " " + this.pageIndex + "," + this.pageSize);
		this.pagedSql = sqlString;
		// this.setSQL(sqlString);
	}

	/**
	 * 查询总记录数
	 * 
	 * @param dbType
	 * @param qb
	 * @return
	 */
	public int getCount() {
		String sql = this.sql.toString().toLowerCase();
		int index1 = sql.lastIndexOf(")");
		int index2 = sql.lastIndexOf("order by");
		if (index2 > index1) {
			sql = sql.substring(0, index2);
		}

		IDataAccess da = new DataAccess();
		Object t = null;
		try {
			t = da.executeOneValue("select count(1) from (" + sql + ") t1",
					params);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Integer.parseInt(t.toString());
	}

	public StringBuffer getSql() {
		return sql;
	}

	public void setSql(StringBuffer sql) {
		this.sql = sql;
	}

	public ArrayList<Object> getParams() {
		return params;
	}

	public void setParams(ArrayList<Object> params) {
		this.params = params;
	}

	public ArrayList<ArrayList<Object>> getBatches() {
		return batches;
	}

	public void setBatches(ArrayList<ArrayList<Object>> batches) {
		this.batches = batches;
	}

	public String getPagedSql() {
		return pagedSql;
	}

	public void setPagedSql(String pagedSql) {
		this.pagedSql = pagedSql;
	}

	public int getPageSize() {
		return pageSize;
	}

	public QueryBuilder setPageSize(int pageSize) {
		this.isPagedQuery = true;
		this.pageSize = pageSize;
		return this;
	}

	public int getPageIndex() {
		this.isPagedQuery = true;
		return pageIndex;
	}

	public QueryBuilder setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		return this;
	}

	public boolean isBatchMode() {
		return batchMode;
	}

	public boolean isPagedQuery() {
		return isPagedQuery;
	}

	public DataTable executePagedDataTable(int pageSize, int pageIndex) {
		return this.setPageSize(pageSize).setPageIndex(pageIndex)
				.executePagedDataTable();
	}
}
