package com.xdarkness.shop.web;

import com.xdarkness.schema.ZSOrderSchema;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class Sending extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		String searchUserName = dga.getParam("searchUserName");

		QueryBuilder qb = new QueryBuilder(
				"select * from ZSOrder where IsValid = 'Y' and status='10'");
		QueryBuilder totalQB = new QueryBuilder(
				"select * from ZSOrder where IsValid = 'Y' and status='10'");
		if (XString.isNotEmpty(searchUserName)) {
			qb.append(" and UserName like ? ", "%" + searchUserName + "%");
			totalQB.append(" and UserName like ? ", "%" + searchUserName + "%");
		}
		dga.setTotal(totalQB);

		qb.append(" order by id desc");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.decodeColumn("IsValid", HtmlUtil.codeToMapx("Order.IsValid"));
		dt.decodeColumn("HasInvoice", HtmlUtil.codeToMapx("Order.HasInvoice"));
		dt.decodeColumn("Status", HtmlUtil.codeToMapx("Order.Status"));
		DataTable dc = new QueryBuilder(
				"select Name,Code from zddistrict Order by Code")
				.executeDataTable();
		Mapx map = dc.toMapx("Code", "Name");
		dt.decodeColumn("Province", map);
		dt.decodeColumn("City", map);
		dt.decodeColumn("District", map);
		dga.bindData(dt);
	}

	public static void dg1PrintDataBind(DataGridAction dga) {
		String sql1 = "select * from ZSOrderItem where OrderID = ? order by GoodsID";
		DataTable dt = new QueryBuilder(sql1, dga.getParam("OrderID"))
				.executeDataTable();
		Mapx factoryMap = new QueryBuilder(
				"select id,Factory from zsgoods where exists(select * from zsorderitem where orderID = ? and GoodsID = zsgoods.ID)",
				dga.getParam("OrderID")).executeDataTable().toMapx(0,
				1);
		dt.insertColumn("Factory");
		for (int i = 0; (dt != null) && (i < dt.getRowCount()); i++) {
			dt.set(i, "Factory", factoryMap.getString(dt
					.getString(i, "GoodsID")));
		}
		dga.bindData(dt);
	}

	public static Mapx init(Mapx params) {
		String ID = params.getString("OrderID");
		if (ID != null) {
			ZSOrderSchema order = new ZSOrderSchema();
			order.setID(ID);
			order.fill();
			params.putAll(order.toMapx());
			params.put("HasInvoice", HtmlUtil.codeToMapx("Order.HasInvoice")
					.getString(order.getHasInvoice()));
			Mapx sendTypeMap = new QueryBuilder(
					"select name,id from zssend order by id")
					.executeDataTable().toMapx(1, 0);
			Mapx paymentTypeMap = new QueryBuilder(
					"select name,id from zspayment order by id")
					.executeDataTable().toMapx(1, 0);
			params.put("SendType", sendTypeMap.getString(order.getSendType()));
			params.put("PaymentType", paymentTypeMap.getString(order
					.getPaymentType()));
			Mapx districtMap = new QueryBuilder(
					"select code,name from zddistrict").executeDataTable()
					.toMapx(0, 1);
			String province = params.getString("Province");
			String city = params.getString("City");
			params.put("Province", districtMap.getString(province));
			params.put("City", districtMap.getString(city));
			params.put("District", districtMap.getString(params
					.getString("District")));
			params.put("OrderID", ID);
			return params;
		}
		return params;
	}
}
