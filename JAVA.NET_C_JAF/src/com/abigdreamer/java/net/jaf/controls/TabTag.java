package com.abigdreamer.java.net.jaf.controls;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import com.abigdreamer.java.net.jaf.WebConfig;
import com.abigdreamer.java.net.jaf.html.element.HtmlDiv;
import com.abigdreamer.java.net.jaf.html.element.HtmlScript;

/**
 * @author Darkness Create on 2010-6-1 上午10:55:37
 * @version 1.0
 * @since JDF1.0
 */
public class TabTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	protected ArrayList<String> ChildTabs;
	private String id;
	private int max;
	private boolean showToolBar;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		id = "";
		max = 5;
		showToolBar = false;
		ChildTabs = new ArrayList<String>();
	}

	public int doAfterBody() throws JspException {
		String content = getBodyContent().getString();
		try {
			StringBuffer sb = new StringBuffer();
			sb
					.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"6\" class=\"blockTable\">");
			sb.append("  <tr>");
			sb
					.append("    <td height=\"26\" valign=\"middle\" class=\"blockTd\">");
			sb
					.append("    <table width=\"100%\" border='0' cellpadding='0' cellspacing='0' style=\"background:url("
							+ WebConfig.getContextPath()
							+ "Framework/Images/divchildtabBarBg.gif) repeat-x left bottom; margin-bottom:1px;\">");
			sb.append("    <tr>");
			sb
					.append("      <td id='"
									+ id
									+ "tabdiv' valign=\"bottom\" height='30' style=\"padding:0 0 0 6px;_padding:0 0 0 2px;\">");
			sb.append(content);
			sb.append("\t\t\t</td>");
			sb.append("   </tr>");
			sb.append("   </table>");
			sb.append("   </td>");
			sb.append("  </tr>");
			sb.append("  <tr>");
			sb.append("     <td style=\"padding:2px 6px;\">");

			String[] arr = content.split("<\\/div>\\s*<div ");
			String tabString = "";
			String selectedID = null;
			for (int i = 0; i < arr.length; i++) {
				String html = arr[i].trim();
				if (i == 0)
					html = html + "</div>";
				else if (i == arr.length - 1)
					html = "<div " + html;
				else {
					html = "<div " + html + "</div>";
				}
				if(!"".equals(tabString)) {
					tabString += ",";
				}
				HtmlDiv div = new HtmlDiv();
				div.parseHtml(html);
				tabString += "'" + div.getAttribute("id") + "'";
				if (div.getAttribute("class").equals("divchildtabCurrent")) {
					sb
							.append("<iframe src=\""
									+ div.getAttribute("src")
									+ "\" width=\"100%\" height=\"0\" id=\"_ChildTabFrame_"
									+ div.getAttribute("id")
									+ "\" frameborder=\"0\" scrolling=\"auto\" allowtransparency=\"true\"></iframe>");
					selectedID = div.getAttribute("id").toString();
				} else {
					sb
							.append("<iframe src=\""
									+ div.getAttribute("src")
									+ "\" width='100%' height='0' id=\"_ChildTabFrame_"
									+ div.getAttribute("id")
									+ "\" frameborder=\"0\" scrolling=\"auto\" allowtransparency=\"true\"></iframe>");
				}
			}
			sb.append("     </td>");
			sb.append("  </tr>");
			sb.append("</table>");
			HtmlScript script = new HtmlScript();
			script
					.setInnerHTML("Page.onLoad(function(){Tab.initFrameHeight(\"_ChildTabFrame_"
							+ selectedID + "\");},5);");
			
			script.setInnerHTML("Tab['" + this.id + "']={max:" + this.max
					+ ",Tabs:["+tabString+"]}");
			sb.append(script.getOuterHtml());
			
			sb.append(script.getOuterHtml());
			getPreviousOut().print(sb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Tag.EVAL_PAGE;
	}
}

