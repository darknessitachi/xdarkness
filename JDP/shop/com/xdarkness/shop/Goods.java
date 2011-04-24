package com.xdarkness.shop;

import java.util.Date;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.xdarkness.cms.dataservice.ColumnUtil;
import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.member.Member;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.schema.ZDColumnValueSchema;
import com.xdarkness.schema.ZSFavoriteSchema;
import com.xdarkness.schema.ZSFavoriteSet;
import com.xdarkness.schema.ZSGoodsSchema;
import com.xdarkness.schema.ZSGoodsSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class Goods extends Page {
	public static Mapx initDialog(Mapx params) {
		if (XString.isNotEmpty(params.getString("ID"))) {
			ZSGoodsSchema goods = new ZSGoodsSchema();
			goods.setID(params.getLong("ID"));
			if (!goods.fill()) {
				return params;
			}

			params.put("GoodsLibID", goods.getCatalogID());
			params.put("CatalogName", new QueryBuilder(
					"select Name from ZCCatalog where ID = ?", goods
							.getCatalogID()).executeString());
			params.putAll(goods.toMapx());
			params
					.put("PublishDate", DateUtil.toString(goods
							.getPublishDate()));
			params.put("PicSrc1", goods.getImage0());
			params.put("PicSrc",
					(Config.getContextPath() + Config.getValue("UploadDir")
							+ "/"
							+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID())
							+ "/" + goods.getImage0()).replaceAll("//", "/"));
			DataTable dt = new QueryBuilder(
					"select Name, ID from ZSBrand where SiteID = ? order by ID",
					ApplicationPage.getCurrentSiteID()).executeDataTable();
			params.put("BrandOptions", HtmlUtil.dataTableToOptions(dt, String
					.valueOf(goods.getBrandID()), false));

			params.put("CustomColumn", ColumnUtil.getHtml("1", String
					.valueOf(goods.getCatalogID()), "2", goods.getID()+""));
		} else {
			params.put("PicSrc",
					(Config.getContextPath() + Config.getValue("UploadDir")
							+ "/"
							+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID())
							+ "/" + "upload/Image/nopicture.jpg").replaceAll(
							"//", "/"));
			DataTable dt = new QueryBuilder(
					"select Name, ID from ZSBrand where SiteID = ? order by ID",
					ApplicationPage.getCurrentSiteID()).executeDataTable();
			params.put("BrandOptions", HtmlUtil.dataTableToOptions(dt, false));
			String catalogID = params.getString("CatalogID");
			params.put("CustomColumn", ColumnUtil.getHtml("1", catalogID));
			params.put("UpTime", DateUtil.getCurrentDate());
		}
		return params;
	}

	public void add() {
		Transaction trans = new Transaction();
		long ID = NoUtil.getMaxID("GoodsID");
		ZCCatalogSchema catalog = new ZCCatalogSchema();

		String good = $V("CatalogID");

		if ((good.equals(null)) && (good.length() > 0)) {
			this.response.setLogInfo(0, "请选择商品类别");
			return;
		}
		catalog.setID(good);
		catalog.fill();
		ZSGoodsSchema goods = new ZSGoodsSchema();
		goods.setCatalogID(good);
		goods.setCatalogInnerCode(catalog.getInnerCode());
		goods.setType("1");
		goods.setTopFlag("1");
		goods.setStickTime(123213L);
		goods.setBranchInnerCode(catalog.getBranchInnerCode());
		goods.setID(ID);
		goods.setStatus("0");
		goods.setSiteID(ApplicationPage.getCurrentSiteID());
		goods.setCommentCount(0L);
		goods.setHitCount(0L);

		String ImageID = $V("ImageID");
		if (XString.isNotEmpty(ImageID)) {
			DataTable imageDT = new QueryBuilder(
					"select path,srcfilename from zcimage where id = ? ",
					ImageID).executeDataTable();
			goods.setImage0((imageDT.get(0, "path").toString() + imageDT.get(0,
					"srcfilename")).replaceAll("//", "/").toString());
		} else {
			goods.setImage0("upload/Image/nopicture.jpg");
		}
		goods.setAddTime(new Date());
		goods.setAddUser(User.getUserName());
		goods.setValue(this.request);
		goods.setOrderFlag(OrderUtil.getDefaultOrder());

		SchemaSet ss = ColumnUtil.getValueFromRequest(goods.getCatalogID(),
				goods.getID(), this.request);

		trans.add(ss, OperateType.INSERT);
		trans.add(goods, OperateType.INSERT);

		if (trans.commit())
			this.response.setLogInfo(1, "新建成功");
		else
			this.response.setLogInfo(0, "新建失败");
	}

	public void save() {
		DataTable dt = (DataTable) this.request.get("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZSGoodsSchema goods = new ZSGoodsSchema();
			goods.setID(dt.getString(i, "ID"));
			if (!goods.fill()) {
				this.response.setLogInfo(0, "您要修改的项" + goods.getID() + "不存在!");
				return;
			}
			goods.setValue(dt.getDataRow(i));
			goods.setModifyUser(User.getUserName());
			goods.setModifyTime(new Date());
			trans.add(goods, OperateType.UPDATE);
		}
		if (trans.commit())
			this.response.setLogInfo(1, "保存成功!");
		else
			this.response.setLogInfo(0, "保存失败!");
	}

	public void dg1Edit() {
		String ImageID = $V("ImageID");
		String imagePath = $V("PicSrc1");
		ZSGoodsSchema goods = new ZSGoodsSchema();
		String ID = $V("ID");
		goods.setID(ID);
		if (!goods.fill()) {
			this.response.setLogInfo(0, "您要修改的商品" + goods.getName() + "不存在!");
			return;
		}
		Transaction trans = new Transaction();
		if (goods.getPrice() > Double.parseDouble(this.request
				.getString("Price"))) {
			DataTable dt = new QueryBuilder(
					"select a.Name, b.UserName from ZSFavorite b, ZSGoods a where b.GoodsID = ? and b.GoodsID = a.ID and b.SiteID = ? and a.SiteID = b.SiteID and b.PriceNoteFlag = 'Y'",
					ID, ApplicationPage.getCurrentSiteID()).executeDataTable();

			for (int i = 0; i < dt.getRowCount(); i++) {
				Member member = new Member(dt.getString(i, "UserName"));
				if (!member.fill()) {
					continue;
				}
				SimpleEmail email = new SimpleEmail();
				email.setHostName("smtp.163.com");
				try {
					String siteName = SiteUtil.getName(ApplicationPage
							.getCurrentSiteID());
					StringBuffer sb = new StringBuffer();
					sb.append("尊敬的" + siteName + "用户：<br/>");
					sb.append("你好！<br/>");
					sb.append("您关注的商品" + dt.getString(i, "Name")
							+ "已经降价，请点击一下链接查看相关信息：<br/>");

					sb.append("<br/><br/>注：此邮件为系统自动发送，请勿回复。<br/>");
					sb.append("　　　　　　　　　　　　　　　　　　　　　　　————" + siteName);
					email.setAuthentication("0871huhu@163.com", "08715121182");
					email.addTo(member.getEmail(), member.getUserName());
					email.setFrom("0871huhu@163.com", siteName);
					email.setSubject(siteName + "：商品降价提醒！");
					email.setContent(sb.toString(), "text/html;charset=utf-8");
				} catch (EmailException e) {
					this.response.setLogInfo(0, "邮件发送错误");
					e.printStackTrace();
				}
			}
		}

		goods.setValue(this.request);
		goods.setModifyUser(User.getUserName());
		goods.setModifyTime(new Date());
		goods.setStatus("60");

		if (XString.isNotEmpty(ImageID)) {
			DataTable imageDT = new QueryBuilder(
					"select path,srcfilename from zcimage where id = ? ",
					ImageID).executeDataTable();
			String path = (imageDT.get(0, "path").toString() + imageDT.get(0,
					"srcfilename")).replaceAll("//", "/").toString();
			goods.setImage0(path);
		} else {
			goods.setImage0(imagePath);
		}

		trans.add(goods, OperateType.UPDATE);

		DataTable dt = ColumnUtil.getColumnValue("2", goods.getID());
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZDColumnValueSchema value = new ZDColumnValueSchema();
			value.setValue(dt.getDataRow(i));
			value.setTextValue($V("_C_" + value.getColumnCode()));
			trans.add(value, OperateType.UPDATE);
		}

		if (trans.commit())
			this.response.setLogInfo(1, "修改成功");
		else
			this.response.setLogInfo(0, "修改" + goods.getID() + "失败!");
	}

	public void del() {
		String IDs = $V("IDs");
		if (!XString.checkID(IDs)) {
			return;
		}
		Transaction trans = new Transaction();
		ZSGoodsSchema goods = new ZSGoodsSchema();
		ZSGoodsSet set = goods.query(new QueryBuilder("where id in (" + IDs
				+ ")"));
		trans.add(set, OperateType.DELETE_AND_BACKUP);

		ZSFavoriteSchema fav = new ZSFavoriteSchema();
		ZSFavoriteSet favs = fav.query(new QueryBuilder("where GoodsID in ("
				+ IDs + ")"));
		trans.add(favs, OperateType.DELETE);

		if (trans.commit())
			this.response.setLogInfo(1, "删除成功");
		else
			this.response.setLogInfo(0, "删除失败");
	}

	public boolean checkSN() {
		String SN = $V("SN");
		if (XString.isEmpty(SN)) {
			this.response.setLogInfo(0, "不能为空");
			return false;
		}
		int count = new QueryBuilder("select count(1) from ZSGoods where SN=?",
				SN).executeInt();
		if (count > 0) {
			this.response.setLogInfo(0, "已经存在此类编号的药品:" + $V("SN"));
			return false;
		}
		return true;
	}
}

/*
 * com.xdarkness.shop.Goods JD-Core Version: 0.6.0
 */