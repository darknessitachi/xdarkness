package com.xdarkness.cms.resource.uploader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;

import com.xdarkness.cms.datachannel.Deploy;
import com.xdarkness.cms.dataservice.ColumnUtil;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.platform.pub.VideoUtilEx;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.schema.ZCVideoSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.UploadStatus;
import com.xdarkness.framework.orm.data.DataCollection;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.NumberUtil;
import com.xdarkness.framework.util.VideoUtil;

public class UploadVideo {
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

			long videoID = NoUtil.getMaxID("DocID");
			UploadStatus
					.setTask(fields.getString("taskID"), videoID+"", fields
							.getString("FileType"), fields
							.getString("PathData"), "处理中");
			int random = NumberUtil.getRandomInt(10000);
			String srcFileName = videoID + random + "." + ext;
			uplFile.write(new File(AbsolutePath, srcFileName));

			if (XString.isEmpty(fields.getString("FileName"))) {
				fields.put("FileName", oldName.substring(0, oldName
						.lastIndexOf(".")));
			}

			ZCVideoSchema video = new ZCVideoSchema();
			video.setID(videoID);
			video.setName(fields.getString("FileName"));
			video.setOldName(oldName.substring(0, oldName.lastIndexOf(".")));
			video.setSiteID(fields.getString("SiteID"));
			video.setCatalogID(CatalogID);
			video.setCatalogInnerCode(catalog.getInnerCode());
			video.setBranchInnerCode(User.getBranchInnerCode());
			video.setPath(fields.getString("PathData"));
			if ("flv".equalsIgnoreCase(ext)) {
				video.setFileName(videoID + random + ".flv");
				video.setImageName(videoID + random + ".jpg");
			} else {
				int random1 = NumberUtil.getRandomInt(10000);
				video.setFileName(videoID + random1 + ".flv");
				video.setImageName(videoID + random1 + ".jpg");
			}
			video.setSrcFileName(srcFileName);
			video.setSuffix(ext);
			video.setTag(fields.getString("Tag"));
			video.setInfo(fields.getString("Info"));
			video.setIsOriginal(fields.getString("IsOriginal"));
			video.setFileSize(FileUtils.byteCountToDisplaySize(uplFile
					.getSize()));
			video.setHitCount(0L);
			video.setStickTime(0L);
			video.setSystem("CMS");
			video.setOrderFlag(OrderUtil.getDefaultOrder());
			video.setAddTime(new java.util.Date());
			video.setAddUser("admin");
			video.setModifyTime(new java.util.Date());
			video.setModifyUser(User.getUserName());
			video.setStatus(20L);
			String ProduceTime = (String) fields.get("ProduceTime");
			if (XString.isNotEmpty(ProduceTime)) {
				video.setProduceTime(DateUtil.parse(ProduceTime));
			}
			String Integral = (String) fields.get("Integral");
			if (XString.isNotEmpty(Integral)) {
				video.setIntegral(Integer.parseInt(Integral));
			}
			VideoUtil.videoid = videoID+"";
			VideoUtil.taskid = fields
					.getString("taskID");
			VideoUtil.srcPath = fields
					.getString("PathData")
					+ srcFileName;
			if (!VideoUtilEx.afterUploadVideo(video, AbsolutePath, false)) {
				UploadStatus.setTask(fields.getString("taskID"), videoID+"",
						fields.getString("FileType"), fields
								.getString("PathData")
								+ srcFileName, "视频转换出错");
			}
			trans.add(video, OperateType.INSERT);
			trans.add(ColumnUtil.getValueFromRequest(Long.parseLong(CatalogID),
					video.getID(), dc), OperateType.INSERT);
			fileList.add(AbsolutePath + video.getFileName());
			fileList.add(AbsolutePath + video.getImageName());
			UploadStatus.setTask(fields.getString("taskID"), ""+videoID, fields
					.getString("FileType"), fields.getString("PathData")
					+ srcFileName, "视频转换完成");
		}
		trans
				.add(new QueryBuilder(
						"update zccatalog set isdirty = 1,total = (select count(*) from zcvideo where catalogID =?) where ID =?",
						catalog.getID(), catalog.getID()));

		Deploy d = new Deploy();
		trans.add(d.getJobs(Long.parseLong(fields.getString("SiteID")),
				fileList, "copy"), OperateType.INSERT);
		trans.commit();
	}
}

/*
 * com.xdarkness.cms.resource.uploader.UploadVideo JD-Core Version: 0.6.0
 */