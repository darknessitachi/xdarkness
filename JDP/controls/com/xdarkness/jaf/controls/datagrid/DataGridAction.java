package com.xdarkness.jaf.controls.datagrid;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xdarkness.common.data.DataCollection;
import com.xdarkness.common.data.DataColumn;
import com.xdarkness.common.data.DataRow;
import com.xdarkness.common.data.DataTable;
import com.xdarkness.common.data.DataTableUtil;
import com.xdarkness.common.util.Mapx;
import com.xdarkness.common.util.StringUtil;
import com.xdarkness.jaf.JafResponse;
import com.xdarkness.jaf.MainServlet;
import com.xdarkness.jaf.controls.Constant;
import com.xdarkness.jaf.controls.IControlAction;
import com.xdarkness.jaf.controls.VerifyRule;
import com.xdarkness.jaf.controls.html.HtmlScript;
import com.xdarkness.jaf.controls.html.HtmlTD;
import com.xdarkness.jaf.controls.html.HtmlTR;
import com.xdarkness.jaf.controls.html.HtmlTable;

public class DataGridAction implements IControlAction {

	protected String ID;
	private boolean MultiSelect;
	private boolean AutoFill;
	private boolean Scroll;
	private boolean Lazy;
	private int cacheSize;
	protected HtmlTable Template;
	
	protected boolean SortFlag;
	protected boolean TreeFlag;
	protected boolean WebMode;
	protected StringBuffer SortString;
	protected String Method;
	protected String TagBody;
	protected PageInfo PageInfo;
	protected DataTable DataSource;
	protected HtmlTable Table;
	protected Mapx Params;
	protected JafResponse Response;
	
	ArrayList templateAreas;
	ArrayList templateFields;
	String style1;
	String style2;
	String class1;
	String class2;
	String templateHtml;
	HtmlTR headTR;
	HtmlTR templateTR;
	HtmlTR editTR;
	HtmlTR pageBarTR;
	boolean isSimplePageBar;
	
	private static Pattern sortPattern = Pattern.compile("[\\w\\,\\s]*", Pattern.CASE_INSENSITIVE);
	
	public DataGridAction() {
		MultiSelect = true;
		AutoFill = true;
		Scroll = false;
		Lazy = false;
		WebMode = true;
		SortString = new StringBuffer();
		Params = new Mapx();
		templateAreas = new ArrayList();
		templateFields = new ArrayList();
		headTR = null;
		templateTR = null;
		editTR = null;
		pageBarTR = null;
		isSimplePageBar = false;
	}

	public void parse() throws Exception {
		Table = new HtmlTable();
		Table.setAttributes(Template.getAttributes());
		for (int i = 0; i < Template.Children.size(); i++) {
			HtmlTR tr = (HtmlTR) Template.Children.get(i);
			if ("Head".equalsIgnoreCase(tr.getAttribute("xtype")))
				headTR = tr;
			else if ("PageBar".equalsIgnoreCase(tr.getAttribute("xtype")))
				pageBarTR = tr;
			else if ("SimplePageBar".equalsIgnoreCase(tr.getAttribute("xtype"))) {
				pageBarTR = tr;
				isSimplePageBar = true;
			} else if ("Edit".equalsIgnoreCase(tr.getAttribute("xtype"))) {
				editTR = tr;
				editTR.setAttribute("style", "display:none");
			} else {
				style1 = tr.getAttribute("style1");
				style2 = tr.getAttribute("style2");
				class1 = tr.getAttribute("class1");
				class2 = tr.getAttribute("class2");
				tr.removeAttribute("style1");
				tr.removeAttribute("style2");
				tr.removeAttribute("class1");
				tr.removeAttribute("class2");
				templateTR = tr;
			}
		}

		if (headTR == null)
			headTR = Template.getTR(0);
		Table.addTR(headTR);
		
		String str = (String) Params.get("_SKY_SORTSTRING");
		
		
		// 获取排序字段，将_SKY_SORTSTRING中的排序字段解析放置sortMap
		boolean emptyFlag = true;
		Mapx sortMap = new Mapx();
		if (StringUtil.isNotEmpty(str) && new VerifyRule("String").verify(str)) {
			if ("_SKY_NULL".equals(str))
				str = "";
			SortString.append(str);
			String arr[] = str.split("\\,");
			for (int i = 0; i < arr.length; i++)
				if (arr[i].indexOf(' ') > 0) {
					String arr2[] = arr[i].split("\\s");
					sortMap.put(arr2[0].trim().toLowerCase(), arr2[1].trim());
				} else {
					sortMap.put(arr[i].trim().toLowerCase(), "");
				}

			emptyFlag = false;
		}
		
		
		boolean firstSortFieldFlag = true;
		for (int i = 0; i < headTR.Children.size(); i++) {
			HtmlTD td = headTR.getTD(i);
			String xtype = td.getAttribute("xtype");
			if ("Tree".equalsIgnoreCase(xtype))
				TreeFlag = true;
			String sortField = td.getAttribute("sortField");
			String direction = td.getAttribute("direction");
			if (StringUtil.isNotEmpty(sortField)) {
				SortFlag = true;
				if (emptyFlag) {// _SKY_SORTSTRING 为空
					if (StringUtil.isNotEmpty(direction)) {
						if (!firstSortFieldFlag)
							SortString.append(",");
						SortString.append(sortField);
						SortString.append(" ");
						SortString.append(direction);
						firstSortFieldFlag = false;
					} else {
						direction = "";
					}
				} else {
					direction = (String) sortMap.get(sortField.toLowerCase());
					if (StringUtil.isEmpty(direction))
						direction = "";
					td.setAttribute("direction", direction);
				}
			}
			if (StringUtil.isNotEmpty(sortField)) {// 该字段为排序字段，设置其属性及图标
				td.setAttribute("style", "cursor:pointer");
				td.setAttribute("onMouseOver", "DataGrid.onSortHeadMouseOver(this);");
				td.setAttribute("onMouseOut", "DataGrid.onSortHeadMouseOut(this);");
				td.setAttribute("onClick", "DataGrid.onSort(this);");
				StringBuffer sb = new StringBuffer();
				sb.append("<span style='float:left'>");
				sb.append(td.getInnerHTML());
				sb.append("</span>");
				sb.append("<img src='");
				sb.append(MainServlet.CONTEXT_PATH);
				sb.append("Framework/Images/icon_sort");
				sb.append(direction.toUpperCase());
				sb.append(".gif' width='12' height='12' style='float:right'>");
				td.setInnerHTML(sb.toString());
			}
		}

		Table.setAttribute("SortString", SortString.toString());
		Table.setAttribute("id", ID);
		Table.setAttribute("page", "" + PageInfo.isPageFlag());
		Table.setAttribute("size", "" + PageInfo.getPageSize());
		Table.setAttribute("method", Method);
		Table.setAttribute("multiSelect", "" + MultiSelect);
		Table.setAttribute("autoFill", "" + AutoFill);
		Table.setAttribute("scroll", "" + Scroll);
		Table.setAttribute("Lazy", "" + Lazy);
		Table.setAttribute("cacheSize", "" + cacheSize);
		templateHtml = templateTR.getOuterHtml();
		if (templateHtml == null)
			throw new RuntimeException("DataGrid不能没有模板行");
		Matcher m = Constant.PatternField.matcher(templateHtml);
		int lastEndIndex=0;
		while (m.find(lastEndIndex)) {
			templateAreas.add(templateHtml.substring(lastEndIndex, m.start()));
			templateFields.add(m.group(1));
			lastEndIndex = m.end(); 
		}

		templateAreas.add(templateHtml.substring(lastEndIndex));
	}

	private void addChild(DataTable dt, String parentID) {
		for (int i = 0; i < DataSource.getRowCount(); i++)
			if (DataSource.getString(i, "ParentID").equals(parentID)) {
				dt.insertRow(DataSource.getDataRow(i));
				addChild(dt, DataSource.getString(i, "ID"));
			}

	}

	private void bindData() throws Exception {
		if (!PageInfo.isPageFlag())
		    PageInfo.setRowCount(DataSource.getRowCount());
		if (TreeFlag && DataSource.getDataColumn("ParentID") != null
				&& DataSource.getDataColumn("ID") != null) {
			Mapx map = new Mapx();
			for (int i = 0; i < DataSource.getRowCount(); i++)
				map.put(DataSource.getString(i, "ID"), DataSource.getString(i,
						"ParentID"));

			DataTable dt = new DataTable(DataSource.getDataColumns(), null);
			for (int i = 0; i < DataSource.getRowCount(); i++)
				if (map.get(DataSource.getString(i, "ParentID")) == null) {
					dt.insertRow(DataSource.getDataRow(i));
					addChild(dt, DataSource.getString(i, "ID"));
				}

			DataSource = dt;
		}
		if (DataSource == null)
			throw new RuntimeException(
					"必须在bindData方法中设定DataSource");
		if (DataSource.getDataColumn("_RowNo") == null)
			DataSource.insertColumn(new DataColumn("_RowNo", 8));
		for (int j = 0; j < DataSource.getRowCount(); j++) {
			int rowNo = PageInfo.getPageNum() * PageInfo.getPageSize() + j + 1;
			DataSource.set(j, "_RowNo", new Integer(rowNo));
		}

		for (int i = 0; i < DataSource.getRowCount(); i++) {
			DataRow dr = DataSource.getDataRow(i);
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < templateAreas.size(); j++) {
				sb.append(templateAreas.get(j));
				if (j < templateFields.size())
					sb.append(dr.getString(templateFields.get(j).toString()));
			}

			HtmlTR tr = new HtmlTR(Table);
			tr.parseHtml(sb.toString());
			if (i % 2 == 1) {
				if (style1 != null)
					tr.setAttribute("style", style1);
				if (class1 != null)
					tr.setAttribute("class", class1);
			} else {
				if (style2 != null)
					tr.setAttribute("style", styleOperateType.UPDATE);
				if (class2 != null)
					tr.setAttribute("class", classOperateType.UPDATE);
			}
			String clickEvent = tr.getAttribute("onclick");
			if (StringUtil.isEmpty(clickEvent))
				clickEvent = "";
			tr.setAttribute("onclick", "DataGrid.onRowClick(this,event);"
					+ clickEvent);
			String dblEvent = tr.getAttribute("ondblclick");
			if (StringUtil.isNotEmpty(dblEvent))
				tr.setAttribute("ondblclick", dblEvent);
			else if (editTR != null)
				tr.setAttribute("ondblclick", "DataGrid.editRow(this)");
			tr.setAttribute("oncontextmenu",
					"DataGrid._onContextMenu(this,event)");
			Table.addTR(tr);
		}

		for (int i = 0; i < headTR.Children.size(); i++) {
			boolean rowNoFlag = "RowNo".equalsIgnoreCase(headTR.getTD(i)
					.getAttribute("xtype"));
			for (int j = 1; j < Table.Children.size(); j++) {
				int rowNo = PageInfo.getPageNum() * PageInfo.getPageSize() + j;
				if (rowNoFlag) {
					Table.getTR(j).getTD(i).setInnerHTML("" + rowNo);
					Table.getTR(j).getTD(i).setAttribute("rowno", "" + rowNo);
				}
			}

			if ("Selector".equalsIgnoreCase(headTR.getTD(i).getAttribute(
					"xtype"))) {
				String field = headTR.getTD(i).getAttribute("field");
				String onSelect = headTR.getTD(i).getAttribute("onselect");
				if (onSelect == null)
					onSelect = "";
				if (MultiSelect)
					headTR
							.getTD(i)
							.setInnerHTML(
									"<input type='checkbox' value='*' id='"
											+ ID
											+ "_AllCheck' onclick=\"DataGrid.onAllCheckClick('"
											+ ID + "')\"/>");
				String type = MultiSelect ? "checkbox" : "radio";
				for (int j = 1; j < Table.Children.size(); j++) {
					Table.getTR(j).getTD(i)
							.setInnerHTML(
									"<input type='"
											+ type
											+ "' name='"
											+ ID
											+ "_RowCheck' id='"
											+ ID
											+ "_RowCheck"
											+ j
											+ "' value='"
											+ DataSource
													.getString(j - 1, field)
											+ "'>");
					Table.getTR(j).getTD(i).setAttribute("onclick",
							"DataGrid.onSelectorClick(this,event);" + onSelect);
					Table.getTR(j).getTD(i).setAttribute("ondblclick",
							"stopEvent(event);" + onSelect);
				}

			}
			if ("Checkbox".equalsIgnoreCase(headTR.getTD(i).getAttribute(
					"xtype"))) {
				String field = headTR.getTD(i).getAttribute("field");
				String checkedvalue = headTR.getTD(i).getAttribute(
						"checkedvalue");
				String disabled = headTR.getTD(i).getAttribute("disabled");
				if (checkedvalue == null)
					checkedvalue = "Y";
				if (disabled == null || disabled.equalsIgnoreCase("true"))
					disabled = "disabled";
				else
					disabled = "";
				for (int j = 1; j < Table.Children.size(); j++) {
					String checked = checkedvalue.equals(DataSource.getString(
							j - 1, field)) ? "checked" : "";
					Table.getTR(j).getTD(i).setInnerHTML(
							"<input type='checkbox' " + disabled + " name='"
									+ ID + "_" + field + "_Checkbox' id='" + ID
									+ "_" + field + "_Checkbox" + j
									+ "' value='" + checkedvalue + "' "
									+ checked + ">");
				}

			}
//			if ("DropDownList".equalsIgnoreCase(headTR.getTD(i).getAttribute(
//					"xtype"))) {
//			    // ingore
//				String field = headTR.getTD(i).getAttribute("field");
//				String sql = headTR.getTD(i).getAttribute("sql");
//				String zstyle = headTR.getTD(i).getAttribute("zstyle");
//				if (StringUtil.isEmpty(zstyle))
//					zstyle = "width:100px";
//				DataTable dt = (new QueryBuilder(sql)).executeDataTable();
//				for (int j = 1; j < Table.Children.size(); j++) {
//					StringBuffer sb = new StringBuffer();
//					sb
//							.append("<div xtype='select' disabled='true' style='display:none;"
//									+ zstyle
//									+ ";' name='"
//									+ ID
//									+ "_"
//									+ field
//									+ "_DropDownList"
//									+ j
//									+ "' id='"
//									+ ID
//									+ "_"
//									+ field + "_DropDownList" + j + "' >");
//					for (int k = 0; k < dt.getRowCount(); k++) {
//						String value = DataSource.getString(j - 1, field);
//						String selected = "";
//						if (value.equals(dt.getString(k, 0)))
//							selected = "selected='true'";
//						sb.append("<span value='" + dt.getString(k, 0) + "' "
//								+ selected + ">" + dt.getString(k, 1)
//								+ "</span>");
//					}
//
//					sb.append("</div>");
//					Table.getTR(j).getTD(i).setInnerHTML(sb.toString());
//				}
//
//			}
			if ("Tree".equalsIgnoreCase(headTR.getTD(i).getAttribute("xtype"))) {
				String field = headTR.getTD(i).getAttribute("field");
				String str_level = headTR.getTD(i).getAttribute("level");
				for (int j = 1; j < Table.Children.size(); j++) {
					int level = Integer.parseInt(DataSource.getString(j - 1,
							str_level));
					StringBuffer sb = new StringBuffer();
					for (int k = 0; k < level; k++)
						sb.append("<q style='padding:0 10px'></q>");

					int nextLevel = 0;
					if (j != Table.Children.size() - 1)
						nextLevel = Integer.parseInt(DataSource.getString(j,
								str_level));
					if (level < nextLevel)
						sb
								.append("<img src='" + MainServlet.CONTEXT_PATH
										+ "Framework/Images/butExpand.gif' onclick='DataGrid.treeClick(this)'/>&nbsp;");
					else
						sb.append("<img src='" + MainServlet.CONTEXT_PATH
								+ "Framework/Images/butNoChild.gif'/>&nbsp;");
					if (field != null) {
						String treeID = DataSource.getString(j - 1, field);
						sb.append("<input type='checkbox'  name='" + ID
								+ "_TreeRowCheck' id='" + ID + "_TreeRowCheck_"
								+ j + "' value='" + treeID + "' level='"
								+ level
								+ "' onClick='treeCheckBoxClick(this);'>");
					}
					sb.append(Table.getTR(j).getTD(i).getInnerHTML());
					Table.getTR(j).getTD(i).setInnerHTML(sb.toString());
					Table.getTR(j).setAttribute("level", "" + level);
				}

			}
			if ("true".equalsIgnoreCase(headTR.getTD(i).getAttribute("drag"))) {
				for (int j = 1; j < Table.Children.size(); j++) {
					HtmlTD td = Table.getTR(j).getTD(i);
					String style = td.getAttribute("style");
					if (style == null)
						style = "";
					td.setAttribute("style", "cursor:move;" + style);
					td.setAttribute("dragStart", "DataGrid.dragStart");
					td.setAttribute("onMouseDown",
							"DragManager.onMouseDown(event,this)");
					Table.getTR(j).setAttribute("dragEnd", "DataGrid.dragEnd");
					Table.getTR(j).setAttribute("onMouseUp",
							"DragManager.onMouseUp(event,this)");
					String userAgent = Params.getString("Header.UserAgent");
					if (userAgent == null)
						userAgent = "";
					else
						userAgent = userAgent.toLowerCase();
					if (userAgent.indexOf("msie") >= 0)
						Table.getTR(j).setAttribute("onMouseEnter",
								"DragManager.onMouseOver(event,this)");
					else
						Table.getTR(j).setAttribute("onMouseOver",
								"DragManager.onMouseOver(event,this)");
					Table.getTR(j)
							.setAttribute("dragOver", "DataGrid.dragOver");
					if (userAgent.indexOf("msie") >= 0)
						Table.getTR(j).setAttribute("onMouseLeave",
								"DragManager.onMouseOut(event,this)");
					else
						Table.getTR(j).setAttribute("onMouseOut",
								"DragManager.onMouseOut(event,this)");
					Table.getTR(j).setAttribute("dragOut", "DataGrid.dragOut");
				}

			}
		}

		if (AutoFill)
			if (!PageInfo.isPageFlag()) {
				if (DataSource.getRowCount() < 15) {
					HtmlTR tr = new HtmlTR(Table);
					tr.setAttribute("xtype", "blank");
					for (int i = 0; i < headTR.Children.size(); i++)
						tr.addTD(new HtmlTD());

					tr.setAttribute("height", String.valueOf((15 - DataSource
							.getRowCount()) * 23));
					Table.addTR(tr);
				}
			} else if (DataSource.getRowCount() < PageInfo.getPageSize()) {
				HtmlTR tr = new HtmlTR(Table);
				tr.setAttribute("xtype", "blank");
				for (int i = 0; i < headTR.Children.size(); i++)
					tr.addTD(new HtmlTD());

				tr.setAttribute("height", String.valueOf((PageInfo.getPageSize() - DataSource
						.getRowCount()) * 23));
				Table.addTR(tr);
			}
		for (int i = 0; i < Table.getChildren().size(); i++) {
			HtmlTR tr = Table.getTR(i);
			for (int j = 0; j < tr.getChildren().size(); j++) {
				HtmlTD td = tr.getTD(j);
				if (StringUtil.isEmpty(td.getInnerHTML()))
					td.setInnerHTML("&nbsp;");
			}

		}

	}

	public String getHtml() {
		HtmlScript script = new HtmlScript();
		script.setInnerHTML(getScript());
		Table.addChild(script);
		String html = null;
		if (Scroll) {
			html = scrollWrap();
		} else {
			if (!TreeFlag && PageInfo.isPageFlag() && pageBarTR != null) {
				dealPageBar();
				Table.addTR(pageBarTR);
			}
			html = Table.getOuterHtml();
		}
		return html;
	}

	public String scrollWrap() {
		for (int i = 0; i < Table.getTR(0).getChildren().size(); i++)
			Table.getTR(0).getTD(i).setHead(true);

		Table.removeAttribute("class");
		Table.getTR(0).removeAttribute("class");
		StringBuffer sb = new StringBuffer();
		String fw = Table.getAttribute("fixedWidth");
		String fh = Table.getAttribute("fixedHeight");
		sb
				.append("<div id='"
						+ ID
						+ "_Wrap' class='dataTable dt_scrollable dt_nobr' xtype='_DataGridWrapper'");
		if (StringUtil.isNotEmpty(fw))
			sb.append(" style='width:" + fw + ";'");
		sb.append(">");
		sb.append("<div id='" + ID + "_Wrap_head' class='dt_head'>");
		HtmlTable tmpTable = (HtmlTable) Table.clone();
		for (int i = tmpTable.Children.size() - 1; i > 0; i--)
			tmpTable.removeTR(i);

		tmpTable.setID(null);
		tmpTable.setClassName("dt_headTable");
		tmpTable.getTR(0).setClassName("dt_headTr");
		for (int i = 0; i < tmpTable.getTR(0).getChildren().size(); i++) {
			HtmlTD td = tmpTable.getTR(0).getTD(i);
			td.InnerHTML = "<div id='dataTable0_th" + i + "' class='dt_th'>"
					+ td.InnerHTML + "</div>";
		}

		sb.append(tmpTable.getOuterHtml());
		sb.append("</div>");
		sb.append("<div id='" + ID + "_Wrap_body' class='dt_body' style='");
		if (StringUtil.isNotEmpty(fw) && fw.indexOf("%") < 0)
			sb.append("width:" + fw + ";");
		if (StringUtil.isNotEmpty(fh))
			sb.append("height:" + fh + ";");
		sb.append("'>");
		sb.append(Table.getOuterHtml());
		sb.append("</div>");
		if (!TreeFlag && PageInfo.isPageFlag() && pageBarTR != null) {
			dealPageBar();
			HtmlTable footTable = new HtmlTable();
			footTable.setAttribute("width", "100%");
			footTable.addTR(pageBarTR);
			sb.append("<div class='dt_foot'>");
			sb.append(footTable.getOuterHtml());
			sb.append("</div>");
		}
		sb.append("</div>");
		return sb.toString();
	}

	private void dealPageBar() {
		try {
			for (int i = pageBarTR.Children.size() - 1; i > 0; i--)
				pageBarTR.removeTD(i);

			String html = getPageBarHtml(ID, Params, PageInfo.getRowCount(), PageInfo.getPageNum(),
					PageInfo.getPageSize(), isSimplePageBar);
			pageBarTR.getTD(0).setInnerHTML(html);
			pageBarTR.getTD(0).setColSpan("" + headTR.Children.size());
			pageBarTR.getTD(0).setID(ID + "_PageBar");
		} catch (Exception e) {
			e.printStackTrace();
		}
		pageBarTR.setAttribute("dragOver", "DataGrid.dragOver");
		pageBarTR.setAttribute("dragOut", "DataGrid.dragOut");
		pageBarTR.setAttribute("dragEnd", "DataGrid.dragEnd");
	}

	public String getScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("Page.onLoad(DataGrid_" + ID + "_Init,9);");
		sb.append("function DataGrid_" + ID + "_Init(){");
		if (DataSource != null) {
			sb.append(DataCollection.dataTableToJS(DataSource));
			sb.append("$('" + ID + "').DataSource = new DataTable();");
			sb.append("$('" + ID + "').DataSource.init(_SKY_Cols,_SKY_Values);");
		}
		sb.append("var _SKY_Arr = [];");
		HtmlTR tr = new HtmlTR(Table);
		try {
			tr.parseHtml(templateHtml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < tr.getChildren().size(); i++)
			sb.append("_SKY_Arr.push(\""
					+ StringUtil.javaEncode(tr.getTD(i).getInnerHTML())
					+ "\");");

		sb.append("$('" + ID + "').TemplateArray = _SKY_Arr;");
		if (editTR != null) {
			sb.append("_SKY_Arr = [];\n");
			for (int i = 0; i < editTR.getChildren().size(); i++)
				sb.append("_SKY_Arr.push(\""
						+ StringUtil.javaEncode(editTR.getTD(i).getInnerHTML())
						+ "\");");

			sb.append("$('" + ID + "').EditArray = _SKY_Arr;");
		}
		sb.append("$('" + ID + "').TagBody = \""
				+ StringUtil.htmlEncode(getTagBody().replaceAll("\\s+", " "))
				+ "\";");
		Object ks[] = Params.keyArray();
		Object vs[] = Params.valueArray();
		for (int i = 0; i < Params.size(); i++) {
			Object key = ks[i];
			if (!key.equals("_SKY_TAGBODY")
					&& !key.toString().startsWith("Cookie.")
					&& !key.toString().startsWith("Header."))
				sb.append("DataGrid.setParam('" + ID + "','" + key + "',\""
						+ vs[i] + "\");");
		}

		if (PageInfo.isPageFlag()) {
			sb.append("DataGrid.setParam('" + ID + "','" + "_SKY_PAGEINDEX"
					+ "'," + PageInfo.getPageNum() + ");");
			sb.append("DataGrid.setParam('" + ID + "','" + "_SKY_PAGETOTAL"
					+ "'," + PageInfo.getRowCount() + ");");
			sb.append("DataGrid.setParam('" + ID + "','" + "_SKY_SIZE" + "',"
					+ PageInfo.getPageSize() + ");");
		}
		if (SortFlag)
			sb.append("DataGrid.setParam('" + ID + "','" + "_SKY_SORTSTRING"
					+ "','" + SortString + "');");
		sb.append("DataGrid.setParam('" + ID + "','" + "_SKY_MULTISELECT"
				+ "','" + MultiSelect + "');");
		sb.append("DataGrid.setParam('" + ID + "','" + "_SKY_AUTOFILL"
				+ "','" + AutoFill + "');");
		sb.append("DataGrid.setParam('" + ID + "','" + "_SKY_SCROLL" + "','"
				+ Scroll + "');");
		sb.append("DataGrid.setParam('" + ID + "','" + "_SKY_LAZY" + "','"
				+ Lazy + "');");
		if (cacheSize > 0)
			sb.append("DataGrid.setParam('" + ID + "','" + "_SKY_CACHESIZE"
					+ "','" + cacheSize + "');");
		sb.append("DataGrid.init('" + ID + "');");
		sb.append("}");
		String content = sb.toString();
		Matcher matcher = Constant.PatternField.matcher(content);
		sb = new StringBuffer();
		int lastEndIndex;
		for (lastEndIndex = 0; matcher.find(lastEndIndex); lastEndIndex = matcher
				.end()) {
			sb.append(content.substring(lastEndIndex, matcher.start()));
			sb.append("$\\{");
			sb.append(matcher.group(1));
			sb.append("}");
		}

		sb.append(content.substring(lastEndIndex));
		content = sb.toString();
		matcher = Constant.PatternSpeicalField.matcher(content);
		sb = new StringBuffer();
		for (lastEndIndex = 0; matcher.find(lastEndIndex); lastEndIndex = matcher
				.end()) {
			sb.append(content.substring(lastEndIndex, matcher.start()));
			sb.append("${#");
			sb.append(matcher.group(1));
			sb.append("}");
		}

		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}

	public void bindData(PageInfo pageInfo) {
        
    }
	
	public void bindData(DataTable dt) {
		if ("1".equals(Params.get("_ExcelFlag"))) {
			String columnNames[] = (String[]) Params.get("_ColumnNames");
			String widths[] = (String[]) Params.get("_Widths");
			String columnIndexes[] = (String[]) Params.get("_ColumnIndexes");
			DataTableUtil.dataTableToExcel(dt, (OutputStream) Params
					.get("_OutputStream"), columnNames, widths, columnIndexes);
		} else {
			DataSource = dt;
			try {
				bindData();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String getPageBarHtml(String id, Mapx params, int total,
			int pageIndex, int pageSize) {
		return getPageBarHtml(id, params, total, pageIndex, pageSize, false);
	}

	public static String getPageBarHtml(String id, Mapx params, int total,
			int pageIndex, int pageSize, boolean simpleFlag) {
		StringBuffer sb = new StringBuffer();
		int totalPages = (new Double(Math.ceil(((double) total * 1.0D)
				/ (double) pageSize))).intValue();
		params.put("_SKY_PAGETOTAL", total);
		params.remove("_SKY_PAGEINDEX");
		sb.append("<div style='float:right;font-family:Tahoma'>");
		if (pageIndex > 0) {
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataGrid.firstPage('"
							+ id + "');\">\u7B2C\u4E00\u9875</a>&nbsp;|&nbsp;");
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataGrid.previousPage('"
							+ id + "');\">\u4E0A\u4E00\u9875</a>&nbsp;|&nbsp;");
		} else {
			sb.append("\u7B2C\u4E00\u9875&nbsp;|&nbsp;");
			sb.append("\u4E0A\u4E00\u9875&nbsp;|&nbsp;");
		}
		if (totalPages != 0 && pageIndex + 1 != totalPages) {
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataGrid.nextPage('"
							+ id + "');\">\u4E0B\u4E00\u9875</a>&nbsp;|&nbsp;");
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataGrid.lastPage('"
							+ id + "');\">\u6700\u672B\u9875</a>");
		} else {
			sb.append("\u4E0B\u4E00\u9875&nbsp;|&nbsp;");
			sb.append("\u6700\u672B\u9875&nbsp;&nbsp;");
		}
		if (!simpleFlag) {
			sb
					.append("&nbsp;&nbsp;\u8F6C\u5230\u7B2C&nbsp;<input id='_PageBar_Index' type='text' class='inputText' style='width:40px' ");
			sb
					.append("onKeyUp=\"value=value.replace(/\\D/g,'')\">&nbsp;\u9875");
			sb
					.append("&nbsp;&nbsp;<input type='button' onclick=\"if(!/^\\d+$/.test($V('_PageBar_Index'))||$V('_PageBar_Index')<1||$V('_PageBar_Index')>"
							+ totalPages
							+ "){alert('\u9519\u8BEF\u7684\u9875\u7801');$('_PageBar_Index').focus();}else{var pageIndex = ($V('_PageBar_Index')-1)>0?$V('_PageBar_Index')-1:0;DataGrid.setParam('"
							+ id
							+ "','"
							+ "_SKY_PAGEINDEX"
							+ "',pageIndex);DataGrid.loadData('"
							+ id
							+ "');}\" class='inputButton' value='\u8DF3\u8F6C'>");
		}
		sb.append("</div>");
		sb.append("<div style='float:left;font-family:Tahoma'>");
		sb.append("\u5171 " + total + " \u6761\u8BB0\u5F55\uFF0C\u6BCF\u9875 "
				+ pageSize + " \u6761\uFF0C\u5F53\u524D\u7B2C "
				+ (totalPages != 0 ? pageIndex + 1 : 0) + " / " + totalPages
				+ " \u9875</div>");
		return sb.toString();
	}

	
	public String getSortString() {
		if (SortString.length() == 0)
			return "";
		String str = SortString.toString();
		if (sortPattern.matcher(str).matches())
			return " order by " + SortString.toString();
		else
			return "";
	}
	
	public boolean isWebMode() {
		return WebMode;
	}

	public void setWebMode(boolean webMode) {
		WebMode = webMode;
	}

	public String getParam(String key) {
		return (String) Params.get(key);
	}

	public Mapx getParams() {
		return Params;
	}

	public void setParams(Mapx params) {
		Params = params;
	}

	public HtmlTable getTemplate() {
		return Template;
	}

	public void setTemplate(HtmlTable table) {
		Template = table;
	}

	public DataTable getDataSource() {
		return DataSource;
	}

	public boolean isSortFlag() {
		return SortFlag;
	}

	public void setSortFlag(boolean sortFlag) {
		SortFlag = sortFlag;
	}

	public HtmlTable getTable() {
		return Table;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public boolean isTreeFlag() {
		return TreeFlag;
	}

	protected void setTreeFlag(boolean treeFlag) {
		TreeFlag = treeFlag;
	}

	public String getMethod() {
		return Method;
	}

	public void setMethod(String method) {
		Method = method;
	}

	public String getTagBody() {
		return TagBody;
	}

	public void setTagBody(String tagBody) {
		TagBody = tagBody;
	}

	public boolean isMultiSelect() {
		return MultiSelect;
	}

	public void setMultiSelect(boolean multiSelect) {
		MultiSelect = multiSelect;
	}

	public boolean isAutoFill() {
		return AutoFill;
	}

	public void setAutoFill(boolean autoFill) {
		AutoFill = autoFill;
	}

	public boolean isScroll() {
		return Scroll;
	}

	public void setScroll(boolean scroll) {
		Scroll = scroll;
	}

	public int getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public boolean isLazy() {
		return Lazy;
	}

	public void setLazy(boolean lazy) {
		Lazy = lazy;
	}

    public PageInfo getPageInfo() {
        if(PageInfo == null)
            PageInfo = new PageInfo();
        return PageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        PageInfo = pageInfo;
    }

}
