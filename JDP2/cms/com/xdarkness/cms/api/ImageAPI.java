package com.xdarkness.cms.api;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import com.xdarkness.cms.datachannel.Deploy;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.platform.pub.ImageUtilEx;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCImageSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.NumberUtil;

public class ImageAPI implements APIInterface {
	private boolean isCreateSchema = false;
	private String ImageHtml;
	private ZCImageSchema image;

	public static void main(String[] args) {
		User.UserData u = new User.UserData();
		User.setCurrent(u);
		User.setUserName("admin");
		User.setBranchInnerCode("86");

		ImageAPI image = new ImageAPI();
		image.setFileName("i:/1.jpg");
		image.setCatalogID(5725L);
		String filePath = image.getImageHtml();
		System.out.println(filePath);
		image.insert();

		image = new ImageAPI();
		image.setFileName("i:/2.jpg");
		image.setCatalogID(6038L);
		image.insert();
	}

	public ImageAPI() {
		this.image = new ZCImageSchema();
	}

	public ImageAPI(ZCImageSchema image) {
		this.image = image;
	}

	public void setFileName(String fileName) {
		this.image.setFileName(fileName);
	}

	public void setCatalogID(long catalogID) {
		this.image.setCatalogID(catalogID);
	}

	public String getImageHtml() {
		if (!this.isCreateSchema) {
			createSchema();
		}
		return this.ImageHtml;
	}

	private void createSchema() {
		if (!this.isCreateSchema) {
			String fileName = this.image.getFileName();
			fileName = fileName.replaceAll("\\\\", "/");
			String name = fileName.substring(fileName.lastIndexOf("/") + 1,
					fileName.lastIndexOf("."));
			this.image.setFileName(name);
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

			long imageID = NoUtil.getMaxID("DocID");
			this.image.setID(imageID);
			this.image.setName(name);
			this.image.setOldName(this.image.getName());
			if (this.image.getCatalogID() == 0L) {
				System.out.println("必须CatalogID不能为空");
				return;
			}
			this.image.setSiteID(CatalogUtil.getSiteID(this.image
					.getCatalogID()));
			String innerCode = CatalogUtil.getInnerCode(this.image
					.getCatalogID());
			this.image.setCatalogInnerCode(innerCode);
			this.image.setPath("upload/Image/"
					+ CatalogUtil.getPath(this.image.getCatalogID()));
			this.image.setFileName(imageID + NumberUtil.getRandomInt(10000)
					+ ".jpg");
			this.image.setSrcFileName(imageID + NumberUtil.getRandomInt(10000)
					+ ".jpg");
			this.image.setSuffix(suffix);
			this.image.setWidth(0L);
			this.image.setHeight(0L);
			this.image.setCount(2L);
			this.image.setAddTime(new Date());
			this.image.setAddUser("admin");
			this.ImageHtml = (".." + Config.getValue("UploadDir")
					+ SiteUtil.getAlias(this.image.getSiteID()) + "/"
					+ this.image.getPath() + this.image.getSrcFileName());
			this.isCreateSchema = true;
			dealImageFile(fileName);
		}
	}

	public long insert() {
		Transaction trans = new Transaction();
		if (insert(trans) > 0L) {
			if (trans.commit()) {
				return 1L;
			}
			return -1L;
		}
		return -1L;
	}

	public long insert(Transaction trans) {
		if (!this.isCreateSchema) {
			createSchema();
		}
		trans.add(this.image, OperateType.INSERT);
		trans
				.add(new QueryBuilder(
						"update zccatalog set total = (select count(*) from zcimage where catalogID = zccatalog.ID) where type=? and id =?",
						4L, this.image.getID()));
		return 1L;
	}

	private boolean dealImageFile(String srcFileName) {
		String dir = Config.getContextRealPath() + Config.getValue("UploadDir")
				+ SiteUtil.getAlias(this.image.getSiteID()) + "/"
				+ this.image.getPath();
		new File(dir).mkdirs();
		if (FileUtil.copy(srcFileName, dir + this.image.getSrcFileName())) {
			try {
				ArrayList imageList = ImageUtilEx.afterUploadImage(this.image,
						dir);
				Deploy d = new Deploy();
				d.addJobs(this.image.getSiteID(), imageList);
				return true;
			} catch (Throwable e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public boolean delete() {
		return false;
	}

	public boolean setSchema(Schema schema) {
		this.image = ((ZCImageSchema) schema);
		return false;
	}

	public boolean update() {
		return false;
	}
}

/*
 * com.xdarkness.cms.api.ImageAPI JD-Core Version: 0.6.0
 */