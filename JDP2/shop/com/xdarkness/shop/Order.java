package com.xdarkness.shop;

import java.util.Date;

import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZSGoodsSchema;
import com.xdarkness.schema.ZSOrderSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class Order extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		if (XString.isNotEmpty(dga.getParam("PageIndex"))) {
			dga.setPageIndex(Integer.parseInt(dga.getParam("PageIndex")));
			dga.getParams().put("PageIndex", "");
		}
		String searchUserName = dga.getParam("searchUserName");
		String searchStatus = dga.getParam("searchStatus");
		QueryBuilder qb = new QueryBuilder(
				"select * from ZSOrder where SiteID = ?");
		qb.add(ApplicationPage.getCurrentSiteID());
		if (XString.isNotEmpty(searchUserName)) {
			qb.append(" and UserName like ? ", "%" + searchUserName + "%");
		}
		if (XString.isNotEmpty(searchStatus)) {
			qb.append(" and Status = ? ");
			qb.add(searchStatus);
			if (!searchStatus.equals("5"))
				qb.append(" and IsValid = 'Y' ");
		} else {
			qb.append(" and IsValid = 'Y' ");
		}
		qb.append(" and status not in ('8','9') order by id desc");
		dga.setTotal(qb);
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

	public static Mapx initStatusSelect(Mapx params) {
		params
				.put("StatusSelect", HtmlUtil.codeToOptions("Order.Status",
						true));
		return params;
	}

	public static Mapx initStore(Mapx params) {
		DataTable storeDT = new QueryBuilder(
				"select name,storecode from zsstore where Prop1 = 'Y'")
				.executeDataTable();
		params.put("StoreCode", HtmlUtil.dataTableToOptions(storeDT));
		return params;
	}

	public static Mapx initAddDialog(Mapx params) {
		Mapx map = new Mapx();
		map.put("IsValid", HtmlUtil.codeToRadios("IsValid", "Order.IsValid",
				"Y"));
		map.put("HasInvoice", HtmlUtil.codeToRadios("HasInvoice",
				"Order.HasInvoice", "Y"));
		map.put("Status", HtmlUtil.codeToOptions("Order.Status"));
		map.put("SendTimeSlice", HtmlUtil.codeToOptions("Order.SendTimeSlice"));
		DataTable sendTypeDT = new QueryBuilder(
				"select name,id from zssend order by id").executeDataTable();
		map.put("SendType", HtmlUtil.dataTableToOptions(sendTypeDT));
		DataTable paymentTypeDT = new QueryBuilder(
				"select name,id from zspayment order by id").executeDataTable();
		map.put("PaymentType", HtmlUtil.dataTableToOptions(paymentTypeDT));
		return map;
	}

	public static Mapx initEditDialog(Mapx params) {
		String ID = params.getString("OrderID");
		if (XString.isNotEmpty(ID)) {
			ZSOrderSchema order = new ZSOrderSchema();
			order.setID(ID);
			if (order.fill()) {
				params.putAll(order.toMapx());
				params.put("IsValid", HtmlUtil.codeToRadios("IsValid",
						"Order.IsValid", order.getIsValid()));
				params.put("HasInvoice", HtmlUtil.codeToRadios("HasInvoice",
						"Order.HasInvoice", order.getHasInvoice()));
				params.put("Status", HtmlUtil.codeToOptions("Order.Status",
						order.getStatus()));
				params.put("SendTimeSlice", HtmlUtil.codeToOptions(
						"Order.SendTimeSlice", order.getSendTimeSlice()));
				DataTable sendTypeDT = new QueryBuilder(
						"select name,id from zssend order by id")
						.executeDataTable();
				DataTable paymentTypeDT = new QueryBuilder(
						"select name,id from zspayment order by id")
						.executeDataTable();
				params.put("SendType", HtmlUtil.dataTableToOptions(sendTypeDT,
						order.getSendType()));
				params.put("PaymentType", HtmlUtil.dataTableToOptions(
						paymentTypeDT, order.getPaymentType()));
				params.put("OrderID", ID);
			}
			return params;
		}
		return params;
	}

	public void add() {
		ZSOrderSchema ZSorder = new ZSOrderSchema();
		String UserName = $V("UserName");
		String date = DateUtil.getCurrentDate("yyyyMMdd");
		String OrderID = NoUtil.getMaxNo("OrderID", date, 4);
		ZSorder.setID(OrderID);
		ZSorder.setValue(this.request);
		ZSorder.setAddUser(User.getUserName());
		ZSorder.setAddTime(new Date());
		ZSorder.setUserName(UserName);
		ZSorder.setSiteID(ApplicationPage.getCurrentSiteID());
		ZSorder.setAmount(0.0F);
		if (ZSorder.insert())
			this.response.setLogInfo(1, "新增订单成功！");
		else
			this.response.setLogInfo(0, "发生错误!");
	}

	public void orderEdit() {
		ZSOrderSchema order = new ZSOrderSchema();
		order.setValue(this.request);
		order.fill();
		if ((order.getStatus().equals("7"))
				&& (!this.request.getString("Status").equals("8"))
				&& (!this.request.getString("Status").equals("9"))) {
			this.response.setLogInfo(0, "已完成交易的订单只能接受投诉或退货!");
			return;
		}

		order.setValue(this.request);

		boolean flag = false;
		StringBuffer sb = new StringBuffer();
		Transaction trans = new Transaction();

		if (order.getStatus().equals("1")) {
			DataTable orderItems = new QueryBuilder(
					"select GoodsID, Name, Count from ZSOrderItem where OrderID = ?",
					order.getID()).executeDataTable();
			for (int j = 0; j < orderItems.getRowCount(); j++) {
				ZSGoodsSchema goods = new ZSGoodsSchema();
				goods.setID(orderItems.getString(j, "GoodsID"));
				if (goods.fill()) {
					if (goods.getStore() < orderItems.getInt(j, "Count")) {
						sb.append("商品<span style='color:red'>"
								+ goods.getName() + "</span>的库存不够！<br/>");
						flag = true;
					}
				} else {
					sb.append("商品<span style='color:red'>"
							+ orderItems.getString(j, "Name")
							+ "</span>不存在！<br/>");
					flag = true;
				}
			}
		}
		DataTable orderItems;
		if (order.getStatus().equals("7")) {
			orderItems = new QueryBuilder(
					"select GoodsID, Name, Count from ZSOrderItem where OrderID = ?",
					order.getID()).executeDataTable();
			for (int j = 0; j < orderItems.getRowCount(); j++) {
				ZSGoodsSchema goods = new ZSGoodsSchema();
				goods.setID(orderItems.getString(j, "GoodsID"));
				if (goods.fill()) {
					goods.setStore(goods.getStore()
							- orderItems.getLong(j, "Count"));
					trans.add(goods, OperateType.UPDATE);
				} else {
					sb.append("商品<span style='color:red'>"
							+ orderItems.getString(j, "Name")
							+ "</span>不存在！<br/>");
					flag = true;
				}
			}
		}
		if (flag) {
			this.response.setLogInfo(0, sb.toString());
			return;
		}

		order.setSiteID(ApplicationPage.getCurrentSiteID());
		order.setModifyUser(User.getUserName());
		order.setModifyTime(new Date());
		trans.add(order, OperateType.UPDATE);
		if ("N".equals(order.getIsValid())) {
			Log.info(User.getRealName() + "于"
					+ DateUtil.getCurrentDateTime("yyyy-MM-dd") + "设置订单"
					+ order.getID() + "为无效状态");
		}

		if (trans.commit())
			this.response.setLogInfo(1, "修改成功");
		else
			this.response.setLogInfo(0, "修改" + order.getUserName() + "失败!");
	}

	public void dg1Edit() {
		DataTable dt = this.request.getDataTable("DT");
		System.out.println(this.request);
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZSOrderSchema order = new ZSOrderSchema();
			order.setValue(dt.getDataRow(i));
			order.fill();
			if ((order.getStatus().equals("7"))
					&& (!dt.getString(i, "Status").equals("8"))
					&& (!dt.getString(i, "Status").equals("9"))) {
				this.response.setLogInfo(0, "已完成交易的订单只能接受投诉或退货!");
				return;
			}

			order.setValue(dt.getDataRow(i));

			if (order.getStatus().equals("1")) {
				DataTable orderItems = new QueryBuilder(
						"select GoodsID, Count from ZSOrderItem where OrderID = ?",
						order.getID()).executeDataTable();
				boolean flag = false;
				StringBuffer sb = new StringBuffer();
				for (int j = 0; j < orderItems.getRowCount(); j++) {
					ZSGoodsSchema goods = new ZSGoodsSchema();
					goods.setID(orderItems.getString(j, "GoodsID"));
					if (goods.fill()) {
						if (goods.getStore() < orderItems.getInt(j, "Count")) {
							sb.append("商品" + goods.getName() + "的库存不够！<br/>");
							flag = true;
						}
					} else {
						sb.append("ID为" + orderItems.getString(j, "GoodsID")
								+ "的商品不存在！<br/>");
						flag = true;
					}
				}
				if (flag) {
					this.response.setLogInfo(0, sb.toString());
					return;
				}
			}
			DataTable orderItems;
			if (order.getStatus().equals("7")) {
				orderItems = new QueryBuilder(
						"select GoodsID, Count from ZSOrderItem where OrderID = ?",
						order.getID()).executeDataTable();
				for (int j = 0; j < orderItems.getRowCount(); j++) {
					ZSGoodsSchema goods = new ZSGoodsSchema();
					goods.setID(orderItems.getString(j, "GoodsID"));
					if (goods.fill()) {
						goods.setStore(goods.getStore()
								- orderItems.getLong(j, "Count"));
						trans.add(goods, OperateType.UPDATE);
					} else {
						this.response.setLogInfo(0, "ID为"
								+ orderItems.getString(j, "GoodsID")
								+ "的商品已被删除！");
						return;
					}
				}
			}

			order.setModifyTime(new Date());
			order.setModifyUser(User.getUserName());
			trans.add(order, OperateType.UPDATE);

			if ("N".equals(order.getIsValid())) {
				Log.info(User.getRealName() + "于"
						+ DateUtil.getCurrentDateTime("yyyy-MM-dd") + "设置订单"
						+ order.getID() + "为无效状态");
			}

		}

		if (trans.commit())
			this.response.setLogInfo(1, "修改成功");
		else
			this.response.setLogInfo(0, "修改失败");
	}

	public void createSending() {
		String ids = $V("IDs");

		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		ids = ids.replaceAll(",", "','");
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder(
				"update zsorder set status=10 where ID in ('" + ids + "')"));
		if (trans.commit())
			this.response.setLogInfo(1, "生成配送单成功!");
		else
			this.response.setLogInfo(0, "生成配送单失败!");
	}
}

/*
 * com.xdarkness.shop.Order JD-Core Version: 0.6.0
 */