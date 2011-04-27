package com.xdarkness.cms.document;

import java.io.File;
import java.util.ArrayList;

import com.xdarkness.cms.datachannel.Deploy;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.ImageUtilEx;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.schema.ZCImageSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.NumberUtil;

public class AutoUpload {
	public static ZCImageSchema dealImage(String path, String filename,
			Transaction trans) {
		String autoSaveLib = Config.getValue("AutoSaveImageLib");
		if (XString.isEmpty(autoSaveLib)) {
			autoSaveLib = "默认图片";
		}
		String catalogID = new QueryBuilder(
				"select ID from ZCCatalog where type='4' and Name =?  and siteid=?",
				autoSaveLib, ApplicationPage.getCurrentSiteID()).executeString();

		if (XString.isEmpty(catalogID)) {
			catalogID = new QueryBuilder(
					"select ID from ZCCatalog where type='4' and siteid=?",
					ApplicationPage.getCurrentSiteID()).executeString();
		}

		boolean uploadFlag = true;
		String absolutePath = path + "defaultTemp/" + filename;
		String ext = filename.substring(filename.lastIndexOf(".") + 1);
		filename = filename.substring(0, filename.lastIndexOf("."));
		long imageID = NoUtil.getMaxID("DocID");
		int random = NumberUtil.getRandomInt(10000);
		String newFileName = imageID + random + "." + ext;
		new File(absolutePath).renameTo(new File(path
				+ CatalogUtil.getPath(catalogID) + newFileName));

		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);

		ZCImageSchema image = new ZCImageSchema();
		image.setID(imageID);
		image.setCatalogID(catalogID);
		image.setCatalogInnerCode(catalog.getInnerCode());
		image.setName(filename);
		image.setOldName(filename);
		image.setSiteID(ApplicationPage.getCurrentSiteID());
		image.setPath("upload/Image/" + CatalogUtil.getPath(catalogID));
		image.setFileName(newFileName);
		image.setSrcFileName(newFileName);
		image.setSuffix(ext);
		image.setCount(0L);
		image.setWidth(0L);
		image.setHeight(0L);
		image.setHitCount(0L);
		image.setStickTime(0L);
		image.setAuthor("articleEditor");
		image.setSystem("CMS");
		image.setAddTime(new java.util.Date());
		image.setAddUser(User.getUserName());
		image.setOrderFlag(OrderUtil.getDefaultOrder());
		image.setStatus(20L);
		try {
			ImageUtilEx.afterUploadImage(image, path
					+ CatalogUtil.getPath(catalogID));
			ArrayList imageList = ImageUtilEx.afterUploadImage(image, path
					+ CatalogUtil.getPath(catalogID));
			Deploy d = new Deploy();
			d.addJobs(ApplicationPage.getCurrentSiteID(), imageList, "copy");
			uploadFlag = true;
		} catch (Throwable e) {
			e.printStackTrace();
			uploadFlag = false;
		}
		if (uploadFlag) {
			trans.add(image, OperateType.INSERT);
			return image;
		}
		return new ZCImageSchema();
	}
}

/*
 * com.xdarkness.cms.document.AutoUpload JD-Core Version: 0.6.0
 */