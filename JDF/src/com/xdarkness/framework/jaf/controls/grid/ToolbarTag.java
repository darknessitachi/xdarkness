package com.xdarkness.framework.jaf.controls.grid;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;


public class ToolbarTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;

	public int doAfterBody() throws JspException {
		String content = getBodyContent().getString();
		GridTag gridTag = (GridTag) TagSupport.findAncestorWithClass(this,
                GridTag.class);
		gridTag.ToolBar = content;
        
		return Tag.EVAL_PAGE;
	}

}
