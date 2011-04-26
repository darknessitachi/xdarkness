package com.xdarkness.cmcore.tag;

import com.xdarkness.cmcore.template.TemplateBase;
import com.xdarkness.cmcore.template.TemplateContext;
import com.xdarkness.framework.util.Mapx;

public abstract class TagBase {
	public static final int SKIP = 1;
	public static final int CONTINUE = 2;
	public static final int END = 2;
	protected TemplateBase template;
	protected TemplateContext context;
	protected Mapx attributes = new Mapx();

	public abstract int onTagStart();

	public abstract String[] getMandantoryAttributeNames();

	public abstract String[] getAllAttributeNames();

	public abstract int onTagEnd();

	public abstract boolean isIterative();

	public abstract boolean prepareNext();

	public abstract String getPrefix();

	public abstract String getTagName();

	public boolean checkAttribute() {
		String[] mandantory = getMandantoryAttributeNames();
		for (int i = 0; i < mandantory.length; i++) {
			String v = this.attributes.getString(mandantory[i]);
			if (XString.isEmpty(v)) {
				this.context.addError("标签" + getPrefix() + ":" + getTagName()
						+ "的属性名" + mandantory[i] + "不能为空");
			}
		}
		return true;
	}

	public TemplateContext getContext() {
		return this.context;
	}

	public void setContext(TemplateContext context) {
		this.context = context;
	}

	public TemplateBase getTemplate() {
		return this.template;
	}

	public void setTemplate(TemplateBase template) {
		this.template = template;
	}

	public void setAttribute(String key, String value) {
		String[] all = getAllAttributeNames();
		boolean flag = false;
		for (int i = 0; i < all.length; i++) {
			if (all[i].equalsIgnoreCase(key)) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			this.context.addError("标签" + getPrefix() + ":" + getTagName()
					+ "不支持属性名" + key);
		}
		this.attributes.put(key, value);
	}

	public String getAttribute(String key) {
		return this.attributes.getString(key);
	}

	public Mapx getAttributes() {
		return this.attributes;
	}
}

/*
 * com.xdarkness.cmcore.tag.TagBase JD-Core Version: 0.6.0
 */