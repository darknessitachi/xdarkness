package com.xdarkness.shop;

import java.util.Date;

import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZSPaymentPropSchema;
import com.xdarkness.schema.ZSPaymentPropSet;
import com.xdarkness.schema.ZSPaymentSchema;
import com.xdarkness.schema.ZSPaymentSet;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class PaymentService extends Page {
	public void save() {
		String paymentName = new QueryBuilder(
				"select CodeName from ZDCode where ParentCode='Payment' and CodeValue=?",
				this.request.get("PaymentPath")).executeOneValue().toString();
		ZSPaymentSet payments = new ZSPaymentSchema().query(new QueryBuilder(
				"where SiteID = ? and name = ?",
				ApplicationPage.getCurrentSiteID(), paymentName));
		if (payments.size() > 0) {
			ZSPaymentSchema payment = payments.get(0);
			payment.setValue(this.request);
			payment.setModifyUser(User.getUserName());
			payment.setModifyTime(new Date());

			String ImageID = $V("ImageID");
			if (XString.isNotEmpty(ImageID)) {
				DataTable imageDT = new QueryBuilder(
						"select path,srcfilename from zcimage where id = ? ",
						ImageID).executeDataTable();
				payment
						.setImagePath((imageDT.get(0, "path").toString() + imageDT
								.get(0, "srcfilename")).replaceAll("//", "/")
								.toString());
			} else {
				payment.setImagePath("upload/Image/nopicture.jpg");
			}

			Object[] reqKeys = this.request.keyArray();
			for (int i = 0; i < reqKeys.length; i++) {
				if (reqKeys[i].toString().split("_")[0].equals("Pmt")) {
					ZSPaymentPropSet pmtProp = new ZSPaymentPropSchema()
							.query(new QueryBuilder(
									"where PropName = ? and PaymentID = ?",
									this.request.get(reqKeys[i]), payment
											.getID()));

					if (pmtProp.size() > 0) {
						pmtProp.get(0).setPropName(
								reqKeys[i].toString().split("_")[1]);
						pmtProp.get(0).setPropValue(
								this.request.getString(reqKeys[i]));
						pmtProp.get(0).setModifyUser(User.getUserName());
						pmtProp.get(0).setModifyTime(new Date());
						pmtProp.get(0).update();
					} else {
						ZSPaymentPropSchema prop = new ZSPaymentPropSchema();
						prop.setID(NoUtil.getMaxNo("PaymentPropID"));
						prop.setPaymentID(payment.getID());
						prop.setPropName(reqKeys[i].toString().split("_")[1]);
						prop.setPropValue(this.request.getString(reqKeys[i]));
						prop.setAddUser(User.getUserName());
						prop.setAddTime(new Date());
						prop.insert();
					}
				}
			}

			if (payment.update()) {
				this.response.setStatus(1);
				this.response.setMessage("保存成功！");
			} else {
				this.response.setStatus(0);
				this.response.setMessage("发生错误!");
			}
		} else {
			ZSPaymentSchema payment = new ZSPaymentSchema();
			payment.setValue(this.request);
			payment.setID(NoUtil.getMaxNo("paymentID"));
			payment.setName(paymentName);
			payment.setSiteID(ApplicationPage.getCurrentSiteID());
			payment.setAddUser(User.getUserName());
			payment.setAddTime(new Date());

			String ImageID = $V("ImageID");
			if (XString.isNotEmpty(ImageID)) {
				DataTable imageDT = new QueryBuilder(
						"select path,srcfilename from zcimage where id = ? ",
						ImageID).executeDataTable();
				payment
						.setImagePath((imageDT.get(0, "path").toString() + imageDT
								.get(0, "srcfilename")).replaceAll("//", "/")
								.toString());
			} else {
				payment.setImagePath("upload/Image/nopicture.jpg");
			}

			Object[] reqKeys = this.request.keyArray();
			for (int i = 0; i < reqKeys.length; i++) {
				if (reqKeys[i].toString().split("_")[0].equals("Pmt")) {
					ZSPaymentPropSet pmtProp = new ZSPaymentPropSchema()
							.query(new QueryBuilder(
									"where PropName = ? and PaymentID = ?",
									this.request.get(reqKeys[i]), payment
											.getID()));

					if (pmtProp.size() > 0) {
						pmtProp.get(0).setPropName(
								reqKeys[i].toString().split("_")[1]);
						pmtProp.get(0).setPropValue(
								this.request.getString(reqKeys[i]));
						pmtProp.get(0).setModifyUser(User.getUserName());
						pmtProp.get(0).setModifyTime(new Date());
						pmtProp.get(0).update();
					} else {
						ZSPaymentPropSchema prop = new ZSPaymentPropSchema();
						prop.setID(NoUtil.getMaxNo("PaymentPropID"));
						prop.setPaymentID(payment.getID());
						prop.setPropName(reqKeys[i].toString().split("_")[1]);
						prop.setPropValue(this.request.getString(reqKeys[i]));
						prop.setAddUser(User.getUserName());
						prop.setAddTime(new Date());
						prop.insert();
					}
				}
			}

			if (payment.insert()) {
				this.response.setStatus(1);
				this.response.setMessage("保存成功！");
			} else {
				this.response.setStatus(0);
				this.response.setMessage("发生错误!");
			}
		}
	}

	public static Mapx init(Mapx params) {
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
			ZSPaymentPropSet props = new ZSPaymentPropSchema()
					.query(new QueryBuilder("where PaymentID = ?", ID));
			for (int i = 0; i < props.size(); i++) {
				map
						.put(props.get(i).getPropName(), props.get(i)
								.getPropValue());
			}
			map
					.put(
							"PaymentOptions",
							HtmlUtil
									.codeToOptions(
											"Payment",
											XString
													.isEmpty(params.get(
															"PaymentSelect")
															.toString()) ? "Shop/PaymentService/PmtAliPay.jsp"
													: params.get(
															"PaymentSelect")
															.toString(), false));
			return map;
		}
		params
				.put(
						"PaymentOptions",
						HtmlUtil
								.codeToOptions(
										"Payment",
										XString.isEmpty(params.get(
												"PaymentSelect").toString()) ? "Shop/PaymentService/PmtAliPay.jsp"
												: params.get("PaymentSelect")
														.toString(), false));
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
 * com.xdarkness.shop.PaymentService JD-Core Version: 0.6.0
 */