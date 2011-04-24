package com.xdarkness.shop;

import java.util.Date;

import com.xdarkness.cms.pub.PubFun;
import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZSBrandSchema;
import com.xdarkness.schema.ZSBrandSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class Brand extends Page {
	public static Mapx init(Mapx params) {
		return null;
	}

	public static Mapx initDialog(Mapx params) {
		String id = params.getString("ID");
		if (XString.isNotEmpty(id)) {
			ZSBrandSchema brand = new ZSBrandSchema();
			brand.setID(id);
			if (!brand.fill()) {
				return params;
			}

			Mapx brandMap = brand.toMapx();
			brandMap.put("Name", brand.getName());
			brandMap.put("Alias", brand.getAlias());
			brandMap.put("URL", brand.getURL());
			brandMap.put("Info", brand.getInfo());
			brandMap.put("PicSrc1", brand.getImagePath());
			brandMap
					.put("PicSrc", (Config.getContextPath()
							+ Config.getValue("UploadDir") + "/"
							+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID())
							+ "/" + brand.getImagePath()).replaceAll("//", "/"));
			DataTable dt = new QueryBuilder(
					"select Name, BranchInnerCode, TreeLevel from ZDBranch where BranchInnerCode like '"
							+ User.getBranchInnerCode() + "%'")
					.executeDataTable();
			PubFun.indentDataTable(dt);
			brandMap.put("BranchInnerCodeOptions", HtmlUtil.dataTableToOptions(
					dt, brand.getBranchInnerCode()));
			return brandMap;
		}
		params.put("PicSrc",
				(Config.getContextPath() + Config.getValue("UploadDir") + "/"
						+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID())
						+ "/" + "upload/Image/nopicture.jpg").replaceAll("//",
						"/"));
		DataTable dt = new QueryBuilder(
				"select Name, BranchInnerCode, TreeLevel from ZDBranch where BranchInnerCode like '"
						+ User.getBranchInnerCode() + "%'").executeDataTable();
		PubFun.indentDataTable(dt);
		params.put("BranchInnerCodeOptions", HtmlUtil.dataTableToOptions(dt,
				User.getBranchInnerCode()));
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String searchWord = dga.getParam("SearchWord");
		QueryBuilder qb = new QueryBuilder(
				"select * from ZSBrand where SiteID = ?");
		qb.add(ApplicationPage.getCurrentSiteID());
		if (XString.isNotEmpty(searchWord)) {
			qb.append(" and Name like ?", "%" + searchWord.trim() + "%");
		}
		dga.bindData(qb);
	}

	public void add() {
		String ImageID = $V("ImageID");
		ZSBrandSchema brand = new ZSBrandSchema();
		brand.setID(NoUtil.getMaxID("ZSBrandID"));
		if (!brand.fill()) {
			brand.setValue(this.request);
			brand.setSiteID(ApplicationPage.getCurrentSiteID());
			DataTable dt = new QueryBuilder(
					"select * from ZSBrand order by orderflag")
					.executeDataTable();
			long orderflag = 0L;
			if ((dt != null) && (dt.getRowCount() > 0)) {
				orderflag = dt.getLong(dt.getRowCount() - 1, "OrderFlag");
			}

			if (XString.isNotEmpty(ImageID)) {
				DataTable imageDT = new QueryBuilder(
						"select path,srcfilename from zcimage where id = ? ",
						ImageID).executeDataTable();
				brand.setImagePath((imageDT.get(0, "path").toString() + imageDT
						.get(0, "srcfilename")).replaceAll("//", "/")
						.toString());
			} else {
				brand.setImagePath("upload/Image/nopicture.jpg");
			}

			brand.setOrderFlag(orderflag + 1L);
			brand.setPublishFlag("0");
			brand.setAddTime(new Date());
			brand.setAddUser(User.getUserName());

			if (brand.insert()) {
				this.response.setStatus(1);
				this.response.setMessage("新增成功!");
			} else {
				this.response.setStatus(0);
				this.response.setMessage("发生错误!");
			}
		} else {
			this.response.setStatus(0);
			this.response.setMessage("发生错误!");
		}
	}

	public void edit() {
		String ImageID = $V("ImageID");
		String imagePath = $V("PicSrc1");
		ZSBrandSchema brand = new ZSBrandSchema();
		brand.setID($V("ID"));
		if (brand.fill()) {
			brand.setValue(this.request);
			brand.setModifyTime(new Date());
			brand.setModifyUser(User.getUserName());

			if (XString.isNotEmpty(ImageID)) {
				DataTable imageDT = new QueryBuilder(
						"select path,srcfilename from zcimage where id = ? ",
						ImageID).executeDataTable();
				String path = (imageDT.get(0, "path").toString() + imageDT.get(
						0, "srcfilename")).replaceAll("//", "/").toString();
				brand.setImagePath(path);
			} else {
				brand.setImagePath(imagePath);
			}

			if (brand.update()) {
				this.response.setStatus(1);
				this.response.setMessage("修改成功!");
			} else {
				this.response.setStatus(0);
				this.response.setMessage("发生错误!");
			}
		} else {
			this.response.setStatus(0);
			this.response.setMessage("发生错误!不存在的商品品牌!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZSBrandSchema brand = new ZSBrandSchema();
		ZSBrandSet set = brand.query(new QueryBuilder(" where id in (" + ids
				+ ")"));
		trans.add(set, OperateType.DELETE_AND_BACKUP);

		if (trans.commit()) {
			this.response.setStatus(1);
			this.response.setMessage("删除成功!");
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
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
 * com.xdarkness.shop.Brand JD-Core Version: 0.6.0
 */