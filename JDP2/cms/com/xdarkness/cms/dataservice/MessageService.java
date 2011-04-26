package com.xdarkness.cms.dataservice;

import com.xdarkness.framework.jaf.controls.DataListAction;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class MessageService extends Ajax {
	public static Mapx init(Mapx params) {
		String BoardID = params.getString("BoardID");
		if (XString.isNotEmpty(BoardID)) {
			params.put("BoardName", new QueryBuilder(
					"select Name from ZCMessageBoard where ID = ?", BoardID)
					.executeString());
		}
		params.put("ServicesContext", Config.getValue("ServicesContext"));
		params.put("MessageActionURL", Config.getValue("MessageActionURL"));
		return params;
	}

	public static void dg1DataBind(DataListAction dla) {
		String BoardID = dla.getParam("BoardID");
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCBoardMessage where BoardID = ? and PublishFlag = 'Y' order by ID desc",
				BoardID);
		dla.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla
				.getPageIndex());
		dt.insertColumn("Reply");
		dt.insertColumn("Prefix");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.getString(i, "ReplyFlag").equals("Y")) {
				dt.set(i, "Prefix", "<font color='#9B0D17'>[回复]：</font>");
				dt.set(i, "Reply", "<font color='#9B0D17'>"
						+ dt.getString(i, "ReplyContent") + "</font>");
			}
		}
		dla.bindData(dt);
	}
}

/*
 * com.xdarkness.cms.dataservice.MessageService JD-Core Version: 0.6.0
 */