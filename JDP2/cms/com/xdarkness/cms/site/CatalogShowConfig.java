package com.xdarkness.cms.site;

import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.NumberUtil;

public class CatalogShowConfig extends Page {
	public static Mapx init(Mapx params) {
		DataTable dt = new QueryBuilder(
				"select Type,Value from ZDConfig where Memo='TreeConfig'")
				.executeDataTable();
		params.putAll(dt.toMapx(0, 1));
		return params;
	}

	public void save() {
		Transaction trans = new Transaction();
		trans
				.add(new QueryBuilder(
						"update ZDConfig set Value=? where Memo='TreeConfig' and Type='ArticleCatalogLoadType'",
						$V("ArticleCatalogLoadType")));
		trans
				.add(new QueryBuilder(
						"update ZDConfig set Value=? where Memo='TreeConfig' and Type='ArticleCatalogShowLevel'",
						$V("ArticleCatalogShowLevel")));
		trans
				.add(new QueryBuilder(
						"update ZDConfig set Value=? where Memo='TreeConfig' and Type='AttachLibLoadType'",
						$V("AttachLibLoadType")));
		trans
				.add(new QueryBuilder(
						"update ZDConfig set Value=? where Memo='TreeConfig' and Type='AttachLibShowLevel'",
						$V("AttachLibShowLevel")));
		trans
				.add(new QueryBuilder(
						"update ZDConfig set Value=? where Memo='TreeConfig' and Type='AudioLibLoadType'",
						$V("AudioLibLoadType")));
		trans
				.add(new QueryBuilder(
						"update ZDConfig set Value=? where Memo='TreeConfig' and Type='AudioLibShowLevel'",
						$V("AudioLibShowLevel")));
		trans
				.add(new QueryBuilder(
						"update ZDConfig set Value=? where Memo='TreeConfig' and Type='ImageLibLoadType'",
						$V("ImageLibLoadType")));
		trans
				.add(new QueryBuilder(
						"update ZDConfig set Value=? where Memo='TreeConfig' and Type='ImageLibShowLevel'",
						$V("ImageLibShowLevel")));
		trans
				.add(new QueryBuilder(
						"update ZDConfig set Value=? where Memo='TreeConfig' and Type='VideoLibLoadType'",
						$V("VideoLibLoadType")));
		trans
				.add(new QueryBuilder(
						"update ZDConfig set Value=? where Memo='TreeConfig' and Type='VideoLibShowLevel'",
						$V("VideoLibShowLevel")));
		if (trans.commit()) {
			Config.update();
			this.response.setLogInfo(1, "保存成功");
		} else {
			this.response.setLogInfo(0, "保存失败");
		}
	}

	public static int getArticleCatalogShowLevel() {
		String str = Config.getValue("ArticleCatalogShowLevel");
		if ((XString.isEmpty(str)) || (!NumberUtil.isInt(str))) {
			return 2;
		}
		return Integer.parseInt(str);
	}

	public static int getImageLibShowLevel() {
		String str = Config.getValue("ImageLibShowLevel");
		if ((XString.isEmpty(str)) || (!NumberUtil.isInt(str))) {
			return 2;
		}
		return Integer.parseInt(str);
	}

	public static int getAttachLibShowLevel() {
		String str = Config.getValue("AttachLibShowLevel");
		if ((XString.isEmpty(str)) || (!NumberUtil.isInt(str))) {
			return 2;
		}
		return Integer.parseInt(str);
	}

	public static int getVideoLibShowLevel() {
		String str = Config.getValue("VideoLibShowLevel");
		if ((XString.isEmpty(str)) || (!NumberUtil.isInt(str))) {
			return 2;
		}
		return Integer.parseInt(str);
	}

	public static int getAudioLibShowLevel() {
		String str = Config.getValue("AudioLibShowLevel");
		if ((XString.isEmpty(str)) || (!NumberUtil.isInt(str))) {
			return 2;
		}
		return Integer.parseInt(str);
	}

	public static boolean isImageLibLoadAllChild() {
		String str = Config.getValue("ImageLibLoadType");
		return "AllChild".equals(str);
	}

	public static boolean isAttachLibLoadAllChild() {
		String str = Config.getValue("AttachLibLoadType");
		return "AllChild".equals(str);
	}

	public static boolean isVideoLibLoadAllChild() {
		String str = Config.getValue("VideoLibLoadType");
		return "AllChild".equals(str);
	}

	public static boolean isAudioLibLoadAllChild() {
		String str = Config.getValue("AudioLibLoadType");
		return "AllChild".equals(str);
	}

	public static boolean isArticleCatalogLoadAllChild() {
		String str = Config.getValue("ArticleCatalogLoadType");
		return "AllChild".equals(str);
	}
}

/*
 * com.xdarkness.cms.site.CatalogShowConfig JD-Core Version: 0.6.0
 */