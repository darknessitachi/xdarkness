package com.xdarkness.shop.web;

import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.schema.ZSFavoriteSchema;
import com.xdarkness.schema.ZSFavoriteSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class MemberFavorite extends Ajax {
	public static void dg1DataBind(DataGridAction dga) {
		if (dga.getTotal() == 0) {
			String sql2 = "select count(*) from ZSGoods a, ZSFavorite b where a.SiteID = ? and a.ID = b.GoodsID and b.UserName = ?";
			QueryBuilder qb = new QueryBuilder(sql2);
			qb.add(ApplicationPage.getCurrentSiteID());
			qb.add(User.getUserName());
			dga.setTotal(qb.executeInt());
		}

		String sql1 = "select a.ID, a.Name, a.Image0, a.Price, b.AddTime, b.PriceNoteFlag from ZSGoods a, ZSFavorite b where a.SiteID = ? and a.ID = b.GoodsID and b.UserName = ? order by AddTime";
		QueryBuilder qb = new QueryBuilder(sql1);
		qb.add(dga.getParam("SiteID"));
		qb.add(User.getUserName());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.insertColumn("Image");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "Image", (Config.getContextPath()
					+ Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(dga.getParam("SiteID")) + "/" + dt
					.getString(i, "Image0")).replaceAll("//", "/"));
		}

		dga.bindData(dt);
	}

	public void priceNote() {
		String ids = $V("IDs");
		String siteID = $V("SiteID");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZSFavoriteSchema favorite = new ZSFavoriteSchema();
		ZSFavoriteSet set = favorite.query(new QueryBuilder(
				" where SiteID=? and GoodsID in (" + ids + ") and UserName = '"
						+ User.getValue("UserName") + "'", siteID));
		for (int i = 0; i < set.size(); i++) {
			set.get(i).setPriceNoteFlag("Y");
		}
		trans.add(set, OperateType.UPDATE);

		if (trans.commit()) {
			this.response.setStatus(1);
			this.response.setMessage("降价提醒设置成功!");
		} else {
			this.response.setStatus(0);
			this.response.setMessage("对不起，发生错误!请您联系客服!");
		}
	}

	public void removeFromFavorite() {
		String ids = $V("IDs");
		String siteID = $V("SiteID");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZSFavoriteSchema favorite = new ZSFavoriteSchema();

		ZSFavoriteSet set = favorite.query(new QueryBuilder(
				" where SiteID=? and GoodsID in (" + ids + ") and UserName = '"
						+ User.getUserName() + "'", siteID));
		trans.add(set, OperateType.DELETE);

		if (trans.commit()) {
			this.response.setStatus(1);
			this.response.setMessage("移出商品成功!");
		} else {
			this.response.setStatus(0);
			this.response.setMessage("对不起，发生错误!请您联系客服!");
		}
	}

	public void addToCart() {
		String GoodsID = this.request.getString("GoodsID");
		String buyNowFlag = this.request.getString("BuyNowFlag");
		Mapx map = new Mapx();
		String shopCartCookie = this.cookie.getCookie("ShopCart");
		if (XString.isNotEmpty(shopCartCookie)) {
			String[] goodsArray = shopCartCookie.split("X");
			for (int i = 0; i < goodsArray.length; i++) {
				map.put(goodsArray[i].split("-")[0],
						goodsArray[i].split("-")[1]);
			}
		}
		for (int i = 0; i < GoodsID.split("-").length; i++) {
			if (map.containsKey(GoodsID.split("-")[i]))
				map.put(GoodsID.split("-")[i], map
						.getInt(GoodsID.split("-")[i]) + 1);
			else {
				map.put(GoodsID.split("-")[i], OperateType.INSERT);
			}
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < map.keyArray().length; i++) {
			sb.append(map.keyArray()[i]);
			sb.append("-");
			sb.append(map.getString(map.keyArray()[i]));
			if (i < map.keyArray().length - 1) {
				sb.append("X");
			}
		}
		this.cookie.setCookie("ShopCart", sb.toString());
		if ("1".equals(buyNowFlag))
			this.response.setLogInfo(1, "成功加入购物车!");
		else
			this.response.setLogInfo(2, "成功加入购物车!");
	}
}
