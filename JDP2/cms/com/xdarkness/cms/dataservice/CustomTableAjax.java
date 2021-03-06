package com.xdarkness.cms.dataservice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCCustomTableColumnSchema;
import com.xdarkness.schema.ZCCustomTableColumnSet;
import com.xdarkness.schema.ZCCustomTableSchema;
import com.xdarkness.schema.ZCCustomTableSet;
import com.xdarkness.framework.jaf.controls.DataListAction;
import com.xdarkness.framework.jaf.controls.HtmlTD;
import com.xdarkness.framework.jaf.controls.HtmlTR;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.DataAccess;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.LogUtil;

public class CustomTableAjax extends Ajax {
	public void dataBindAllColumns(DataListAction dla) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setSiteID($V("SiteID"));
		table.setCode($V("TableCode"));
		ZCCustomTableSet set = table.query();
		if ((set == null) || (set.size() < 1)) {
			LogUtil.warn("ID为" + table.getSiteID() + "的站点下没有代码为"
					+ table.getCode() + "的表!");
			return;
		}
		table = set.get(0);
		if (!"Y".equals(table.getAllowView())) {
			LogUtil.warn("ID为" + table.getSiteID() + "的站点下代码为"
					+ table.getCode() + "的表不允许前台查看!");
			return;
		}
		DataTable dt = CustomTableUtil.getData(set.get(0), null, dla
				.getPageSize(), dla.getPageIndex());
		ZCCustomTableColumnSet cset = new ZCCustomTableColumnSchema()
				.query(new QueryBuilder("where TableID=?", table.getID()));
		HtmlTR tr = new HtmlTR();
		ArrayList list = new ArrayList();
		for (int i = 0; i < cset.size(); i++) {
			HtmlTD td = new HtmlTD();
			td.setInnerHTML(cset.get(i).getName());
			tr.addTD(td);
		}
		list.add(tr);
		for (int i = 0; i < dt.getRowCount(); i++) {
			tr = new HtmlTR();
			for (int j = 0; j < dt.getColCount(); j++) {
				HtmlTD td = new HtmlTD();
				td.setInnerHTML(dt.getString(i, j));
				tr.addTD(td);
			}
			list.add(tr);
		}
		dt = new DataTable();
		dt.insertColumn("RowHTML");
		for (int i = 0; i < list.size(); i++) {
			tr = (HtmlTR) list.get(i);
			dt.insertRow(new Object[] { tr.getOuterHtml() });
		}
		dla.setTotal(CustomTableUtil.getTotal(table, "where 1=1"));
		dla.bindData(dt);
	}

	public void dataBindSpecifiedColumns(DataListAction dla) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setSiteID($V("SiteID"));
		table.setCode($V("TableCode"));
		ZCCustomTableSet set = table.query();
		if ((set == null) || (set.size() < 1)) {
			LogUtil.warn("ID为" + table.getSiteID() + "的站点下没有代码为"
					+ table.getCode() + "的表!");
			return;
		}
		table = set.get(0);
		if (!"Y".equals(table.getAllowView())) {
			LogUtil.warn("ID为" + table.getSiteID() + "的站点下代码为"
					+ table.getCode() + "的表不允许前台查看!");
			return;
		}
		DataTable dt = CustomTableUtil.getData(set.get(0), null, dla
				.getPageSize(), dla.getPageIndex());
		dla.setTotal(CustomTableUtil.getTotal(table, "where 1=1"));
		dla.bindData(dt);
	}

	public void processSubmit() {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID($V("_TableID"));
		if (!table.fill()) {
			LogUtil.warn("没有ID为" + table.getCode() + "的表!");
			return;
		}
		if ((!"Y".equals(table.getAllowModify())) && (!User.isManager())) {
			LogUtil.warn("ID为" + table.getID() + "的表不允许前台修改!");
			return;
		}

		DataAccess da = null;
		String code = table.getCode();
		if (table.getType().equals("Link")) {
			da = new DataAccess(OuterDatabase.getConnection(table
					.getDatabaseID()));
			code = table.getOldCode();
		} else {
			da = new DataAccess();
		}
		try {
			da.setAutoCommit(false);
			ZCCustomTableColumnSet set = new ZCCustomTableColumnSchema()
					.query(new QueryBuilder("where TableID=?", table.getID()));
			StringBuffer sb = new StringBuffer("insert into " + code + "(");
			for (int j = 0; j < set.size(); j++) {
				if (j != 0) {
					sb.append(",");
				}
				sb.append(set.get(j).getCode());
			}
			sb.append(") values (");
			for (int j = 0; j < set.size(); j++) {
				if (j != 0) {
					sb.append(",");
				}
				sb.append("?");
			}
			sb.append(")");
			QueryBuilder qb = new QueryBuilder(sb.toString());
			StringBuffer messageSB = new StringBuffer();
			for (int j = 0; j < set.size(); j++) {
				ZCCustomTableColumnSchema column = set.get(j);
				String v = $V("_Form_" + set.get(j).getCode());
				if (XString.isEmpty(v)) {
					v = null;
					if ("Y".equals(set.get(j).getIsAutoID())) {
						v = String.valueOf(OrderUtil.getDefaultOrder());
					}
				}
				if ((("Y".equals(column.getIsMandatory())) || ("Y"
						.equals(column.getIsPrimaryKey())))
						&& (XString.isEmpty(v))) {
					messageSB.append(column.getName() + "不能为空!\n");
				}

				int dataType = Integer.parseInt(column.getDataType());
				if (v != null) {
					if ((column.getMaxLength() != 0)
							&& (v.length() < column.getMaxLength()))
						messageSB.append(column.getName() + "数据过长，最大允许"
								+ column.getMaxLength() + "个字符!\n");
					try {
						if (dataType == 0) {
							v = DateUtil.toDateTimeString(DateUtil
									.parseDateTime(v));
							if (v == null) {
								throw new SQLException("日期时间错误");
							}
						}
						if ((dataType == 8) || (dataType == 9)) {
							v = String
									.valueOf(new Double(Double.parseDouble(v))
											.intValue());
						}
						if (dataType == 7) {
							v = String
									.valueOf(new Double(Double.parseDouble(v))
											.longValue());
						}
						if (dataType == 5) {
							v = String
									.valueOf(new Double(Double.parseDouble(v))
											.floatValue());
						}
						if ((dataType == 4) || (dataType == 6)
								|| (dataType == 3))
							v = String.valueOf(Double.parseDouble(v));
					} catch (Exception e) {
						messageSB.append(column.getName() + "数据不正确!\n");
					}
				}
				qb.add(v);
			}
			if (messageSB.length() != 0) {
				this.response.setError(messageSB.toString());
			} else {
				qb.executeNoQuery();
				da.commit();
				this.response.setMessage("提交成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				da.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			this.response.setMessage("提交失败:" + e.getMessage());
			try {
				da.setAutoCommit(true);
				da.close();
			} catch (SQLException xe) {
				e.printStackTrace();
			}
		} finally {
			try {
				da.setAutoCommit(true);
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void processSubmit(HttpServletRequest request,
			HttpServletResponse response) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(request.getParameter("_TableID"));
		if (!table.fill()) {
			LogUtil.warn("没有ID为" + table.getCode() + "的表!");
			return;
		}
		if ((!"Y".equals(table.getAllowModify())) && (!User.isManager())) {
			LogUtil.warn("ID为" + table.getID() + "的表不允许前台修改!");
			return;
		}

		DataAccess da = null;
		String code = table.getCode();
		if (table.getType().equals("Link")) {
			da = new DataAccess(OuterDatabase.getConnection(table
					.getDatabaseID()));
			code = table.getOldCode();
		} else {
			da = new DataAccess();
		}
		try {
			da.setAutoCommit(false);
			ZCCustomTableColumnSet set = new ZCCustomTableColumnSchema()
					.query(new QueryBuilder("where TableID=?", table.getID()));
			StringBuffer insertSB = new StringBuffer("insert into " + code
					+ "(");
			QueryBuilder deleteQB = new QueryBuilder("delete from  " + code
					+ " where 1=1 ");
			for (int j = 0; j < set.size(); j++) {
				if (j != 0) {
					insertSB.append(",");
				}
				insertSB.append(set.get(j).getCode());
			}
			insertSB.append(") values (");
			for (int j = 0; j < set.size(); j++) {
				if (j != 0) {
					insertSB.append(",");
				}
				insertSB.append("?");
				if ("Y".equals(set.get(j).getIsPrimaryKey())) {
					String v = request.getParameter("_Form_"
							+ set.get(j).getCode() + "_Old");
					deleteQB.append(" and " + set.get(j).getCode() + "=?", v);
				}
			}
			insertSB.append(")");
			QueryBuilder qb = new QueryBuilder(insertSB.toString());
			StringBuffer messageSB = new StringBuffer();
			for (int j = 0; j < set.size(); j++) {
				ZCCustomTableColumnSchema column = set.get(j);
				String v = request
						.getParameter("_Form_" + set.get(j).getCode());
				if (XString.isEmpty(v)) {
					v = null;
					if ("Y".equals(set.get(j).getIsAutoID())) {
						v = String.valueOf(OrderUtil.getDefaultOrder());
					}
				}
				if ((("Y".equals(column.getIsMandatory())) || ("Y"
						.equals(column.getIsPrimaryKey())))
						&& (XString.isEmpty(v))) {
					messageSB.append(column.getName() + "不能为空!\\n");
				}

				int dataType = Integer.parseInt(column.getDataType());
				if (v != null) {
					if ((column.getMaxLength() != 0)
							&& (v.length() < column.getMaxLength()))
						messageSB.append(column.getName() + "数据过长，最大允许"
								+ column.getMaxLength() + "个字符!\\n");
					try {
						if (dataType == 0) {
							v = DateUtil.toDateTimeString(DateUtil
									.parseDateTime(v));
							if (v == null) {
								throw new SQLException("日期时间错误");
							}
						}
						if ((dataType == 8) || (dataType == 9)) {
							v = String
									.valueOf(new Double(Double.parseDouble(v))
											.intValue());
						}
						if (dataType == 7) {
							v = String
									.valueOf(new Double(Double.parseDouble(v))
											.longValue());
						}
						if (dataType == 5) {
							v = String
									.valueOf(new Double(Double.parseDouble(v))
											.floatValue());
						}
						if ((dataType == 4) || (dataType == 6)
								|| (dataType == 3))
							v = String.valueOf(Double.parseDouble(v));
					} catch (Exception e) {
						messageSB.append(column.getName() + "数据不正确!\\n");
					}
				}
				qb.add(v);
			}
			if (messageSB.length() != 0) {
				insertSB = new StringBuffer();
				insertSB.append("<script>");
				insertSB.append("alert(\"" + messageSB + "\");");
				insertSB.append("history.go(-1);");
				insertSB.append("</script>");
				response.getWriter().print(insertSB);
			} else {
				deleteQB.executeNoQuery();
				qb.executeNoQuery();
				da.commit();
				insertSB = new StringBuffer();
				insertSB.append("<script>");
				insertSB.append("alert(\"提交成功!\");");
				insertSB.append("window.location=\""
						+ request.getHeader("referer") + "\";");
				insertSB.append("</script>");
				response.getWriter().print(insertSB);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				da.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			StringBuffer sb = new StringBuffer();
			sb.append("<script>");
			sb.append("alert(\"提交失败!\");");
			sb.append("history.go(-1);");
			sb.append("</script>");
			try {
				response.getWriter().print(sb);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				da.setAutoCommit(true);
				da.close();
			} catch (SQLException xe) {
				e.printStackTrace();
			}
		} finally {
			try {
				da.setAutoCommit(true);
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

/*
 * com.xdarkness.cms.dataservice.CustomTableAjax JD-Core Version: 0.6.0
 */