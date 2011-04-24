package com.xdarkness.cmcore.template;

import com.xdarkness.framework.util.Errorx;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.Treex;

public class TemplateParser {
	private String template;
	private Treex tree;
	private Treex.TreeNode currentParent;
	private TemplateConfig config;

	public TemplateParser(String template) {
		this.template = template;
	}

	public void parse() {
		if (this.tree != null) {
			return;
		}
		if (this.template == null) {
			Errorx.addError("待解析的模板不能为空!");
			return;
		}
		this.template = this.template.trim();
		String head = this.template.substring(0, this.template.indexOf("\n"));
		if (head.toLowerCase().startsWith("<z:config")) {
			parseConfig(head);
			this.template = this.template
					.substring(this.template.indexOf("\n") + 1);
		} else {
			Errorx.addMessage("第1行不是模板配置信息!");
			this.config = new TemplateConfig("未命名", "Unknown", null, null,
					null, "<%", "%>");
		}
		char[] cs = this.template.toCharArray();
		int currentLineNo = 0;
		int holderStartIndex = -1;
		int htmlStartIndex = 0;
		int htmlStartLineNo = 0;
		int scriptStartIndex = -1;
		int scriptStartLineNo = -1;
		this.tree = new Treex();
		this.currentParent = this.tree.getRoot();
		String lowerTemplate = this.template.toLowerCase();
		for (int i = 0; i < cs.length; i++) {
			char c = cs[i];
			if (c == '\n') {
				currentLineNo++;
			}
			if (scriptStartIndex > 0) {
				if ((c != '>')
						|| (this.template.indexOf(this.config.getScriptEnd(), i
								- this.config.getScriptEnd().length() + 1) != i
								- this.config.getScriptEnd().length() + 1))
					continue;
				TemplateFragment tf = new TemplateFragment();
				tf.Type = 4;
				tf.FragmentText = this.template.substring(scriptStartIndex
						+ this.config.getScriptStart().length(), i
						- this.config.getScriptEnd().length() + 1);
				tf.StartLineNo = scriptStartLineNo;
				this.currentParent.addChild(tf);
				htmlStartIndex = i + 1;
				htmlStartLineNo = currentLineNo;
				scriptStartIndex = -1;
			} else if ((c == '$') && (i < cs.length - 1)
					&& (cs[(i + 1)] == '{')) {
				if (holderStartIndex >= 0) {
					Errorx.addMessage("第" + currentLineNo
							+ "行可能有错误，疑似占位符未正常结束!");
					htmlStartIndex = holderStartIndex;
					htmlStartLineNo = currentLineNo;
				}
				if ((htmlStartIndex != -1) && (htmlStartIndex != i)) {
					TemplateFragment tf = new TemplateFragment();
					tf.Type = 1;
					tf.FragmentText = this.template
							.substring(htmlStartIndex, i);
					tf.StartLineNo = htmlStartLineNo;
					this.currentParent.addChild(tf);
					htmlStartIndex = -1;
				}
				holderStartIndex = i;
			} else if ((c == '}') || (c == ' ') || (c == '\n')) {
				if (holderStartIndex >= 0) {
					if ((c == '}') && (holderStartIndex + 2 < i)) {
						TemplateFragment tf = new TemplateFragment();
						tf.Type = 3;
						tf.FragmentText = this.template.substring(
								holderStartIndex + 2, i);
						tf.StartLineNo = currentLineNo;
						tf.StartCharIndex = holderStartIndex;
						tf.EndCharIndex = i;
						this.currentParent.addChild(tf);
						htmlStartIndex = i + 1;
						htmlStartLineNo = currentLineNo;
					} else {
						Errorx.addMessage("第" + currentLineNo
								+ "行可能有错误，疑似占位符未正常结束!");
						htmlStartIndex = holderStartIndex;
						htmlStartLineNo = currentLineNo;
					}
				}
				holderStartIndex = -1;
			} else if (c == '<') {
				int index = this.template.indexOf(":", i);
				boolean tagEndFlag = true;
				boolean tagStartFlag = true;
				if (index - i < 30) {
					String str = this.template.substring(i + 1, index);
					for (int k = 0; i < str.length(); k++) {
						if ((str.charAt(k) != '/')
								&& (!Character.isLetterOrDigit(str.charAt(k)))) {
							tagStartFlag = false;
							break;
						}
					}
					if ((tagStartFlag) && (str.startsWith("/"))) {
						tagEndFlag = true;
						tagStartFlag = false;
					}
				}
				if ((lowerTemplate.indexOf("<z:", i) == i)
						|| (lowerTemplate.indexOf("<cms:", i) == i)
						|| (lowerTemplate.indexOf("<custom:", i) == i)) {
					int tagEnd = getTagEnd(cs, i + 1);
					if (tagEnd < 0) {
						Errorx.addError("第" + currentLineNo + "行有错误，置标未正确结束!");
						return;
					}
					if ((htmlStartIndex != -1) && (htmlStartIndex != i)) {
						TemplateFragment tf = new TemplateFragment();
						tf.Type = 1;
						tf.FragmentText = this.template.substring(
								htmlStartIndex, i);
						tf.StartLineNo = htmlStartLineNo;
						this.currentParent.addChild(tf);
						htmlStartIndex = -1;
					}
					String tag = this.template.substring(i + 1, tagEnd).trim();
					TemplateFragment tf = new TemplateFragment();
					parseTagAttributes(tf, tag);
					if (tag.endsWith("/")) {
						tf.StartLineNo = currentLineNo;
						tf.Type = 2;
						tf.StartCharIndex = i;
						tf.EndCharIndex = tagEnd;
						tf.FragmentText = null;
						this.currentParent.addChild(tf);
					} else {
						tf.StartLineNo = currentLineNo;
						tf.Type = 2;
						tf.StartCharIndex = i;
						Treex.TreeNode tn = this.currentParent.addChild(tf);
						this.currentParent = tn;
					}

				} else if ((lowerTemplate.indexOf("</z:", i) == i)
						|| (lowerTemplate.indexOf("</cms:", i) == i)
						|| (lowerTemplate.indexOf("</custom:", i) == i)) {
					String tagEnd = this.template.substring(i, this.template
							.indexOf(">", i) + 1);
					TemplateFragment tf = (TemplateFragment) this.currentParent
							.getData();
					if (tf == null) {
						Errorx.addError("第" + currentLineNo + "行有错误，发现置标结束标记:"
								+ tagEnd + "，但没有找到相应的置标开始标记!");
						return;
					}
					String prefix = tagEnd.substring(2, tagEnd.indexOf(":"));
					String tagName = tagEnd.substring(tagEnd.indexOf(":") + 1,
							tagEnd.length() - 1);
					if ((!prefix.equalsIgnoreCase(tf.TagPrefix))
							|| (!tagName.equalsIgnoreCase(tf.TagName))) {
						Errorx.addError("第" + currentLineNo + "行有错误，发现置标结束标记:"
								+ tagEnd + "，但对应的置标开始标记是:<" + tf.TagPrefix
								+ ":" + tf.TagName + ">");
						return;
					}
					tf.FragmentText = this.template.substring(getTagEnd(cs,
							tf.StartCharIndex + 1) + 1, i);
					tf.EndCharIndex = (i = this.template.indexOf('>', i));
					this.currentParent = this.currentParent.getParent();
					htmlStartIndex = i + 1;
					htmlStartLineNo = currentLineNo;
				} else {
					if ((this.template.indexOf(this.config.getScriptStart(), i) != i)
							|| (htmlStartIndex == -1) || (htmlStartIndex == i))
						continue;
					TemplateFragment tf = new TemplateFragment();
					tf.Type = 1;
					tf.FragmentText = this.template
							.substring(htmlStartIndex, i);
					tf.StartLineNo = htmlStartLineNo;
					this.currentParent.addChild(tf);
					htmlStartIndex = -1;
					scriptStartIndex = i;
					scriptStartLineNo = currentLineNo;
				}
			}

		}

		if ((htmlStartIndex != -1) && (htmlStartIndex != cs.length - 1)) {
			TemplateFragment tf = new TemplateFragment();
			tf.Type = 1;
			tf.FragmentText = this.template.substring(htmlStartIndex);
			tf.StartLineNo = htmlStartLineNo;
			this.currentParent.addChild(tf);
			htmlStartIndex = -1;
		}
	}

	public int getTagEnd(char[] cs, int start) {
		char lastStringChar = '\000';
		for (int i = start; i < cs.length; i++) {
			char c = cs[i];
			if ((c == '"') || (c == '\'')) {
				if ((i > 0) && (cs[(i - 1)] == '\\')) {
					continue;
				}
				if (lastStringChar == c)
					lastStringChar = '\000';
				else if (lastStringChar == 0) {
					lastStringChar = c;
				}
			}
			if ((c == '>') && (lastStringChar == 0)) {
				return i;
			}
			if ((c == '<') && (lastStringChar == 0)) {
				return -1;
			}
		}
		return -1;
	}

	public void parseConfig(String head) {
		head = head.trim();
		head = head.substring(head.indexOf(" "), head.length() - 1).trim();
		if (head.endsWith("/")) {
			head = head.substring(0, head.length() - 1).trim();
		}
		head = head.replaceAll("\\s+", " ");
		Mapx map = XString.splitToMapx(head, " ", "=");
		String Name = map.getString("Name");
		String Type = map.getString("Type");
		String Author = map.getString("Author");
		String Version = map.getString("Version");
		String Description = map.getString("Description");
		String ScriptStart = map.getString("ScriptStart");
		String ScriptEnd = map.getString("ScriptEnd");
		if (XString.isEmpty(ScriptStart)) {
			ScriptStart = "<!--%";
		}
		if (XString.isEmpty(ScriptEnd)) {
			ScriptEnd = "%-->";
		}
		this.config = new TemplateConfig(Name, Type, Author, Version,
				Description, ScriptStart, ScriptEnd);
	}

	public void parseTagAttributes(TemplateFragment tf, String tag) {
		String prefix = tag.substring(0, tag.indexOf(":")).trim();
		String tagName = tag.substring(tag.indexOf(":") + 1, tag.indexOf(" "))
				.trim();
		tag = tag.substring(tag.indexOf(" ") + 1).trim();
		if (tag.endsWith("/")) {
			tag = tag.substring(0, tag.length() - 1).trim();
		}
		tag = tag.replaceAll("\\s+", " ");
		Mapx map = new Mapx();
		char lastStringChar = '\000';
		int nameStartIndex = 0;
		int valueStartIndex = -1;
		String key = null;
		char[] cs = tag.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			char c = cs[i];
			if ((c == '=') && (lastStringChar == 0)) {
				key = tag.substring(nameStartIndex, i);
				nameStartIndex = 0;
			}
			if ((c == ' ') && (lastStringChar == 0)) {
				nameStartIndex = i + 1;
			}
			if (((c != '"') && (c != '\''))
					|| ((i > 0) && (cs[(i - 1)] == '\\'))) {
				continue;
			}
			if (lastStringChar == c) {
				lastStringChar = '\000';
				map.put(key, tag.subSequence(valueStartIndex, i));
			} else {
				lastStringChar = c;
				valueStartIndex = i + 1;
			}
		}

		tf.TagPrefix = prefix;
		tf.TagName = tagName;
		tf.Attributes = map;
	}

	public TemplateConfig getConfig() {
		return this.config;
	}

	public Treex getTree() {
		return this.tree;
	}
}

/*
 * com.xdarkness.cmcore.template.TemplateParser JD-Core Version: 0.6.0
 */