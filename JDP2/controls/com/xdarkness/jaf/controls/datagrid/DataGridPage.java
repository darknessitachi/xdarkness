package com.xdarkness.jaf.controls.datagrid;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xdarkness.common.data.DataTable;
import com.xdarkness.common.util.DateUtil;
import com.xdarkness.common.util.Mapx;
import com.xdarkness.common.util.ServletUtil;
import com.xdarkness.common.util.StringUtil;
import com.xdarkness.jaf.Current;
import com.xdarkness.jaf.Page;
import com.xdarkness.jaf.controls.Constant;
import com.xdarkness.jaf.controls.HtmlUtil;
import com.xdarkness.jaf.controls.html.HtmlTR;
import com.xdarkness.jaf.controls.html.HtmlTable;

public class DataGridPage extends Page {

	public DataGridPage() {
	}

	public void doWork() {
		try {
			DataGridAction dga = new DataGridAction();
			dga.PageInfo = new PageInfo();
			dga.setTagBody(StringUtil.htmlDecode($V("_SKY_TAGBODY")));
			String method = $V("_SKY_METHOD");
			dga.setMethod(method);
			dga.setID($V("_SKY_ID"));
			dga.PageInfo.setPageFlag("true".equalsIgnoreCase($V("_SKY_PAGE")));
			dga.setMultiSelect(!"false"
					.equalsIgnoreCase($V("_SKY_MULTISELECT")));
			dga.setAutoFill(!"false".equalsIgnoreCase($V("_SKY_AUTOFILL")));
			dga.setScroll("true".equalsIgnoreCase($V("_SKY_SCROLL")));
			dga.setLazy("true".equalsIgnoreCase($V("_SKY_LAZY")));
			if (StringUtil.isNotEmpty($V("_SKY_CACHESIZE")))
				dga.setCacheSize(Integer.parseInt($V("_SKY_CACHESIZE")));
			dga.setParams(Current.getRequest());
			dga.Response = Current.getResponse();
			if (dga.PageInfo.isPageFlag()) {
			    dga.PageInfo.setPageNum(0);
				if (Request.get("_SKY_PAGEINDEX") != null
						&& !Request.get("_SKY_PAGEINDEX").equals(""))
				    dga.PageInfo.setPageNum(Integer.parseInt(Request.get(
							"_SKY_PAGEINDEX").toString()));
				if (dga.PageInfo.getPageNum() < 0)
				    dga.PageInfo.setPageNum(0);
				dga.PageInfo.setPageSize(Integer.parseInt($V("_SKY_SIZE")));
			}
			HtmlTable table = new HtmlTable();
			table.parseHtml(dga.getTagBody());
			dga.setTemplate(table);
			dga.parse();
			String strInsertRowIndex = Request.getString("_SKY_INSERTROW");
			if (StringUtil.isNotEmpty(strInsertRowIndex)) {
				DataTable dt = (DataTable) Request.get("_SKY_DataTable");
				Request.remove("_SKY_DataTable");
				Request.remove("_SKY_INSERTROW");
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
			if (tagBody != null && !tagBody.equals(""))
				tagBody = StringUtil.htmlDecode(tagBody);
			DataGridAction dga = new DataGridAction();dga.PageInfo = new PageInfo();
			HtmlTable table = new HtmlTable();
			dga.setMethod(method);
			dga.setID(ID);
			dga.setTagBody(tagBody);
			if ("1".equals(excelPageFlag)) {
				if ("true".equals(pageFlag)) {
				    dga.PageInfo.setPageFlag(true);
				    dga.PageInfo.setPageNum(0);
					dga.PageInfo.setPageSize(Integer.parseInt(pageTotal));
				}
			} else if ("true".equals(pageFlag)) {
			    dga.PageInfo.setPageFlag(true);
			    dga.PageInfo.setPageNum(StringUtil.isEmpty(pageIndex) ? 0 : Integer
						.parseInt(pageIndex));
				dga.PageInfo.setPageSize(StringUtil.isEmpty(pageSize) ? 0 : Integer
						.parseInt(pageSize));
			}
			table.parseHtml(dga.getTagBody());
			dga.setTemplate(table);
			dga.parse();
			OutputStream os = response.getOutputStream();
			Current.init(request, response, method);
			Mapx map = Current.getRequest();
			Object ks[] = map.keyArray();
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
			String rows[] = (String[]) null;
			if (StringUtil.isNotEmpty(strRows))
				rows = strRows.split(",");
			HtmlTable ht = dga.getTable();
			if (ht.getChildren().size() > 0
					&& "blank".equalsIgnoreCase(ht.getTR(
							ht.getChildren().size() - 1).getAttribute("xtype")))
				ht.removeTR(ht.getChildren().size() - 1);
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
//		dgp.bindData(new QueryBuilder((String) dgp.getParams().get(
//				"_SKY_DATAGRID_SQL")).executePageInfo());
		throw new RuntimeException("未实现");
	}
}
