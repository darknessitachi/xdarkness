package com.xdarkness.platform.pub;

import java.util.Date;

import com.abigdreamer.java.net.Config;
import com.abigdreamer.java.net.data.BlockingTransaction;
import com.abigdreamer.java.net.data.Transaction;
import com.abigdreamer.java.net.io.FileUtil;
import com.abigdreamer.java.net.jaf.WebConfig;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.XString;
import com.abigdreamer.java.net.util.ZipUtil;
import com.zving.schema.ZDCodeSet;
import com.zving.schema.ZDConfigSchema;
import com.zving.schema.ZDIPRangeSet;
import com.zving.schema.ZDMenuSchema;

public class Patch {
	private static Object mutex = new Object();

	public static synchronized void checkUpdate() {
		if (!WebConfig.isInstalled) {
			return;
		}
		if ((!WebConfig.isNeedCheckPatch) || (WebConfig.isPatching)) {
			return;
		}
		synchronized (mutex) {
			if ((!WebConfig.isNeedCheckPatch) || (WebConfig.isPatching)) {
				return;
			}
			WebConfig.isNeedCheckPatch = false;
			WebConfig.isPatching = true;
		}
		BlockingTransaction tran = new BlockingTransaction();
		try {
			String version = new QueryBuilder(
					"select Value from ZDConfig where Type=?", "System.Version")
					.executeString();
			float mainVersion = 0.0F;
			float minorVersion = 0.0F;
			if (XString.isEmpty(version)) {
				mainVersion = 1.3F;
				minorVersion = 0.5F;
			} else {
				String[] arr = version.split("\\.");
				mainVersion = Float.parseFloat(arr[0] + "." + arr[1]);
				if (arr.length == 4)
					minorVersion = Float.parseFloat(arr[2] + "." + arr[3]);
				else {
					minorVersion = Float.parseFloat(arr[2]);
				}
			}
			if ((mainVersion < 1.3D) && (minorVersion < 1.0D))
				updateTo1_3_1(tran);
		} catch (Exception e) {
			e.printStackTrace();
			tran.rollback();
		} finally {
			WebConfig.isPatching = false;
		}
	}

	public static void updateTo1_3_1(BlockingTransaction tran) {
		try {
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig  VALUES ('RewriteIndex','是否将站点首页重新生成index.html','N',NULL,NULL,NULL,NULL,NULL,'2010-01-18 14:35:52','admin',NULL,NULL)"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			tran
					.addWithException(new QueryBuilder(
							"update zdcode set codevalue ='Y' where codetype = 'VerifyFlag' and parentcode ='VerifyFlag' and codevalue ='S'"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ArticleCatalogLoadType','文章栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:03:06','admin',NULL,NULL)"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ArticleCatalogShowLevel','文章栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:01:26','admin',NULL,NULL)"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('AttachLibLoadType','附件库栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:20:52','admin',NULL,NULL)"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('AttachLibShowLevel','附件库栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:19:39','admin',NULL,NULL)"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('AudioLibLoadType','音频库栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:22:32','admin',NULL,NULL)"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('AudioLibShowLevel','音频库栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:22:11','admin',NULL,NULL)"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ImageLibLoadType','图片库栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:14:16','admin',NULL,NULL)"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('ImageLibShowLevel','图片库栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:06:30','admin',NULL,NULL)"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('VideoLibLoadType','视频库栏目树延迟加载方式','AllChild',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:21:47','admin',NULL,NULL)"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdconfig (Type,Name,Value,Prop1,Prop2,Prop3,Prop4,Memo,AddTime,AddUser,ModifyTime,ModifyUser) VALUES ('VideoLibShowLevel','视频库栏目树默认展开层级','2',NULL,NULL,NULL,NULL,'TreeConfig','2010-06-22 15:21:19','admin',NULL,NULL)"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			tran
					.addWithException(new QueryBuilder(
							"update zcarticle set pageTitle=prop1 where prop1 is not null"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			tran
					.addWithException(new QueryBuilder(
							"update ZCStatItem set Type='District',SubType='PV' where Type='Client' and SubType='District'"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		ZDMenuSchema menu = new ZDMenuSchema();
		menu.setID(NoUtil.getMaxID("MenuID"));
		menu.setParentID(122L);
		menu.setType("2");
		menu.setName("工作台配置");
		menu.setURL("Site/WorkBenchConfig.jsp");
		menu.setVisiable("Y");
		menu.setIcon("Icons/icon001a7.gif");
		menu.setOrderFlag("14");
		menu.setAddTime(new Date());
		menu.setAddUser("SYSTEM");
		tran.add(menu, 1);

		tran.add(new QueryBuilder("delete from ZDCode where CodeType=?",
				"ArticleRefer"));
		byte[] bs = FileUtil.readByte(Config.getContextRealPath()
				+ "WEB-INF/data/installer/1.3.1.ArticleRefer.dat");
		bs = ZipUtil.unzip(bs);
		ZDCodeSet codeSet = (ZDCodeSet) FileUtil.unserialize(bs);
		tran.add(codeSet, 1);
		tran.add(new QueryBuilder("delete from ZDIPRange"));

		bs = FileUtil.readByte(Config.getContextRealPath()
				+ "WEB-INF/data/installer/1.3.1.IPRanges.dat");
		bs = ZipUtil.unzip(bs);
		ZDIPRangeSet ipSet = (ZDIPRangeSet) FileUtil.unserialize(bs);
		tran.add(ipSet, 1);
		try {
			tran
					.addWithException(new QueryBuilder(
							"UPDATE zdmenu set Name = '会员配置',URL='DataService/MemberConfig.jsp',Icon='Icons/icon025a6.gif' where Name = '会员等级'"));
			tran
					.addWithException(new QueryBuilder(
							"INSERT INTO zdmemberlevel (ID,Name,Type,Discount,IsDefault,TreeLevel,Score,IsValidate,AddUser,AddTime,ModifyUser,ModifyTime) VALUES (31,'注册会员','用户',1,'Y',0,0,'Y','admin','2010-06-29 18:50:45',NULL,NULL)"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002049000001", "000020004900000001"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002049000002", "000020004900000002"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002049000003", "000020004900000003"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002049000004", "000020004900000004"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002049000005", "000020004900000005"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002049000006", "000020004900000006"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002049000007", "000020004900000008"));

		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002164000001", "000021006400000001"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002164000002", "000021006400000002"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002164000003", "000021006400000003"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002164000004", "000021006400000004"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002164000005", "000021006400000005"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002164000006", "000021006400000006"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002164000007", "000021006400000007"));

		tran
				.add(new QueryBuilder(
						"update ZCArticle set CatalogInnerCode=(select InnerCode from ZCCatalog where ID=ZCArticle.CatalogID)"));
		try {
			ZDConfigSchema config = new ZDConfigSchema();
			config.setType("System.Version");
			config.setName("当前版本");
			config.setValue("1.3.1.0");
			config.setAddTime(new Date());
			config.setAddUser("SYSTEM");
			tran.add(config, 6);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tran.commit();
	}

	public static void main(String[] args) {
		Transaction tran = new Transaction();

		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002049000001", "000020004900000001"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002049000002", "000020004900000002"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002049000003", "000020004900000003"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002049000004", "000020004900000004"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002049000005", "000020004900000005"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002049000006", "000020004900000006"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002049000007", "000020004900000008"));

		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002164000001", "000021006400000001"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002164000002", "000021006400000002"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002164000003", "000021006400000003"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002164000004", "000021006400000004"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002164000005", "000021006400000005"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002164000006", "000021006400000006"));
		tran.add(new QueryBuilder(
				"update ZCCatalog set InnerCode=? where InnerCode=?",
				"002164000007", "000021006400000007"));

		tran
				.add(new QueryBuilder(
						"update ZCArticle set CatalogInnerCode=(select InnerCode from ZCCatalog where ID=ZCArticle.CatalogID)"));

		tran.commit();
	}
}

/*
 * com.xdarkness.platform.pub.Patch JD-Core Version: 0.6.0
 */