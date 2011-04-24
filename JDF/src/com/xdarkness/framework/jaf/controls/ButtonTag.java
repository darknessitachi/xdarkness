package com.xdarkness.framework.jaf.controls;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

public class ButtonTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	private String id;
	private String onClick;
	private String priv;
	private boolean disabled;
	private String image;
	private String icon;
	private String text;
	
	public static final Pattern PImg = Pattern
			.compile("<img .*?src\\=.*?>", Pattern.CASE_INSENSITIVE);
	private static final String Img_Template = "<img src=\"{src}\" width=\"20\" height=\"20\"/>";

	@Override//doAfterBody
	public int doEndTag() throws JspException {
		String content;
		if(getBodyContent() == null) {
			content = this.text;
		} else {
			content = getBodyContent().getString();
		}
		
		try {
			Matcher matcher = PImg.matcher(content);
			String img = null;
			String text = null;
			if (matcher.find()) {
				img = content.substring(matcher.start(), matcher.end());
				text = content.substring(matcher.end());
			} else {
				img = Img_Template.replace("{src}", image);
				text = content;
			}
			pageContext.getOut().print(
					getHtml(this.id, this.onClick, this.priv, img, text
							+ "&nbsp;", this.disabled));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Tag.EVAL_PAGE;
	}

	public static String getHtml(String onclick, String img, String text,
			boolean disabled) {
		return getHtml(null, onclick, null, img, text, disabled);
	}

	public static String getHtml(String onclick, String priv, String img,
			String text) {
		return getHtml(null, onclick, priv, img, text, false);
	}

	public static String getHtml(String onclick, String priv, String img,
			String text, boolean disabled) {
		return getHtml(null, onclick, priv, img, text, disabled);
	}

	public static String getHtml(String id, String onclick, String priv,
			String img, String text, boolean disabled) {
		StringBuffer sb = new StringBuffer();
		sb
				.append("<a href='javascript:void(0);' xtype='zPushBtn' class='zPushBtn"
						+ (disabled ? " zPushBtnDisabled" : "")
						+ "' hidefocus='true' tabindex='-1' onselectstart='return false' id='"
						+ (id == null ? "" : id) + "'");
		if (onclick != null) {
			if (disabled)
				sb.append(" onclickbak=\"" + onclick + ";return false;");
			else {
				sb.append(" onClick=\"" + onclick + ";return false;");
			}
		}
		if (priv != null) {
			sb.append("\" priv=\"" + priv);
		}
		sb.append("\" >");
		sb.append(img);
		sb.append("<b>" + text + "</b></a>");
		return sb.toString();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOnClick() {
		return this.onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getPriv() {
		return this.priv;
	}

	public void setPriv(String priv) {
		this.priv = priv;
	}

	public boolean isDisabled() {
		return this.disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public static void main(String[] args) {
		System.out.println(Img_Template.replace("{src}", "../../a.gif"));;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
