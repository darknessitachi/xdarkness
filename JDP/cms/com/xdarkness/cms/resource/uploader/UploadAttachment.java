package com.xdarkness.cms.resource.uploader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;

import com.xdarkness.cms.datachannel.Deploy;
import com.xdarkness.cms.dataservice.ColumnUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCAttachmentSchema;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.UploadStatus;
import com.xdarkness.framework.orm.data.DataCollection;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.NumberUtil;

public class UploadAttachment {
	public static void repeatUpload(Mapx files, Mapx fields) throws Exception {
		ZCAttachmentSchema attach = new ZCAttachmentSchema();
		attach.setID(fields.getString("RepeatID"));
		if (!attach.fill()) {
			UploadStatus.setTask(fields.getString("taskID"), fields
					.getString("RepeatID"), fields.getString("FileType"),
					"empty", "上传失败，未找到文件,完成");
			return;
		}
		ArrayList fileList = new ArrayList();
		Transaction trans = new Transaction();
		Iterator it = files.keySet().iterator();
		while (it.hasNext()) {
			FileItem uplFile = (FileItem) files.get(it.next());
			UploadStatus.setTask(fields.getString("taskID"), fields
					.getString("RepeatID"), fields.getString("FileType"),
					fields.getString("PathData"), "处理中");
			String AbsolutePath = fields.getString("AbsolutePath");
			uplFile.write(new File(AbsolutePath + attach.getSrcFileName()));
			attach.setFileSize(FileUtils.byteCountToDisplaySize(uplFile
					.getSize()));
		}
		attach.setModifyTime(new java.util.Date());
		attach.setModifyUser(User.getUserName());
		attach.setStatus(60L);
		trans.add(attach, OperateType.UPDATE);

		Deploy d = new Deploy();
		trans.add(d.getJobs(ApplicationPage.getCurrentSiteID(), fileList, "copy"),
				OperateType.INSERT);
		trans.commit();

		UploadStatus.setTask(fields.getString("taskID"), fields
				.getString("RepeatID"), fields.getString("FileType"), fields
				.getString("PathData")
				+ attach.getSrcFileName(), "文件上传完成");
	}

	public static void upload(Mapx files, Mapx fields) throws Exception {
		String CatalogID = fields.getString("CatalogID");
		String AbsolutePath = fields.getString("AbsolutePath");

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(CatalogID);
		if (!catalog.fill()) {
			System.out.println("没有找到上传库");
		}

		ArrayList fileList = new ArrayList();
		Transaction trans = new Transaction();
		DataCollection dc = new DataCollection();
		dc.putAll(fields);

		Iterator it = files.keySet().iterator();
		while (it.hasNext()) {
			FileItem uplFile = (FileItem) files.get(it.next());
			String fileNameLong = uplFile.getName();
			fileNameLong = fileNameLong.replaceAll("\\\\", "/");
			String oldName = fileNameLong.substring(fileNameLong
					.lastIndexOf("/") + 1);
			String ext = ZUploaderServlet.getExtension(oldName);

			long attachID = NoUtil.getMaxID("DocID");
			UploadStatus
					.setTask(fields.getString("taskID"), attachID+"", fields
							.getString("FileType"), fields
							.getString("PathData"), "处理中");
			int random = NumberUtil.getRandomInt(10000);
			String srcFileName = attachID + random + "." + ext;
			uplFile.write(new File(AbsolutePath, srcFileName));

			fileList.add(AbsolutePath + srcFileName);
			if (XString.isEmpty(fields.getString("FileName"))) {
				fields.put("FileName", oldName.substring(0, oldName
						.lastIndexOf(".")));
			}

			ZCAttachmentSchema attachment = new ZCAttachmentSchema();
			attachment.setID(attachID);
			attachment.setName(fields.getString("FileName"));
			attachment.setOldName(oldName
					.substring(0, oldName.lastIndexOf(".")));
			attachment.setSiteID(fields.getString("SiteID"));
			attachment.setInfo(fields.getString("Info"));
			attachment.setCatalogID(Long.parseLong(CatalogID));
			attachment.setCatalogInnerCode(catalog.getInnerCode());
			attachment.setBranchInnerCode(User.getBranchInnerCode());
			attachment.setPath(fields.getString("PathData"));
			attachment.setImagePath(fields.getString("ImagePath"));
			attachment.setFileName(srcFileName);
			attachment.setSrcFileName(srcFileName);
			attachment.setSuffix(ext);
			attachment.setIsLocked(fields.getString(new StringBuffer(String
					.valueOf(uplFile.getFieldName())).append("Locked")
					.toString()));
			if ("Y".equals(attachment.getIsLocked())) {
				attachment.setPassword(XString.md5Hex(fields
						.getString(uplFile.getFieldName() + "Password")));
			}
			attachment.setSystem("CMS");
			attachment.setFileSize(FileUtils.byteCountToDisplaySize(uplFile
					.getSize()));
			attachment.setAddTime(new java.util.Date());
			attachment.setAddUser("admin");
			attachment.setOrderFlag(OrderUtil.getDefaultOrder());
			attachment.setModifyTime(new java.util.Date());
			attachment.setModifyUser(User.getUserName());
			attachment.setProp1("0");
			attachment.setStatus(20L);
			trans.add(attachment, OperateType.INSERT);
			trans.add(ColumnUtil.getValueFromRequest(Long.parseLong(CatalogID),
					attachment.getID(), dc), OperateType.INSERT);
			UploadStatus.setTask(fields.getString("taskID"), attachID+"", fields
					.getString("FileType"), fields.getString("PathData")
					+ srcFileName, "文件上传完成");
		}
		trans
				.add(new QueryBuilder(
						"update zccatalog set isdirty = 1,total = (select count(*) from zcattachment where catalogID =?) where ID =?",
						catalog.getID(), catalog.getID()));

		Deploy d = new Deploy();
		trans.add(d.getJobs(ApplicationPage.getCurrentSiteID(), fileList, "copy"),
				OperateType.INSERT);

		trans.commit();
	}
}

/*
 * com.xdarkness.cms.resource.uploader.UploadAttachment JD-Core Version: 0.6.0
 */