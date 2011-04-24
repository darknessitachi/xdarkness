package com.xdarkness.framework.jaf.controls.grid;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xdarkness.framework.Constant;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.jaf.Current;
import com.xdarkness.framework.jaf.controls.HtmlTR;
import com.xdarkness.framework.jaf.controls.HtmlTable;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.DateUtil;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.ServletUtil;
import com.xdarkness.framework.util.XString;

public class DataGridPage extends Ajax {
	public void doWork() {
		try {
			DataGridAction dga = new DataGridAction();

			dga.setTagBody(XString.htmlDecode($V("_SKY_TAGBODY")));
			String method = $V("_SKY_METHOD");
			dga.setMethod(method);

			dga.setID($V("_SKY_ID"));
			dga.setPageFlag("true".equalsIgnoreCase($V("_SKY_PAGE")));
			dga.setMultiSelect(!"false"
					.equalsIgnoreCase($V("_SKY_MULTISELECT")));
			dga.setAutoFill(!"false".equalsIgnoreCase($V("_SKY_AUTOFILL")));
			dga.setScroll("true".equalsIgnoreCase($V("_SKY_SCROLL")));
			dga.setLazy("true".equalsIgnoreCase($V("_SKY_LAZY")));
			if (XString.isNotEmpty($V("_SKY_CACHESIZE"))) {
				dga.setCacheSize(Integer.parseInt($V("_SKY_CACHESIZE")));
			}
			dga.setParams(Current.getRequest());
			dga.Response = Current.getResponse();

			if (dga.isPageFlag()) {
				dga.setPageIndex(0);
				if ((this.request.get("_SKY_PAGEINDEX") != null)
						&& (!this.request.get("_SKY_PAGEINDEX").equals(""))) {
					dga.setPageIndex(Integer.parseInt(this.request
							.getString("_SKY_PAGEINDEX")));
				}
				if (dga.getPageIndex() < 0) {
					dga.setPageIndex(0);
				}
				if (dga.getPageIndex() != 0) {
					dga.setTotal(Integer.parseInt(this.request
							.getString("_SKY_PAGETOTAL")));
				}
				dga.setPageSize(Integer.parseInt($V("_SKY_SIZE")));
			}

			HtmlTable table = new HtmlTable();
			table.parseHtml(dga.getTagBody());
			dga.setTemplate(table);
			dga.parse();

			String strInsertRowIndex = this.request
					.getString("_SKY_INSERTROW");
			if (XString.isNotEmpty(strInsertRowIndex)) {
				DataTable dt = (DataTable) this.request.get("_SKY_DataTable");
				this.request.remove("_SKY_DataTable");
				this.request.remove("_SKY_INSERTROW");
				dga.bindData(dt);

				HtmlTR tr = dga.getTable().getTR(1);
				$S("TRAttr", tr.getAttributes());
				for (int i = 0; i < tr.Children.size(); i++) {
					$S("TDAttr" + i, tr.getTD(i).getAttributes());
					$S("TDHtml" + i, tr.getTD(i).getInnerHTML());
				}
			} else {
				Current.invokeMethod(method, new Object[] { dga });
				$S("HTML", dga.getHtml());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void toExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constant.GlobalCharset);
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=Excel_"
				+ DateUtil.getCurrentDateTime("yyyyMMddhhmmss") + ".xls");
		try {
			String xls = "_Excel_";
			Mapx params = ServletUtil.getParameterMap(request);
			String ID = params.getString(xls + "_SKY_ID");
			String tagBody = params.getString(xls + "_SKY_TAGBODY");
			String pageIndex = params.getString(xls + "_SKY_PAGEINDEX");
			String pageSize = params.getString(xls + "_SKY_SIZE");
			String pageTotal = params.getString(xls + "_SKY_PAGETOTAL");
			String method = params.getString(xls + "_SKY_METHOD");
			String pageFlag = params.getString(xls + "_SKY_PAGE");
			String excelPageFlag = params.getString(xls
					+ "_SKY_ToExcelPageFlag");
			String strWidths = params.getString(xls + "_SKY_Widths");
			String strIndexes = params.getString(xls + "_SKY_Indexes");
			String strRows = params.getString(xls + "_SKY_Rows");

			if ((tagBody != null) && (!tagBody.equals(""))) {
				tagBody = XString.htmlDecode(tagBody);
			}
			DataGridAction dga = new DataGridAction();
			HtmlTable table = new HtmlTable();
			dga.setMethod(method);
			dga.setID(ID);
			dga.setTagBody(tagBody);
			if ("1".equals(excelPageFlag)) {
				if ("true".equals(pageFlag)) {
					dga.setPageFlag(true);
					dga.setPageIndex(0);
					dga.setPageSize(Integer.parseInt(pageTotal));
				}

			} else if ("true".equals(pageFlag)) {
				dga.setPageFlag(true);
				dga.setPageIndex(XString.isEmpty(pageIndex) ? 0 : Integer
						.parseInt(pageIndex));
				dga.setPageSize(XString.isEmpty(pageSize) ? 0 : Integer
						.parseInt(pageSize));
			}

			table.parseHtml(dga.getTagBody());
			dga.setTemplate(table);
			dga.parse();

			OutputStream os = response.getOutputStream();

			Current.init(request, response, method);
			Mapx map = Current.getRequest();
			Object[] ks = map.keyArray();
			for (int i = 0; i < ks.length; i++) {
				String k = ks[i].toString();
				if (k.startsWith(xls)) {
					Object v = map.get(k);
					map.remove(k);
					map.put(k.substring(xls.length()), v);
				}
			}
			dga.setParams(map);
			dga.Response = Current.getResponse();
			Current.invokeMethod(method, new Object[] { dga });

			String[] rows = (String[]) null;
			if (XString.isNotEmpty(strRows)) {
				rows = strRows.split(",");
			}

			HtmlTable ht = dga.getTable();
			if ((ht.getChildren().size() > 0)
					&& ("blank".equalsIgnoreCase(ht.getTR(
							ht.getChildren().size() - 1).getAttribute("xtype")))) {
				ht.removeTR(ht.getChildren().size() - 1);
			}
			HtmlUtil.htmlTableToExcel(os, ht, strWidths.split(","), strIndexes
					.split(","), rows);

			os.flush();
			os.close();

			os = null;
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sqlBind(DataGridAction dgp) {
		dgp.bindData(new QueryBuilder((String) dgp.getParams().get(
				"_SKY_DATAGRID_SQL")));
	}
}

/*
 * com.xdarkness.framework.controls.DataGridPage JD-Core Version: 0.6.0
 */