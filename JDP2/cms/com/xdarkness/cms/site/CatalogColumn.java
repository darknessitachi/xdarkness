package com.xdarkness.cms.site;

import java.util.Date;

import com.xdarkness.cms.dataservice.ColumnUtil;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.schema.ZDColumnRelaSchema;
import com.xdarkness.schema.ZDColumnSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class CatalogColumn extends Page {
	public static String Extend_Self = "1";

	public static String Extend_Children = "2";

	public static String Extend_All = "3";

	public static String Extend_SameLevel = "4";

	public static Mapx ExtendMap = new Mapx();

	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ColumnID");
		if (XString.isEmpty(ID)) {
			params.put("VerifyType", HtmlUtil
					.mapxToOptions(ColumnUtil.VerifyTypeMap));
			params.put("InputType", HtmlUtil
					.mapxToOptions(ColumnUtil.InputTypeMap));
			params.put("IsMandatory", HtmlUtil.codeToRadios("IsMandatory",
					"YesOrNo", "N"));
			params.put("MaxLength", "100");
			params.put("Cols", "265");
			params.put("Rows", "90");
		} else {
			ZDColumnSchema column = new ZDColumnSchema();
			column.setID(ID);
			column.fill();
			params = column.toMapx();
			params.put("VerifyType", HtmlUtil.mapxToOptions(
					ColumnUtil.VerifyTypeMap, column.getVerifyType()));
			params.put("InputType", HtmlUtil.mapxToOptions(
					ColumnUtil.InputTypeMap, column.getInputType()));
			params.put("IsMandatory", HtmlUtil.codeToRadios("IsMandatory",
					"YesOrNo", column.getIsMandatory()));
		}
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String CatalogID = dga.getParam("CatalogID");
		String sql = "select * from ZDColumn where  exists (select ColumnID from ZDColumnRela where ZDColumnRela.ColumnID=ZDColumn.ID and ZDColumnRela.RelaType='1' and RelaID =?) order by ID asc";

		QueryBuilder qb = new QueryBuilder();
		qb.add(CatalogID);
		qb.setSQL(sql);
		DataTable dt = qb.executeDataTable();
		dt.decodeColumn("VerifyType", ColumnUtil.VerifyTypeMap);
		dt.decodeColumn("InputType", ColumnUtil.InputTypeMap);
		dga.bindData(dt);
	}

	public void add() {
		String catalogID = $V("CatalogID");
		String columnCode = $V("Code");
		int catalogType = new QueryBuilder(
				"select type from zccatalog where id = ?", catalogID)
				.executeInt();
		long count = new QueryBuilder(
				"select count(*) from ZDColumnRela where RelaType='1' and RelaID = ? and ColumnCode =? ",
				catalogID, columnCode).executeLong();
		if (count > 0L) {
			this.response.setLogInfo(0, "已经存在相同的字段");
			return;
		}
		Transaction trans = new Transaction();
		ZDColumnSchema column = new ZDColumnSchema();
		column.setValue(this.request);

		String defaultValue = column.getDefaultValue();
		defaultValue = defaultValue.replaceAll("　　", ",");
		defaultValue = defaultValue.replaceAll("　", ",");
		defaultValue = defaultValue.replaceAll("  ", ",");
		defaultValue = defaultValue.replaceAll(" ", ",");
		defaultValue = defaultValue.replaceAll(",,", ",");
		defaultValue = defaultValue.replaceAll("，，", ",");
		defaultValue = defaultValue.replaceAll("，", ",");
		column.setDefaultValue(defaultValue);

		column.setID(NoUtil.getMaxID("ColumnID"));
		column.setSiteID(ApplicationPage.getCurrentSiteID());
		column.setOrderFlag(OrderUtil.getDefaultOrder());
		column.setAddTime(new Date());
		column.setAddUser(User.getUserName());

		if (ColumnUtil.Input.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setListOption("");
		} else if (ColumnUtil.Text.equals(column.getInputType())) {
			column.setListOption("");
		} else if (ColumnUtil.Select.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.Radio.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.Checkbox.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setVerifyType(ColumnUtil.STRING);
		} else if ((ColumnUtil.DateInput.equals(column.getInputType()))
				|| (ColumnUtil.TimeInput.equals(column.getInputType()))) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.ImageInput.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.HTMLInput.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		}

		String extend = $V("Extend");
		if (Extend_Self.equals(extend)) {
			ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
			rela.setID(NoUtil.getMaxID("ColumnRelaID"));
			rela.setColumnID(column.getID());
			rela.setColumnCode(column.getCode());
			rela.setRelaType("1");
			rela.setRelaID(catalogID);
			rela.setAddTime(column.getAddTime());
			rela.setAddUser(column.getAddUser());
			trans.add(rela, OperateType.INSERT);
		} else if (Extend_Children.equals(extend)) {
			String innerCode = CatalogUtil.getInnerCode(catalogID);
			DataTable childCatalogDT = new QueryBuilder(
					"select id from zccatalog where innercode like '"
							+ innerCode + "%'").executeDataTable();
			for (int i = 0; i < childCatalogDT.getRowCount(); i++) {
				ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
				rela.setColumnCode(column.getCode());
				rela.setRelaType("1");
				rela.setRelaID(childCatalogDT.getString(i, 0));
				if (rela.query().size() > 0) {
					continue;
				}
				rela.setID(NoUtil.getMaxID("ColumnRelaID"));
				rela.setColumnID(column.getID());
				rela.setColumnCode(column.getCode());
				rela.setRelaType("1");
				rela.setRelaID(childCatalogDT.getString(i, 0));
				rela.setAddTime(column.getAddTime());
				rela.setAddUser(column.getAddUser());
				trans.add(rela, OperateType.INSERT);
			}
		} else if (Extend_All.equals(extend)) {
			DataTable allCatalogDT = new QueryBuilder(
					"select id from zccatalog where Type=" + catalogType
							+ " and siteID =" + ApplicationPage.getCurrentSiteID())
					.executeDataTable();
			for (int i = 0; i < allCatalogDT.getRowCount(); i++) {
				ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
				rela.setColumnCode(column.getCode());
				rela.setRelaType("1");
				rela.setRelaID(allCatalogDT.getString(i, 0));
				if (rela.query().size() > 0) {
					continue;
				}
				rela.setID(NoUtil.getMaxID("ColumnRelaID"));
				rela.setColumnID(column.getID());
				rela.setColumnCode(column.getCode());
				rela.setRelaType("1");
				rela.setRelaID(allCatalogDT.getString(i, 0));
				rela.setAddTime(column.getAddTime());
				rela.setAddUser(column.getAddUser());
				trans.add(rela, OperateType.INSERT);
			}
		} else if (Extend_SameLevel.equals(extend)) {
			int level = new QueryBuilder(
					"select treelevel from zccatalog where id =" + catalogID)
					.executeInt();
			DataTable levelCatalogDT = new QueryBuilder(
					"select id from zccatalog where siteID ="
							+ ApplicationPage.getCurrentSiteID() + " and Type="
							+ catalogType + " and treelevel=" + level)
					.executeDataTable();
			for (int i = 0; i < levelCatalogDT.getRowCount(); i++) {
				ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
				rela.setColumnCode(column.getCode());
				rela.setRelaType("1");
				rela.setRelaID(levelCatalogDT.getString(i, 0));
				if (rela.query().size() > 0) {
					continue;
				}
				rela.setID(NoUtil.getMaxID("ColumnRelaID"));
				rela.setColumnID(column.getID());
				rela.setColumnCode(column.getCode());
				rela.setRelaType("1");
				rela.setRelaID(levelCatalogDT.getString(i, 0));
				rela.setAddTime(column.getAddTime());
				rela.setAddUser(column.getAddUser());
				trans.add(rela, OperateType.INSERT);
			}
		}

		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
		catalog.setModifyTime(new Date());
		catalog.setModifyUser(User.getUserName());
		trans.add(catalog, OperateType.UPDATE);

		trans.add(column, OperateType.INSERT);
		if (trans.commit())
			this.response.setLogInfo(1, "新建成功");
		else
			this.response.setLogInfo(0, "新建失败");
	}

	public void save() {
		String catalogID = $V("CatalogID");
		String columnCode = $V("Code");
		long count = new QueryBuilder(
				"select count(*) from ZDColumnRela where RelaType='1' and RelaID = ? and ColumnCode =? and ColumnID!="
						+ $V("ColumnID"), catalogID, columnCode).executeLong();
		if (count > 0L) {
			this.response.setLogInfo(0, "已经存在相同的字段");
			return;
		}
		ZDColumnSchema column = new ZDColumnSchema();
		column.setID($V("ColumnID"));
		column.fill();
		column.setValue(this.request);

		String defaultValue = column.getDefaultValue();
		defaultValue = defaultValue.replaceAll("　　", ",");
		defaultValue = defaultValue.replaceAll("　", ",");
		defaultValue = defaultValue.replaceAll("  ", ",");
		defaultValue = defaultValue.replaceAll(" ", ",");
		defaultValue = defaultValue.replaceAll(",,", ",");
		defaultValue = defaultValue.replaceAll("，，", ",");
		defaultValue = defaultValue.replaceAll("，", ",");
		column.setDefaultValue(defaultValue);

		if (ColumnUtil.Input.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setListOption("");
		} else if (ColumnUtil.Text.equals(column.getInputType())) {
			column.setListOption("");
		} else if (ColumnUtil.Select.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.Radio.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.Checkbox.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.DateInput.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.ImageInput.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.HTMLInput.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		}
		Transaction trans = new Transaction();
		trans.add(column, OperateType.UPDATE);
		trans.add(new QueryBuilder(
				"update zdcolumnrela set ColumnCode=? where ColumnID=?", column
						.getCode(), column.getID()));
		trans.add(new QueryBuilder(
				"update zdcolumnvalue set ColumnCode=? where ColumnID=?",
				column.getCode(), column.getID()));

		int catalogType = new QueryBuilder(
				"select type from zccatalog where id = ?", catalogID)
				.executeInt();

		String extend = $V("Extend");
		if (!Extend_Self.equals(extend)) {
			if (Extend_Children.equals(extend)) {
				String innerCode = CatalogUtil.getInnerCode(catalogID);
				DataTable childCatalogDT = new QueryBuilder(
						"select id from zccatalog where innercode like '"
								+ innerCode
								+ "%' and not exists (select 'x' from zdcolumnrela b where b.ColumnCode='"
								+ column.getCode() + "' and b.RelaType='" + "1"
								+ "' and b.RelaID = zccatalog.ID)")
						.executeDataTable();
				for (int i = 0; i < childCatalogDT.getRowCount(); i++) {
					ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
					rela.setID(NoUtil.getMaxID("ColumnRelaID"));
					rela.setColumnID(column.getID());
					rela.setColumnCode(column.getCode());
					rela.setRelaType("1");
					rela.setRelaID(childCatalogDT.getString(i, 0));
					rela.setAddTime(column.getAddTime());
					rela.setAddUser(column.getAddUser());
					trans.add(rela, OperateType.INSERT);
				}
			} else if (Extend_All.equals(extend)) {
				DataTable allCatalogDT = new QueryBuilder(
						"select id from zccatalog where Type="
								+ catalogType
								+ " and siteID ="
								+ ApplicationPage.getCurrentSiteID()
								+ " and not exists (select 'x' from zdcolumnrela b where b.ColumnCode='"
								+ column.getCode() + "' and b.RelaType='" + "1"
								+ "' and b.RelaID = zccatalog.ID)")
						.executeDataTable();
				for (int i = 0; i < allCatalogDT.getRowCount(); i++) {
					ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
					rela.setID(NoUtil.getMaxID("ColumnRelaID"));
					rela.setColumnID(column.getID());
					rela.setColumnCode(column.getCode());
					rela.setRelaType("1");
					rela.setRelaID(allCatalogDT.getString(i, 0));
					rela.setAddTime(column.getAddTime());
					rela.setAddUser(column.getAddUser());
					trans.add(rela, OperateType.INSERT);
				}
			} else if (Extend_SameLevel.equals(extend)) {
				int level = new QueryBuilder(
						"select treelevel from zccatalog where id ="
								+ catalogID).executeInt();
				DataTable levelCatalogDT = new QueryBuilder(
						"select id from zccatalog where siteID ="
								+ ApplicationPage.getCurrentSiteID()
								+ " and Type="
								+ catalogType
								+ " and treelevel="
								+ level
								+ " and not exists (select 'x' from zdcolumnrela b where b.ColumnCode='"
								+ column.getCode() + "' and b.RelaType='" + "1"
								+ "' and b.RelaID = zccatalog.ID)")
						.executeDataTable();
				for (int i = 0; i < levelCatalogDT.getRowCount(); i++) {
					ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
					rela.setID(NoUtil.getMaxID("ColumnRelaID"));
					rela.setColumnID(column.getID());
					rela.setColumnCode(column.getCode());
					rela.setRelaType("1");
					rela.setRelaID(levelCatalogDT.getString(i, 0));
					rela.setAddTime(column.getAddTime());
					rela.setAddUser(column.getAddUser());
					trans.add(rela, OperateType.INSERT);
				}
			}
		}

		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
		catalog.setModifyTime(new Date());
		catalog.setModifyUser(User.getUserName());
		trans.add(catalog, OperateType.UPDATE);

		if (trans.commit())
			this.response.setLogInfo(1, "保存成功!");
		else
			this.response.setLogInfo(0, "保存失败!");
	}

	public void del() {
		String IDs = $V("IDs");
		long catalogID = Long.parseLong($V("CatalogID"));
		Transaction trans = new Transaction();
		String extend = $V("Extend");
		String wherePart = "";
		if (Extend_Self.equals(extend)) {
			wherePart = " where columnID in (" + IDs + ") and RelaType='" + "1"
					+ "' and RelaID='" + catalogID + "'";
			deleteTableData("ZDColumnRela", wherePart, trans);
			wherePart = " where columnID in (" + IDs + ") and RelaType='" + "2"
					+ "' and RelaID='" + catalogID + "'";
			deleteTableData("ZDColumnValue", wherePart, trans);
		} else if (Extend_Children.equals(extend)) {
			String innerCode = CatalogUtil.getInnerCode(catalogID);
			wherePart = " where columnID in ("
					+ IDs
					+ ") and RelaType='"
					+ "1"
					+ "' and exists (select '' from ZCCatalog b where b.ID=RelaID and b.InnerCode like '"
					+ innerCode + "%')";
			deleteTableData("ZDColumnRela", wherePart, trans);
			wherePart = " where columnID in ("
					+ IDs
					+ ") and RelaType='"
					+ "2"
					+ "' and exists (select '' from ZCCatalog b where b.ID=RelaID and b.InnerCode like '"
					+ innerCode + "%')";
			deleteTableData("ZDColumnValue", wherePart, trans);
		} else if (Extend_All.equals(extend)) {
			wherePart = " where columnID in ("
					+ IDs
					+ ") and RelaType='"
					+ "1"
					+ "' and exists (select '' from ZCCatalog b where b.ID=RelaID and b.SiteID = "
					+ ApplicationPage.getCurrentSiteID() + ")";
			deleteTableData("ZDColumnRela", wherePart, trans);
			wherePart = " where columnID in ("
					+ IDs
					+ ") and RelaType='"
					+ "2"
					+ "' and exists (select '' from ZCCatalog b where b.ID=RelaID and b.SiteID = "
					+ ApplicationPage.getCurrentSiteID() + ")";
			deleteTableData("ZDColumnValue", wherePart, trans);
		}

		wherePart = " where not exists (select 1 from ZDColumnRela where ZDColumn.ID=ColumnID)";
		deleteTableData("ZDColumn", wherePart, trans);

		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
		catalog.setModifyTime(new Date());
		catalog.setModifyUser(User.getUserName());
		trans.add(catalog, OperateType.UPDATE);

		if (trans.commit())
			this.response.setLogInfo(1, "删除成功");
		else
			this.response.setLogInfo(0, "删除失败");
	}

	public void deleteTableData(String tableName, String wherePart,
			Transaction trans) {
		String backupNO = String.valueOf(System.currentTimeMillis()).substring(
				1, 11);
		String backupOperator = User.getUserName();
		String backupTime = DateUtil
				.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
		String backup = "insert into b" + tableName + " select " + tableName
				+ ".*,'" + backupNO + "','" + backupOperator + "','"
				+ backupTime + "',null from " + tableName + wherePart;
		String delete = "delete from " + tableName + wherePart;
		trans.add(new QueryBuilder(backup));
		trans.add(new QueryBuilder(delete));
	}

	public static Mapx initCopyTo(Mapx params) {
		Mapx map = new Mapx();
		String tIDs = params.get("IDs").toString();
		map.put("IDs", tIDs);
		String tCatalogID = params.get("CatalogID").toString();
		map.put("CatalogID", tCatalogID);
		String tSiteID = new QueryBuilder(
				"select siteid from ZCCatalog where id=?", tCatalogID)
				.executeString();
		map.put("SiteID", tSiteID);
		DataTable dt = new QueryBuilder(
				"select name,id from ZCCatalog where siteid=? and id<>? order by id",
				tSiteID, tCatalogID).executeDataTable();
		map.put("optCatalog", HtmlUtil.dataTableToOptions(dt));
		return map;
	}
}

/*
 * com.xdarkness.cms.site.CatalogColumn JD-Core Version: 0.6.0
 */