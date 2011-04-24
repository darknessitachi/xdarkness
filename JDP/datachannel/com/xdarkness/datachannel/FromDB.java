package com.xdarkness.datachannel;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.xdarkness.cms.api.ArticleAPI;
import com.xdarkness.cms.dataservice.CustomTableUtil;
import com.xdarkness.cms.dataservice.OuterDatabase;
import com.xdarkness.cms.document.Article;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCArticleSchema;
import com.xdarkness.schema.ZCArticleSet;
import com.xdarkness.schema.ZCDBGatherSchema;
import com.xdarkness.schema.ZCDBGatherSet;
import com.xdarkness.framework.DBUtil;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.connection.XConnectionConfig;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.extend.ExtendManager;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.orm.data.DataTableUtil;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class FromDB extends Page {
	public static Mapx init(Mapx map) {
		String id = map.getString("ID");
		if (XString.isNotEmpty(id)) {
			ZCDBGatherSchema gather = new ZCDBGatherSchema();
			gather.setID(id);
			gather.fill();
			map.putAll(gather.toMapx());
			map.put("ArticleStatus", HtmlUtil.mapxToOptions(Article.STATUS_MAP,
					gather.getArticleStatus()));
			map.put("CatalogName", CatalogUtil.getName(gather.getCatalogID()+""));
		} else {
			map
					.put("ArticleStatus", HtmlUtil
							.mapxToOptions(Article.STATUS_MAP));
		}
		return map;
	}

	public static Mapx initColumn(Mapx map) {
		Mapx columns = new Mapx();
		columns.put("Title", "文章标题");
		columns.put("Content", "文章内容");
		columns.put("Author", "作者");
		columns.put("PublishDate", "发布时间");
		columns.put("AddTime", "添加时间");
		columns.put("Attributes", "文章属性");

		columns.put("Keyword", "关键摘要");
		columns.put("Summary", "摘要");
		columns.put("ShortTitle", "短标题");
		columns.put("SubTitle", "副标题");

		String ID = map.getString("ID");
		if (XString.isNotEmpty(ID)) {
			ZCDBGatherSchema gather = new ZCDBGatherSchema();
			gather.setID(ID);
			gather.fill();
			Mapx configMap = parseConfig(gather.getMappingConfig());
			map.putAll(configMap);
			map.put("TitleUniteRule", XString.javaEncode(map
					.getString("TitleUniteRule")));
			map.put("ContentUniteRule", XString.javaEncode(map
					.getString("ContentUniteRule")));
			map.put("RedirectURLUniteRule", XString.javaEncode(map
					.getString("RedirectURLUniteRule")));
		}

		String html = HtmlUtil.mapxToOptions(columns, true);
		map.put("Columns", html);
		return map;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String sql = "select * from ZCDBGather where SiteID=?";
		DataTable dt = new QueryBuilder(sql, ApplicationPage.getCurrentSiteID())
				.executeDataTable();
		dt.insertColumn("CatalogName");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "CatalogName", CatalogUtil.getName(dt.getString(i,
					"CatalogID")));
		}
		Mapx map = new QueryBuilder("select ID,Name from ZCDatabase")
				.executeDataTable().toMapx(0, 1);
		dt.decodeColumn("DatabaseID", map);
		map = new Mapx();
		map.put("Y", "启用");
		map.put("N", "停用");
		dt.decodeColumn("Status", map);
		dga.bindData(dt);
	}

	public static void columnDataBind(DataGridAction dga) {
		String ID = dga.getParam("ID");
		if (XString.isNotEmpty(ID)) {
			ZCDBGatherSchema gather = new ZCDBGatherSchema();
			gather.setID(ID);
			gather.fill();
			Mapx map = parseConfig(gather.getMappingConfig());
			DataTable dt = (DataTable) map.get("DataTable");
			dga.bindData(dt);
		} else {
			String DatabaseID = dga.getParam("DatabaseID");
			String TableName = dga.getParam("TableName");
			if ((XString.isEmpty(DatabaseID))
					|| (XString.isEmpty(TableName))) {
				dga.bindData(new DataTable());
				return;
			}
			XConnectionConfig dcc = OuterDatabase.getDBConnConfig(Long
					.parseLong(DatabaseID));
			DataTable dt = DBUtil.getColumnInfo(dcc, TableName);
			DataTable newTable = new DataTable();
			newTable.insertColumn("Code");
			newTable.insertColumn("IsPrimaryKey");
			newTable.insertColumn("DataType");
			for (int i = 0; i < dt.getRowCount(); i++) {
				DataRow dr = dt.getDataRow(i);
				newTable.insertRow(new Object[] { dr.getString("Column_Name"),
						dr.getString("isKey"), dr.getString("Type_Name") });
			}
			dga.bindData(newTable);
		}
	}

	private static Mapx parseConfig(String xml) {
		Mapx map = new Mapx();
		SAXReader reader = new SAXReader(false);
		try {
			Document doc = reader.read(new StringReader(xml));
			Element root = doc.getRootElement();
			List configs = root.elements("config");
			for (int i = 0; i < configs.size(); i++) {
				Element conf = (Element) configs.get(i);
				String name = conf.attributeValue("name");
				String value = conf.getText();
				if (name.equalsIgnoreCase("DataTable")) {
					DataTable dt = DataTableUtil.txtToDataTable(value, 
							"\t", "\n");
					map.put(name, dt);
				} else {
					map.put(name, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public void add() {
		String id = $V("ID");
		ZCDBGatherSchema dg = new ZCDBGatherSchema();
		Transaction tran = new Transaction();
		if (XString.isNotEmpty(id)) {
			dg.setID(id);
			dg.fill();
			dg.setModifyTime(new Date());
			dg.setModifyUser(User.getUserName());
			tran.add(dg, OperateType.UPDATE);
		} else {
			dg.setID(NoUtil.getMaxID("DBGatherID"));
			dg.setAddTime(new Date());
			dg.setAddUser(User.getUserName());
			tran.add(dg, OperateType.INSERT);
		}
		dg.setValue(this.request);
		dg.setMappingConfig(generateConfig(this.request));
		dg.setSiteID(ApplicationPage.getCurrentSiteID());
		if (tran.commit())
			this.response.setMessage("保存成功!");
		else
			this.response.setMessage("保存数据时发生错误:" + tran.getExceptionMessage());
	}

	private static String generateConfig(Mapx Request) {
		DataTable dt = (DataTable) Request.get("DataTable");
		Document doc = DocumentHelper.createDocument();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(Constant.GlobalCharset);
		Element root = doc.addElement("configs");
		Element ele = root.addElement("config");
		ele.addAttribute("name", "TitleUniteFlag");
		ele.addCDATA(Request.getString("TitleUniteFlag"));

		ele = root.addElement("config");
		ele.addAttribute("name", "TitleUniteRule");
		ele.addCDATA(Request.getString("TitleUniteRule"));

		ele = root.addElement("config");
		ele.addAttribute("name", "ContentUniteFlag");
		ele.addCDATA(Request.getString("ContentUniteFlag"));

		ele = root.addElement("config");
		ele.addAttribute("name", "ContentUniteRule");
		ele.addCDATA(Request.getString("ContentUniteRule"));

		ele = root.addElement("config");
		ele.addAttribute("name", "RedirectURLUniteFlag");
		ele.addCDATA(Request.getString("RedirectURLUniteFlag"));

		ele = root.addElement("config");
		ele.addAttribute("name", "RedirectURLUniteRule");
		ele.addCDATA(Request.getString("RedirectURLUniteRule"));

		ele = root.addElement("config");
		ele.addAttribute("name", "DataTable");
		ele.addCDATA(dt.toString());

		Object[] ks = Request.keyArray();
		for (int i = 0; i < ks.length; i++) {
			if (ks[i].toString().startsWith("MaxValue.")) {
				ele = root.addElement("config");
				ele.addAttribute("name", ks[i].toString());
				ele.addCDATA(Request.getString(ks[i]));
			}
		}

		StringWriter sw = new StringWriter();
		try {
			XMLWriter output = new XMLWriter(sw, format);
			output.write(doc);
			output.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return sw.toString();
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			return;
		}
		ZCDBGatherSet set = new ZCDBGatherSchema().query(new QueryBuilder(
				"where id in (" + ids + ")"));
		if (set.deleteAndBackup())
			this.response.setMessage("删除成功!");
		else
			this.response.setMessage("删除数据时发生错误!");
	}

	public void execute() {
		final long id = Long.parseLong($V("ID"));
		final boolean restartFlag = "Y".equals($V("RestartFlag"));
		LongTimeTask ltt = LongTimeTask.getInstanceByType("DBGather" + id);
		if (ltt != null) {
			if (!ltt.isAlive()) {
				LongTimeTask.removeInstanceById(ltt.getTaskID());
			} else {
				this.response.setError("相关任务正在运行中，请先中止！");
				return;
			}
		}
		ltt = new LongTimeTask() {

			public void execute() {
				ZCDBGatherSchema gather = new ZCDBGatherSchema();
				gather.setID(id);
				gather.fill();
				FromDB.executeGather(gather, restartFlag, this);
				setPercent(100);
			}
		};
		ltt.setType("DBGather" + id);
		ltt.setUser(User.getCurrent());
		ltt.start();
		$S("TaskID", ltt.getTaskID());
	}

	public static void executeGather(ZCDBGatherSchema gather,
			boolean restartFlag, LongTimeTask task) {
		if (task == null) {
			task = LongTimeTask.createEmptyInstance();
		}

		Mapx map = parseConfig(gather.getMappingConfig());
		if (restartFlag) {
			map.put("LastTime", "0");
			Object[] ks = map.keyArray();
			for (int i = 0; i < ks.length; i++) {
				if (ks[i].toString().startsWith("MaxValue.")) {
					map.put(ks[i], "");
				}
			}
		}
		String wherePart = "";

		String newRecordRule = gather.getNewRecordRule();
		DataTable mappingTable = (DataTable) map.get("DataTable");
		ArrayList maxList = new ArrayList();
		if (XString.isNotEmpty(newRecordRule)) {
			int lastIndex = 0;
			StringBuffer sb = new StringBuffer();
			while (newRecordRule.indexOf("${", lastIndex) >= 0) {
				int indexStart = newRecordRule.indexOf("${", lastIndex);
				int indexEnd = newRecordRule.indexOf("}", indexStart + 1);
				sb.append(newRecordRule.substring(lastIndex, indexStart));
				String field = newRecordRule
						.substring(indexStart + 2, indexEnd);
				if ((field.indexOf("(") < 0) || (field.indexOf(")") > 0)) {
					sb.append("'0'");
				} else {
					field = field.substring(field.indexOf("(") + 1, field
							.indexOf(")"));
					String max = map.getString("MaxValue." + field);
					maxList.add(field);
					if (XString.isNotEmpty(max)) {
						sb.append(getMaxValue(mappingTable, field, max));
					}
				}
				lastIndex = indexEnd + 1;
			}
			sb.append(newRecordRule.substring(lastIndex));
			wherePart = sb.toString();
		}

		if (XString.isNotEmpty(gather.getSQLCondition())) {
			if (XString.isNotEmpty(wherePart))
				wherePart = wherePart + " and " + gather.getSQLCondition();
			else {
				wherePart = gather.getSQLCondition();
			}
		}

		if (XString.isEmpty(wherePart))
			wherePart = "where 1=1";
		else {
			wherePart = "where " + wherePart;
		}

		int pageSize = 100;
		int total = CustomTableUtil.getTotal(gather.getTableName(), gather
				.getDatabaseID(), wherePart);
		for (int i = 0; i * pageSize < total; i++) {
			DataTable dt = CustomTableUtil.getData(gather.getTableName(),
					gather.getDatabaseID(), new QueryBuilder(wherePart),
					pageSize, i);

			for (int j = 0; j < dt.getRowCount(); j++) {
				for (int k = 0; k < maxList.size(); k++) {
					String field = maxList.get(k).toString();
					int dataType = getDataType(mappingTable, field);
					String max = map.getString("MaxValue." + field);
					String v = dt.getString(j, field);
					if (dataType == 1) {
						if (max.compareTo(v) < 0)
							map.put("MaxValue." + field, v);
					} else if (dataType == 0) {
						Date d1 = DateUtil.parseDateTime(max);
						Date d2 = DateUtil.parseDateTime(v);
						if (d1.getTime() < d2.getTime())
							map.put("MaxValue." + field, v);
					} else {
						double d1 = Double.parseDouble(max);
						double d2 = Double.parseDouble(v);
						if (d1 < d2) {
							map.put("MaxValue." + field, v);
						}
					}
				}
			}

			Transaction tran = new Transaction();
			for (int j = 0; j < dt.getRowCount(); j++) {
				DataRow dr = dt.getDataRow(j);
				Mapx valueMap = new Mapx();

				task.setPercent(new Double((i * pageSize + j + 1) * 1.0D
						/ total).intValue());
				task.setCurrentInfo("正在采集第" + (i * pageSize + j + 1) + "条数据");

				if ("Y".equals(map.getString("TitleUniteFlag"))) {
					String rule = map.getString("TitleUniteRule");
					if (XString.isNotEmpty(rule)) {
						rule = HtmlUtil.replaceWithDataRow(dr, rule);
						valueMap.put("Title", rule);
					}
				}
				if ("Y".equals(map.getString("RedirectURLUniteFlag"))) {
					String rule = map.getString("RedirectURLUniteRule");
					if (XString.isNotEmpty(rule)) {
						rule = HtmlUtil.replaceWithDataRow(dr, rule);
						valueMap.put("RedirectURL", rule);
						valueMap.put("Type", "4");
					}
				}
				if ("Y".equals(map.getString("ContentUniteFlag"))) {
					String rule = map.getString("ContentUniteRule");
					if (XString.isNotEmpty(rule)) {
						rule = HtmlUtil.replaceWithDataRow(dr, rule);
						valueMap.put("Content", rule);
					}
				}

				String key = "DBGatherKey";
				for (int k = 0; k < mappingTable.getRowCount(); k++) {
					if (XString.isNotEmpty(mappingTable.getString(k,
							"Mapping"))) {
						valueMap.put(mappingTable.getString(k, "Mapping"), dr
								.getString(mappingTable.getString(k, "Code")));
					}
					if ("Y".equals(mappingTable.getString(k, "IsPrimaryKey"))) {
						key = key
								+ ","
								+ dr.getString(mappingTable
										.getString(k, "Code"));
					}
				}
				valueMap.put("ClusterSource", key);

				if (XString.isEmpty(valueMap.getString("Title"))) {
					continue;
				}
				if (ExtendManager.hasAction("FromDB.BeforeSave")) {
					ExtendManager.executeAll("FromDB.BeforeSave", new Object[] {
							tran, valueMap });
				}

				ZCArticleSchema article = new ZCArticleSchema();
				article.setClusterSource(key);
				article.setCatalogID(gather.getCatalogID());
				ZCArticleSet set = article.query();
				if (set.size() > 0) {
					article = set.get(0);
					article.setValue(valueMap);
					article.setBranchInnerCode("0001");
					tran.add(article, OperateType.UPDATE);
				} else {
					article.setValue(valueMap);
					article.setBranchInnerCode("0001");
					article.setStatus(gather.getArticleStatus());
					ArticleAPI api = new ArticleAPI();
					api.setSchema(article);
					api.insert(tran);
				}
			}
			tran.commit();
		}
		String config = generateConfig(map);
		gather.setMappingConfig(config);
		gather.update();
	}

	private static int getDataType(DataTable dt, String field) {
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.getString(i, "Code").equalsIgnoreCase(field)) {
				int dataType = Integer.parseInt(dt.getString(i, "DataType"));
				return dataType;
			}
		}
		return 1;
	}

	private static String getMaxValue(DataTable dt, String field,
			String maxValue) {
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.getString(i, "Code").equalsIgnoreCase(field)) {
				int dataType = Integer.parseInt(dt.getString(i, "DataType"));
				if ((dataType == 0) || (dataType == 1)) {
					return "'" + maxValue + "'";
				}
				return maxValue;
			}
		}

		return "0";
	}
}

/*
 * com.xdarkness.datachannel.FromDB JD-Core Version: 0.6.0
 */