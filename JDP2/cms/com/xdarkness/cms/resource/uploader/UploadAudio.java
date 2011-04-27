package com.xdarkness.cms.resource.uploader;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;

import com.xdarkness.cms.datachannel.Deploy;
import com.xdarkness.cms.dataservice.ColumnUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCAudioSchema;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.UploadStatus;
import com.xdarkness.framework.orm.data.DataCollection;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.NumberUtil;

public class UploadAudio {
	public static void repeatUpload(Mapx files, Mapx fields) throws Exception {
		ZCAudioSchema audio = new ZCAudioSchema();
		audio.setID(fields.getString("RepeatID"));
		if (!audio.fill()) {
			UploadStatus.setTask(fields.getString("taskID"), fields
					.getString("RepeatID"), fields.getString("FileType"),
					"empty", "上传失败，未找到文件,完成");
			return;
		}
		Iterator it = files.keySet().iterator();
		ArrayList fileList = new ArrayList();
		Transaction trans = new Transaction();
		while (it.hasNext()) {
			FileItem uplFile = (FileItem) files.get(it.next());
			UploadStatus.setTask(fields.getString("taskID"), fields
					.getString("RepeatID"), fields.getString("FileType"),
					fields.getString("PathData"), "处理中");
			String AbsolutePath = fields.getString("AbsolutePath");
			uplFile.write(new File(AbsolutePath + audio.getSrcFileName()));
			audio.setFileSize(FileUtils.byteCountToDisplaySize(uplFile
					.getSize()));
			fileList.add(AbsolutePath + audio.getSrcFileName());
		}
		audio.setModifyTime(new Date());
		audio.setModifyUser(User.getUserName());
		audio.setStatus(60L);
		trans.add(audio, OperateType.UPDATE);

		Deploy d = new Deploy();
		trans.add(d.getJobs(ApplicationPage.getCurrentSiteID(), fileList, "copy"),
				OperateType.INSERT);
		trans.commit();
		UploadStatus.setTask(fields.getString("taskID"), fields
				.getString("RepeatID"), fields.getString("FileType"), fields
				.getString("PathData")
				+ audio.getSrcFileName(), "文件上传完成");
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

			long audioID = NoUtil.getMaxID("DocID");
			UploadStatus
					.setTask(fields.getString("taskID"), audioID+"", fields
							.getString("FileType"), fields
							.getString("PathData"), "处理中");
			int random = NumberUtil.getRandomInt(10000);
			String srcFileName = audioID + random + "." + ext;
			uplFile.write(new File(AbsolutePath, srcFileName));

			ZCAudioSchema audio = new ZCAudioSchema();
			audio.setID(audioID);
			audio.setName(oldName.substring(0, oldName.lastIndexOf(".")));
			audio.setOldName(oldName.substring(0, oldName.lastIndexOf(".")));
			audio.setSiteID(fields.getString("SiteID"));
			audio.setCatalogID(CatalogID);
			audio.setCatalogInnerCode(catalog.getInnerCode());
			audio.setBranchInnerCode(User.getBranchInnerCode());
			audio.setPath(fields.getString("PathData"));
			audio.setFileName(srcFileName);
			audio.setSrcFileName(srcFileName);
			audio.setSuffix(ext);

			audio.setTag(fields.getString("FileTag"));
			audio.setIsOriginal("Y");
			audio.setFileSize(FileUtils.byteCountToDisplaySize(uplFile
					.getSize()));
			audio.setSystem("CMS");
			audio.setOrderFlag(OrderUtil.getDefaultOrder());
			audio.setAddTime(new Date());
			audio.setAddUser("admin");
			audio.setModifyTime(new Date());
			audio.setModifyUser(User.getUserName());
			audio.setStatus(20L);
			trans.add(audio, OperateType.INSERT);
			trans.add(ColumnUtil.getValueFromRequest(Long.parseLong(CatalogID),
					audio.getID(), dc), OperateType.INSERT);
			fileList.add(AbsolutePath + srcFileName);
			UploadStatus.setTask(fields.getString("taskID"), audioID+"", fields
					.getString("FileType"), fields.getString("PathData")
					+ srcFileName, "上传完成");
		}
		trans
				.add(new QueryBuilder(
						"update zccatalog set isdirty = 1,total = (select count(*) from zcaudio where catalogID =?) where ID =?",
						catalog.getID(), catalog.getID()));

		Deploy d = new Deploy();
		trans.add(d.getJobs(ApplicationPage.getCurrentSiteID(), fileList, "copy"),
				OperateType.INSERT);

		trans.commit();
	}
}

/*
 * com.xdarkness.cms.resource.uploader.UploadAudio JD-Core Version: 0.6.0
 */