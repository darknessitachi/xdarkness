package com.xdarkness.cms.template;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xdarkness.cms.dataservice.AdvertiseLayout;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCAdPositionSchema;
import com.xdarkness.schema.ZCImagePlayerSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;

public class PreParser {
	private static final Pattern cmsAD = Pattern.compile(
			"<cms:ad\\s(.*?)(/>|>(.*?)</cms:ad>)", 34);

	private static final Pattern cmsImagePlayer = Pattern.compile(
			"<cms:imageplayer\\s(.*?)(/>|>(.*?)</cms:imageplayer>)", 34);

	private static final Pattern cmsList = Pattern.compile(
			"<cms:list\\s(.*?)>(.*?)</cms:List>", 34);

	private static final Pattern pAttr1 = Pattern.compile(
			"\\s*?(\\w+?)\\s*?=\\s*?(\\\"|\\')(.*?)\\2", 34);

	private static final Pattern pAttr2 = Pattern.compile(
			"\\s*?(\\w+?)\\s*?=\\s*?([^\\'\\\"\\s]+)", 34);
	private String templateFileName;
	private String content;
	private long siteID;

	public boolean parse() {
		this.content = FileUtil.readText(this.templateFileName);
		if (XString.isEmpty(this.content)) {
			return true;
		}

		return (parseAD()) && (parseImagePlayer());
	}

	public ArrayList parseList() {
		this.content = FileUtil.readText(this.templateFileName);
		ArrayList idList = new ArrayList();
		Matcher m = cmsList.matcher(this.content);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String item = ((String) map.get("item")).toLowerCase();

			if ((!"article".equalsIgnoreCase(item))
					&& (!"image".equalsIgnoreCase(item))
					&& (!"video".equalsIgnoreCase(item))
					&& (!"audio".equalsIgnoreCase(item))
					&& (!"attachment".equalsIgnoreCase(item))
					&& (!"goods".equalsIgnoreCase(item)))
				continue;
			String catalog = (String) map.get("name");
			String parent = (String) map.get("parent");

			String catalogID = null;
			if (XString.isNotEmpty(catalog)) {
				if (XString.isDigit(catalog)) {
					catalogID = catalog;
				} else if (XString.isNotEmpty(parent))
					catalogID = CatalogUtil.getIDByName(this.siteID, parent,
							catalog);
				else {
					try {
						if (catalog.indexOf(",") != -1)
							catalogID = CatalogUtil.getIDsByName(this.siteID,
									catalog);
						else if (catalog.indexOf("/") != -1)
							catalogID = CatalogUtil.getIDByNames(this.siteID,
									catalog);
						else
							catalogID = CatalogUtil.getIDByName(this.siteID,
									catalog);
					} catch (Exception e) {
						LogUtil.warn(e.getMessage());
					}
				}

			}

			if (XString.isNotEmpty(catalogID)) {
				String[] ids = catalogID.split("\\,");
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}
			}

		}

		return idList;
	}

	private boolean parseAD() {
		Matcher m = cmsAD.matcher(this.content);
		int lastEndIndex = 0;
		Transaction trans = new Transaction();
		while (m.find(lastEndIndex)) {
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String name = (String) map.get("name");
			String type = (String) map.get("type");
			String size = (String) map.get("size");
			String description = (String) map.get("description");

			ZCAdPositionSchema ad = new ZCAdPositionSchema();
			int NameCount = new QueryBuilder(
					"select count(*) from zcadposition where PositionName = ? and siteid=?",
					name, this.siteID).executeInt();
			if (NameCount > 0) {
				continue;
			}
			ad.setID(NoUtil.getMaxID("AdPositionID"));
			ad.setCode(ad.getID()+"");
			if ((User.getCurrent() != null) && (User.getUserName() != null))
				ad.setAddUser("SYSTEM");
			else {
				ad.setAddUser(User.getUserName());
			}
			ad.setAddTime(new Date());
			ad.setJsName(AdvertiseLayout.createJS("add", ad));

			ad.setSiteID(this.siteID);
			ad.setPositionName(name);
			ad.setDescription(description);
			ad.setPositionType(type);
			if (XString.isNotEmpty(size)) {
				String[] arr = size.split("\\*");
				ad.setPositionWidth(arr[0]);
				ad.setPositionHeight(arr[1]);
			}
			trans.add(ad, OperateType.INSERT);
		}

		return trans.commit();
	}

	private boolean parseImagePlayer() {
		Matcher m = cmsImagePlayer.matcher(this.content);
		int lastEndIndex = 0;
		Transaction trans = new Transaction();
		while (m.find(lastEndIndex)) {
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String name = (String) map.get("name");
			if (XString.isEmpty(name)) {
				name = (String) map.get("code");
			}
			String code = name;
			String width = (String) map.get("width");
			if (XString.isEmpty(width)) {
				width = "100";
			}
			String height = (String) map.get("height");
			if (XString.isEmpty(height)) {
				height = "100";
			}
			String count = (String) map.get("count");
			if (XString.isEmpty(count)) {
				count = "5";
			}

			ZCImagePlayerSchema imagePlayer = new ZCImagePlayerSchema();
			int NameCount = new QueryBuilder(
					"select count(*) from ZCImagePlayer where name = ? and siteid=?",
					name, this.siteID).executeInt();
			int CodeCount = new QueryBuilder(
					"select count(*) from ZCImagePlayer where code = ? and siteid=?",
					code, this.siteID).executeInt();
			if ((NameCount > 0) || (CodeCount > 0)) {
				continue;
			}
			imagePlayer.setID(NoUtil.getMaxID("ImagePlayerID"));
			imagePlayer.setCode(code);
			imagePlayer.setName(name);
			imagePlayer.setImageSource("0");
			imagePlayer.setIsShowText("N");
			if ((User.getCurrent() != null) && (User.getUserName() != null))
				imagePlayer.setAddUser("SYSTEM");
			else {
				imagePlayer.setAddUser(User.getUserName());
			}
			imagePlayer.setAddTime(new Date());
			imagePlayer.setSiteID(this.siteID);
			imagePlayer.setDisplayType("0");
			imagePlayer.setWidth(width);
			imagePlayer.setHeight(height);
			imagePlayer.setDisplayCount(count);

			trans.add(imagePlayer, OperateType.INSERT);
		}

		return trans.commit();
	}

	private static Mapx getAttrMap(String str) {
		Mapx map = new Mapx();
		Matcher m = pAttr1.matcher(str);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String value = m.group(3);
			if (value != null) {
				value = value.trim();
			}
			map.put(m.group(1).toLowerCase(), value);
			lastEndIndex = m.end();
		}

		m = pAttr2.matcher(str);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String value = m.group(2);
			if (value != null) {
				value = value.trim();
			}
			map.put(m.group(1).toLowerCase(), value);
			lastEndIndex = m.end();
		}
		return map;
	}

	public String getTemplateFileName() {
		return this.templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public long getSiteID() {
		return this.siteID;
	}

	public void setSiteID(long siteID) {
		this.siteID = siteID;
	}
}

/*
 * com.xdarkness.cms.template.PreParser JD-Core Version: 0.6.0
 */