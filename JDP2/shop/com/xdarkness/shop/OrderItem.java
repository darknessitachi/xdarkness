package com.xdarkness.shop;

import java.util.Date;

import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.schema.ZDMemberSchema;
import com.xdarkness.schema.ZSOrderItemSchema;
import com.xdarkness.schema.ZSOrderItemSet;
import com.xdarkness.schema.ZSOrderSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class OrderItem extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder(
				"select * from ZSOrderItem where OrderID =? order by GoodsID",
				dga.getParam("OrderID"));
		dga.bindData(qb);
	}

	public static Mapx init(Mapx params) {
		return params;
	}

	public void checkSN() {
		String sn = $V("SN").trim();
		if (sn.length() == 0) {
			return;
		}
		DataTable dt = new QueryBuilder("select * from zsgoods where SN=?", sn)
				.executeDataTable();
		if ((dt == null) || (dt.getRowCount() == 0)) {
			this.response.setStatus(0);
			return;
		}
		this.response.setStatus(1);
		this.response.put("Name", dt.get(0, "Name").toString());
		this.response.put("Price", dt.get(0, "Price").toString());
		this.response.put("DiscountPrice", dt.get(0, "MemberPrice").toString());
		this.response.put("Score", dt.get(0, "Score").toString());
		this.response.put("GoodsID", dt.get(0, "ID").toString());
		this.response.put("Factory", dt.get(0, "Factory").toString());
		this.response.put("Standard", dt.get(0, "Standard").toString());
		float price = Float.valueOf(dt.get(0, "MemberPrice").toString())
				.floatValue();
		float memberPrice = Float.valueOf(dt.get(0, "Price").toString())
				.floatValue();
		int discount = (int) (price / memberPrice) * 100 / 100;
		this.response.put("Discount", discount);
	}

	public void checkName() {
		String name = $V("Name").trim();
		if (name.length() == 0) {
			return;
		}
		DataTable dt = new QueryBuilder("select * from zsgoods where Name=?",
				name).executeDataTable();
		if ((dt == null) || (dt.getRowCount() == 0)) {
			this.response.setStatus(0);
			return;
		}
		this.response.setStatus(1);
		this.response.put("SN", dt.get(0, "SN").toString());
		this.response.put("Price",
				XString.isEmpty(dt.getString(0, "Price")) ? "0" : dt
						.getString(0, "Price"));
		this.response.put("DiscountPrice", XString.isEmpty(dt.getString(0,
				"MemberPrice")) ? "0" : dt.getString(0, "MemberPrice"));
		this.response.put("Score",
				XString.isEmpty(dt.getString(0, "Score")) ? "0" : dt
						.getString(0, "Score"));
		this.response.put("GoodsID", dt.getString(0, "ID"));
		this.response.put("Factory", XString.isEmpty(dt.getString(0,
				"Factory")) ? "" : dt.getString(0, "Factory"));
		this.response.put("Standard", XString.isEmpty(dt.getString(0,
				"Standard")) ? "" : dt.getString(0, "Standard"));
		float price = Float.valueOf(
				XString.isEmpty(dt.getString(0, "MemberPrice")) ? "0" : dt
						.getString(0, "MemberPrice")).floatValue();
		float memberPrice = Float.valueOf(
				XString.isEmpty(dt.getString(0, "Price")) ? "0" : dt
						.getString(0, "Price")).floatValue();
		int discount = (int) (price / memberPrice) * 100 / 100;
		this.response.put("Discount", discount);
	}

	public void add() {
		Transaction trans = new Transaction();

		ZSOrderItemSchema ZSOrderItem = new ZSOrderItemSchema();
		ZSOrderItem.setValue(this.request);
		ZSOrderItem.setSiteID(ApplicationPage.getCurrentSiteID());
		ZSOrderItem.setAddUser(User.getUserName());
		ZSOrderItem.setAddTime(new Date());
		trans.add(ZSOrderItem, OperateType.INSERT);

		String orderID = $V("OrderID");
		if (XString.isEmpty(orderID)) {
			this.response.setLogInfo(1, "订单号不能为空！");
			return;
		}
		ZSOrderSchema order = new ZSOrderSchema();
		order.setID(orderID);
		if (order.fill()) {
			String amount = this.request.getString("Amount");
			order.setAmount(order.getAmount() + Float.parseFloat(amount));
			order.setOrderAmount(order.getOrderAmount()
					+ Float.parseFloat(amount));
			trans.add(order, OperateType.UPDATE);
		} else {
			this.response.setLogInfo(1, "订单号不存在！");
			return;
		}

		String memberID = this.request.getString("MemberID");
		String score = this.request.getString("Score");

		if ((XString.isNotEmpty(memberID))
				&& (!memberID.equalsIgnoreCase("0"))) {
			ZDMemberSchema member = new ZDMemberSchema();
			member.setUserName(memberID);
			if (member.fill()) {
				member.setScore(member.getScore() + Integer.parseInt(score));
				trans.add(member, OperateType.UPDATE);
			}
		}
		if (trans.commit())
			this.response.setLogInfo(1, "新增订单详细项成功！");
		else
			this.response.setLogInfo(0, "发生错误!");
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setLogInfo(0, "传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();

		String memberID = $V("MemberID");
		String sqlScore = "select sum(score) from zsorderitem where GoodsID in ('"
				+ ids + "')";
		Object obj = new QueryBuilder(sqlScore).executeOneValue();
		String score = obj == null ? "0" : obj.toString();

		if ((XString.isNotEmpty(memberID))
				&& (!memberID.equalsIgnoreCase("0"))) {
			ZDMemberSchema member = new ZDMemberSchema();
			member.setUserName(memberID);
			if (member.fill()) {
				member.setScore(String.valueOf(Integer.parseInt(member
						.getScore())
						- Integer.parseInt(score)));
				trans.add(member, OperateType.UPDATE);
			}

		}

		ZSOrderItemSchema ZSOrderItem = new ZSOrderItemSchema();
		ZSOrderItemSet set = ZSOrderItem.query(new QueryBuilder(
				"where GoodsID in ('" + ids + "')"));
		trans.add(set, OperateType.DELETE);

		String orderID = $V("OrderID");
		String sqlAmount = "select sum(Amount) from zsorderitem where GoodsID in ('"
				+ ids + "')";
		String amount = new QueryBuilder(sqlAmount).executeString();
		ZSOrderSchema order = new ZSOrderSchema();
		order.setID(orderID);
		order.fill();
		order.setAmount(order.getAmount() - Float.parseFloat(amount));
		trans.add(order, OperateType.UPDATE);
		if (trans.commit())
			this.response.setLogInfo(1, "删除成功");
		else
			this.response.setLogInfo(0, "删除失败");
	}

	public void dg1Edit() {
		DataTable dt = this.request.getDataTable("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZSOrderItemSchema item = new ZSOrderItemSchema();
			item.setValue(dt.getDataRow(i));
			item.fill();
			item.setValue(dt.getDataRow(i));

			item.setModifyTime(new Date());
			item.setModifyUser(User.getUserName());

			trans.add(item, OperateType.UPDATE);
		}
		if (trans.commit()) {
			this.response.setStatus(1);
			this.response.setMessage("修改成功!");
		} else {
			this.response.setStatus(0);
			this.response.setMessage("修改失败!");
		}
	}
}

/*
 * com.xdarkness.shop.OrderItem JD-Core Version: 0.6.0
 */