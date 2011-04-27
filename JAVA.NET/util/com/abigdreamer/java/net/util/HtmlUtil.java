package com.abigdreamer.java.net.util;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.abigdreamer.java.net.Constant;
import com.abigdreamer.java.net.cache.CacheManager;
import com.abigdreamer.java.net.jaf.controls.SelectTag;
import com.abigdreamer.java.net.jaf.html.element.HtmlTable;
import com.abigdreamer.java.net.orm.data.DataRow;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.sql.QueryBuilder;

public class HtmlUtil {
	public static Mapx codeToMapx(String CodeType) {
		return CacheManager.getMapx("Code", CodeType);
	}

	public static String codeToOptions(String CodeType) {
		return codeToOptions(CodeType, null);
	}

	public static String codeToOptions(String CodeType, Object checkedValue) {
		return mapxToOptions(CacheManager.getMapx("Code", CodeType),
				checkedValue);
	}

	public static String codeToOptions(String CodeType,
			boolean addBlankOptionFlag) {
		return mapxToOptions(CacheManager.getMapx("Code", CodeType), null,
				addBlankOptionFlag);
	}

	public static String codeToOptions(String CodeType, Object checkedValue,
			boolean addBlankOptionFlag) {
		return mapxToOptions(CacheManager.getMapx("Code", CodeType),
				checkedValue, addBlankOptionFlag);
	}

	public static String codeToRadios(String name, String CodeType) {
		return codeToRadios(name, CodeType, null, false);
	}

	public static String codeToRadios(String name, String CodeType,
			Object checkedValue) {
		return codeToRadios(name, CodeType, checkedValue, false);
	}

	public static String codeToRadios(String name, String CodeType,
			boolean direction) {
		return codeToRadios(name, CodeType, null, direction);
	}

	public static String codeToRadios(String name, String CodeType,
			Object checkedValue, boolean direction) {
		return mapxToRadios(name, CacheManager.getMapx("Code", CodeType),
				checkedValue, direction);
	}

	public static String codeToCheckboxes(String name, String CodeType) {
		return mapxToCheckboxes(name, CacheManager.getMapx("Code", CodeType));
	}

	public static String codeToCheckboxes(String name, String CodeType,
			boolean direction) {
		return codeToCheckboxes(name, CodeType, new String[0], direction);
	}

	public static String codeToCheckboxes(String name, String CodeType,
			DataTable checkedDT) {
		return codeToCheckboxes(name, CodeType, checkedDT, false);
	}

	public static String codeToCheckboxes(String name, String CodeType,
			DataTable checkedDT, boolean direction) {
		return mapxToCheckboxes(name, CacheManager.getMapx("Code", CodeType),
				checkedDT, null, direction);
	}

	public static String codeToCheckboxes(String name, String CodeType,
			String[] checkedArray) {
		return codeToCheckboxes(name, CodeType, checkedArray, false);
	}

	public static String codeToCheckboxes(String name, String CodeType,
			String[] checkedArray, boolean direction) {
		return mapxToCheckboxes(name, CacheManager.getMapx("Code", CodeType),
				checkedArray, null, direction);
	}

	public static String mapxToOptions(Map map) {
		return mapxToOptions(map, null);
	}

	public static String mapxToOptions(Map map, Object checkedValue) {
		return mapxToOptions(map, checkedValue, false);
	}

	public static String mapxToOptions(Map map, boolean addBlankOptionFlag) {
		return mapxToOptions(map, null, addBlankOptionFlag);
	}

	public static String mapxToOptions(Map map, Object checkedValue,
			boolean addBlankOptionFlag) {
		StringBuffer sb = new StringBuffer();
		if (addBlankOptionFlag) {
			sb.append("<span value=''></span>");
		}
		if (map == null) {
			return sb.toString();
		}
		Object[] keys = map.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			sb.append("<span value=\"");
			Object v = keys[i];
			sb.append(v);
			if ((v != null) && (v.equals(checkedValue)))
				sb.append("\" selected='true' >");
			else {
				sb.append("\">");
			}
			sb.append(map.get(v));
			sb.append("</span>");
		}
		return sb.toString();
	}

	public static String mapxToRadios(String name, Map map) {
		return mapxToRadios(name, map, null, false);
	}

	public static String mapxToRadios(String name, Map map, Object checkedValue) {
		return mapxToRadios(name, map, checkedValue, false);
	}

	public static String mapxToRadios(String name, Map map,
			Object checkedValue, boolean direction) {
		StringBuffer sb = new StringBuffer();
		Object[] keys = map.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			Object value = keys[i];
			sb.append("<input type='radio' name='" + name);
			sb.append("' id='" + name + "_" + i + "' value='");
			sb.append(value);
			if (value.equals(checkedValue))
				sb.append("' checked >");
			else {
				sb.append("' >");
			}
			sb.append("<label for='" + name + "_" + i + "'>");
			sb.append(map.get(value));
			sb.append("</label>");
			if (direction) {
				sb.append("<br>");
			}
		}
		return sb.toString();
	}

	public static String mapxToCheckboxes(String name, Mapx map) {
		return mapxToCheckboxes(name, map, null);
	}

	public static String mapxToCheckboxes(String name, Mapx map,
			Object[] checkedArray) {
		return mapxToCheckboxes(name, map, checkedArray, null);
	}

	public static String mapxToCheckboxes(String name, Mapx map,
			DataTable checkedDT, Object[] disabledValue) {
		return mapxToCheckboxes(name, map, checkedDT, disabledValue, false);
	}

	public static String mapxToCheckboxes(String name, Mapx map,
			DataTable checkedDT, Object[] disabledValue, boolean direction) {
		String[] checkedArray = new String[checkedDT.getRowCount()];
		for (int i = 0; i < checkedDT.getRowCount(); i++) {
			checkedArray[i] = checkedDT.getString(i, 0);
		}
		return mapxToCheckboxes(name, map, checkedArray, disabledValue,
				direction);
	}

	public static String mapxToCheckboxes(String name, Mapx map,
			Object[] checkedArray, Object[] disabledValue) {
		return mapxToCheckboxes(name, map, checkedArray, disabledValue, false);
	}

	public static String mapxToCheckboxes(String name, Mapx map,
			Object[] checkedArray, Object[] disabledValue, boolean direction) {
		StringBuffer sb = new StringBuffer();
		Object[] keys = map.keyArray();
		for (int k = 0; k < keys.length; k++) {
			Object value = keys[k];
			sb.append("<input type='checkbox'");
			sb.append(" name='" + name);
			sb.append("' id='" + name + "_" + k + "' value='");
			sb.append(value);
			sb.append("'");
			if (disabledValue != null) {
				for (int j = 0; j < disabledValue.length; j++) {
					if (value.equals(disabledValue[j])) {
						sb.append(" disabled");
						break;
					}
				}
			}

			if (checkedArray != null) {
				for (int j = 0; j < checkedArray.length; j++) {
					if (value.equals(checkedArray[j])) {
						sb.append(" checked");
						break;
					}
				}
			}

			sb.append(" >");
			sb.append("<label for='" + name + "_" + k + "'>");
			sb.append(map.get(value));
			sb.append("</label>&nbsp;");
			if (direction) {
				sb.append("<br>");
			}
		}
		return sb.toString();
	}

	public static String arrayToOptions(String[] array) {
		return arrayToOptions(array, null);
	}

	public static String arrayToOptions(String[] array, Object checkedValue) {
		return arrayToOptions(array, checkedValue, false);
	}

	public static String arrayToOptions(String[] array,
			boolean addBlankOptionFlag) {
		return arrayToOptions(array, null, addBlankOptionFlag);
	}

	public static String arrayToOptions(String[] array, Object checkedValue,
			boolean addBlankOptionFlag) {
		StringBuffer sb = new StringBuffer();
		if (addBlankOptionFlag) {
			sb.append("<span value=''></span>");
		}

		for (int i = 0; i < array.length; i++) {
			sb.append("<span value=\"");
			Object v = array[i];
			String value = (String) v;
			String[] arr = value.split(",");
			String name = value;
			if (arr.length > 1) {
				name = arr[0];
				value = arr[1];
			}
			sb.append(value);
			if ((value != null) && (value.equals((String) checkedValue)))
				sb.append("\" selected='true' >");
			else {
				sb.append("\">");
			}
			sb.append(name);
			sb.append("</span>");
		}
		return sb.toString();
	}

	public static String queryToOptions(QueryBuilder qb) {
		return dataTableToOptions(qb.executeDataTable(), null);
	}

	public static String queryToOptions(QueryBuilder qb, Object checkedValue) {
		return dataTableToOptions(qb.executeDataTable(), checkedValue);
	}

	public static String queryToOptions(QueryBuilder qb,
			boolean addBlankOptionFlag) {
		return dataTableToOptions(qb.executeDataTable(), addBlankOptionFlag);
	}

	public static String queryToOptions(QueryBuilder qb, Object checkedValue,
			boolean addBlankOptionFlag) {
		return dataTableToOptions(qb.executeDataTable(), checkedValue,
				addBlankOptionFlag);
	}

	public static String dataTableToOptions(DataTable dt) {
		return dataTableToOptions(dt, null);
	}

	public static String dataTableToOptions(DataTable dt, Object checkedValue) {
		return dataTableToOptions(dt, checkedValue, false);
	}

	public static String dataTableToOptions(DataTable dt,
			boolean addBlankOptionFlag) {
		return dataTableToOptions(dt, null, addBlankOptionFlag);
	}

	public static String dataTableToOptions(DataTable dt, Object checkedValue,
			boolean addBlankOptionFlag) {
		StringBuffer sb = new StringBuffer();
		if (addBlankOptionFlag) {
			sb.append(SelectTag.getOptionHtml("", "", false));
		}
		if (dt == null) {
			return null;
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			String value = dt.getString(i, 1);
			if (value.equals(checkedValue))
				sb.append(SelectTag.getOptionHtml(dt.getString(i, 0), value,
						true));
			else {
				sb.append(SelectTag.getOptionHtml(dt.getString(i, 0), value,
						false));
			}
		}
		return sb.toString();
	}

	public static String dataTableToRadios(String name, DataTable dt) {
		return dataTableToRadios(name, dt, null, false);
	}

	public static String dataTableToRadios(String name, DataTable dt,
			String checkedValue) {
		return dataTableToRadios(name, dt, checkedValue, false);
	}

	public static String dataTableToRadios(String name, DataTable dt,
			boolean direction) {
		return dataTableToRadios(name, dt, null, direction);
	}

	public static String dataTableToRadios(String name, DataTable dt,
			Object checkedValue, boolean direction) {
		StringBuffer sb = new StringBuffer();
		for (int k = 0; k < dt.getRowCount(); k++) {
			String value = dt.getString(k, 1);
			sb.append("<input type='radio' name='" + name);
			sb.append("' id='" + name + "_" + k + "' value='");
			sb.append(value);
			if (value.equals(checkedValue))
				sb.append("' checked >");
			else {
				sb.append("' >");
			}
			sb.append("<label for='" + name + "_" + k + "'>");
			sb.append(dt.getString(k, 0));
			sb.append("</label>");
			if (direction) {
				sb.append("<br>");
			}
		}
		return sb.toString();
	}

	public static String arrayToRadios(String name, String[] array) {
		return arrayToRadios(name, array, null, false);
	}

	public static String arrayToRadios(String name, String[] array,
			String checkedValue) {
		return arrayToRadios(name, array, checkedValue, false);
	}

	public static String arrayToRadios(String name, String[] array,
			boolean direction) {
		return arrayToRadios(name, array, null, direction);
	}

	public static String arrayToRadios(String name, String[] array,
			Object checkedValue, boolean direction) {
		StringBuffer sb = new StringBuffer();
		for (int k = 0; k < array.length; k++) {
			String value = array[k];
			sb.append("<input type='radio' name='" + name);
			sb.append("' id='" + name + "_" + k + "' value='");
			sb.append(value);
			if (value.equals(checkedValue))
				sb.append("' checked >");
			else {
				sb.append("' >");
			}
			sb.append("<label for='" + name + "_" + k + "'>");
			sb.append(value);
			sb.append("</label>");
			if (direction) {
				sb.append("<br>");
			}
		}
		return sb.toString();
	}

	public static String dataTableToCheckboxes(String name, DataTable dt) {
		return dataTableToCheckboxes(name, dt, false);
	}

	public static String dataTableToCheckboxes(String name, DataTable dt,
			boolean direction) {
		return dataTableToCheckboxes(name, dt, new String[0], direction);
	}

	public static String dataTableToCheckboxes(String name, DataTable dt,
			DataTable checkedDT) {
		return dataTableToCheckboxes(name, dt, checkedDT, false);
	}

	public static String dataTableToCheckboxes(String name, DataTable dt,
			DataTable checkedDT, boolean direction) {
		String[] checkedArray = new String[checkedDT.getRowCount()];
		for (int i = 0; i < checkedDT.getRowCount(); i++) {
			checkedArray[i] = checkedDT.getString(i, 0);
		}
		return dataTableToCheckboxes(name, dt, checkedArray, direction);
	}

	public static String dataTableToCheckboxes(String name, DataTable dt,
			String[] checkedArray) {
		return dataTableToCheckboxes(name, dt, checkedArray, false);
	}

	public static String dataTableToCheckboxes(String name, DataTable dt,
			String[] checkedArray, boolean direction) {
		StringBuffer sb = new StringBuffer();
		for (int k = 0; k < dt.getRowCount(); k++) {
			String value = dt.getString(k, 1);
			sb.append("<input type='checkbox' name='" + name);
			sb.append("' id='" + name + "_" + k + "' value='");
			sb.append(value);
			boolean flag = false;
			if (checkedArray != null) {
				for (int j = 0; j < checkedArray.length; j++) {
					if (value.equals(checkedArray[j])) {
						sb.append("' checked >");
						flag = true;
						break;
					}
				}
			}

			if (!flag) {
				sb.append("' >");
			}
			sb.append("<label for='" + name + "_" + k + "'>");
			sb.append(dt.getString(k, 0));
			sb.append("</label>&nbsp;");
			if (direction) {
				sb.append("<br>");
			}
		}
		return sb.toString();
	}

	public static String arrayToCheckboxes(String name, String[] array) {
		return arrayToCheckboxes(name, array, null, null, false);
	}

	public static String arrayToCheckboxes(String name, String[] array,
			String[] checkedArray) {
		return arrayToCheckboxes(name, array, checkedArray, null, false);
	}

	public static String arrayToCheckboxes(String name, String[] array,
			String onclick) {
		return arrayToCheckboxes(name, array, null, onclick, false);
	}

	public static String arrayToCheckboxes(String name, String[] array,
			String[] checkedArray, String onclick, boolean direction) {
		StringBuffer sb = new StringBuffer();
		for (int k = 0; k < array.length; k++) {
			String value = array[k];
			sb.append("<label><input type='checkbox'");
			if (XString.isNotEmpty(onclick)) {
				sb.append("onclick='" + onclick + "'");
			}
			sb.append(" name='" + name);
			sb.append("' id='" + name + "_" + k + "'value='");
			sb.append(value);
			boolean flag = false;
			for (int j = 0; (checkedArray != null) && (j < checkedArray.length); j++) {
				if (value.equals(checkedArray[j])) {
					sb.append("' checked >");
					flag = true;
					break;
				}
			}
			if (!flag) {
				sb.append("' >");
			}
			sb.append("<label for='" + name + "_" + k + "'>");
			sb.append(value);
			sb.append("</label>");
			if (direction) {
				sb.append("<br>");
			}
		}
		return sb.toString();
	}

	public static String replacePlaceHolder(String html, HashMap map,
			boolean blankFlag) {
		return replacePlaceHolder(html, map, blankFlag, false);
	}

	public static String replacePlaceHolder(String html, HashMap map,
			boolean blankFlag, boolean spaceFlag) {
		Matcher matcher = Constant.PatternField.matcher(html);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		String blank = "";
		if (spaceFlag) {
			blank = "&nbsp;";
		}
		map = new CaseIgnoreMapx(map);
		while (matcher.find(lastEndIndex)) {
			sb.append(html.substring(lastEndIndex, matcher.start()));
			String key = matcher.group(1).toLowerCase();
			if (map.containsKey(key)) {
				Object o = map.get(key);
				if ((o == null) || (o.equals("")))
					sb.append(blank);
				else
					sb.append(o);
			} else if (blankFlag) {
				sb.append("");
			} else {
				sb.append(html.substring(matcher.start(), matcher.end()));
			}
			lastEndIndex = matcher.end();
		}
		sb.append(html.substring(lastEndIndex));
		return sb.toString();
	}

	public static String replaceWithDataTable(DataTable dt, String html) {
		return replaceWithDataTable(dt, html, true);
	}

	public static String replaceWithDataTable(DataTable dt, String html,
			boolean spaceFlag) {
		if ((html == null) || (dt == null)) {
			return "";
		}
		Matcher matcher = Constant.PatternField.matcher(html);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		String blank = "";
		if (spaceFlag) {
			blank = "&nbsp;";
		}
		ArrayList arr = new ArrayList();
		ArrayList key = new ArrayList();

		while (matcher.find(lastEndIndex)) {
			arr.add(html.substring(lastEndIndex, matcher.start()));
			String str = matcher.group(1);
			key.add(str);
			lastEndIndex = matcher.end();
		}
		arr.add(html.substring(lastEndIndex));

		for (int i = 0; i < dt.getRowCount(); i++) {
			for (int j = 0; j < arr.size(); j++) {
				sb.append(arr.get(j));
				if (j != key.size()) {
					String str = dt.getString(i, key.get(j).toString());
					if (XString.isEmpty(str))
						sb.append(blank);
					else {
						sb.append(str);
					}
				}
			}
		}
		return sb.toString();
	}

	public static String replaceWithDataRow(DataRow dr, String html) {
		return replaceWithDataRow(dr, html, true);
	}

	public static String replaceWithDataRow(DataRow dr, String html,
			boolean spaceFlag) {
		if ((html == null) || (dr == null)) {
			return "";
		}
		Matcher matcher = Constant.PatternField.matcher(html);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		String blank = "";
		if (spaceFlag) {
			blank = "&nbsp;";
		}
		ArrayList arr = new ArrayList();
		ArrayList key = new ArrayList();

		while (matcher.find(lastEndIndex)) {
			arr.add(html.substring(lastEndIndex, matcher.start()));
			String str = matcher.group(1);
			key.add(str);
			lastEndIndex = matcher.end();
		}
		arr.add(html.substring(lastEndIndex));

		for (int j = 0; j < arr.size(); j++) {
			sb.append(arr.get(j));
			if (j != key.size()) {
				String str = dr.getString(key.get(j).toString());
				if (XString.isEmpty(str))
					sb.append(blank);
				else {
					sb.append(str);
				}
			}
		}
		return sb.toString();
	}

	public static void htmlTableToExcel(OutputStream os, HtmlTable table,
			String[] widths, String[] indexes, String[] rows) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("First");
		try {
			HSSFFont fontBold = wb.createFont();
			fontBold.setFontHeightInPoints((short)10);
			fontBold.setFontName("宋体");
			fontBold.setBoldweight((short)700);

			HSSFFont fontNormal = wb.createFont();
			fontNormal.setFontHeightInPoints((short)10);
			fontNormal.setFontName("宋体");
			fontNormal.setBoldweight((short)400);

			HSSFCellStyle styleBorderBold = wb.createCellStyle();
			styleBorderBold.setBorderBottom((short)1);
			styleBorderBold.setBorderLeft((short)1);
			styleBorderBold.setBorderRight((short)1);
			styleBorderBold.setBorderTop((short)1);
			styleBorderBold.setVerticalAlignment((short)1);
			styleBorderBold.setAlignment((short)2);
			styleBorderBold.setWrapText(true);

			styleBorderBold.setFont(fontBold);

			HSSFCellStyle styleBorderNormal = wb.createCellStyle();
			styleBorderNormal.setBorderBottom((short)1);
			styleBorderNormal.setBorderLeft((short)1);
			styleBorderNormal.setBorderRight((short)1);
			styleBorderNormal.setBorderTop((short)1);
			styleBorderBold.setVerticalAlignment((short)1);
			styleBorderNormal.setFont(fontNormal);

			HSSFCellStyle styleBold = wb.createCellStyle();
			styleBold.setFont(fontBold);

			HSSFCellStyle styleNormal = wb.createCellStyle();
			styleNormal.setFont(fontNormal);

			HSSFRow row = sheet.getRow(0);
			if (row == null) {
				row = sheet.createRow(0);
			}
			for (int i = 0; i < indexes.length; i++) {
				HSSFCell cell = row.getCell((short) i);
				if (cell == null) {
					cell = row.createCell((short) i);
				}
				cell.setCellType(1);
				cell.setCellStyle(styleBorderBold);
				cell.setEncoding((short)1);

				String html = table.getTR(0)
						.getTD(Integer.parseInt(indexes[i])).getInnerHTML();
				html = html.replaceAll("<.*?>", "");
				html = XString.htmlDecode(html);
				cell.setCellValue(html.trim());
				row.setHeightInPoints(23.0F);
				if ((widths != null) && (widths.length > i)) {
					double w = Double.parseDouble(widths[i]);
					if (w < 100.0D) {
						w = 100.0D;
					}
					sheet.setColumnWidth((short) i,
							(short) (int) (w * 35.700000000000003D));
				}
			}

			for (int i = 0; i < indexes.length; i++) {
				int j = Integer.parseInt(indexes[i]);
				if (rows != null)
					for (int k = 0; k < rows.length; k++) {
						int n = Integer.parseInt(rows[k]);
						String xtype = table.getTR(n).getAttribute("xtype");
						if (k == table.getChildren().size() - 1) {
							if ((XString.isNotEmpty(xtype))
									&& (xtype.equalsIgnoreCase("pagebar"))) {
								break;
							}
							String html = table.getTR(n).getInnerHTML();
							if ((XString.isEmpty(html))
									|| (html.indexOf("PageBarIndex") > 0)) {
								break;
							}
						}
						row = sheet.getRow(k + 1);
						if (row == null) {
							row = sheet.createRow(k + 1);
							row.setHeightInPoints(18.0F);
						}
						HSSFCell cell = row.getCell((short) i);
						if (cell == null) {
							cell = row.createCell((short) i);
						}
						cell.setCellType(1);
						cell.setCellStyle(styleBorderNormal);
						cell.setEncoding((short)1);

						String html = table.getTR(n).getTD(j).getOuterHtml();
						html = html.replaceAll("<.*?>", "");
						html = XString.htmlDecode(html);
						cell.setCellValue(html.trim());
					}
				else {
					for (int k = 1; k < table.getChildren().size(); k++) {
						String xtype = table.getTR(k).getAttribute("xtype");
						if (k == table.getChildren().size() - 1) {
							if ((XString.isNotEmpty(xtype))
									&& (xtype.equalsIgnoreCase("pagebar"))) {
								break;
							}
							String html = table.getTR(k).getInnerHTML();
							if ((XString.isEmpty(html))
									|| (html.indexOf("PageBarIndex") > 0)) {
								break;
							}
						}
						row = sheet.getRow(k);
						if (row == null) {
							row = sheet.createRow(k);
							row.setHeightInPoints(18.0F);
						}
						HSSFCell cell = row.getCell((short) i);
						if (cell == null) {
							cell = row.createCell((short) i);
						}
						cell.setCellType(1);
						cell.setCellStyle(styleBorderNormal);
						cell.setEncoding((short)1);

						String html = "";
						if (table.getTR(k).getChildren().size() > j) {
							html = table.getTR(k).getTD(j).getOuterHtml();
							html = html.replaceAll("<.*?>", "");
							html = XString.htmlDecode(html);
						}
						cell.setCellValue(html.trim());
					}
				}
			}
			wb.write(os);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/*
 * com.xdarkness.framework.utility.HtmlUtil JD-Core Version: 0.6.0
 */