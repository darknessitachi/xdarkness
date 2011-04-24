package com.xdarkness.cms.site;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xdarkness.cms.datachannel.Publisher;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.schema.ZCSiteSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.Cookies;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.util.Mapx;

public class CatalogSite extends Page {
	public static Mapx init(Mapx params) {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(ApplicationPage.getCurrentSiteID());
		site.fill();
		Mapx map = site.toMapx();
		if (XString.isEmpty(site.getIndexTemplate())) {
			map
					.put(
							"edit",
							"<a id='editLink' href='javascript:void(0);' onclick=\"openEditor('')\"><img src='../Platform/Images/icon_edit.gif' width='14' height='14' style='display:none'></a>");
		} else {
			String s1 = "<a id='editLink' href='javascript:void(0);' onclick=\"openEditor('"
					+ site.getIndexTemplate()
					+ "')\"><img src='../Platform/Images/icon_edit.gif' width='14' height='14'></a>";
			map.put("edit", s1);
		}
		params.putAll(map);
		return params;
	}

	public void publishIndex() {
		long id = publishTask(ApplicationPage.getCurrentSiteID());
		this.response.setStatus(1);
		$S("TaskID", id);
	}

	private long publishTask(final long siteID) {
		LongTimeTask ltt = new LongTimeTask() {

			public void execute() {
				Publisher p = new Publisher();
				p.publishIndex(siteID, this);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	public void changeTemplate() {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(ApplicationPage.getCurrentSiteID());
		if (!site.fill()) {
			this.response.setLogInfo(0, "更新首页模板失败!");
			return;
		}
		String indexTemplate = $V("IndexTemplate");
		if (indexTemplate == null) {
			this.response.setLogInfo(0, "请选择模板!");
			return;
		}
		String fileName = Config.getContextRealPath()
				+ Config.getValue("Statical.TemplateDir") + "/"
				+ ApplicationPage.getCurrentSiteAlias() + indexTemplate;
		fileName = fileName.replaceAll("///", "/");
		fileName = fileName.replaceAll("//", "/");
		File file = new File(fileName);
		if (!file.exists()) {
			this.response.setLogInfo(0, "该模板文件不存在!");
			return;
		}
		site.setIndexTemplate(indexTemplate);
		Transaction trans = new Transaction();
		trans.add(site, OperateType.UPDATE);
		if (trans.commit())
			this.response.setLogInfo(1, "更新首页模板成功!");
		else
			this.response.setLogInfo(0, "更新首页模板失败!");
	}

	public static void onRequestBegin(HttpServletRequest request,
			HttpServletResponse response) {
		String type = request.getParameter("Type");
		if (XString.isEmpty(type)) {
			Cookies ci = new Cookies(request);
			String id = ci.getCookie("DocList.LastCatalog");
			if (XString.isNotEmpty(id)) {
				String siteID = ApplicationPage.getCurrentSiteID()+"";
				if (siteID.equals(CatalogUtil.getSiteID(id)))
					try {
						request.getRequestDispatcher(
								"CatalogBasic.jsp?CatalogID=" + id).forward(
								request, response);
					} catch (ServletException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}
}

/*
 * com.xdarkness.cms.site.CatalogSite JD-Core Version: 0.6.0
 */