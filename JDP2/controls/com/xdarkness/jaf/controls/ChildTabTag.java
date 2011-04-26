package com.xdarkness.jaf.controls;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.xdarkness.common.util.StringFormat;
import com.xdarkness.common.util.StringUtil;

/**
 * 
 * @author Darkness Create on 2010-6-1 上午10:58:45
 * @version 1.0
 * @since JDF1.0
 */
public class ChildTabTag extends BodyTagSupport {

    private static final long serialVersionUID = 6717664844392506400L;

    private String id;

    private String title = "标题";

    private String icon = "tab-default";

    private String onClick;

    private String afterClick;

    private String src;

    private boolean selected;

    private boolean disabled;

    private boolean visible;

    private boolean lazy;
    
    private boolean showClose;

    private static int No = 0;

    public ChildTabTag() {
    }

    public void setPageContext(PageContext pc) {
        super.setPageContext(pc);
        id = null;
        onClick = "";
        afterClick = "";
        src = null;
        selected = false;
        disabled = false;
        visible = true;
        lazy = false;
    }

    public int doAfterBody() throws JspException {

        String content = getBodyContent().getString();
        TabTag tabTag = (TabTag) TagSupport.findAncestorWithClass(this,
                TabTag.class);
        tabTag.ChildTabs.add(content);

        try {

            if (StringUtil.isEmpty(id))
                id = "" + (No++);

            if (!StringUtil.isEmpty(onClick) && !onClick.trim().endsWith(";"))
                onClick = onClick.trim() + ";";

            String type = selected ? "Current" : disabled ? "Disabled" : "";
            String vStr = visible ? "" : "style='display:none'";
            String paddingString = showClose ? "style='padding-right:0px'" : "" ;

            String closeImageString = (showClose?"<img src="+((HttpServletRequest)pageContext.getRequest()).getContextPath() + "/jslib/resources/icons/close.gif"+"' onclick='Tab.closeChild(this.parentNode.parentNode.parentNode.id);'/>":"");
            
            StringFormat sf = new StringFormat(
                    "<div style='-moz-user-select:none;' onselectstart='return false' id='{id}' src='{src}' targetURL='{src}' {vStr} class='divchildtab{type}' onclick=\"{onClick}Tab.onChildTabClick(this);{afterClick}\" onMouseOver='Tab.onChildTabMouseOver(this)' onMouseOut='Tab.onChildTabMouseOut(this)'><img src='{icon}' /><b "+paddingString+"><span style='float:left'>{title}</span><span style='float:left'>"+closeImageString+"</span></b></div>",
                    id, (lazy || !selected) ? "javascript:void(0);" : src, src,
                    vStr, type, onClick, afterClick, icon, title);

            getPreviousOut().print(sf.toString());
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

    public String getOnClick() {
        return onClick;
    }

    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    public String getAfterClick() {
        return afterClick;
    }

    public void setAfterClick(String afterClick) {
        this.afterClick = afterClick;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isLazy() {
        return lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isShowClose() {
        return showClose;
    }

    public void setShowClose(boolean showClose) {
        this.showClose = showClose;
    }

}
