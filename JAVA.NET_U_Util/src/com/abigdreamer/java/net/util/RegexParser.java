package com.abigdreamer.java.net.util;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;



/**
 * 
 * @author Darkness
 * Create on Nov 19, 2010 2:34:19 PM
 * @version 1.0
 */
public class RegexParser {
	private String regex;
	private int currentPos;
	private String text;
	private String orginalText;
	private ArrayList list = new ArrayList(16);

	private ArrayList groups = null;

	private Mapx map = null;

	private int startPos = 0;

	private boolean caseIngore = true;

	private boolean lineWrapIngore = true;

	public RegexParser(String regex) {
		this(regex, true, true);
	}
	private org.apache.oro.text.regex.Pattern pattern;
	public MatchedMap[] getMatchedMaps(String content) {
//		content = content.replaceAll("\\r\\n", "\n");
//		ArrayList arr = new ArrayList();
//		PatternMatcherInput input = new PatternMatcherInput(content);
//		Perl5Matcher matcher = new Perl5Matcher();
//		while (matcher.contains(input, this.pattern)) {
//			MatchResult result = matcher.getMatch();
//			MatchedMap map = new MatchedMap();
//			for (int i = 0; i < this.itemFields.length; ++i) {
//				map.put(this.itemFields[i], result.group(i + 1));
//			}
//			map.MatchedString = result.group(0);
//			arr.add(map);
//		}
//		MatchedMap[] maps = new MatchedMap[arr.size()];
//		for (int i = 0; i < arr.size(); ++i) {
//			maps[i] = ((MatchedMap) arr.get(i));
//		}
//		return maps;
		return null;
	}
	Pattern tagPattern = Pattern.compile("<.*?>", Pattern.CASE_INSENSITIVE);
	public Mapx getMatchedMap(String content){
		Mapx map = getMapx();
		Object[] ks = map.keyArray();
		Object[] vs = map.valueArray();
		for (int i = 0; i < map.size(); i++) {
			String key = ks[i].toString();
			String value = vs[i].toString();
			if (!key.equalsIgnoreCase("Content")) {
				value = this.tagPattern.matcher(value)
						.replaceAll("");
			}
			value = XString.htmlDecode(value);
			value = value.trim();
			map.put(key, value);
		}
		return map;
	}
	
	public RegexParser(String regex, boolean caseIngoreFlag,
			boolean lineWrapIngoreFlag) {
		this.regex = (caseIngoreFlag ? regex.toLowerCase() : regex);
		this.caseIngore = caseIngoreFlag;
		this.lineWrapIngore = lineWrapIngoreFlag;
		parse();
	}

	public String getText() {
		return this.orginalText;
	}

	public synchronized void setText(String text) {
		this.orginalText = text;
		this.text = (this.caseIngore ? text.toLowerCase() : text);
		this.currentPos = 0;
	}

	private void parse() {
		if (XString.isEmpty(this.regex)) {
			throw new RuntimeException("简易正则表达式不能为空!");
		}
		int lastIndex = 0;
		while (true) {
			int start = this.regex.indexOf("${", lastIndex);
			if (start < 0) {
				break;
			}
			int end = this.regex.indexOf("}", start);
			if (end < 0) {
				break;
			}
			String previous = this.regex.substring(lastIndex, start);
			if (XString.isNotEmpty(previous)) {
				if (this.lineWrapIngore) {
					String[] arr = previous.split("\\n");
					for (int i = 0; i < arr.length; i++)
						if (XString.isNotEmpty(arr[i]))
							this.list.add(arr[i].trim());
				} else {
					this.list.add(previous);
				}
			}
			String item = this.regex.substring(start + 2, end);
			this.list.add(new RegexItem(item));
			lastIndex = end + 1;
		}
		if (lastIndex != this.regex.length() - 1) {
			String str = this.regex.substring(lastIndex);
			if (this.lineWrapIngore) {
				String[] arr = str.split("\\n");
				for (int i = 0; i < arr.length; i++)
					if (XString.isNotEmpty(arr[i]))
						this.list.add(arr[i].trim());
			} else {
				this.list.add(str);
			}
		}
	}

	public synchronized boolean match() {
		if (this.currentPos == this.text.length()) {
			return false;
		}
		boolean matchFlag = true;
		this.map = new CaseIgnoreMapx();
		this.groups = new ArrayList(16);
		this.startPos = this.currentPos;
		for (int i = 0; i < this.list.size(); i++) {
			Object obj = this.list.get(i);
			if ((obj instanceof String)) {
				String item = (String) obj;
				int index = this.text.indexOf(item, this.currentPos);
				if (index < 0) {
					matchFlag = false;
					break;
				}
				if ((i != 0) && (index != this.currentPos)) {
					matchFlag = false;
					break;
				}
				if (i == 0) {
					this.startPos = index;
				}
				this.currentPos = (index + item.length());
				if (this.lineWrapIngore)
					for (int j = this.currentPos; j < this.text.length(); j++) {
						char c = this.text.charAt(j);
						if ((c != '\n') && (c != '\t') && (c != '\r')
								&& (c != ' ') && (c != '\b') && (c != '\f')) {
							this.currentPos = j;
							break;
						}
					}
			} else {
				RegexItem item = (RegexItem) obj;
				int pos = this.currentPos;
				int count = 0;
				int nextPos;
				do {
					while (true) {
						if ((i == 0) && (count == 0)) {
							while (true) {
								int index = item.match(this.text, pos, 0);
								if (pos == this.text.length() - 1) {
									return false;
								}
								if (index >= 0) {
									this.startPos = pos;
									pos = index;
									break;
								}
								pos++;
							}
						}
						pos = item.match(this.text, this.currentPos, count);
						count++;
						if (pos < 0) {
							return false;
						}
						if ((!item.greaterFlag) || (i == this.list.size() - 1))
							try {
								String str = this.orginalText.substring(
										this.currentPos, pos);
								if (XString.isNotEmpty(item.getName())) {
									this.map.put(item.getName(), str);
								}
								this.groups.add(str);
							} catch (Exception e) {
								e.printStackTrace();
							}
						obj = this.list.get(i + 1);
						if (!(obj instanceof String))
							break;
						int index = this.text.indexOf(obj.toString(), pos);
						if (index == -1) {
							return false;
						}
						if (index == pos)
							try {
								String str = this.orginalText.substring(
										this.currentPos, pos);
								if (XString.isNotEmpty(item.getName())) {
									this.map.put(item.getName(), str);
								}
								this.groups.add(str);
							} catch (Exception e) {
								e.printStackTrace();
							}
						if (item.parts == null) {
							count += index - pos - 1;
						}

					}

					RegexItem nextItem = (RegexItem) obj;
					nextPos = nextItem.match(this.text, pos, 0);
				} while (nextPos < 0);

				this.currentPos = pos;
			}
		}
		if (!matchFlag) {
			this.map = null;
			this.groups = null;
			if ((this.currentPos > 0) && (this.startPos != this.currentPos)) {
				return match();
			}
		}
		return matchFlag;
	}

	public String replace(String content, String replacement) {
		setText(content);
		StringBuffer sb = new StringBuffer();
		int lastIndex = 0;
		while (match()) {
			sb.append(this.orginalText.substring(lastIndex, this.startPos));
			sb.append(replacement);
			lastIndex = this.currentPos;
		}
		sb.append(this.orginalText.substring(lastIndex));
		return sb.toString();
	}

	public String[] getGroups() {
		if (this.groups == null) {
			return null;
		}
		String[] arr = new String[this.groups.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = ((String) this.groups.get(i));
		}
		return arr;
	}

	public Mapx getMapx() {
		return this.map;
	}

	public String getMacthedText() {
		return this.orginalText.substring(this.startPos, this.currentPos);
	}

	static class RegexItem {
		private String name;
		private String expr;
		private char[] charArr;
		private boolean exclusive;
		private boolean greaterFlag = true;

		private int needCount = 0;
		private String[] parts;

		public RegexItem(String item) {
			int index = item.lastIndexOf(":");
			if (index >= 0) {
				this.name = item.substring(index + 1);
				this.expr = item.substring(0, index);
			} else {
				this.expr = item;
			}
			if (XString.isEmpty(this.expr)) {
				throw new RuntimeException("不正确的简单正则表达式占位符：" + item);
			}

			if (this.expr.equalsIgnoreCase("A"))
				this.expr = "-[]*";
			else if (this.expr.equalsIgnoreCase("D"))
				this.expr = "[\\d]*";
			else if (this.expr.equalsIgnoreCase("-D"))
				this.expr = "-[\\d]*";
			else if (this.expr.equalsIgnoreCase("W"))
				this.expr = "[\\w]*";
			else if (this.expr.equalsIgnoreCase("-W")) {
				this.expr = "-[\\w]*";
			}
			if (this.expr.startsWith("(")) {
				index = this.expr.lastIndexOf(")");
				if (index < this.expr.length() - 1) {
					String tail = this.expr.substring(index + 1);
					if (tail.equals("")) {
						this.greaterFlag = false;
						this.needCount = 1;
					} else if (tail.equals("*")) {
						this.greaterFlag = true;
						this.needCount = 0;
					} else if (tail.equals("+")) {
						this.greaterFlag = true;
						this.needCount = 1;
					} else if (tail.endsWith("+")) {
						this.greaterFlag = true;
						try {
							this.needCount = Integer.parseInt(tail.substring(0,
									tail.length() - 1));
						} catch (Exception e) {
							throw new RuntimeException("不正确的简单正则表达式占位符：" + item);
						}
					} else {
						this.greaterFlag = false;
						try {
							this.needCount = Integer.parseInt(tail.substring(0,
									tail.length()));
						} catch (Exception e) {
							throw new RuntimeException("不正确的简单正则表达式占位符："
									+ this.expr);
						}
					}
				}
				this.expr = this.expr.substring(1, index);

				ArrayList list = new ArrayList();
				String[] arr = XString.splitEx(this.expr, "|");
				for (int i = arr.length - 1; i >= 0; i--) {
					if (XString.isEmpty(arr[i])) {
						ArrayUtils.remove(arr, i);
					}
				}
				for (int i = 0; i < arr.length; i++) {
					if (arr[i].endsWith("\\")) {
						if (i == arr.length - 1) {
							throw new RuntimeException("不正确的简单正则表达式占位符：" + item);
						}
						String str = arr[i] + "|" + arr[(i + 1)];
						list.add(str);
						i++;
					} else {
						String str = arr[i];
						list.add(str);
					}
				}
				arr = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					arr[i] = ((String) list.get(i));
				}
				this.parts = arr;
			} else {
				if (this.expr.startsWith("-")) {
					this.exclusive = true;
					this.expr = this.expr.substring(1);
				}
				int end = this.expr.lastIndexOf("]");
				int start = this.expr.indexOf("[");
				if (start != 0) {
					throw new RuntimeException("不正确的简单正则表达式占位符：" + this.expr);
				}
				if ((start < 0) || (end < 0) || (end < start)) {
					throw new RuntimeException("不正确的简单正则表达式占位符：" + this.expr);
				}
				String content = this.expr.substring(start + 1, end);
				if (end < this.expr.length() - 1) {
					String tail = this.expr.substring(end + 1);
					if (tail.equals("")) {
						this.greaterFlag = false;
						this.needCount = 1;
					} else if (tail.equals("*")) {
						this.greaterFlag = true;
						this.needCount = 0;
					} else if (tail.equals("+")) {
						this.greaterFlag = true;
						this.needCount = 1;
					} else if (tail.endsWith("+")) {
						this.greaterFlag = true;
						try {
							this.needCount = Integer.parseInt(tail.substring(0,
									tail.length() - 1));
						} catch (Exception e) {
							throw new RuntimeException("不正确的简单正则表达式占位符："
									+ this.expr);
						}
					} else {
						this.greaterFlag = false;
						try {
							this.needCount = Integer.parseInt(tail.substring(0,
									tail.length()));
						} catch (Exception e) {
							throw new RuntimeException("不正确的简单正则表达式占位符："
									+ this.expr);
						}
					}
				}
				StringBuffer sb = new StringBuffer(128);
				boolean flag = false;
				for (int i = 0; i < content.length(); i++) {
					char c = content.charAt(i);
					if (flag) {
						if (c == 'd')
							sb.append("0123456789");
						else if (c == 'w')
							sb
									.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789-");
						else if (c == 's')
							sb.append("\t\n\b\f\r");
						else if (c == 't')
							sb.append("\t");
						else if (c == 'n')
							sb.append("\n");
						else if (c == 'b')
							sb.append("\n");
						else if (c == 'f')
							sb.append("\f");
						else if (c == 'r')
							sb.append("\r");
						else if (c == '\\')
							sb.append("\\");
						else
							sb.append(c);
					} else if (c != '\\') {
						sb.append(c);
					}
					if (c == '\\')
						flag = true;
					else {
						flag = false;
					}
				}
				this.charArr = sb.toString().toCharArray();
			}
		}

		public int match(String text, int startPos, int extraCount) {
			int pos = startPos;
			int count = this.needCount + extraCount;
			for (int j = 0; j < count; j++) {
				if (pos >= text.length()) {
					return -1;
				}
				if (this.parts == null) {
					char c = text.charAt(pos);
					boolean flag = false;
					for (int i = 0; i < this.charArr.length; i++) {
						if (this.charArr[i] == c) {
							if (this.exclusive) {
								return -1;
							}
							flag = true;
							break;
						}
					}
					if ((!this.exclusive) && (!flag)) {
						return -1;
					}
					pos++;
				} else {
					boolean flag = false;
					for (int i = 0; i < this.parts.length; i++) {
						int index = text.indexOf(this.parts[i], pos);
						if (index == pos) {
							pos = index + this.parts[i].length();
							flag = true;
							break;
						}
					}
					if (!flag) {
						return -1;
					}
				}
			}
			return pos;
		}

		public String getName() {
			return this.name;
		}
	}
	
	public static class MatchedMap extends Mapx {
		private static final long serialVersionUID = 1L;
		private String MatchedString;

		public String get(String key) {
			return ((String) super.get(key));
		}

		public String getMatchedString() {
			return this.MatchedString;
		}
	}
}
