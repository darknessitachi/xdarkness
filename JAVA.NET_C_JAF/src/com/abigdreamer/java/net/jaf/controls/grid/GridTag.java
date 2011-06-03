package com.abigdreamer.java.net.jaf.controls.grid;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import com.abigdreamer.java.net.io.FileUtil;

public class GridTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	protected String ToolBar;
	protected String Datagrid;

	private String title;
	private String image;

	public int doAfterBody() throws JspException {
		
		String content = FileUtil.readText(GridTag.class.getResourceAsStream("Grid.template"));//Config.getClassesPath()+"com/sky/framework/controls/grid/");
		try {
			content = content.replace("{Title}", title)
							 .replace("{Image}", image)
							 .replace("{ToolBar}", ToolBar)
							 .replace("{Datagrid}",Datagrid);
			getPreviousOut().print(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Tag.EVAL_PAGE;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public static void main(String[] args) {
//		String content = FileUtil.readText();//Config.getClassesPath()+"com/sky/framework/controls/grid/");
		System.out.println(GridTag.class.getResourceAsStream("Grid.template"));
	}

}
