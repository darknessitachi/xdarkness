package com.xdarkness.shop;

import java.util.Date;

import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZSPaymentPropSchema;
import com.xdarkness.schema.ZSPaymentPropSet;
import com.xdarkness.schema.ZSPaymentSchema;
import com.xdarkness.schema.ZSPaymentSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class Payment extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder(
				"select * from ZSPayment where siteid = ?", ApplicationPage
						.getCurrentSiteID());
		dga.setTotal(new QueryBuilder(
				"select count(1) from ZSPayment where siteid = ?", ApplicationPage
						.getCurrentSiteID()));
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.insertColumn("PmtLink", dt.getColumnValues("Name"));
		Mapx map = new QueryBuilder(
				"select CodeName, CodeValue from zdcode where ParentCode='Payment'")
				.executeDataTable().toMapx("CodeName", "CodeValue");
		dt.decodeColumn("PmtLink", map);
		dga.bindData(dt);
	}

	public void add() {
		ZSPaymentSchema payment = new ZSPaymentSchema();
		payment.setValue(this.request);
		payment.setID(NoUtil.getMaxNo("paymentID"));
		payment.setSiteID(ApplicationPage.getCurrentSiteID());
		payment.setAddUser("");
		payment.setAddTime(new Date());

		String ImageID = $V("ImageID");
		if (XString.isNotEmpty(ImageID)) {
			DataTable imageDT = new QueryBuilder(
					"select path,srcfilename from zcimage where id = ? ",
					ImageID).executeDataTable();
			payment.setImagePath((imageDT.get(0, "path").toString() + imageDT
					.get(0, "srcfilename")).replaceAll("//", "/").toString());
		} else {
			payment.setImagePath("upload/Image/nopicture.jpg");
		}

		if (payment.insert()) {
			this.response.setStatus(1);
			this.response.setMessage("新增支付方式信息项成功！");
		} else {
			this.response.setStatus(0);
			this.response.setMessage("发生错误!");
		}
	}

	public void dg1Edit() {
		ZSPaymentSchema payment = new ZSPaymentSchema();
		String ID = $V("ID");
		payment.setID(ID);
		payment.fill();
		payment.setValue(this.request);
		payment.setModifyTime(new Date());
		payment.setModifyUser(User.getUserName());

		String ImageID = $V("ImageID");
		String imagePath = $V("PicSrc1");
		if (XString.isNotEmpty(ImageID)) {
			DataTable imageDT = new QueryBuilder(
					"select path,srcfilename from zcimage where id = ? ",
					ImageID).executeDataTable();
			String path = (imageDT.get(0, "path").toString() + imageDT.get(0,
					"srcfilename")).replaceAll("//", "/").toString();
			payment.setImagePath(path);
		} else {
			payment.setImagePath(imagePath);
		}

		if (payment.update())
			this.response.setLogInfo(1, "修改成功");
		else
			this.response.setLogInfo(0, "保存失败");
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		ids = ids.replaceAll(",", "','");
		Transaction trans = new Transaction();
		ZSPaymentSchema payment = new ZSPaymentSchema();
		ZSPaymentSet set = payment.query(new QueryBuilder("where ID in ('"
				+ ids + "')"));
		trans.add(set, OperateType.DELETE);

		ZSPaymentPropSet props = new ZSPaymentPropSchema()
				.query(new QueryBuilder("where PaymentID in ('" + ids + "')"));
		trans.add(props, OperateType.DELETE);

		if (trans.commit()) {
			this.response.setStatus(1);
			this.response.setMessage("删除成功！");
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}

	public static Mapx initDialog(Mapx params) {
		params.put("PaymentOptions", HtmlUtil.codeToOptions("Payment", false));
		String ID = params.getString("ID");
		if (XString.isNotEmpty(ID)) {
			ZSPaymentSchema zspayment = new ZSPaymentSchema();
			zspayment.setID(ID);
			zspayment.fill();
			Mapx map = zspayment.toMapx();
			map.put("PicSrc1", zspayment.getImagePath());
			map.put("ImagePath",
					(Config.getContextPath() + Config.getValue("UploadDir")
							+ "/"
							+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID())
							+ "/" + zspayment.getImagePath()).replaceAll("//",
							"/"));
			return map;
		}
		params.put("ImagePath",
				(Config.getContextPath() + Config.getValue("UploadDir") + "/"
						+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID())
						+ "/" + "upload/Image/nopicture.jpg").replaceAll("//",
						"/"));
		return params;
	}

	public void getPicSrc() {
		String ID = $V("PicID");
		DataTable dt = new QueryBuilder(
				"select path,srcfilename from zcimage where id=?", ID)
				.executeDataTable();
		if (dt.getRowCount() > 0)
			this.response.put("picSrc", (Config.getContextPath()
					+ Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/"
					+ dt.get(0, "path").toString() + dt.get(0, "srcfilename"))
					.replaceAll("//", "/").toString());
	}
}

/*
 * com.xdarkness.shop.Payment JD-Core Version: 0.6.0
 */