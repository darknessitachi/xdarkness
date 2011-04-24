package com.xdarkness.framework.jaf.controls;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import com.xdarkness.framework.Config;
import com.xdarkness.framework.orm.data.DataCollection;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.ServletUtil;
import com.xdarkness.framework.util.XString;

/**
 * 
 * @author Darkness create on 2010 2010-12-6 上午11:52:16
 * @version 1.0
 * @since JDF 1.0
 */
public class SelectTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String onChange;
	private String style;
	private int listWidth;
	private int listHeight;
	private String listURL;
	private String verify;
	private String condition;
	private String value;
	private String className;
	private boolean disabled;
	private boolean input;
	private String code;
	private String conditionField;
	private String conditionValue;
	private boolean showValue;
	private boolean lazy;
	private boolean defaultblank;
	private String method;
	private static CodeSource codeSourceInstance;
	private int selectedIndex = 0;

	private int optionCount = 0;

	private static Object mutex = new Object();

	public static final Pattern POption = Pattern
			.compile(
					"<(span|option).*?value=(\\\"|\\')(.*?)\\2.*?>(.*?)</(span|option)>",
					Pattern.CASE_INSENSITIVE);

	public void setPageContext(PageContext pc) {
		super.setPageContext(pc);
		this.id = null;
		this.name = null;
		this.className = null;
		this.code = null;
		this.method = null;
		this.condition = null;
		this.conditionField = null;
		this.conditionValue = null;
		this.disabled = false;
		this.input = false;
		this.showValue = false;
		this.value = null;
		this.verify = null;
		this.onChange = null;
		this.style = null;
		this.defaultblank = false;
		this.listWidth = 0;
		this.listHeight = 0;
		this.listURL = null;
		this.lazy = false;
		this.selectedIndex = 0;
		this.optionCount = 0;
	}

	public int doAfterBody() throws JspException {
		String content = getBodyContent().getString();
		try {
			getPreviousOut().print(getHtml(content));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Tag.EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		if ((this.bodyContent == null)
				|| (XString.isEmpty(this.bodyContent.getString()))) {
			try {
				this.pageContext.getOut().print(getHtml(""));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return Tag.EVAL_PAGE;
	}

	public String getHtml(String content) {
		content = parseOptions(content);
		String codeData = "";
		if ((XString.isNotEmpty(this.code))
				|| (XString.isNotEmpty(this.method))) {
			codeData = codeData + getCodeData();
		}
		if (XString.isEmpty(this.id)) {
			this.id = HtmlElement.generateId();
		}
		if (XString.isEmpty(this.name)) {
			this.name = this.id;
		}
		StringBuffer sb = new StringBuffer();
		sb
				.append("<div id='" + this.id + "' name='" + this.name
						+ "' selectedIndex='" + this.selectedIndex
						+ "' xtype='Select'");
		if (XString.isNotEmpty(this.className)) {
			sb.append("  class='" + this.className + " ");
			sb.append(" zSelect'");
		} else {
			sb.append(" class='zSelect'");
		}
		if (XString.isNotEmpty(this.style)) {
			sb
					.append(" style=\"display:inline-block; *zoom: 1;*display: inline;vertical-align:middle;position:relative;height:20px;white-space: nowrap;"
							+ this.style + "\"");
			sb.append(" styleOriginal=\"" + this.style + "\"");
		} else {
			sb
					.append(" style=\"display:inline-block; *zoom: 1;*display: inline;vertical-align:middle;position:relative;height:20px;white-space: nowrap;\"");

			sb.append(" styleOriginal='NULL'");
		}
		if (XString.isNotEmpty(this.onChange)) {
			sb.append(" onChange=\"" + this.onChange + "\"");
		}
		if (this.disabled) {
			sb.append(" disabled=\"" + this.disabled + "\"");
		}
		if (XString.isNotEmpty(this.className)) {
			sb.append(" zclass=\"" + this.className + "\"");
		}
		if (this.listWidth > 0) {
			sb.append(" listWidth=\"" + this.listWidth + "\"");
		}
		if (this.listHeight > 0) {
			sb.append(" listHeight=\"" + this.listHeight + "\"");
		}
		if (XString.isNotEmpty(this.listURL)) {
			sb.append(" listURL=\"" + this.listURL + "\"");
		}
		if (XString.isNotEmpty(this.verify)) {
			sb.append(" verify=\"" + this.verify + "\"");
		}
		if (XString.isNotEmpty(this.condition)) {
			sb.append(" condition=\"" + this.condition + "\"");
		}
		if (this.input) {
			sb.append(" input=\"" + this.input + "\"");
		}
		if (this.lazy) {
			sb.append(" lazy=\"" + this.lazy + "\"");
		}
		if (this.showValue) {
			sb.append(" showValue=\"" + this.showValue + "\"");
		}
		if (XString.isNotEmpty(this.code)) {
			sb.append(" code=\"" + this.code + "\"");
		}
		if (XString.isNotEmpty(this.method)) {
			sb.append(" method=\"" + this.method + "\"");
		}
		sb.append(" defaultblank=\"" + this.defaultblank + "\"");
		if (XString.isNotEmpty(this.conditionField)) {
			sb.append(" conditionField=\"" + this.conditionField + "\"");
		}
		if (XString.isNotEmpty(this.conditionValue)) {
			sb.append(" conditionValue=\"" + this.conditionValue + "\"");
		}
		if (XString.isNotEmpty(this.value)) {
			sb.append(" value=\"" + this.value + "\"");
			sb.append(" initValue=\"" + this.value + "\"");
		}
		sb.append(">");

		sb.append("<input type='text' id='" + this.id
				+ "_textField' xtype='select' autocomplete='off'");
		if (XString.isNotEmpty(this.verify)) {
			sb.append(" verify=\"" + this.verify + "\"");
		}
		sb.append(" name=\"" + this.name + "\"");
		if (XString.isNotEmpty(this.className)) {
			sb.append(" class=\"" + this.className + "Input\"");
		}
		if (XString.isNotEmpty(this.condition)) {
			sb.append(" condition=\"" + this.condition + "\"");
		}
		if (XString.isNotEmpty(this.style))
			sb
					.append(" style=\"vertical-align:top; background:transparent none; cursor:default;"
							+ this.style + "\"");
		else {
			sb
					.append(" style=\"vertical-align:top; background:transparent none; cursor:default;\"");
		}
		sb.append(" value=''/>");
		sb
				.append("<img class='arrowimg' src='"
						+ Config.getContextPath()
						+ "Framework/Images/blank.gif' width='18' height='20' id='"
						+ this.id
						+ "_arrow' style='position:relative; left:-17px; margin-right:-18px; cursor:pointer; "
						+ "width:18px; height:20px;vertical-align:top;'/>");
		sb
				.append("<div id='"
						+ this.id
						+ "_list' class='optgroup' style='text-align:left;display:none;'>");
		sb.append("<div id='" + this.id
				+ "_ul' style='left:-1px; width:-1px;'>");

		if (this.defaultblank) {
			sb.append(getOption("", ""));
		}
		sb.append(content);

		if (XString.isNotEmpty(codeData)) {
			sb.append(codeData);
		}
		sb.append("</div></div></div>");
		return sb.toString();
	}

	private String getOption(String text, String value) {
		this.optionCount += 1;
		return getOptionHtml(this.id, text, value, false);
	}

	public static String getOptionHtml(String text, String value, boolean flag) {
		return "<a href=\"javascript:void(0);\" onclick=\"Selector.onItemClick(this);\" onmouseover='Selector.onItemMouseOver(this)' "
				+ (flag ? "selected='true'" : "")
				+ " hidefocus value=\""
				+ value + "\">" + text + "</a>";
	}
	
	public static String getOptionHtml(String id, String text, String value,
			boolean flag) {
		return "<a href=\"javascript:void(0);\" divId='select_" + id
				+ "' onclick=\"Selector.onItemClick(this);\" onmouseover='Selector.onItemMouseOver(this)' "
				+ ((flag) ? "selected='true'" : "") + " hidefocus value=\""
				+ value + "\">" + text + "</a>";
	}

	private String parseOptions(String content) {
		if (content.indexOf("select") >= 0) {
			HtmlSelect select = new HtmlSelect();
			try {
				select.parseHtml(content);

				this.id = XString.or(this.id, select.getID());
				this.className = XString.or(this.className, select.getClassName());
				this.style = XString.or(this.style, select.getStyle());
				this.code = XString.or(this.code, select.getAttribute("code"));
				this.condition = XString.or(this.condition, select.getAttribute("condition"));
				this.conditionField = XString.or(this.conditionField, select.getAttribute("conditionfield"));
				this.conditionValue = XString.or(this.conditionValue, select.getAttribute("conditionvalue"));
				this.disabled = "true".equals(select.getAttribute("disabled"));
				this.input = "true".equals(select.getAttribute("input"));
				this.defaultblank = "true".equals(select.getAttribute("defaultblank"));
				this.showValue = "true".equals(select.getAttribute("showvalue"));
				this.lazy = "true".equals(select.getAttribute("lazy"));
				this.value = XString.or(this.value, select.getAttribute("value"));
				this.verify = XString.or(this.verify, select.getAttribute("verify"));
				this.onChange = XString.or(this.onChange, select.getAttribute("onchange"));
				this.listURL = XString.or(this.listURL, select.getAttribute("listurl"));
				if (select.getIntAttribute("listwidth") > 0)
					this.listWidth = select.getIntAttribute("listwidth");
				if (select.getIntAttribute("listheight") > 0)
					this.listHeight = select.getIntAttribute("listheight");
				
				content = select.getInnerHTML();
			} catch (Exception e) {
				if (XString.isEmpty(this.id)) {
					throw new RuntimeException("必须为<sky:select>标签或<select>元素定义ID");
				}
			}
		}
		StringBuffer sb = new StringBuffer();
		Matcher m = POption.matcher(content);
		int lastIndex = 0;
		int i = 0;
		while (m.find(lastIndex)) {
			String tmp = content.substring(lastIndex, m.start());
			if (XString.isNotEmpty(tmp.trim())) {
				sb.append(tmp);
			}
			if (this.showValue)
				sb.append(getOption(m.group(3)
						+ (XString.isNotEmpty(m.group(4)) ? "-" + m.group(4)
								: ""), m.group(3)));
			else {
				sb.append(getOption(m.group(4), m.group(3)));
			}
			if (m.group().toLowerCase().substring(0, m.group().indexOf(">"))
					.indexOf("selected") > 0) {
				this.selectedIndex = i;
			}
			if (m.group(3).equals(this.value)) {
				this.selectedIndex = i;
			}
			lastIndex = m.end();
			i++;
		}
		if (lastIndex != content.length() - 1) {
			sb.append(content.substring(lastIndex));
		}
		if (i != 0) {
			content = sb.toString();
		}
		return content;
	}

	private String getCodeData() {
		if (this.lazy) {
			return "";
		}
		DataTable dt = null;
		Mapx params = ServletUtil
				.getParameterMap((HttpServletRequest) this.pageContext
						.getRequest());
		if (XString.isNotEmpty(this.conditionField)) {
			params.put("ConditionField", this.conditionField);
			params.put("ConditionValue", this.conditionValue);
		} else {
			params.put("ConditionField", "1");
			params.put("ConditionValue", "1");
		}
		if ((XString.isEmpty(this.method)) && (this.code.startsWith("#"))) {
			this.method = this.code.substring(1);
		}
		if (XString.isNotEmpty(this.method)) {
			String className = this.method.substring(0, this.method
					.lastIndexOf("."));
			String methodName = this.method.substring(this.method
					.lastIndexOf(".") + 1);
			try {
				Class c = Class.forName(className);
				Object o = c.getMethod(methodName, new Class[] { Mapx.class })
						.invoke(null, new Object[] { params });
				dt = (DataTable) o;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("确认类" + className + "的方法"
						+ methodName + "返回值是DataTable类型!");
			}
		} else {
			initCodeSource();
			dt = codeSourceInstance.getCodeData(this.code, params);
		}
		StringBuffer sb = new StringBuffer();
		if (dt != null) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (dt.getString(i, 0).equals(this.value)) {
					this.selectedIndex = this.optionCount;
				}
				if (!this.showValue)
					sb
							.append(getOption(dt.getString(i, 1), dt.getString(
									i, 0)));
				else {
					sb.append(getOption(dt.getString(i, 0) + "-"
							+ dt.getString(i, 1), dt.getString(i, 0)));
				}
			}
			if (dt.getColCount() > 2) {
				sb.append("<script>Page.onLoad(Selector_" + this.id
						+ "_Init,10);");
				sb.append("function Selector_" + this.id + "_Init(){");
				sb.append(DataCollection.dataTableToJS(dt));
				sb
						.append("$('" + this.id
								+ "').DataSource = new DataTable();;");
				sb.append("$('" + this.id
						+ "').DataSource.init(_SKY_Cols,_SKY_Values);");
				sb.append("}\n</script>\n");
			}
		}
		return sb.toString();
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getListWidth() {
		return this.listWidth;
	}

	public void setListWidth(int listWidth) {
		this.listWidth = listWidth;
	}

	public String getOnChange() {
		return this.onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getVerify() {
		return this.verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public int getListHeight() {
		return this.listHeight;
	}

	public void setListHeight(int listHeight) {
		this.listHeight = listHeight;
	}

	public String getListURL() {
		return this.listURL;
	}

	public void setListURL(String listURL) {
		this.listURL = listURL;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public boolean getDisabled() {
		return this.disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isInput() {
		return this.input;
	}

	public void setInput(boolean input) {
		this.input = input;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getConditionField() {
		return this.conditionField;
	}

	public void setConditionField(String conditionField) {
		this.conditionField = conditionField;
	}

	public String getConditionValue() {
		return this.conditionValue;
	}

	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}

	public boolean getShowValue() {
		return this.showValue;
	}

	public void setShowValue(boolean showValue) {
		this.showValue = showValue;
	}

	public boolean getInput() {
		return this.input;
	}

	public static void initCodeSource() {
		if (codeSourceInstance == null)
			synchronized (mutex) {
				if (codeSourceInstance == null) {
					String className = Config.getValue("App.CodeSource");
					if (XString.isEmpty(className)) {
						LogUtil.warn("framework.xml中未配置CodeSource类!");
						return;
					}
					try {
						Class c = Class.forName(className);
						Object o = c.newInstance();
						codeSourceInstance = (CodeSource) o;
					} catch (Exception e) {
						throw new RuntimeException(
								"framework.xml中的CodeSource配置不正确,请确认类"
										+ className + "存在!");
					}
				}
			}
	}

	public static CodeSource getCodeSourceInstance() {
		initCodeSource();
		return codeSourceInstance;
	}

	public boolean isLazy() {
		return this.lazy;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}

	public boolean isDefaultblank() {
		return this.defaultblank;
	}

	public void setDefaultblank(boolean defaultblank) {
		this.defaultblank = defaultblank;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
}
