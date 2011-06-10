package com.abigdreamer.java.net.jaf.controls.grid;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.abigdreamer.java.net.Config;
import com.abigdreamer.java.net.jaf.Constant;
import com.abigdreamer.java.net.jaf.JafResponse;
import com.abigdreamer.java.net.jaf.WebConfig;
import com.abigdreamer.java.net.jaf.controls.IControlAction;
import com.abigdreamer.java.net.jaf.html.element.HtmlScript;
import com.abigdreamer.java.net.jaf.html.element.HtmlTD;
import com.abigdreamer.java.net.jaf.html.element.HtmlTR;
import com.abigdreamer.java.net.jaf.html.element.HtmlTable;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataCollection;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.orm.data.DataRow;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.orm.data.DataTableTree;
import com.abigdreamer.java.net.orm.data.DataTableUtil;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.LogUtil;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.Treex;
import com.abigdreamer.java.net.util.XString;

public class DataGridAction implements IControlAction {
	protected String ID;
	private boolean MultiSelect = true;

	private boolean AutoFill = true;

	private boolean Scroll = false;

	private boolean Lazy = false;
	private int cacheSize;
	protected HtmlTable Template;
	protected int PageSize;
	protected int PageIndex;
	private int Total;
	private boolean PageFlag;
	protected boolean SortFlag;
	protected boolean TreeFlag;
	protected boolean WebMode = true;

	protected StringBuffer SortString = new StringBuffer();
	protected String Method;
	protected String TagBody;
	protected DataTable DataSource;
	protected HtmlTable Table;
	protected Mapx Params = new Mapx();
	public JafResponse Response;
	ArrayList a1 = new ArrayList();

	ArrayList a2 = new ArrayList();
	String style1;
	String style2;
	String class1;
	String class2;
	String templateHtml;
	HtmlTR headTR = null;

	HtmlTR templateTR = null;

	HtmlTR editTR = null;

	HtmlTR pageBarTR = null;

	boolean isSimplePageBar = false;

	boolean TotalFlag = false;

	private static Pattern sortPattern = Pattern.compile("[\\w\\,\\s]*", 34);

	public void parse() throws Exception {
		this.Table = new HtmlTable();
		this.Table.setAttributes(this.Template.getAttributes());

		for (int i = 0; i < this.Template.Children.size(); i++) {
			HtmlTR tr = (HtmlTR) this.Template.Children.get(i);

			if ("Head".equalsIgnoreCase(tr.getAttribute("xtype"))) {
				this.headTR = tr;
			} else if ("PageBar".equalsIgnoreCase(tr.getAttribute("xtype"))) {
				this.pageBarTR = tr;
			} else if ("SimplePageBar".equalsIgnoreCase(tr
					.getAttribute("xtype"))) {
				this.pageBarTR = tr;
				this.isSimplePageBar = true;
			} else if ("Edit".equalsIgnoreCase(tr.getAttribute("xtype"))) {
				this.editTR = tr;
				this.editTR.setAttribute("style", "display:none");
			} else {
				this.style1 = tr.getAttribute("style1");
				this.style2 = tr.getAttribute("style2");
				this.class1 = tr.getAttribute("class1");
				this.class2 = tr.getAttribute("class2");
				tr.removeAttribute("style1");
				tr.removeAttribute("style2");
				tr.removeAttribute("class1");
				tr.removeAttribute("class2");
				this.templateTR = tr;
			}
		}
		if (this.headTR == null) {
			this.headTR = this.Template.getTR(0);
		}
		this.Table.addTR(this.headTR);

		String str = (String) this.Params.get("_SKY_SORTSTRING");
		boolean firstSortFieldFlag = true;
		boolean emptyFlag = true;
		Mapx sortMap = new Mapx();
		if ((XString.isNotEmpty(str)) && (XString.verify(str, "String"))) {
			if ("_SKY_NULL".equals(str)) {
				str = "";
			}
			this.SortString.append(str);
			String[] arr = str.split("\\,");
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].indexOf(' ') > 0) {
					String[] arr2 = arr[i].split("\\s");
					sortMap.put(arr2[0].trim().toLowerCase(), arr2[1].trim());
				} else {
					sortMap.put(arr[i].trim().toLowerCase(), "");
				}
			}
			emptyFlag = false;
		}
		for (int i = 0; i < this.headTR.Children.size(); i++) {
			HtmlTD td = this.headTR.getTD(i);

			String xtype = td.getAttribute("xtype");
			if ("Tree".equalsIgnoreCase(xtype)) {
				this.TreeFlag = true;
			}
			String sortField = td.getAttribute("sortField");
			String direction = td.getAttribute("direction");
			if (XString.isNotEmpty(sortField)) {
				this.SortFlag = true;
				if (emptyFlag) {
					if (XString.isNotEmpty(direction)) {
						if (!firstSortFieldFlag) {
							this.SortString.append(",");
						}
						this.SortString.append(sortField);
						this.SortString.append(" ");
						this.SortString.append(direction);
						firstSortFieldFlag = false;
					} else {
						direction = "";
					}
				} else {
					direction = (String) sortMap.get(sortField.toLowerCase());
					if (XString.isEmpty(direction)) {
						direction = "";
					}
					td.setAttribute("direction", direction);
				}
			}
			if (XString.isNotEmpty(sortField)) {
				td.setAttribute("style", "cursor:pointer");
				td.setAttribute("onMouseOver",
						"DataGrid.onSortHeadMouseOver(this);");
				td.setAttribute("onMouseOut",
						"DataGrid.onSortHeadMouseOut(this);");
				td.setAttribute("onClick", "DataGrid.onSort(this);");
				StringBuffer sb = new StringBuffer();
				sb.append("<span style='float:left'>");
				sb.append(td.getInnerHTML());
				sb.append("</span>");
				sb.append("<img src='");
				sb.append(WebConfig.getContextPath());
				sb.append("Framework/Images/icon_sort");
				sb.append(direction.toUpperCase());
				sb.append(".gif' width='12' height='12' style='float:right'>");
				td.setInnerHTML(sb.toString());
			}
		}

		this.Table.setAttribute("SortString", this.SortString.toString());

		this.Table.setAttribute("id", this.ID);
		this.Table.setAttribute("page", this.PageFlag);
		this.Table.setAttribute("size", this.PageSize);
		this.Table.setAttribute("method", this.Method);
		this.Table.setAttribute("multiSelect", this.MultiSelect);
		this.Table.setAttribute("autoFill", this.AutoFill);
		this.Table.setAttribute("scroll", this.Scroll);
		this.Table.setAttribute("Lazy", this.Lazy);
		this.Table.setAttribute("cacheSize", this.cacheSize);

		this.templateHtml = this.templateTR.getOuterHtml();
		if (this.templateHtml == null) {
			throw new RuntimeException("DataGrid不能没有模板行");
		}

		Matcher m = Constant.PatternField.matcher(this.templateHtml);
		int lastEndIndex = 0;

		while (m.find(lastEndIndex)) {
			this.a1.add(this.templateHtml.substring(lastEndIndex, m.start()));
			this.a2.add(m.group(1));
			lastEndIndex = m.end();
		}
		this.a1.add(this.templateHtml.substring(lastEndIndex));
	}

	private void bindData() throws Exception {
		if (!this.PageFlag) {
			this.Total = this.DataSource.getRowCount();
		}

		if ((this.TreeFlag)
				&& (this.DataSource.getDataColumn("ParentID") != null)
				&& (this.DataSource.getDataColumn("ID") != null)) {
			this.DataSource = sortTreeDataTable(this.DataSource, "ID",
					"ParentID");
		}

		if (this.DataSource == null) {
			throw new RuntimeException("必须在bindData方法中设定DataSource");
		}

		if (this.DataSource.getDataColumn("_RowNo") == null) {
			this.DataSource.insertColumn(new DataColumn("_RowNo", 8));
		}
		for (int j = 0; j < this.DataSource.getRowCount(); j++) {
			int rowNo = this.PageIndex * this.PageSize + j + 1;
			this.DataSource.set(j, "_RowNo", new Integer(rowNo));
		}

		for (int i = 0; i < this.DataSource.getRowCount(); i++) {
			DataRow dr = this.DataSource.getDataRow(i);
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < this.a1.size(); j++) {
				sb.append(this.a1.get(j));
				if (j < this.a2.size()) {
					sb.append(dr.getString(this.a2.get(j).toString()));
				}
			}
			HtmlTR tr = new HtmlTR(this.Table);
			tr.parseHtml(sb.toString());
			if (i % 2 == 1) {
				if (this.style1 != null) {
					tr.setAttribute("style", this.style1);
				}
				if (this.class1 != null)
					tr.setAttribute("class", this.class1);
			} else {
				if (this.style2 != null) {
					tr.setAttribute("style", this.style2);
				}
				if (this.class2 != null) {
					tr.setAttribute("class", this.class2);
				}
			}
			String clickEvent = tr.getAttribute("onclick");
			if (XString.isEmpty(clickEvent)) {
				clickEvent = "";
			}
			tr.setAttribute("onclick", "DataGrid.onRowClick(this,event);"
					+ clickEvent);
			String dblEvent = tr.getAttribute("ondblclick");
			if (XString.isNotEmpty(dblEvent))
				tr.setAttribute("ondblclick", dblEvent);
			else if (this.editTR != null) {
				tr.setAttribute("ondblclick", "DataGrid.editRow(this)");
			}
			tr.setAttribute("oncontextmenu",
					"DataGrid._onContextMenu(this,event)");

			this.Table.addTR(tr);
		}

		for (int i = 0; i < this.headTR.Children.size(); i++) {
			boolean rowNoFlag = "RowNo".equalsIgnoreCase(this.headTR.getTD(i)
					.getAttribute("xtype"));
			for (int j = 1; j < this.Table.Children.size(); j++) {
				int rowNo = this.PageIndex * this.PageSize + j;
				if (rowNoFlag) {
					this.Table.getTR(j).getTD(i).setInnerHTML(rowNo + "");
					this.Table.getTR(j).getTD(i).setAttribute("rowno", rowNo);
				}

			}

			if ("Selector".equalsIgnoreCase(this.headTR.getTD(i).getAttribute(
					"xtype"))) {
				String field = this.headTR.getTD(i).getAttribute("field");
				String onSelect = this.headTR.getTD(i).getAttribute("onselect");
				if (onSelect == null) {
					onSelect = "";
				}
				if (this.MultiSelect) {
					this.headTR
							.getTD(i)
							.setInnerHTML(
									"<input type='checkbox' value='*' id='"
											+ this.ID
											+ "_AllCheck' onclick=\"DataGrid.onAllCheckClick('"
											+ this.ID + "')\"/>");
				}
				String type = this.MultiSelect ? "checkbox" : "radio";
				for (int j = 1; j < this.Table.Children.size(); j++) {
					this.Table.getTR(j).getTD(i).setInnerHTML(
							"<input type='" + type + "' name='" + this.ID
									+ "_RowCheck' id='" + this.ID + "_RowCheck"
									+ j + "' value='"
									+ this.DataSource.getString(j - 1, field)
									+ "'>");
					this.Table.getTR(j).getTD(i).setAttribute("onclick",
							"DataGrid.onSelectorClick(this,event);" + onSelect);
					this.Table.getTR(j).getTD(i).setAttribute("ondblclick",
							"stopEvent(event);" + onSelect);
				}
			}

			if ("Checkbox".equalsIgnoreCase(this.headTR.getTD(i).getAttribute(
					"xtype"))) {
				String field = this.headTR.getTD(i).getAttribute("field");
				String checkedvalue = this.headTR.getTD(i).getAttribute(
						"checkedvalue");
				String disabled = this.headTR.getTD(i).getAttribute("disabled");
				if (checkedvalue == null) {
					checkedvalue = "Y";
				}
				if ((disabled == null) || (disabled.equalsIgnoreCase("true")))
					disabled = "disabled";
				else {
					disabled = "";
				}
				for (int j = 1; j < this.Table.Children.size(); j++) {
					String checked = checkedvalue.equals(this.DataSource
							.getString(j - 1, field)) ? "checked" : "";
					this.Table.getTR(j).getTD(i).setInnerHTML(
							"<input type='checkbox' " + disabled + " name='"
									+ this.ID + "_" + field + "_Checkbox' id='"
									+ this.ID + "_" + field + "_Checkbox" + j
									+ "' value='" + checkedvalue + "' "
									+ checked + ">");
				}
			}

			if ("DropDownList".equalsIgnoreCase(this.headTR.getTD(i)
					.getAttribute("xtype"))) {
				String field = this.headTR.getTD(i).getAttribute("field");
				String sql = this.headTR.getTD(i).getAttribute("sql");
				String zstyle = this.headTR.getTD(i).getAttribute("zstyle");
				if (XString.isEmpty(zstyle)) {
					zstyle = "width:100px";
				}
				DataTable dt = new QueryBuilder(sql).executeDataTable();
				for (int j = 1; j < this.Table.Children.size(); j++) {
					StringBuffer sb = new StringBuffer();
					sb
							.append("<div xtype='select' disabled='true' style='display:none;"
									+ zstyle
									+ ";' name='"
									+ this.ID
									+ "_"
									+ field
									+ "_DropDownList"
									+ j
									+ "' id='"
									+ this.ID
									+ "_"
									+ field
									+ "_DropDownList"
									+ j + "' >");
					for (int k = 0; k < dt.getRowCount(); k++) {
						String value = this.DataSource.getString(j - 1, field);
						String selected = "";
						if (value.equals(dt.getString(k, 0))) {
							selected = "selected='true'";
						}
						sb.append("<span value='" + dt.getString(k, 0) + "' "
								+ selected + ">" + dt.getString(k, 1)
								+ "</span>");
					}
					sb.append("</div>");
					this.Table.getTR(j).getTD(i).setInnerHTML(sb.toString());
				}
			}

			if ("Tree".equalsIgnoreCase(this.headTR.getTD(i).getAttribute(
					"xtype"))) {
				String field = this.headTR.getTD(i).getAttribute("field");

				String str_level = this.headTR.getTD(i).getAttribute("level");

				for (int j = 1; j < this.Table.Children.size(); j++) {
					int level = Integer.parseInt(this.DataSource.getString(
							j - 1, str_level));

					StringBuffer sb = new StringBuffer();
					for (int k = 0; k < level; k++) {
						sb.append("<q style='padding:0 10px'></q>");
					}
					int nextLevel = 0;
					if (j != this.Table.Children.size() - 1) {
						nextLevel = Integer.parseInt(this.DataSource.getString(
								j, str_level));
					}
					if (level < nextLevel)
						sb
								.append("<img src='"
										+ WebConfig.getContextPath()
										+ "Framework/Images/butExpand.gif' onclick='DataGrid.treeClick(this)'/>&nbsp;");
					else {
						sb.append("<img src='" + WebConfig.getContextPath()
								+ "Framework/Images/butNoChild.gif'/>&nbsp;");
					}
					if (field != null) {
						String treeID = this.DataSource.getString(j - 1, field);
						sb.append("<input type='checkbox'  name='" + this.ID
								+ "_TreeRowCheck' id='" + this.ID
								+ "_TreeRowCheck_" + j + "' value='" + treeID
								+ "' level='" + level
								+ "' onClick='treeCheckBoxClick(this);'>");
					}

					sb.append(this.Table.getTR(j).getTD(i).getInnerHTML());
					this.Table.getTR(j).getTD(i).setInnerHTML(sb.toString());
					this.Table.getTR(j).setAttribute("level", level);
				}

			}

			if ("true".equalsIgnoreCase(this.headTR.getTD(i).getAttribute(
					"drag"))) {
				for (int j = 1; j < this.Table.Children.size(); j++) {
					HtmlTD td = this.Table.getTR(j).getTD(i);
					String style = td.getAttribute("style");
					if (style == null) {
						style = "";
					}
					td.setAttribute("style", "cursor:move;" + style);
					td.setAttribute("dragStart", "DataGrid.dragStart");
					td.setAttribute("onMouseDown",
							"DragManager.onMouseDown(event,this)");

					this.Table.getTR(j).setAttribute("dragEnd",
							"DataGrid.dragEnd");
					this.Table.getTR(j).setAttribute("onMouseUp",
							"DragManager.onMouseUp(event,this)");

					String userAgent = this.Params
							.getString("Header.UserAgent");
					if (userAgent == null)
						userAgent = "";
					else {
						userAgent = userAgent.toLowerCase();
					}
					if (userAgent.indexOf("msie") >= 0)
						this.Table.getTR(j).setAttribute("onMouseEnter",
								"DragManager.onMouseOver(event,this)");
					else {
						this.Table.getTR(j).setAttribute("onMouseOver",
								"DragManager.onMouseOver(event,this)");
					}
					this.Table.getTR(j).setAttribute("dragOver",
							"DataGrid.dragOver");

					if (userAgent.indexOf("msie") >= 0)
						this.Table.getTR(j).setAttribute("onMouseLeave",
								"DragManager.onMouseOut(event,this)");
					else {
						this.Table.getTR(j).setAttribute("onMouseOut",
								"DragManager.onMouseOut(event,this)");
					}
					this.Table.getTR(j).setAttribute("dragOut",
							"DataGrid.dragOut");
				}
			}

		}

		if (this.AutoFill) {
			if (!this.PageFlag) {
				if (this.DataSource.getRowCount() < 15) {
					HtmlTR tr = new HtmlTR(this.Table);
					tr.setAttribute("xtype", "blank");
					for (int i = 0; i < this.headTR.Children.size(); i++) {
						tr.addTD(new HtmlTD());
					}
					tr
							.setAttribute("height", String
									.valueOf((15 - this.DataSource
											.getRowCount()) * 23));
					this.Table.addTR(tr);
				}
			} else if (this.DataSource.getRowCount() < this.PageSize) {
				HtmlTR tr = new HtmlTR(this.Table);
				tr.setAttribute("xtype", "blank");
				for (int i = 0; i < this.headTR.Children.size(); i++) {
					tr.addTD(new HtmlTD());
				}
				tr.setAttribute("height",
						String.valueOf((this.PageSize - this.DataSource
								.getRowCount()) * 23));
				this.Table.addTR(tr);
			}

		}

		for (int i = 0; i < this.Table.getChildren().size(); i++) {
			HtmlTR tr = this.Table.getTR(i);
			for (int j = 0; j < tr.getChildren().size(); j++) {
				HtmlTD td = tr.getTD(j);
				if (XString.isEmpty(td.getInnerHTML()))
					td.setInnerHTML("&nbsp;");
			}
		}
	}

	public String getHtml() {
		HtmlScript script = new HtmlScript();
		script.setAttribute("xtype", "DataGrid");
		script.setInnerHTML(getScript());
		this.Table.addChild(script);
		String html = null;
		if (this.Scroll) {
			html = scrollWrap();
		} else {
			if ((!this.TreeFlag) && (this.PageFlag) && (this.pageBarTR != null)) {
				dealPageBar();
				this.Table.addTR(this.pageBarTR);
			}
			html = this.Table.getOuterHtml();
		}
		return html;
	}

	public String scrollWrap() {
		for (int i = 0; i < this.Table.getTR(0).getChildren().size(); i++) {
			this.Table.getTR(0).getTD(i).setHead(true);
		}
		this.Table.removeAttribute("class");
		this.Table.getTR(0).removeAttribute("class");

		StringBuffer sb = new StringBuffer();
		String fw = this.Table.getAttribute("fixedWidth");
		String fh = this.Table.getAttribute("fixedHeight");

		sb
				.append("<div id='"
						+ this.ID
						+ "_Wrap' class='dataTable dt_scrollable dt_nobr' xtype='_DataGridWrapper'");
		if (XString.isNotEmpty(fw)) {
			sb.append(" style='width:" + fw + ";'");
		}
		sb.append(">");
		sb.append("<div id='" + this.ID + "_Wrap_head' class='dt_head'>");
		HtmlTable tmpTable = (HtmlTable) this.Table.clone();
		for (int i = tmpTable.Children.size() - 1; i > 0; i--) {
			tmpTable.removeTR(i);
		}
		tmpTable.setID(null);
		tmpTable.setClassName("dt_headTable");
		tmpTable.getTR(0).setClassName("dt_headTr");
		for (int i = 0; i < tmpTable.getTR(0).getChildren().size(); i++) {
			HtmlTD td = tmpTable.getTR(0).getTD(i);
			td.InnerHTML = ("<div id='dataTable0_th" + i + "' class='dt_th'>"
					+ td.InnerHTML + "</div>");
		}
		sb.append(tmpTable.getOuterHtml());
		sb.append("</div>");

		sb
				.append("<div id='" + this.ID
						+ "_Wrap_body' class='dt_body' style='");
		if ((XString.isNotEmpty(fw)) && (fw.indexOf("%") < 0)) {
			sb.append("width:" + fw + ";");
		}
		if (XString.isNotEmpty(fh)) {
			sb.append("height:" + fh + ";");
		}
		sb.append("'>");
		sb.append(this.Table.getOuterHtml());
		sb.append("</div>");

		if ((!this.TreeFlag) && (this.PageFlag) && (this.pageBarTR != null)) {
			dealPageBar();

			HtmlTable footTable = new HtmlTable();
			footTable.setAttribute("width", "100%");
			footTable.addTR(this.pageBarTR);

			sb.append("<div class='dt_foot'>");
			sb.append(footTable.getOuterHtml());
			sb.append("</div>");
		}
		sb.append("</div>");
		return sb.toString();
	}

	private void dealPageBar() {
		try {
			for (int i = this.pageBarTR.Children.size() - 1; i > 0; i--) {
				this.pageBarTR.removeTD(i);
			}
			String html = getPageBarHtml(this.ID, this.Params, this.Total,
					this.PageIndex, this.PageSize, this.isSimplePageBar);
			this.pageBarTR.getTD(0).setInnerHTML(html);
			this.pageBarTR.getTD(0)
					.setColSpan(this.headTR.Children.size() + "");
			this.pageBarTR.getTD(0).setID(this.ID + "_PageBar");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.pageBarTR.setAttribute("dragOver", "DataGrid.dragOver");
		this.pageBarTR.setAttribute("dragOut", "DataGrid.dragOut");
		this.pageBarTR.setAttribute("dragEnd", "DataGrid.dragEnd");
	}

	public String getScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("Page.onLoad(DataGrid_" + this.ID + "_Init,9);");
		sb.append("function DataGrid_" + this.ID + "_Init(){");
		if (this.DataSource != null) {
			sb.append(DataCollection.dataTableToJS(this.DataSource));
			sb.append("$('" + this.ID + "').DataSource = new DataTable();");
			sb.append("$('" + this.ID
					+ "').DataSource.init(_SKY_Cols,_SKY_Values);");
		}
		sb.append("var _SKY_Arr = [];");
		HtmlTR tr = new HtmlTR(this.Table);
		try {
			tr.parseHtml(this.templateHtml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < tr.getChildren().size(); i++) {
			sb.append("_SKY_Arr.push(\""
					+ XString.javaEncode(tr.getTD(i).getInnerHTML())
					+ "\");");
		}
		sb.append("$('" + this.ID + "').TemplateArray = _SKY_Arr;");
		if (this.editTR != null) {
			sb.append("_SKY_Arr = [];\n");
			for (int i = 0; i < this.editTR.getChildren().size(); i++) {
				sb.append("_SKY_Arr.push(\""
						+ XString.javaEncode(this.editTR.getTD(i)
								.getInnerHTML()) + "\");");
			}
			sb.append("$('" + this.ID + "').EditArray = _SKY_Arr;");
		}

		sb.append("$('" + this.ID + "').TagBody = \""
				+ XString.htmlEncode(getTagBody().replaceAll("\\s+", " "))
				+ "\";");
		Object[] ks = this.Params.keyArray();
		Object[] vs = this.Params.valueArray();
		for (int i = 0; i < this.Params.size(); i++) {
			Object key = ks[i];
			if ((key.equals("_SKY_TAGBODY"))
					|| (key.toString().startsWith("Cookie."))
					|| (key.toString().startsWith("Header."))
					|| (vs[i] == null))
				continue;
			if (((vs[i] instanceof DataTable))
					|| ((vs[i] instanceof SchemaSet))) {
				DataTable dt = null;
				if ((vs[i] instanceof SchemaSet))
					dt = ((SchemaSet) vs[i]).toDataTable();
				else {
					dt = (DataTable) vs[i];
				}
				sb.append(DataCollection.dataTableToJS(dt));
				sb.append("var _TmpDt = new DataTable();");
				sb.append("_TmpDt.init(_SKY_Cols,_SKY_Values);");
				sb.append("DataGrid.setParam('" + this.ID + "','" + key
						+ "',_TmpDt);");
			} else {
				sb.append("DataGrid.setParam('" + this.ID + "','" + key
						+ "',\"" + XString.javaEncode(vs[i].toString())
						+ "\");");
			}
		}

		if (this.PageFlag) {
			sb.append("DataGrid.setParam('" + this.ID + "','"
					+ "_SKY_PAGEINDEX" + "'," + this.PageIndex + ");");
			sb.append("DataGrid.setParam('" + this.ID + "','"
					+ "_SKY_PAGETOTAL" + "'," + this.Total + ");");
			sb.append("DataGrid.setParam('" + this.ID + "','" + "_SKY_SIZE"
					+ "'," + this.PageSize + ");");
		}
		if (this.SortFlag) {
			sb.append("DataGrid.setParam('" + this.ID + "','"
					+ "_SKY_SORTSTRING" + "','" + this.SortString + "');");
		}
		sb.append("DataGrid.setParam('" + this.ID + "','"
				+ "_SKY_MULTISELECT" + "','" + this.MultiSelect + "');");
		sb.append("DataGrid.setParam('" + this.ID + "','" + "_SKY_AUTOFILL"
				+ "','" + this.AutoFill + "');");
		sb.append("DataGrid.setParam('" + this.ID + "','" + "_SKY_SCROLL"
				+ "','" + this.Scroll + "');");
		sb.append("DataGrid.setParam('" + this.ID + "','" + "_SKY_LAZY"
				+ "','" + this.Lazy + "');");
		if (this.cacheSize > 0) {
			sb.append("DataGrid.setParam('" + this.ID + "','"
					+ "_SKY_CACHESIZE" + "','" + this.cacheSize + "');");
		}

		sb.append("DataGrid.init('" + this.ID + "');");
		sb.append("}");

		String content = sb.toString();
		Matcher matcher = Constant.PatternField.matcher(content);
		sb = new StringBuffer();
		int lastEndIndex = 0;
		while (matcher.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, matcher.start()));
			sb.append("$\\{");
			sb.append(matcher.group(1));
			sb.append("}");
			lastEndIndex = matcher.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		matcher = Constant.PatternSpeicalField.matcher(content);
		sb = new StringBuffer();
		lastEndIndex = 0;
		while (matcher.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, matcher.start()));
			sb.append("${#");
			sb.append(matcher.group(1));
			sb.append("}");
			lastEndIndex = matcher.end();
		}
		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}

	public HtmlTable getTemplate() {
		return this.Template;
	}

	public void setTemplate(HtmlTable table) {
		this.Template = table;
	}

	public DataTable getDataSource() {
		return this.DataSource;
	}

	public void bindData(QueryBuilder qb) {
		bindData(qb, this.PageFlag);
	}

	public void bindData(QueryBuilder qb, boolean pageFlag) {
		if (pageFlag) {
			if (!this.TotalFlag) {
				setTotal(qb.getCount());
			}
			bindData(qb.executePagedDataTable(this.PageSize, this.PageIndex));
		} else {
			bindData(qb.executeDataTable());
		}
	}

	public void bindData(DataTable dt) {
		if ("1".equals(this.Params.get("_ExcelFlag"))) {
			String[] columnNames = (String[]) this.Params.get("_ColumnNames");
			String[] widths = (String[]) this.Params.get("_Widths");
			String[] columnIndexes = (String[]) this.Params
					.get("_ColumnIndexes");
			DataTableUtil.dataTableToExcel(dt, (OutputStream) this.Params
					.get("_OutputStream"), columnNames, widths, columnIndexes);
		} else {
			this.DataSource = dt;
			try {
				bindData();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void bindData(SchemaSet set) {
		bindData(set.toDataTable());
	}

	public static String getPageBarHtml(String id, Mapx params, int total,
			int pageIndex, int pageSize) {
		return getPageBarHtml(id, params, total, pageIndex, pageSize, false);
	}

	public static String getPageBarHtml(String id, Mapx params, int total,
			int pageIndex, int pageSize, boolean simpleFlag) {
		StringBuffer sb = new StringBuffer();
		int totalPages = new Double(Math.ceil(total * 1.0D / pageSize))
				.intValue();

		params.put("_SKY_PAGETOTAL", total);
		params.remove("_SKY_PAGEINDEX");

		sb.append("<div style='float:right;font-family:Tahoma'>");
		if (pageIndex > 0) {
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataGrid.firstPage('"
							+ id + "');\">第一页</a>&nbsp;|&nbsp;");
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataGrid.previousPage('"
							+ id + "');\">上一页</a>&nbsp;|&nbsp;");
		} else {
			sb.append("第一页&nbsp;|&nbsp;");
			sb.append("上一页&nbsp;|&nbsp;");
		}
		if ((totalPages != 0) && (pageIndex + 1 != totalPages)) {
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataGrid.nextPage('"
							+ id + "');\">下一页</a>&nbsp;|&nbsp;");
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataGrid.lastPage('"
							+ id + "');\">最末页</a>");
		} else {
			sb.append("下一页&nbsp;|&nbsp;");
			sb.append("最末页&nbsp;&nbsp;");
		}
		if (!simpleFlag) {
			sb
					.append("&nbsp;&nbsp;转到第&nbsp;<input id='_PageBar_Index' type='text' class='inputText' style='width:40px' ");
			sb.append("onKeyUp=\"value=value.replace(/\\D/g,'')\">&nbsp;页");
			sb
					.append("&nbsp;&nbsp;<input type='button' onclick=\"if(!/^\\d+$/.test($V('_PageBar_Index'))||$V('_PageBar_Index')<1||$V('_PageBar_Index')>"
							+ totalPages
							+ "){alert('错误的页码');$('_PageBar_Index').focus();}else{var pageIndex = ($V('_PageBar_Index')-1)>0?$V('_PageBar_Index')-1:0;DataGrid.setParam('"
							+ id
							+ "','"
							+ "_SKY_PAGEINDEX"
							+ "',pageIndex);DataGrid.loadData('"
							+ id
							+ "');}\" class='inputButton' value='跳转'>");
		}
		sb.append("</div>");
		sb.append("<div style='float:left;font-family:Tahoma'>");
		sb.append("共 " + total + " 条记录，每页 " + pageSize + " 条，当前第 "
				+ (totalPages == 0 ? 0 : pageIndex + 1) + " / " + totalPages
				+ " 页</div>");

		return sb.toString();
	}

	public int getPageIndex() {
		return this.PageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.PageIndex = pageIndex;
	}

	public int getPageSize() {
		return this.PageSize;
	}

	public void setPageSize(int pageSize) {
		this.PageSize = pageSize;
	}

	public String getParam(String key) {
		return (String) this.Params.get(key);
	}

	public Mapx getParams() {
		return this.Params;
	}

	public void setParams(Mapx params) {
		this.Params = params;
	}

	public boolean isPageFlag() {
		return this.PageFlag;
	}

	public void setPageFlag(boolean pageFlag) {
		this.PageFlag = pageFlag;
	}

	public boolean isSortFlag() {
		return this.SortFlag;
	}

	public void setSortFlag(boolean sortFlag) {
		this.SortFlag = sortFlag;
	}

	public HtmlTable getTable() {
		return this.Table;
	}

	public String getID() {
		return this.ID;
	}

	public void setID(String id) {
		this.ID = id;
	}

	public boolean isTreeFlag() {
		return this.TreeFlag;
	}

	protected void setTreeFlag(boolean treeFlag) {
		this.TreeFlag = treeFlag;
	}

	public String getMethod() {
		return this.Method;
	}

	public void setMethod(String method) {
		this.Method = method;
	}

	public int getTotal() {
		return this.Total;
	}

	public void setTotal(int total) {
		this.Total = total;
		if (this.PageIndex > Math.ceil(this.Total * 1.0D / this.PageSize)) {
			this.PageIndex = new Double(Math.floor(this.Total * 1.0D
					/ this.PageSize)).intValue();
		}
		this.TotalFlag = true;
	}

	public void setTotal(QueryBuilder qb) {
		if (this.PageIndex == 0)
			setTotal(qb.getCount());
	}

	public boolean isWebMode() {
		return this.WebMode;
	}

	public void setWebMode(boolean webMode) {
		this.WebMode = webMode;
	}

	public String getSortString() {
		if (this.SortString.length() == 0) {
			return "";
		}
		String str = this.SortString.toString();
		if (sortPattern.matcher(str).matches()) {
			return " order by " + this.SortString.toString();
		}
		return "";
	}

	public String getTagBody() {
		return this.TagBody;
	}

	public void setTagBody(String tagBody) {
		this.TagBody = tagBody;
	}

	public boolean isMultiSelect() {
		return this.MultiSelect;
	}

	public void setMultiSelect(boolean multiSelect) {
		this.MultiSelect = multiSelect;
	}

	public boolean isAutoFill() {
		return this.AutoFill;
	}

	public void setAutoFill(boolean autoFill) {
		this.AutoFill = autoFill;
	}

	public boolean isScroll() {
		return this.Scroll;
	}

	public void setScroll(boolean scroll) {
		this.Scroll = scroll;
	}

	public int getCacheSize() {
		return this.cacheSize;
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public boolean isLazy() {
		return this.Lazy;
	}

	public void setLazy(boolean lazy) {
		this.Lazy = lazy;
	}

	public static DataTable sortTreeDataTable(DataTable dt,
			String identifierColumnName, String parentIdentifierColumnName) {
		if ((dt == null) || (dt.getRowCount() == 0)) {
			return dt;
		}
		if (dt.getDataColumn(identifierColumnName) == null) {
			LogUtil.warn("DataGridAction.sortTreeDataTable():数据中不存在列"
					+ identifierColumnName);
		}
		if (dt.getDataColumn(parentIdentifierColumnName) == null) {
			LogUtil.warn("DataGridAction.sortTreeDataTable():数据中不存在列"
					+ parentIdentifierColumnName);
		}
		Treex tree = DataTableTree.dataTableToTree(dt, identifierColumnName,
				parentIdentifierColumnName);
		Treex.TreeIterator ti = tree.iterator();
		DataTable dest = new DataTable(dt.getDataColumns(), null);
		while (ti.hasNext()) {
			Treex.TreeNode node = (Treex.TreeNode) ti.next();
			DataRow dr = (DataRow) node.getData();
			if (dr != null) {
				dest.insertRow(dr);
			}
		}
		return dest;
	}
}
