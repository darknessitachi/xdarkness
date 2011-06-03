package com.abigdreamer.java.net.orm;

import java.io.IOException;

import com.abigdreamer.java.net.io.FileUtil;
import com.abigdreamer.java.net.message.LongTimeTask;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.sql.DBUtil;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.BufferedRandomAccessFile;
import com.abigdreamer.java.net.util.LogUtil;
import com.abigdreamer.java.net.util.NumberUtil;
import com.abigdreamer.java.net.util.ZipUtil;

public class DBExport {
	private static final int PageSize = 500;
	private BufferedRandomAccessFile braf;
	private LongTimeTask task;

	public void setTask(LongTimeTask task) {
		this.task = task;
	}

	public void exportDB(String file) {
		FileUtil.delete(file);
		try {
			this.braf = new BufferedRandomAccessFile(file, "rw");
			String[] arr = SchemaUtil.getAllSchemaClassName();
			for (int i = 0; i < arr.length; i++) {
				try {
					if (this.task != null) {
						this.task
								.setPercent(new Double(i * 100.0D / arr.length)
										.intValue());
						this.task.setCurrentInfo("正在导出表" + arr[i]);
					}
					transferOneTable(arr[i]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				DataTable dt = new QueryBuilder(
						"select Code,ID from ZCCustomTable where Type='Custom'")
						.executeDataTable();
				for (int i = 0; i < dt.getRowCount(); i++)
					transferCustomTable(dt.getString(i, "Code"), dt.getString(
							i, "ID"));
			} catch (Throwable t) {
				LogUtil.warn("系统中没有自定义表");
			}
		} catch (Exception e1) {
			e1.printStackTrace();

			if (this.braf != null)
				try {
					this.braf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		} finally {
			if (this.braf != null)
				try {
					this.braf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	private void transferOneTable(String schemaName) throws Exception {
	
		Schema schema = (Schema) Class.forName(schemaName).newInstance();
		int count = 0;
		try {
			count = new QueryBuilder("select count(*) from " + schema.TableCode)
					.executeInt();
			for (int i = 0; i * PageSize < count || i == 0 && count == 0; i++) {
					SchemaSet set = schema.querySet(null, PageSize, i);

					byte[] bs = schemaName.getBytes();
					this.braf.write(NumberUtil.toBytes(bs.length));
					this.braf.write(bs);

					bs = FileUtil.serialize(set);
					bs = ZipUtil.zip(bs);
					this.braf.write(NumberUtil.toBytes(bs.length));
					this.braf.write(bs);

					i++;
				} 
		} catch (Exception e) {
			LogUtil.warn("Schema对应的表不存在：" + schemaName);
			return;
		}
	}

	private void transferCustomTable(String table, String ID) throws Exception {
		int count = 0;
		try {
			String columnName = new QueryBuilder(
					"select Code from ZCCustomTableColumn where TableID=?", ID)
					.executeString();
			QueryBuilder qb = new QueryBuilder("select * from " + table
					+ " order by " + columnName);
			count = DBUtil.getCount(qb);
			for (int i = 0; i * 500 < count || i == 0 && count == 0; i++) {
					DataTable dt = qb.setPageSize(PageSize).setPageIndex(i).executePagedDataTable();

					byte[] bs = table.getBytes();
					this.braf.write(NumberUtil.toBytes(bs.length));
					this.braf.write(bs);

					bs = FileUtil.serialize(dt);
					bs = ZipUtil.zip(bs);
					this.braf.write(NumberUtil.toBytes(bs.length));
					this.braf.write(bs);

					i++;
				} 
		} catch (Exception e) {
			LogUtil.warn("对应的自定义表不存在" + table + ":" + e.getMessage());
			return;
		}
	}
}
