package com.xdarkness.cms.dataservice;

import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.xdarkness.cms.datachannel.Deploy;
import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCAdPositionSchema;
import com.xdarkness.schema.ZCAdvertisementSchema;
import com.xdarkness.schema.ZCAdvertisementSet;
import com.xdarkness.schema.ZCImageSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class Advertise extends Page {
	public static Mapx ADTypes = new Mapx();

	static {
		ADTypes.put("image", "图片");
		ADTypes.put("flash", "动画");
		ADTypes.put("text", "文本");
		ADTypes.put("code", "代码");
		ADTypes.put("", "无广告");
	}

	public static Mapx init(Mapx params) {
		String PosID = params.getString("PosID");
		params.put("PositionID", PosID);
		params.put("PositionName", new QueryBuilder(
				"select PositionName from zcadposition where ID = ?", PosID)
				.executeString());
		params.put("PosName", params.getString("PositionName"));
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String PosID = dga.getParam("PosID");
		String SearchContent = dga.getParam("SearchContent");
		QueryBuilder qb = new QueryBuilder(
				"select id,AdName,ADType,(select b.PositionName from zcadposition b where b.id=zcadvertisement.positionid) PositionName,case isopen when 'Y' then '√' else '' end isopen,HitCount,OrderFlag,AddTime from zcadvertisement ");
		qb.append(" where PositionID=?", PosID);
		if (XString.isNotEmpty(SearchContent)) {
			qb.append(" and AdName like ?", "%" + SearchContent.trim() + "%");
		}
		qb.append(" order by OrderFlag desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.decodeColumn("ADType", ADTypes);
		dga.bindData(dt);
	}

	public static Mapx DialogInit(Mapx params) {
		String id = (String) params.get("ID");
		String type = (String) params.get("Type");
		String PosID = params.getString("PosID");
		String PositionID = params.getString("PositionID");
		if (!XString.isEmpty(PosID)) {
			if (!"New".equals(type)) {
				id = (String)new QueryBuilder(
						"select ID from zcadvertisement where PositionID = ? and IsOpen = 'Y'",
						PosID).executeOneValue();
				if ((XString.isEmpty(id)) || (id.equals("null"))) {
					id = "";
				}
			}
			PositionID = PosID;
		}
		String PositionType = new QueryBuilder(
				"select PositionType from zcadposition where ID = ?",
				PositionID).executeString();

		if (XString.isNotEmpty(id)) {
			ZCAdvertisementSchema ad = new ZCAdvertisementSchema();
			ad.setID(id);
			ad.fill();
			PositionID = ad.getPositionID()+"";
			String StartTime = ad.getStartTime()+"";
			String EndTime = ad.getEndTime()+"";
			if (XString.isNotEmpty(StartTime)) {
				params.put("StartDate", StartTime.substring(0, 10));
				params.put("STime", StartTime.substring(11, 19));
			}
			if (XString.isNotEmpty(EndTime)) {
				params.put("EndDate", EndTime.substring(0, 10));
				params.put("ETime", EndTime.substring(11, 19));
			}
			params.putAll(ad.toMapx());
			if (PositionType.equals("code")) {
				params.put("AdContent", "");
				params.put("codeContent", ad.getAdContent());
			}
		}
		params.put("PositionID", PositionID);
		params.put("PositionType", PositionType);
		params.put("PositionName", new QueryBuilder(
				"select PositionName from zcadposition where ID = ?",
				PositionID).executeString());
		params.put("PositionTypeName", AdvertiseLayout.PosTypes
				.get(PositionType));
		params.put("imgADLinkUrl", "http://");
		params.put("textLinkUrl", "http://");
		params.put("showAlt", "Y");
		if ((PositionType.equalsIgnoreCase("imagechange"))
				|| (PositionType.equalsIgnoreCase("imagelist")))
			params.put("AdTypeOptions",
					"<span value='image' selected>图片</span>");
		else if ((PositionType.equalsIgnoreCase("fixure"))
				|| (PositionType.equalsIgnoreCase("float"))
				|| (PositionType.equalsIgnoreCase("couplet")))
			params
					.put("AdTypeOptions",
							"<span value='image' selected>图片</span><span value='flash'>动画</span>");
		else if (PositionType.equalsIgnoreCase("code"))
			params
					.put("AdTypeOptions",
							"<span value='code' selected>代码</span>");
		else if (PositionType.equalsIgnoreCase("banner"))
			params
					.put("AdTypeOptions",
							"<span value='image' selected>图片</span><span value='flash'>动画</span>");
		else if (PositionType.equalsIgnoreCase("text")) {
			params
					.put("AdTypeOptions",
							"<span value='text' selected>文本</span>");
		}
		params.put("UploadFilePath", (Config.getContextPath()
				+ Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/")
				.replaceAll("//", "/"));
		return params;
	}

	public void getImgSrc() {
		String ImgID = $V("ImgID");
		ZCImageSchema image = new ZCImageSchema();
		image.setID(ImgID);
		if (image.fill())
			this.response.put("ImgSrc", (image.getPath() + image
					.getSrcFileName()).replaceAll("//", "/"));
		else
			return;
	}

	public void start() {
		String ID = $V("ID");
		if (!XString.isEmpty(ID)) {
			ZCAdvertisementSchema ad = new ZCAdvertisementSchema();
			ad.setID(ID);
			ad.fill();
			ZCAdPositionSchema adp = new ZCAdPositionSchema();
			adp.setID(ad.getPositionID());
			adp.fill();
			if ((!adp.getPositionType().equals("text"))
					&& (!adp.getPositionType().equals("imagelist"))
					&& (!adp.getPositionType().equals("imagechange"))) {
				try {
					new QueryBuilder(
							"update zcadvertisement set IsOpen = 'N' where positionid=?",
							ad.getPositionID()).executeNoQuery();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ad.getIsOpen().equals("Y"))
				ad.setIsOpen("N");
			else {
				ad.setIsOpen("Y");
			}
			ad.update();
			if (CreateJSCode(ad, adp))
				this.response.setLogInfo(1, "操作成功！");
			else
				this.response.setLogInfo(0, "操作失败！");
		}
	}

	public void sortAdvertise() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String positionID = $V("PositionID");
		if ((!XString.checkID(target)) && (!XString.checkID(orders))) {
			return;
		}
		Transaction tran = new Transaction();
		OrderUtil.updateOrder("ZCAdvertisement", "OrderFlag", type, target,
				orders, " PositionID = " + positionID, tran);
		if (tran.commit()) {
			CreateJSCode(positionID);
			this.response.setMessage("操作成功");
		} else {
			this.response.setError("操作失败");
		}
	}

	public void add() {
		String id = $V("ID");

		ZCAdvertisementSchema ad = new ZCAdvertisementSchema();
		ZCAdPositionSchema adp = new ZCAdPositionSchema();
		adp.setID($V("PositionID"));
		adp.fill();
		if (XString.isNotEmpty(id)) {
			ad.setID(id);
			ad.fill();
			ad.setModifyUser(User.getUserName());
			ad.setModifyTime(new Date());
		} else {
			ad.setID(NoUtil.getMaxID("AdvertiseID"));
			ad.setAddUser(User.getUserName());
			ad.setAddTime(new Date());
			if ((new QueryBuilder(
					"select Count(*) from ZCAdvertisement where PositionID = ?",
					adp.getID()).executeInt() == 0)
					|| ($V("addType").equals("layout"))
					|| (adp.getPositionType().equals("text"))
					|| (adp.getPositionType().equals("imagechange"))
					|| (adp.getPositionType().equals("imagelist")))
				ad.setIsOpen("Y");
			else {
				ad.setIsOpen("N");
			}
		}
		String StartTime = "";
		String EndTime = "";
		if (XString.isNotEmpty($V("StartDate"))) {
			StartTime = StartTime + $V("StartDate") + " ";
			if (XString.isNotEmpty($V("STime")))
				StartTime = StartTime + $V("STime");
			else
				StartTime = StartTime + "00:00:00";
		} else {
			StartTime = DateUtil.getCurrentDateTime();
		}
		if (XString.isNotEmpty($V("EndDate"))) {
			EndTime = EndTime + $V("EndDate") + " ";
			if (XString.isNotEmpty($V("ETime")))
				EndTime = EndTime + $V("ETime");
			else
				EndTime = EndTime + "23:59:59";
		} else {
			EndTime = "2999-12-31 23:59:59";
		}
		ad.setStartTime(DateUtil
				.parseDateTime(StartTime, "yyyy-MM-dd hh:mm:ss"));
		ad.setEndTime(DateUtil.parseDateTime(EndTime, "yyyy-MM-dd hh:mm:ss"));
		ad.setPositionID(adp.getID());
		ad.setPositionCode(adp.getCode());
		ad.setSiteID(ApplicationPage.getCurrentSiteID());
		ad.setAdName(XString.htmlEncode($V("AdName")));
		ad.setAdType($V("AdType"));
		ad.setIsHitCount("N");
		ad.setHitCount(0L);
		ad.setOrderFlag(OrderUtil.getDefaultOrder());

		if ("image".equals(ad.getAdType())) {
			if ((adp.getPositionType().equals("imagechange"))
					|| (adp.getPositionType().equals("imagelist")))
				ad = imagesAD(ad, adp);
			else {
				ad = imageAD(ad, adp);
			}
		}

		if ("flash".equals(ad.getAdType())) {
			ad = flashAD(ad, adp);
		}

		if ("text".equals(ad.getAdType())) {
			ad = textAD(ad);
		}

		if ("code".equals(ad.getAdType())) {
			ad = codeAD(ad);
		}

		boolean flag = true;
		try {
			if (XString.isNotEmpty(StartTime)) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Date start = df.parse(StartTime);
				Date now = new Date();
				if (start.getTime() > now.getTime()) {
					flag = false;
				}
			}
			if (XString.isNotEmpty(EndTime)) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date end = df.parse(EndTime);
				Date now = new Date();
				if (end.getTime() < now.getTime())
					flag = false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (XString.isNotEmpty(id)) {
			if (ad.update()) {
				if ((flag) && (!adp.getPositionType().equals("code"))) {
					CreateJSCode(ad, adp);
				}
				this.response.setLogInfo(1, "修改广告成功!");
			} else {
				this.response.setLogInfo(0, "修改广告发生错误!");
			}
		} else if (ad.insert()) {
			if ((flag) && (!adp.getPositionType().equals("code"))) {
				CreateJSCode(ad, adp);
			}
			this.response.setLogInfo(1, "新增广告成功!");
		} else {
			this.response.setLogInfo(0, "新增广告发生错误!");
		}
	}

	public ZCAdvertisementSchema imagesAD(ZCAdvertisementSchema ad,
			ZCAdPositionSchema adp) {
		String[] Urls = XString.splitEx($V("imgADLinkUrl"), "^");
		String[] Alts = XString.splitEx($V("imgADAlt"), "^");
		String[] Paths = XString.splitEx($V("ImgPath"), "^");
		String showAlt = $V("showAlt");
		if ((XString.isEmpty(showAlt)) || (!showAlt.equals("Y"))) {
			showAlt = "N";
		}
		String ContentStr = "{'imgID':'" + ad.getID() + "','imgADLinkUrl':'"
				+ Urls[0] + "','imgADAlt':'" + XString.htmlEncode(Alts[0])
				+ "','ImgPath':'" + Paths[0] + "','imgADLinkTarget':'"
				+ $V("imgADLinkTarget") + "','showAlt':'" + showAlt + "'}";
		ad.setAdContent(ContentStr);
		return ad;
	}

	public ZCAdvertisementSchema imageAD(ZCAdvertisementSchema ad,
			ZCAdPositionSchema adp) {
		String[] Urls = XString.splitEx($V("imgADLinkUrl"), "^");
		String[] Alts = XString.splitEx($V("imgADAlt"), "^");
		String[] Paths = XString.splitEx($V("ImgPath"), "^");
		String showAlt = $V("showAlt");
		if ((XString.isEmpty(showAlt)) || (!showAlt.equals("Y"))) {
			showAlt = "N";
		}
		String ContentStr = "{'Images':[";
		for (int i = 0; i < Paths.length; i++) {
			ContentStr = ContentStr + "{'imgADLinkUrl':'" + Urls[i]
					+ "','imgADAlt':'" + XString.htmlEncode(Alts[i])
					+ "','ImgPath':'" + Paths[i] + "'}";
			if (i != Paths.length - 1) {
				ContentStr = ContentStr + ",";
			}
		}
		ContentStr = ContentStr + "],'imgADLinkTarget':'"
				+ $V("imgADLinkTarget") + "','Count':'" + Paths.length
				+ "','showAlt':'" + showAlt + "'}";
		ad.setAdContent(ContentStr);
		return ad;
	}

	public ZCAdvertisementSchema flashAD(ZCAdvertisementSchema ad,
			ZCAdPositionSchema adp) {
		String[] Paths = XString.splitEx($V("SwfFilePath"), "^");
		String ContentStr = "{'Flashes':[";
		for (int i = 0; i < Paths.length; i++) {
			ContentStr = ContentStr + "{'SwfFilePath':'" + Paths[i] + "'}";
			if (i != Paths.length - 1) {
				ContentStr = ContentStr + ",";
			}
		}
		ContentStr = ContentStr + "],'Count':'" + Paths.length + "'}";
		ad.setAdContent(ContentStr);
		return ad;
	}

	public ZCAdvertisementSchema textAD(ZCAdvertisementSchema ad) {
		String textContent = XString.htmlEncode($V("textContent"));
		String textLinkUrl = $V("textLinkUrl");
		String TextColor = $V("TextColor");
		if (TextColor.equals("^")) {
			TextColor = "";
		}
		String ContentStr = "{'textID':'" + ad.getID() + "','textContent':'"
				+ textContent + "','textLinkUrl':'" + textLinkUrl
				+ "','TextColor':'" + TextColor + "'}";
		ad.setAdContent(ContentStr);
		return ad;
	}

	public ZCAdvertisementSchema codeAD(ZCAdvertisementSchema ad) {
		String ContentStr = $V("codeContent");
		ad.setAdContent(ContentStr);
		return ad;
	}

	public void del() {
		String ids = $V("IDs");
		if ((ids.indexOf("\"") >= 0) || (ids.indexOf("'") >= 0)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCAdvertisementSchema ad = new ZCAdvertisementSchema();
		ZCAdvertisementSet set = ad.query(new QueryBuilder("where id in ("
				+ ids + ")"));
		trans.add(set, OperateType.DELETE_AND_BACKUP);
		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}

	public void copy() {
		String id = $V("ID");
		ZCAdvertisementSchema ad = new ZCAdvertisementSchema();
		ad.setID(Long.parseLong(id));
		ad.fill();
		String AdName = ad.getAdName();
		AdName = "复制  " + AdName;
		ad.setID(NoUtil.getMaxID("AdvertiseID"));
		ad.setAddUser(User.getUserName());
		ad.setAddTime(new Date());
		ad.setAdName(AdName);
		ad.setIsOpen("Y");
		if (ad.insert()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("发生错误!");
		}
	}

	public static String getJson(String PositionID) {
		StringBuffer sb = new StringBuffer();
		sb.append("");
		if (XString.isNotEmpty(PositionID)) {
			ZCAdPositionSchema adp = new ZCAdPositionSchema();
			adp.setID(PositionID);
			adp.fill();
			if ((adp.getPositionType().equals("text"))
					|| (adp.getPositionType().equals("imagelist"))
					|| (adp.getPositionType().equals("imagechange"))) {
				String ADContent = "";
				if (adp.getPositionType().equals("text"))
					ADContent = ADContent + "{'ADText':[";
				else {
					ADContent = ADContent + "{'ADImage':[";
				}
				QueryBuilder qb = new QueryBuilder(
						"where  IsOpen='Y' and PositionID = "
								+ adp.getID()
								+ " and StartTime<=? and EndTime>=? order by OrderFlag desc",
						new Date(), new Date());
				ZCAdvertisementSet aset = new ZCAdvertisementSchema().query(qb);
				for (int i = 0; i < aset.size(); i++) {
					ADContent = ADContent + aset.get(i).getAdContent();
					if (i != aset.size() - 1) {
						ADContent = ADContent + ",";
					}
				}
				ADContent = ADContent + "]}";
				sb.append(ADContent);
			} else {
				ZCAdvertisementSchema ad = new ZCAdvertisementSchema();
				String adID = new QueryBuilder(
						"select ID from ZCAdvertisement where  IsOpen='Y' and PositionID = "
								+ adp.getID()
								+ " and StartTime<=? and EndTime>=? order by OrderFlag desc",
						new Date(), new Date()).executeString();
				if (XString.isNotEmpty(adID)) {
					ad.setID(adID);
					ad.fill();
					sb.append(ad.getAdContent());
				}
			}
		}
		return sb.toString();
	}

	public static boolean CreateJSCode(String PositionID) {
		ZCAdPositionSchema adp = new ZCAdPositionSchema();
		ZCAdvertisementSchema ad = new ZCAdvertisementSchema();
		Date now = new Date();
		adp.setID(PositionID);
		adp.fill();
		QueryBuilder qb = new QueryBuilder(
				"select ID from ZCAdvertisement where PositionID=? and StartTime<=? and EndTime>=? order by OrderFlag asc");
		qb.add(PositionID);
		qb.add(now);
		qb.add(now);
		String AdID = qb.executeString();
		if ((XString.isEmpty(AdID)) || (AdID.equalsIgnoreCase("null"))) {
			File f = new File(Config.getContextRealPath()
					+ Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(adp.getSiteID()) + "/"
					+ adp.getJsName());
			if (f.exists()) {
				f.delete();
				FileUtil.writeText(f.getPath(),
						"document.write('广告内容已过期，请管理员更换');");
			}
		} else {
			ad.setID(AdID);
			ad.fill();
			CreateJSCode(ad, null);
		}
		return true;
	}

	public static boolean CreateJSCode(ZCAdvertisementSchema adv,
			ZCAdPositionSchema adp) {
		ArrayList deployList = new ArrayList();
		File file = null;
		StringBuffer sb = new StringBuffer();
		ZCAdPositionSchema adposition = new ZCAdPositionSchema();
		String OutString = "";
		String TempPath = Config.getContextRealPath()
				+ "DataService/ADTemplate/";
		if (adp == null) {
			adposition.setID(adv.getPositionID());
			adposition.fill();
		} else {
			adposition = (ZCAdPositionSchema) adp.clone();
		}

		if (adposition.getPositionType().equals("banner")) {
			file = new File(TempPath + "ZCMSAD_Banner.js");
		} else if (adposition.getPositionType().equals("fixure")) {
			file = new File(TempPath + "ZCMSAD_Fixure.js");
		} else if (adposition.getPositionType().equals("float")) {
			file = new File(TempPath + "ZCMSAD_Float.js");
			sb.append(" \n");
			sb.append("function cmsAD_" + adposition.getID()
					+ "_pause_resume(){if(cmsAD_" + adposition.getID()
					+ ".Pause){clearInterval(cmsAD_" + adposition.getID()
					+ ".Interval);cmsAD_" + adposition.getID()
					+ ".Pause = false;}");
			sb.append("else {cmsAD_" + adposition.getID()
					+ ".Interval = setInterval(cmsAD_" + adposition.getID()
					+ ".Start(cmsAD_" + adposition.getID() + "),cmsAD_"
					+ adposition.getID() + ".Delay);cmsAD_"
					+ adposition.getID() + ".Pause = true;}}");
		} else if (adposition.getPositionType().equals("couplet")) {
			file = new File(TempPath + "ZCMSAD_Couplet.js");
		} else if (adposition.getPositionType().equals("imagechange")) {
			file = new File(TempPath + "ZCMSAD_ImageChange.js");
		} else if (adposition.getPositionType().equals("imagelist")) {
			file = new File(TempPath + "ZCMSAD_ImageList.js");
		} else if (adposition.getPositionType().equals("text")) {
			file = new File(TempPath + "ZCMSAD_Text.js");
		} else if (adposition.getPositionType().equals("code")) {
			return true;
		}
		OutString = FileUtil.readText(file);

		sb.append(" \n");
		String Services = Config.getValue("ServicesContext");
		sb.append("var cmsAD_" + adposition.getID() + " = new ZCMSAD('cmsAD_"
				+ adposition.getID() + "'); \n");
		sb.append("cmsAD_" + adposition.getID() + ".PosID = "
				+ adposition.getID() + "; \n");
		sb.append("cmsAD_" + adposition.getID() + ".ADID = " + adv.getID()
				+ "; \n");
		sb.append("cmsAD_" + adposition.getID() + ".ADType = \""
				+ adv.getAdType() + "\"; \n");
		sb.append("cmsAD_" + adposition.getID() + ".ADName = \""
				+ adv.getAdName() + "\"; \n");
		if ((adposition.getPositionType().equals("text"))
				|| (adposition.getPositionType().equals("imagelist"))
				|| (adposition.getPositionType().equals("imagechange"))) {
			String ADContent = "";
			if (adposition.getPositionType().equals("text"))
				ADContent = ADContent + "{'ADText':[";
			else {
				ADContent = ADContent + "{'ADImage':[";
			}
			QueryBuilder qb = new QueryBuilder(
					"where  IsOpen='Y' and PositionID = "
							+ adposition.getID()
							+ " and StartTime<=? and EndTime>=? order by OrderFlag desc",
					new Date(), new Date());
			ZCAdvertisementSet aset = new ZCAdvertisementSchema().query(qb);
			for (int i = 0; i < aset.size(); i++) {
				ADContent = ADContent + aset.get(i).getAdContent();
				if (i != aset.size() - 1) {
					ADContent = ADContent + ",";
				}
			}
			ADContent = ADContent + "]}";
			sb.append("cmsAD_" + adposition.getID() + ".ADContent = \""
					+ ADContent + "\"; \n");
		} else {
			sb.append("cmsAD_" + adposition.getID() + ".ADContent = \""
					+ adv.getAdContent() + "\"; \n");
		}
		sb.append("cmsAD_" + adposition.getID() + ".URL = \"" + Services
				+ "/ADClick.jsp\"; \n");
		sb.append("cmsAD_" + adposition.getID() + ".SiteID = "
				+ adposition.getSiteID() + "; \n");
		if ((adposition.getPositionType().equals("fixure"))
				|| (adposition.getPositionType().equals("float"))
				|| (adposition.getPositionType().equals("couplet"))) {
			sb.append("cmsAD_" + adposition.getID() + ".PaddingLeft = "
					+ adposition.getPaddingLeft() + "; \n");
			sb.append("cmsAD_" + adposition.getID() + ".PaddingTop = "
					+ adposition.getPaddingTop() + "; \n");
		}
		if ((adposition.getPositionType().equals("fixure"))
				|| (adposition.getPositionType().equals("couplet"))) {
			sb.append("cmsAD_" + adposition.getID() + ".Scroll = '"
					+ adposition.getScroll() + "'; \n");
			if (adposition.getPositionType().equals("fixure")) {
				sb.append("cmsAD_" + adposition.getID() + ".Align = '"
						+ adposition.getAlign() + "'; \n");
			}
		}
		sb.append("cmsAD_" + adposition.getID() + ".Width = "
				+ adposition.getPositionWidth() + "; \n");
		sb.append("cmsAD_" + adposition.getID() + ".Height = "
				+ adposition.getPositionHeight() + "; \n");

		String siteUrl = SiteUtil.getURL(adposition.getSiteID());
		String imagePath;
		if (siteUrl.equals("http://"))
			imagePath = Config.getContextPath() + Config.getValue("UploadDir")
					+ "/" + SiteUtil.getAlias(adposition.getSiteID());
		else {
			imagePath = siteUrl;
		}

		sb.append("cmsAD_" + adposition.getID() + ".UploadFilePath = \""
				+ imagePath + "\"; \n");
		if ((adposition.getPositionType().equals("text"))
				|| (adposition.getPositionType().equals("imagelist"))) {
			sb.append("cmsAD_" + adposition.getID() + ".ShowAD();\n");
		} else if (adv.getIsOpen().equals("Y")) {
			sb.append("cmsAD_" + adposition.getID() + ".ShowAD();\n");
			if (adposition.getPositionType().equals("float")) {
				sb.append("document.getElementById('ZCMSAD_"
						+ adposition.getID() + "').visibility = 'visible';\n");
				sb.append("cmsAD_" + adposition.getID()
						+ ".Interval = setInterval(cmsAD_" + adposition.getID()
						+ ".Start(cmsAD_" + adposition.getID() + "),cmsAD_"
						+ adposition.getID() + ".Delay);");
			} else if (adposition.getPositionType().equals("couplet")) {
				sb.append("cmsAD_" + adposition.getID() + ".Start(cmsAD_"
						+ adposition.getID() + ".Scroll);\n");
			} else if (adposition.getPositionType().equals("imagechange")) {
				sb.append("ldh.on(window,'load',function (){\n");
				sb.append("shower.init({box:'ZCMSAD_" + adposition.getID()
						+ "_Box',tip:'ZCMSAD_" + adposition.getID()
						+ "_Tip'});})\n");
				sb.append("displayAlt(cmsAD_" + adposition.getID()
						+ ".ADContent,'ZCMSAD_" + adposition.getID()
						+ "_Tip');");
			}
		}

		OutString = OutString + sb.toString();
		String positionPath = Config.getContextRealPath()
				+ Config.getValue("UploadDir")
				+ "/"
				+ SiteUtil.getAlias(adposition.getSiteID())
				+ "/"
				+ adposition.getJsName().substring(0,
						adposition.getJsName().lastIndexOf("/")) + "/";
		File f = new File(positionPath);
		if (!f.exists()) {
			FileUtil.mkdir(positionPath);
		}
		FileUtil.writeText(Config.getContextRealPath()
				+ Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(adposition.getSiteID()) + "/"
				+ adposition.getJsName(), OutString);

		deployList.add(Config.getContextRealPath()
				+ Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(adposition.getSiteID()) + "/"
				+ adposition.getJsName());
		Deploy d = new Deploy();
		d.addJobs(adposition.getSiteID(), deployList);

		return true;
	}
}

/*
 * com.xdarkness.cms.dataservice.Advertise JD-Core Version: 0.6.0
 */