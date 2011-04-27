package com.xdarkness.cms.site;

import java.util.Date;

import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCImageRelaSchema;
import com.xdarkness.schema.ZCImageSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;

public class ImagePlayerRela extends Page {
	public static final String RELATYPE_IMAGEPLAYER = "ImagePlayerImage";

	public static void dg1DataBind(DataGridAction dga) {
		String ImagePlayerID = (String) dga.getParams().get("ImagePlayerID");
		if ((ImagePlayerID == null) || ("".equals(ImagePlayerID))) {
			dga.bindData(new DataTable());
			return;
		}
		String Alias = Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/";
		QueryBuilder qb = new QueryBuilder(
				"select a.Name as Name,a.Info as Info,a.LinkURL as LinkURL,a.LinkText as LinkText,a.ID as ID,a.Path as Path,a.FileName as FileName,'"
						+ Alias
						+ "' as Alias,b.orderflag,b.relaid as ImagePlayerID "
						+ "from ZCImage a,ZCImageRela b where  a.id=b.id and  b.RelaType = ? and b.RelaID = ? order by b.orderflag desc, "
						+ "b.addtime desc", "ImagePlayerImage", ImagePlayerID);
		dga.bindData(qb);
	}

	public void relaImage() {
		String imagePlayerIDStr = this.request.getString("ImagePlayerID");
		if (XString.isEmpty(imagePlayerIDStr)) {
			this.response.setLogInfo(0, "请先保存基本信息，然后添加图片。");
			return;
		}
		long imagePlayerID = Long.parseLong(imagePlayerIDStr);
		String oldsum = new QueryBuilder(
				"select count(*) from zcimagerela where relaid = ? and relatype = ?",
				imagePlayerID, "ImagePlayerImage").executeString();
		String ImageIDs = (String) this.request.get("ImageIDs");
		String[] Relas = ImageIDs.split(",");
		int checksum = 0;
		if (Relas.length > 6) {
			this.response.setLogInfo(0, "您添加的图片过多，图片播放器最多只支持6张图片！");
			return;
		}

		Transaction trans = new Transaction();
		String imageNames = "";
		boolean isExists = false;
		for (int i = 0; i < Relas.length; i++) {
			ZCImageRelaSchema imageRela = new ZCImageRelaSchema();
			imageRela.setID(Long.parseLong(Relas[i]));
			imageRela.setRelaID(imagePlayerID);
			imageRela.setRelaType("ImagePlayerImage");
			if (imageRela.fill()) {
				checksum++;
				imageRela.setOrderFlag(OrderUtil.getDefaultOrder());
				trans.add(imageRela, OperateType.UPDATE);

				ZCImageSchema image = new ZCImageSchema();
				image.setID(Relas[i]);
				image.fill();
				if (!isExists) {
					imageNames = image.getName();
					isExists = true;
				} else {
					imageNames = imageNames + ", " + image.getName();
				}
			} else {
				imageRela.setOrderFlag(OrderUtil.getDefaultOrder());
				imageRela.setAddUser(User.getUserName());
				imageRela.setAddTime(new Date());
				trans.add(imageRela, OperateType.INSERT);
			}
		}

		if (Relas.length + Integer.parseInt(oldsum) - checksum > 6) {
			this.response.setLogInfo(0, "您添加的图片过多，图片播放器最多只支持6张图片！");
			return;
		}

		String message = "保存成功,您可以去'预览'查看关联图片后的效果!";
		if (checksum > 0) {
			message = "保存成功,图片<b style='color:#F00'> " + imageNames
					+ "</b> 已经存在，您可以去'预览'查看关联图片后的效果!";
		}
		if (trans.commit())
			this.response.setLogInfo(1, message);
		else
			this.response.setLogInfo(0, "保存失败");
	}

	public void editImage() {
		long ImagePlayerID = Long.parseLong(this.request.get("ImagePlayerID")
				.toString());
		String oldID = this.request.getString("OldImageID");
		String ImageIDs = (String) this.request.get("ImageIDs");
		String[] Relas = ImageIDs.split(",");
		String ID = Relas[0];
		Transaction trans = new Transaction();

		ZCImageSchema oldImage = new ZCImageSchema();
		oldImage.setID(oldID);
		oldImage.fill();

		ZCImageSchema newImage = new ZCImageSchema();
		newImage.setID(ID);
		newImage.fill();
		newImage.setLinkURL(oldImage.getLinkURL());
		newImage.setLinkText(oldImage.getLinkText());
		trans.add(newImage, OperateType.UPDATE);

		ZCImageRelaSchema oldRela = new ZCImageRelaSchema();
		oldRela.setID(oldID);
		oldRela.setRelaID(ImagePlayerID);
		oldRela.setRelaType("ImagePlayerImage");
		oldRela.fill();
		trans.add(oldRela, OperateType.DELETE_AND_BACKUP);

		ZCImageRelaSchema newRela = new ZCImageRelaSchema();
		newRela.setID(ID);
		newRela.setRelaID(ImagePlayerID);
		newRela.setRelaType("ImagePlayerImage");
		newRela.setOrderFlag(oldRela.getOrderFlag());
		newRela.setAddTime(oldRela.getAddTime());
		newRela.setAddUser(oldRela.getAddUser());
		newRela.setModifyTime(new Date());
		newRela.setModifyUser(User.getUserName());
		trans.add(newRela, OperateType.INSERT);

		if (trans.commit())
			this.response.setLogInfo(1, "更换图片成功!");
		else
			this.response.setLogInfo(0, "更换图片失败!");
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) this.request.get("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZCImageSchema image = new ZCImageSchema();
			image.setID(Integer.parseInt(dt.getString(i, "ID")));
			image.fill();
			image.setValue(dt.getDataRow(i));
			image.setModifyTime(new Date());
			image.setModifyUser(User.getUserName());
			trans.add(image, OperateType.UPDATE);

			ZCImageRelaSchema rela = new ZCImageRelaSchema();
			rela.setID(image.getID());
			rela.setRelaID(dt.getString(i, "ImagePlayerID"));
			rela.setRelaType("ImagePlayerImage");
			rela.fill();
			rela.setOrderFlag(dt.getString(i, "OrderFlag"));
			rela.setModifyTime(new Date());
			rela.setModifyUser(User.getUserName());
			trans.add(rela, OperateType.UPDATE);
		}
		if (trans.commit())
			this.response.setLogInfo(1, "操作成功!");
		else
			this.response.setLogInfo(0, "发生错误!");
	}

	public void del() {
		String ImagePlayerID = (String) this.request.get("ImagePlayerID");
		String IDs = $V("IDs");
		if (!XString.checkID(IDs)) {
			this.response.setLogInfo(0, "传入ID时发生错误!");
			return;
		}

		if (new ZCImageRelaSchema().query(
				new QueryBuilder(" where ID in (" + IDs
						+ ") and RelaID=? and RelaType=?", ImagePlayerID,
						"ImagePlayerImage")).deleteAndBackup())
			this.response.setLogInfo(1, "删除成功");
		else
			this.response.setLogInfo(0, "删除失败");
	}

	public void sortColumn() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String imagePlayerID = $V("ImagePlayerID");
		if ((!XString.checkID(target)) && (!XString.checkID(orders))) {
			return;
		}
		if (OrderUtil.updateOrder("ZCImageRela", type, target, orders,
				" RelaID = " + imagePlayerID))
			this.response.setMessage("排序成功");
		else
			this.response.setError("排序失败");
	}
}

/*
 * com.xdarkness.cms.site.ImagePlayerRela JD-Core Version: 0.6.0
 */