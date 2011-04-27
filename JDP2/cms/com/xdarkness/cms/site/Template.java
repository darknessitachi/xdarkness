package com.xdarkness.cms.site;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import org.apache.commons.io.filefilter.FileFilterUtils;

import com.xdarkness.cms.datachannel.Deploy;
import com.xdarkness.cms.pub.PubFun;
import com.xdarkness.cms.template.PreParser;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.framework.jaf.controls.TreeAction;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.ZipUtil;

public class Template extends Page {
	public static void treeDataBind(TreeAction ta) {
		Object obj = ta.getParams().get("SiteID");
		String siteID = ApplicationPage.getCurrentSiteID()+"";
		DataTable dt = new QueryBuilder(
				"select ID,ParentID,Level,Name from ZCCatalog Where SiteID=?",
				siteID).executeDataTable();
		String siteName = new QueryBuilder(
				"select name from ZCSite where id=?", siteID).executeString();
		ta.setRootText(siteName);
		ta.bindData(dt);
	}

	public boolean unzipFile(String zipFileName, String upzipPath,
			String siteCode) {
		String copyToPath = Config.getContextRealPath()
				+ Config.getValue("Statical.TemplateDir") + "/" + siteCode;
		copyToPath = copyToPath.replace('\\', '/').replaceAll("/+", "/");
		upzipPath = upzipPath.replace('\\', '/').replaceAll("/+", "/");
		if (ZipUtil.unzip(zipFileName, upzipPath, true)) {
			FileUtil.delete(zipFileName);
		}

		ArrayList deployList = new ArrayList();
		File unzipFile = new File(upzipPath);
		ArrayList fileList = FileList.getAllFiles(upzipPath);
		for (int i = 0; i < fileList.size(); i++) {
			String fileName = (String) fileList.get(i);
			fileName = fileName.replace('\\', '/').replaceAll("/+", "/");
			String ext = fileName.lastIndexOf(".") == -1 ? "" : fileName
					.substring(fileName.lastIndexOf(".") + 1);
			String destPath = fileName.replaceAll(upzipPath, copyToPath);
			if (!PubFun.isAllowExt(ext)) {
				this.response.setStatus(0);
				this.response.setMessage("导入失败,不允许创建" + ext + "文件！");
				return false;
			}
			String destDirs = destPath.substring(0, destPath.lastIndexOf("/"));
			File dirs = new File(destDirs);
			if (!dirs.exists()) {
				dirs.mkdirs();
			}

			FileUtil.copy(fileName, destPath);
			deployList.add(destPath);
		}

		FileUtil.delete(unzipFile);

		Deploy d = new Deploy();
		d.addJobs(ApplicationPage.getCurrentSiteID(), deployList);

		return true;
	}

	public boolean processFile(String fileFullName, String siteCode) {
		ArrayList deployList = new ArrayList();
		File file = new File(fileFullName);
		if (!file.exists()) {
			return false;
		}
		String fileName = file.getName();

		String copyToPath = Config.getContextRealPath()
				+ Config.getValue("Statical.TemplateDir") + "/" + siteCode;

		String ext = fileName.lastIndexOf(".") == -1 ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1);
		if (("html".equalsIgnoreCase(ext)) || ("htm".equalsIgnoreCase(ext))
				|| ("jsp".equalsIgnoreCase(ext))
				|| ("js".equalsIgnoreCase(ext))
				|| ("php".equalsIgnoreCase(ext))
				|| ("jsp".equalsIgnoreCase(ext))
				|| ("asp".equalsIgnoreCase(ext))) {
			String fileText = FileUtil.readText(file);
			String tplPath = copyToPath + "/template/";
			FileUtil.mkdir(tplPath);
			FileUtil.writeText(tplPath + fileName, fileText);
			deployList.add(tplPath + fileName);
		} else {
			FileUtil.mkdir(copyToPath + "/images/");
			FileUtil.copy(file, copyToPath + "/images/" + fileName);
			deployList.add(copyToPath + "/images/" + fileName);
		}

		FileUtil.delete(file);

		Deploy d = new Deploy();
		d.addJobs(ApplicationPage.getCurrentSiteID(), deployList);

		return true;
	}

	public void preParse() {
		String path = $V("Path");
		if (XString.isEmpty(path)) {
			this.response.setStatus(0);
			this.response.setMessage("模板路径为空!");
			return;
		}

		File file = new File(path);
		boolean flag = true;
		if (file.exists()) {
			PreParser p = new PreParser();
			p.setSiteID(ApplicationPage.getCurrentSiteID());
			FilenameFilter filenameFilter = FileFilterUtils
			.makeSVNAware(FileFilterUtils.trueFileFilter());
			File[] templates = file.listFiles(filenameFilter);
			for (int i = 0; i < templates.length; i++) {
				p.setTemplateFileName(templates[i].getPath());
				if (!p.parse())
					flag = true;
			}
		} else {
			this.response.setStatus(0);
			this.response.setMessage("文件不存在!");
			return;
		}

		if (flag) {
			this.response.setStatus(0);
			this.response.setMessage("处理成功!");
		} else {
			this.response.setStatus(1);
			this.response.setMessage("处理失败!");
		}
	}
}

/*
 * com.xdarkness.cms.site.Template JD-Core Version: 0.6.0
 */