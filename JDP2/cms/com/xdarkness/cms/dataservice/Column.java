package com.xdarkness.cms.dataservice;

import java.util.Date;

import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZDColumnRelaSchema;
import com.xdarkness.schema.ZDColumnRelaSet;
import com.xdarkness.schema.ZDColumnSchema;
import com.xdarkness.schema.ZDColumnSet;
import com.xdarkness.schema.ZDColumnValueSchema;
import com.xdarkness.schema.ZDColumnValueSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class Column extends Page {
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
			params.put("ColSize", "265");
			params.put("RowSize", "90");
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
		params.put("DataType", HtmlUtil.mapxToOptions(CustomTable.DBTypeMap));
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String FormID = dga.getParam("FormID");
		String sql = "select ZDColumn.* ,(select FormName from ZDForm where ZDForm.ID = ZDColumn.FormID) as FormName from ZDColumn where FormID=? order by OrderFlag asc ";
		QueryBuilder qb = new QueryBuilder();
		qb.add(FormID);
		qb.setSQL(sql);
		DataTable dt = qb.executeDataTable();
		dt.decodeColumn("VerifyType", ColumnUtil.VerifyTypeMap);
		dt.decodeColumn("InputType", ColumnUtil.InputTypeMap);
		dt.decodeColumn("DataType", CustomTable.DBTypeMap);
		dga.bindData(dt);
	}

	public void add() {
		ZDColumnSchema column = new ZDColumnSchema();
		String code = $V("Code");
		String formID = $V("FormID");
		long count = new QueryBuilder(
				"select count(*) from ZDColumn where FormID=? and Code=?",
				formID, code).executeLong();
		if (count > 0L) {
			this.response.setLogInfo(0, "已经存在相同的字段");
			return;
		}
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
		column.setOrderFlag(OrderUtil.getDefaultOrder());
		column.setAddTime(new Date());
		column.setAddUser(User.getUserName());
		column.setSiteID(ApplicationPage.getCurrentSiteID());

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
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		}

		Transaction trans = new Transaction();
		trans.add(column, OperateType.INSERT);
		if (trans.commit())
			this.response.setLogInfo(1, "新建成功");
		else
			this.response.setLogInfo(0, "新建失败");
	}

	public void edit() {
		String ID = $V("ColumnID");
		ZDColumnSchema column = new ZDColumnSchema();
		column.setID(ID);
		column.fill();
		long count = new QueryBuilder(
				"select count(*) from ZDColumn where FormID=? and Code=?",
				$V("FormID"), $V("ColumnCode")).executeLong();
		if (count > 1L) {
			this.response.setLogInfo(0, "已经存在相同的字段");
			return;
		}
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

		column.setModifyUser(User.getUserName());
		column.setModifyTime(new Date());

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
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		}

		if (column.update())
			this.response.setLogInfo(1, "修改成功!");
		else
			this.response.setLogInfo(0, "修改失败!");
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setLogInfo(0, "传入ID时发生错误!");
			return;
		}
		ZDColumnSchema column = new ZDColumnSchema();
		Transaction trans = new Transaction();
		ZDColumnSet set = column.query(new QueryBuilder("where id in (" + ids
				+ ")"));
		ZDColumnRelaSet relaSet = new ZDColumnRelaSchema()
				.query(new QueryBuilder("where columnID in (" + ids
						+ ") and RelaType='" + "1" + "'"));
		ZDColumnValueSet valueSet = new ZDColumnValueSchema()
				.query(new QueryBuilder("where columnID in (" + ids
						+ ") and RelaType='" + "2" + "'"));
		trans.add(set, OperateType.DELETE_AND_BACKUP);
		trans.add(relaSet, OperateType.DELETE_AND_BACKUP);
		trans.add(valueSet, OperateType.DELETE_AND_BACKUP);
		if (trans.commit())
			this.response.setLogInfo(1, "删除成功");
		else
			this.response.setLogInfo(0, "删除失败");
	}

	public void sortColumn() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String formID = $V("FormID");
		if ((!XString.checkID(target)) && (!XString.checkID(orders))) {
			return;
		}
		if (OrderUtil.updateOrder("ZDColumn", type, target, orders,
				" FormID = " + formID))
			this.response.setMessage("排序成功");
		else
			this.response.setError("排序失败");
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
 * com.xdarkness.cms.dataservice.Column JD-Core Version: 0.6.0
 */