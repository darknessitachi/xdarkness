package com.xdarkness.cms.site;

import java.util.Date;

import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCImagePlayerSchema;
import com.xdarkness.schema.ZCImagePlayerSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class ImagePlayerBasic extends Page {
	public static final String IMAGESOURCE_LOCAL = "0";
	public static final String IMAGESOURCE_CATALOG_FIRST = "1";
	public static final String IMAGESOURCE_CATALOG_SELECT = "2";
	public static final Mapx IMAGESOURCE_MAP = new Mapx();

	static {
		IMAGESOURCE_MAP.put("0", "本地上传");
		IMAGESOURCE_MAP.put("1", "所属栏目文章中的图片(自动取第一张)");
		IMAGESOURCE_MAP.put("2", "所属栏目文章中的图片(编辑手工选择)");
	}

	public static Mapx init(Mapx params) {
		String imagePlayerID = params.getString("ImagePlayerID");
		if (XString.isNotEmpty(imagePlayerID)) {
			long ID = Long.parseLong(imagePlayerID);
			ZCImagePlayerSchema ImagePlayer = new ZCImagePlayerSchema();
			ImagePlayer.setID(ID);
			ImagePlayer.fill();
			Mapx map = ImagePlayer.toMapx();
			map.put("ImagePlayerID", ImagePlayer.getID());
			map.put("radiosShowText", HtmlUtil.codeToRadios("IsShowText",
					"YesOrNo", ImagePlayer.getIsShowText()));
			map.put("radiosImageSource", HtmlUtil.mapxToRadios("ImageSource",
					IMAGESOURCE_MAP, ImagePlayer.getImageSource()));
			return map;
		}
		params.put("radiosShowText", HtmlUtil.codeToRadios("IsShowText",
				"YesOrNo", "Y"));
		params.put("radiosImageSource", HtmlUtil.mapxToRadios("ImageSource",
				IMAGESOURCE_MAP, "0"));
		params.put("display", "none");

		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder(
				"select ID,Name,Code,SiteID,DisplayType,ImageSource,Height,Width,Displaycount from ZCImagePlayer order by ID ");
		dga.bindData(qb);
	}

	public void add() {
		String imagePlayerID = $V("ImagePlayerID");
		ZCImagePlayerSchema ImagePlayer = new ZCImagePlayerSchema();
		if (XString.isNotEmpty(imagePlayerID)) {
			ImagePlayer.setID(imagePlayerID);
			ImagePlayer.fill();
			ImagePlayer.setValue(this.request);
			ImagePlayer.setModifyTime(new Date());
			ImagePlayer.setModifyUser(User.getUserName());
			ImagePlayer.setDisplayType("1");
			if (XString.isNotEmpty($V("RelaCatalogID")))
				ImagePlayer.setRelaCatalogInnerCode(CatalogUtil
						.getInnerCode($V("RelaCatalogID")));
			else {
				ImagePlayer.setRelaCatalogInnerCode("0");
			}
			if (ImagePlayer.update()) {
				this.response.setStatus(1);
				this.response.put("ImagePlayerUrl", "ImagePlayerID="
						+ ImagePlayer.getID() + "&ImageSource="
						+ ImagePlayer.getImageSource() + "&RelaCatalog="
						+ ImagePlayer.getRelaCatalogInnerCode());
				this.response.setMessage("保存成功,您可以去‘预览’查看修改后的效果!");
			} else {
				this.response.setStatus(0);
				this.response.setMessage("发生错误!");
			}
		} else {
			DataTable checkDT = new QueryBuilder(
					"select * from zcimageplayer where code=? and siteID=?",
					$V("Code"), ApplicationPage.getCurrentSiteID())
					.executeDataTable();
			if (checkDT.getRowCount() > 0) {
				this.response.setLogInfo(0, "已经存在代码为‘ <b style='color:#F00'>"
						+ $V("Code") + "</b>’ 的图片播放器，请更换播放器代码！");
				return;
			}
			ImagePlayer.setID(NoUtil.getMaxID("ImagePlayerID"));
			ImagePlayer.setValue(this.request);
			ImagePlayer.setDisplayType("1");
			ImagePlayer.setSiteID(ApplicationPage.getCurrentSiteID());
			ImagePlayer.setAddTime(new Date());
			ImagePlayer.setAddUser(User.getUserName());

			if (XString.isNotEmpty($V("RelaCatalogID")))
				ImagePlayer.setRelaCatalogInnerCode(CatalogUtil
						.getInnerCode($V("RelaCatalogID")));
			else {
				ImagePlayer.setRelaCatalogInnerCode("0");
			}

			if (ImagePlayer.insert()) {
				this.response.put("ImagePlayerUrl", "ImagePlayerID="
						+ ImagePlayer.getID() + "&ImageSource="
						+ ImagePlayer.getImageSource() + "&RelaCatalog="
						+ ImagePlayer.getRelaCatalogInnerCode());
				this.response.setStatus(1);
				this.response.setMessage("新建成功,您现在可以关联图片了!");
			} else {
				this.response.setStatus(0);
				this.response.setMessage("发生错误!");
			}
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
		ZCImagePlayerSchema ImagePlayer = new ZCImagePlayerSchema();
		ZCImagePlayerSet set = ImagePlayer.query(new QueryBuilder(
				"where id in (" + ids + ")"));
		trans.add(set, OperateType.DELETE_AND_BACKUP);

		if (trans.commit()) {
			this.response.setMessage("删除成功,您可以去‘预览’查看删除后的效果!");
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}
}

/*
 * com.xdarkness.cms.site.ImagePlayerBasic JD-Core Version: 0.6.0
 */