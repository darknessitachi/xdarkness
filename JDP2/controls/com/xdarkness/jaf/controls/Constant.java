package com.xdarkness.jaf.controls;

import java.util.regex.Pattern;

public class Constant {

	public Constant() {
	}

	public static final Pattern PatternField = Pattern
			.compile("\\$\\{(\\w+?)\\}");
	public static final Pattern PatternSpeicalField = Pattern
			.compile("\\$\\{[@#](\\w+?)\\}");
	public static final Pattern PatternPropField = Pattern
			.compile("\\$\\{(\\w+?)\\.(\\w+?)(\\|(.*?))??\\}");
	public static final String UserAttrName = "_SKY_USER";
	public static final String DefaultAuthKey = "_SKY_AUTHKEY";
	public static final String SessionIDCookieName = "JSESSIONID";
	public static final String ResponseScriptAttr = "_SKY_SCRIPT";
	public static final String ResponseMessageAttrName = "_SKY_MESSAGE";
	public static final String ResponseStatusAttrName = "_SKY_STATUS";
	public static final String DataGridSQL = "_SKY_DATAGRID_SQL";
	public static final String DataGridPageIndex = "_SKY_PAGEINDEX";
	public static final String DataGridPageTotal = "_SKY_PAGETOTAL";
	public static final String DataGridSortString = "_SKY_SORTSTRING";
	public static final String DataGridInsertRow = "_SKY_INSERTROW";
	public static final String DataGridMultiSelect = "_SKY_MULTISELECT";
	public static final String DataGridAutoFill = "_SKY_AUTOFILL";
	public static final String DataGridScroll = "_SKY_SCROLL";
	public static final String DataTable = "_SKY_DataTable";
	public static final String ID = "_SKY_ID";
	public static final String Method = "_SKY_METHOD";
	public static final String Page = "_SKY_PAGE";
	public static final String Size = "_SKY_SIZE";
	public static final String TagBody = "_SKY_TAGBODY";
	public static final String TreeLevel = "_SKY_TREE_LEVEL";
	public static final String TreeLazy = "_SKY_TREE_LAZY";
	public static final String TreeExpand = "_SKY_TREE_EXPAND";
	public static final String TreeStyle = "_SKY_TREE_STYLE";
	public static final String Data = "_SKY_DATA";
	public static final String URL = "_SKY_URL";
	public static final String Null = "_SKY_NULL";
	public static final String PAGE_BREAK = "<!--_SKY_PAGE_BREAK_-->";
	public static String GlobalCharset = "UTF-8";

}
