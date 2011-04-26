package com.xdarkness.cmcore.tag;

import com.xdarkness.cmcore.template.TemplateContext;
import com.xdarkness.framework.util.Treex;

public class ZIfTag extends SimpleTag {
	public String[] getAllAttributeNames() {
		return new String[] { "condition" };
	}

	public String[] getMandantoryAttributeNames() {
		return new String[] { "condition" };
	}

	public int onTagStart() {
		String condition = this.attributes.getString("condition");
		Treex tree = parseCondition(condition);
		if (evalCondition(tree.getRoot(), this.context)) {
			return 2;
		}
		return 1;
	}

	public static boolean evalCondition(Treex.TreeNode parent,
			TemplateContext context) {
		LogicClause clause = (LogicClause) parent.getData();
		if ((clause != null)
				&& (XString.isNotEmpty(clause.getClauseString()))) {
			return clause.execute(context);
		}
		Treex.TreeNodeList list = parent.getChildren();
		boolean v = true;
		for (int i = 0; i < list.size(); i++) {
			clause = (LogicClause) list.get(i).getData();
			boolean isOr = clause.isOr;
			if (((clause != null) && (!XString.isEmpty(clause
					.getClauseString())))
					|| ((isOr) && (v))) {
				continue;
			}
			if ((!isOr) && (!v)) {
				continue;
			}
			v = (v) || (evalCondition(list.get(i), context));
		}

		return v;
	}

	public static Treex parseCondition(String condition) {
		Treex tree = new Treex();
		char[] cs = condition.toCharArray();
		Treex.TreeNode parent = tree.getRoot();

		boolean isStringBegin = false;
		boolean isOr = false;
		int clauseStart = -1;
		for (int i = 0; i < cs.length; i++) {
			char c = cs[i];
			if (c == '"') {
				if (i == 0)
					isStringBegin = true;
				else if (c != '\\') {
					if (isStringBegin)
						isStringBegin = false;
					else {
						isStringBegin = true;
					}
				}
			}
			if ((c != '(') && (i == 0)) {
				clauseStart = i;
			}
			if ((c != ')') && (i == cs.length - 1)) {
				LogicClause clause = new LogicClause();
				String str = condition.substring(clauseStart, i + 1);
				if (str.startsWith("!")) {
					clause.isNot = true;
					str = str.substring(1);
				}
				clause.isOr = isOr;
				clause.setClauseString(str);
				parent.addChild(clause);
				clauseStart = -1;
			}
			if (!isStringBegin) {
				if (c == '(') {
					if ((i != 0) && (cs[(i - 1)] != '(')
							&& (cs[(i - 1)] != '|') && (cs[(i - 1)] != '&')) {
						continue;
					}
					LogicClause clause = new LogicClause();
					clause.isOr = isOr;
					isOr = false;
					parent = parent.addChild(clause);
					clauseStart = i + 1;
				}
				if (c == ')') {
					if ((i != cs.length - 1) && (cs[(i + 1)] != ')')
							&& (cs[(i + 1)] != '|') && (cs[(i + 1)] != '&')) {
						continue;
					}
					if (clauseStart >= 0) {
						LogicClause clause = new LogicClause();
						String str = condition.substring(clauseStart, i);
						if (str.startsWith("!")) {
							clause.isNot = true;
							str = str.substring(1);
						}
						clause.isOr = isOr;
						clause.setClauseString(str);
						parent.addChild(clause);
						clauseStart = -1;
					}
					parent = parent.getParent();
				}
				if ((c == '|') && (i != 0) && (cs[(i - 1)] == '|')) {
					if (clauseStart >= 0) {
						LogicClause clause = new LogicClause();
						String str = condition.substring(clauseStart, i - 1);
						if (str.startsWith("!")) {
							clause.isNot = true;
							str = str.substring(1);
						}
						clause.isOr = isOr;
						clause.setClauseString(str);
						parent.addChild(clause);
					}
					clauseStart = i + 1;
					isOr = true;
				}
				if ((c == '&') && (i != 0) && (cs[(i - 1)] == '&')) {
					if (clauseStart >= 0) {
						LogicClause clause = new LogicClause();
						String str = condition.substring(clauseStart, i - 1);
						if (str.startsWith("!")) {
							clause.isNot = true;
							str = str.substring(1);
						}
						clause.isOr = isOr;
						clause.setClauseString(str);
						parent.addChild(clause);
					}
					clauseStart = i + 1;
					isOr = false;
				}
			}
		}
		return tree;
	}

	public String getPrefix() {
		return "z";
	}

	public String getTagName() {
		return "if";
	}

	public static void main(String[] args) {
		long t = System.currentTimeMillis();
		for (int i = 0; i < 1; i++) {
			String condition = "2!=2||(!${Article.ID}==3&&${Article.Title}.indexOf(\"A\")>0)||1==1&&(2==1||3==2)";
			Treex tree = parseCondition(condition);
			System.out.println(tree);
		}
		System.out.println(System.currentTimeMillis() - t);
	}
}

/*
 * com.xdarkness.cmcore.tag.ZIfTag JD-Core Version: 0.6.0
 */