
package com.xdarkness.jaf.controls;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import com.xdarkness.common.data.DataColumn;
import com.xdarkness.common.data.DataRow;
import com.xdarkness.common.data.DataTable;
import com.xdarkness.common.util.DateUtil;
import com.xdarkness.common.util.FileUtil;
import com.xdarkness.common.util.Mapx;
import com.xdarkness.common.util.NumberUtil;
import com.xdarkness.common.util.StringUtil;
import com.xdarkness.jaf.MainServlet;
import com.xdarkness.jaf.controls.html.HtmlTR;
import com.xdarkness.jaf.controls.html.HtmlTable;

public class ExplorerTag extends BodyTagSupport {

	public ExplorerTag() {
	}

	public int doAfterBody() throws JspException {
		try {
			String ExplorerPath = pageContext.getRequest().getParameter(
					"ExplorerPath");
			if (ExplorerPath == null)
				ExplorerPath = "";
			StringBuffer sb = new StringBuffer();
			if (type == 0 || type == 1) {
				String html = FileUtil.readText(
						com.xdarkness.jaf.controls.ExplorerTag.class
								.getResourceAsStream("File.template"), "UTF-8");
				String script = html.substring(html.indexOf("#0{Start}") + 10,
						html.indexOf("#0{End}"));
				String head = html.substring(html.indexOf("#1{Start}") + 10,
						html.indexOf("#1{End}"));
				String main = html.substring(html.indexOf("#2{Start}") + 10,
						html.indexOf("#2{End}"));
				sb.append(script);
				String arr[] = ExplorerPath.split("\\/");
				StringBuffer psb = new StringBuffer();
				psb.append(TButtonTag.getHtml("Explorer.goPath('')",
						"<img src='" + MainServlet.CONTEXT_PATH
								+ "Platform/Images/none.gif'>",
						"/\u6839\u76EE\u5F55"));
				for (int i = 0; i < arr.length; i++)
					if (arr[i] != null && !arr[i].equals("")) {
						StringBuffer jsb = new StringBuffer();
						for (int j = 0; j <= i; j++) {
							if (j != 0)
								jsb.append("/");
							jsb.append(arr[j]);
						}

						psb.append(TButtonTag.getHtml("Explorer.goPath('" + jsb
								+ "')", "<img src='" + MainServlet.CONTEXT_PATH
								+ "Platform/Images/none.gif'>", "/" + jsb));
					}

				psb.append("</select>");
				Mapx map = new Mapx();
				map.put("PathSelector", psb.toString());
				sb.append(HtmlUtil.replacePlaceHolder(head, map, true));
				if (style == null)
					style = "";
				sb
						.append("<div style=\"margin-top:0px;height:370;overflow:auto;"
								+ style + "\">");
				try {
					HtmlTable table = new HtmlTable();
					table.parseHtml(main);
					HtmlTR tr = table.getTR(1);
					String style1 = tr.getAttribute("style1");
					String style2 = tr.getAttribute("style2");
					String class1 = tr.getAttribute("class1");
					String class2 = tr.getAttribute("class2");
					tr.removeAttribute("style1");
					tr.removeAttribute("style2");
					tr.removeAttribute("class1");
					tr.removeAttribute("class2");
					String trHtml = tr.getOuterHtml();
					DataTable dt = null;
					String filePath = baseDir + "/" + ExplorerPath;
					if (filter != null && !"".equals(filter)) {
						org.apache.commons.io.filefilter.IOFileFilter dirFilter = FileFilterUtils
								.directoryFileFilter();
						org.apache.commons.io.filefilter.IOFileFilter suffixFilter = new SuffixFileFilter(
								filter.split("\\|"));
						org.apache.commons.io.filefilter.IOFileFilter allFilter = FileFilterUtils
								.orFileFilter(dirFilter, suffixFilter);
						if ("dir".equals(filter.toLowerCase()))
							dt = getFileInfoDataTable(filePath, FileFilterUtils
									.makeSVNAware(dirFilter));
						else
							dt = getFileInfoDataTable(filePath, FileFilterUtils
									.makeSVNAware(allFilter));
					} else {
						dt = getFileInfoDataTable(filePath, FileFilterUtils
								.makeSVNAware(FileFilterUtils.trueFileFilter()));
					}
					dt.setWebMode(true);
					table.Children.remove(1);
					for (int i = 0; i < dt.getRowCount(); i++) {
						tr = new HtmlTR();
						tr.parseHtml(HtmlUtil.replacePlaceHolder(trHtml, dt
								.getDataRow(i).toMapx(), false, true));
						if (i % 2 == 1) {
							if (style1 != null)
								tr.setAttribute("style", style1);
							if (class1 != null)
								tr.setAttribute("class", class1);
						} else {
							if (style2 != null)
								tr.setAttribute("style", styleOperateType.UPDATE);
							if (class2 != null)
								tr.setAttribute("class", classOperateType.UPDATE);
						}
						table.Children.add(tr);
					}

					table.setAttribute("id", id);
					table.setAttribute("baseDir", baseDir);
					sb.append(table.getOuterHtml());
					sb.append("</div>");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			getPreviousOut().write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 6;
	}

	public static DataTable getFileInfoDataTable(String path, FileFilter filter) {
		return getFileInfoDataTable(new File(path), filter);
	}

	public static DataTable getFileInfoDataTable(File f, FileFilter filter) {
		if (f.exists() && f.isDirectory()) {
			File fs[] = f.listFiles(filter);
			DataColumn dcs[] = new DataColumn[6];
			Object values[][] = new Object[fs.length][dcs.length];
			String names[] = { "Name", "Title", "ModifyTime", "Size", "Icon",
					"Type" };
			for (int i = 0; i < dcs.length; i++) {
				DataColumn dc = new DataColumn();
				dc.setColumnName(names[i]);
				dc.setColumnType(1);
				dcs[i] = dc;
			}

			String extPrefix =  "Framework/Images/FileType/";
			ArrayList arrayDir = new ArrayList();
			ArrayList arrayFile = new ArrayList();
			Object row[] = (Object[]) null;
			for (int i = 0; i < fs.length; i++) {
				File cf = fs[i];
				String name = cf.getName();
				row = new Object[dcs.length];
				row[0] = name;
				if (cf.isDirectory())
					row[4] = extPrefix + "folder.gif";
				else if (name.indexOf('.') > 0) {
					String ext = name.substring(name.lastIndexOf('.') + 1)
							.toLowerCase();
					String str = "." + ext;
					if (".html.htm.shtml".indexOf(str) >= 0)
						ext = "html";
					else if (".zip.rar.jar.war.ear".indexOf(str) >= 0)
						ext = "zip";
					else if (".wma.mpg.mpeg.avi".indexOf(str) >= 0)
						ext = "wmp";
					else if (".rm.rmvb".indexOf(str) >= 0)
						ext = "rm";
					else if (".doc.docx.rtf".indexOf(str) >= 0)
						ext = "doc";
					else if (".xls.xlsx.csv".indexOf(str) >= 0)
						ext = "xls";
					else if (".aspx.asp.jsp.php.js.bmp.exe.swf.flv.wmv.gif.jpg.png.mov.mp3.mp4"
							.indexOf(str) < 0)
						if (".css.txt.xml.sql".indexOf(str) >= 0)
							ext = "txt";
						else
							ext = "unknown";
					if (".html.htm.shtml.jsp.php.asp.aspx".indexOf(str) >= 0)
						row[1] = StringUtil.getHtmlTitle(cf);
					row[4] = extPrefix + ext + ".gif";
				} else {
					row[4] = extPrefix + "unknown.gif";
				}
				row[2] = DateUtil.toDateTimeString(new Date(cf.lastModified()));
				long len = cf.length();
				if (len < 1024L)
					row[3] = String.valueOf(len);
				else if (len < 0x100000L)
					row[3] = NumberUtil.round(((double) len * 1.0D) / 1024D, 1)
							+ "K";
				else if (len < 0x40000000L)
					row[3] = NumberUtil.round(((double) len * 1.0D) / 1048576D,
							1)
							+ "M";
				else if (len < 0x10000000000L)
					row[3] = NumberUtil.round(
							((double) len * 1.0D) / 1073741824D, 1)
							+ "G";
				if (cf.isDirectory()) {
					row[3] = "";
					row[5] = "D";
					arrayDir.add(((Object) (row)));
				} else {
					row[5] = "F";
					arrayFile.add(((Object) (row)));
				}
			}

			if (fs.length <= 0) {
				values = new Object[1][dcs.length];
				row = new Object[dcs.length];
				row[0] = "\u672C\u6587\u4EF6\u5939\u6CA1\u6709\u4EFB\u4F55\u6587\u4EF6,\u60A8\u53EF\u4EE5\u5148\u4E0A\u4F20\u6587\u4EF6";
				row[1] = "&nbsp;";
				row[2] = "&nbsp;";
				row[3] = "&nbsp;";
				row[4] = extPrefix + "none.gif";
				row[5] = "N";
				arrayFile.add(((Object) (row)));
			}
			int j = 0;
			for (int i = 0; i < arrayDir.size();) {
				values[j] = (Object[]) arrayDir.get(i);
				i++;
				j++;
			}

			for (int i = 0; i < arrayFile.size();) {
				values[j] = (Object[]) arrayFile.get(i);
				i++;
				j++;
			}

			DataTable dt = new DataTable(dcs, values);
			dt.sort(new Comparator() {

				public int compare(Object row1, Object row2) {
					String name1 = ((DataRow) row1).getString("Name")
							.toLowerCase();
					String type1 = ((DataRow) row1).getString("Type");
					String name2 = ((DataRow) row2).getString("Name")
							.toLowerCase();
					String type2 = ((DataRow) row2).getString("Type");
					if (type1.compareTo(type2) == 0)
						return name1.compareTo(nameOperateType.UPDATE);
					else
						return type1.compareTo(typeOperateType.UPDATE);
				}

			});
			return dt;
		} else {
			return new DataTable();
		}
	}

	public String getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getExclude() {
		return exclude;
	}

	public void setExclude(String exclude) {
		this.exclude = exclude;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public boolean isPage() {
		return page;
	}

	public void setPage(boolean page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private int type;
	private String baseDir;
	private String filter;
	private String exclude;
	private String column;
	private boolean page;
	private int size;
	private String style;
}
