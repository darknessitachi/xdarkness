package com.xdarkness.jaf.controls;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import com.xdarkness.jaf.MainServlet;
import com.xdarkness.jaf.controls.html.HtmlDiv;
import com.xdarkness.jaf.controls.html.HtmlScript;

/**
 * 
 * @author Darkness Create on 2010-6-1 上午10:55:37
 * @version 1.0
 * @since JDF1.0
 */
/**
 * 
 * @author Darkness Create on 2010-6-1 上午10:55:37
 * @version 1.0
 * @since JDF1.0
 */
public class TabTag extends BodyTagSupport {

    private static final long serialVersionUID = 1070717553563114556L;

    protected ArrayList<String> ChildTabs = new ArrayList<String>();
    private String id = "";

    public TabTag() {
    }

    public int doAfterBody() throws JspException {

        String content = getBodyContent().getString();

        try {

            StringBuffer sb = new StringBuffer();
            sb.append(
                            "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"6\" class=\"blockTable\">")
                    .append("  <tr>")
                    .append(
                            "    <td height=\"26\" valign=\"middle\" class=\"blockTd\">")
                    .append(
                            "    <table width=\"100%\" border='0' cellpadding='0' cellspacing='0' style=\"background:url(")
                    .append(
                            "    Framework/Images/divchildtabBarBg.gif) repeat-x left bottom; margin-bottom:1px;\">")
                    .append("    <tr>")
                    .append(
                            "      <td id='" + id + "tabdiv' valign=\"bottom\" height='30' style=\"padding:0 0 0 6px;_padding:0 0 0 2px;\">")

                    .append(content)

                    .append("\t\t\t</td>")
                    .append("   </tr>")
                    .append("   </table>")
                    .append("   </td>")
                    .append("  </tr>")
                    .append("  <tr>")
                    .append("     <td id='" + id + "tabframe' style=\"padding:2px 6px;\" vAlign='top'>");
            
            String arr[] = content.split("<\\/div>\\s*<div ");
            String selectedID = null;
            for (int i = 0; i < arr.length; i++) {
                String html = arr[i].trim();
                if (i == 0)
                    html = html + "</div>";
                else if (i == arr.length - 1)
                    html = "<div " + html;
                else
                    html = "<div " + html + "</div>";
                
                HtmlDiv div = new HtmlDiv();
                div.parseHtml(html);
                if (div.getAttribute("class").equals("divchildtabCurrent")) {
//                    sb.append("<div style='padding-left:10px;' id='_ChildTabFrame_"
//                                    + div.getAttribute("id")
//                                    + "'>"
//                                    + ChildTabs.get(i) + "</div>");
                     sb
                     .append("<iframe src=\""
                     + div.getAttribute("src")
                     + "\" width=\"100%\" height=\"0\" id=\"_ChildTabFrame_"
                     + div.getAttribute("id")
                     + "\" frameborder=\"0\" scrolling=\"auto\"  allowtransparency=\"true\"></iframe>");
                    selectedID = div.getAttribute("id").toString();
                } else {
//                    sb.append("<div style=\'padding-left:10px;\' id='_ChildTabFrame_"
//                                    + div.getAttribute("id")
//                                    + "' style='height:0px;display:none;'>"
//                                    + ChildTabs.get(i) + "</div>");
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
            sb.append(script.getOuterHtml());
            getPreviousOut().print(sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Tag.EVAL_PAGE;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
