package com.xdarkness.cms.pub;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import com.xdarkness.framework.DBUtil;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.NumberUtil;
import com.xdarkness.framework.util.ZipUtil;

public class SiteExporter {
	private int PageSize = 1000;
	private OutputStream os;
	private Mapx relaMap = new Mapx();
	private long siteID;
	private boolean isExportMediaFile = true;

	public SiteExporter(long siteID) {
		this.siteID = siteID;
	}

	public SiteExporter(long siteID, boolean isExportMediaFile) {
		this.siteID = siteID;
		this.isExportMediaFile = isExportMediaFile;
	}

	public void addRelaTable(String sql) {
		this.relaMap.put(sql, "");
	}

	public boolean exportSite(String file) {
		try {
			FileUtil.delete(file);
			this.os = new FileOutputStream(file);
			return exportSite();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean exportSite(HttpServletResponse response) {
		try {
			this.os = response.getOutputStream();
			return exportSite();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean exportSite() {
		try {
			this.os.write(Constant.GlobalCharset.equals("GBK") ? 1
					: 2);
			byte[] bs = NumberUtil.toBytes(this.siteID);
			this.os.write(bs);
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
		boolean flag = true;

		SiteTableRela.TableRela[] trs = SiteTableRela.getRelas();
		String[] tables = SiteTableRela.getSiteIDTables();

		QueryBuilder qb = new QueryBuilder("select * from ZCSite where ID=?",
				this.siteID);
		try {
			transferSQL(qb, "ZCSite");
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		if (!flag) {
			try {
				this.os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < tables.length; i++) {
			if (tables[i].toLowerCase().startsWith("b")) {
				continue;
			}
			if (tables[i].toLowerCase().startsWith("zcdeploy")) {
				continue;
			}
			if (tables[i].toLowerCase().startsWith("zcstatitem")) {
				continue;
			}
			if (tables[i].toLowerCase().startsWith("zcvisitlog")) {
				continue;
			}
			qb = SiteTableRela.getSelectQuery(this.siteID, tables[i]);
			if (tables[i].toLowerCase().equals("zccatalog")) {
				qb.append(" order by innercode");
			}
			if (tables[i].equalsIgnoreCase("ZCArticle"))
				this.PageSize = 100;
			else
				this.PageSize = 1000;
			try {
				transferSQL(qb, tables[i]);
			} catch (Exception e) {
				flag = false;
				e.printStackTrace();
			}
		}
		for (int i = 0; i < trs.length; i++) {
			if (trs[i].TableCode.toLowerCase().startsWith("b")) {
				continue;
			}
			if (!trs[i].isExportData) {
				continue;
			}
			if (!SiteTableRela.hasSiteIDField(trs[i].RelaTable)) {
				continue;
			}
			qb = SiteTableRela.getSelectQuery(this.siteID, trs[i]);
			try {
				transferSQL(qb, trs[i]);
			} catch (Exception e) {
				flag = false;
				e.printStackTrace();
			}
		}

		DataTable dt = new QueryBuilder(
				"select Code from ZCCustomTable where Type='Custom' and SiteID=?",
				this.siteID).executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String code = dt.getString(i, 0);
			transferCustomTable(code);
		}
		if (!flag) {
			return false;
		}
		if (this.isExportMediaFile) {
			flag = exportFile();
		}
		return flag;
	}

	private void transferCustomTable(String table) {
		int count = 0;
		try {
			QueryBuilder qb = new QueryBuilder("select * from " + table);
			count = DBUtil.getCount(qb);
			int i = 0;
			do {
				do {
					DataTable dt = qb.executePagedDataTable(this.PageSize, i);

					String name = "CustomTable:" + table;
					byte[] bs = FileUtil.serialize(name);
					this.os.write(NumberUtil.toBytes(bs.length));
					this.os.write(bs);

					bs = FileUtil.serialize(dt);
					bs = ZipUtil.zip(bs);
					this.os.write(NumberUtil.toBytes(bs.length));
					this.os.write(bs);

					i++;
				} while (i * this.PageSize < count);
				if (i != 0)
					break;
			} while (count == 0);
		} catch (Exception e) {
			LogUtil.warn("对应的自定义表不存在：" + table);
			return;
		}
	}

	private boolean exportFile() {
		String path = SiteUtil.getAbsolutePath(this.siteID);
		try {
			exportDir(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private void exportDir(File parent) throws IOException {
		String root = SiteUtil.getAbsolutePath(this.siteID);
		root = root.replaceAll("[\\\\/]+", "/");
		File[] fs = parent.listFiles();
		for (int i = 0; i < fs.length; i++) {
			File f = fs[i];
			if (f.getName().equals(".svn")) {
				continue;
			}
			if (f.getName().equals(".shtml")) {
				continue;
			}
			if (f.isDirectory()) {
				exportDir(f);
			} else {
				byte[] bs = FileUtil.readByte(f);
				if (bs != null) {
					bs = ZipUtil.zip(bs);
					String path = f.getAbsolutePath();
					path = path.replaceAll("[\\\\/]+", "/");
					path = path.substring(root.length());
					path = "File:" + path;
					byte[] nbs = FileUtil.serialize(path);
					this.os.write(NumberUtil.toBytes(nbs.length));
					this.os.write(nbs);
					this.os.write(NumberUtil.toBytes(bs.length));
					this.os.write(bs);
					this.os.flush();
				}
			}
		}
	}

	private void transferSQL(QueryBuilder qb, Serializable obj)
			throws Exception {
		int count = DBUtil.getCount(qb);
		for (int i = 0; (i * this.PageSize < count)
				|| ((i == 0) && (count == 0)); i++) {
			DataTable dt = qb.executePagedDataTable(this.PageSize, i);

			byte[] bs = FileUtil.serialize(obj);
			this.os.write(NumberUtil.toBytes(bs.length));
			this.os.write(bs);

			bs = FileUtil.serialize(dt);
			bs = ZipUtil.zip(bs);
			this.os.write(NumberUtil.toBytes(bs.length));
			this.os.write(bs);
			this.os.flush();
		}
	}

	public static void main(String[] args) {
		SiteExporter se = new SiteExporter(206L);
		se.exportSite("G:/sky.dat");
	}
}

/*
 * com.xdarkness.cms.pub.SiteExporter JD-Core Version: 0.6.0
 */