package com.xdarkness.shop.web;

import java.util.Date;

import com.xdarkness.schema.ZSOrderSchema;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class MemberOrder extends Ajax {
	public static void dg1DataBind(DataGridAction dga) {
		String orderID = dga.getParam("OrderID");
		String startDate = dga.getParam("StartDate");
		String endDate = dga.getParam("EndDate");
		String siteID = dga.getParam("SiteID");

		QueryBuilder qb = new QueryBuilder(
				"select * from ZSOrder where SiteID = ? and UserName = ?",
				siteID, User.getUserName());

		if (XString.isNotEmpty(orderID)) {
			qb.append(" and ID = ?", orderID);
		}
		if (XString.isNotEmpty(startDate)) {
			qb.append(" and AddTime > ?", startDate);
		}
		if (XString.isNotEmpty(endDate)) {
			qb.append(" and AddTime < ?", endDate);
		}

		qb.append(" order by id desc");
		dga.setTotal(qb);

		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.insertColumn("CancelCol");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.getInt(i, "Status") == 0)
				dt.set(i, "CancelCol",
						"<a href='javascript:void(0)' onClick=\"cancel("
								+ dt.getString(i, "ID") + ")\">取消订单</a>");
			else if (dt.getInt(i, "Status") == 5)
				dt.set(i, "CancelCol", "已取消");
			else {
				dt.set(i, "CancelCol", "已生效");
			}
		}
		dt.decodeColumn("Status", HtmlUtil.codeToMapx("Order.Status"));
		Mapx paymentMapx = new QueryBuilder("select ID, Name from ZSPayment")
				.executeDataTable().toMapx(0, 1);
		dt.decodeColumn("PaymentType", paymentMapx);

		dga.bindData(dt);
	}

	public void cancel() {
		ZSOrderSchema order = new ZSOrderSchema();
		String orderID = $V("OrderID");
		order.setID(orderID);
		if (order.fill()) {
			if (XString.isNotEmpty(order.getStatus())) {
				order.setIsValid("N");
				order.setStatus("5");
				order.setModifyTime(new Date());
				order.setModifyUser(User.getUserName());
				if (order.update())
					this.response.setLogInfo(1, "取消订单成功！");
				else
					this.response.setLogInfo(0, "对不起，发生错误!请您联系客服！");
			} else {
				this.response.setLogInfo(0, "对不起，发生错误!请您联系客服！");
			}
		} else
			this.response.setLogInfo(0, "对不起，发生错误!请您联系客服！");
	}
}

/*
 * com.xdarkness.shop.web.MemberOrder JD-Core Version: 0.6.0
 */