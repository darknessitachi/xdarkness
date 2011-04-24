package com.xdarkness.bbs;

import com.xdarkness.schema.ZCForumGroupSchema;
import com.xdarkness.schema.ZCForumMemberSchema;
import com.xdarkness.schema.ZDMemberPersonInfoSchema;
import com.xdarkness.schema.ZDMemberSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class ControlPanel extends Ajax {
	public static Mapx init(Mapx params) {
		String SiteID = params.getString("SiteID");
		if (XString.isEmpty(User.getUserName())) {
			params.put("redirect", "<script>window.location='Index.jsp?SiteID="
					+ SiteID + "'</script>");
			return params;
		}
		ForumPriv priv = new ForumPriv(SiteID);
		if (!priv.hasPriv("AllowPanel")) {
			params.put("redirect", "<script>window.location='Index.jsp?SiteID="
					+ SiteID + "'</script>");
			return params;
		}
		ZCForumMemberSchema forumUser = new ZCForumMemberSchema();
		ZCForumGroupSchema group = new ZCForumGroupSchema();
		ZDMemberPersonInfoSchema personInfo = new ZDMemberPersonInfoSchema();
		ZDMemberSchema member = new ZDMemberSchema();
		forumUser.setUserName(User.getUserName());
		forumUser.fill();
		personInfo.setUserName(forumUser.getUserName());
		personInfo.fill();
		member.setUserName(User.getUserName());
		member.fill();
		group.setID(forumUser.getUserGroupID());
		group.fill();
		params.putAll(member.toMapx());
		params.putAll(forumUser.toMapx());
		Mapx map = new Mapx();
		map.put("N", "使用组头像");
		map.put("Y", "使用自定义头像");
		params.put("UserImageOption", HtmlUtil.mapxToRadios("UserImageOption",
				map, forumUser.getUseSelfImage()));
		params.put("NickName", forumUser.getNickName());
		params.put("UserName", User.getUserName());
		params.put("RegTime", DateUtil.toDateTimeString(member.getRegTime()));
		params.put("LastLoginTime", DateUtil.toDateTimeString(member
				.getLastLoginTime()));
		params.put("group", group.getName());
		String headImage = forumUser.getHeadImage();
		if (headImage.startsWith("../")) {
			headImage = XString.replaceEx(headImage, "../", "");
		}
		params.put("HeadImage", headImage);
		params.put("Priv", ForumUtil.initPriv(SiteID));
		params.put("Birthday", personInfo.getBirthday());
		params.put("QQ", personInfo.getQQ());
		params.put("MSN", personInfo.getMSN());
		params.put("Tel", personInfo.getTel());
		params.put("Mobile", personInfo.getMobile());
		params.put("Address", personInfo.getAddress());
		params.put("ZipCode", personInfo.getZipCode());
		params.put("SiteID", SiteID);
		params.put("BBSName", ForumUtil.getBBSName(SiteID));

		return params;
	}

	public void perInfoSave() {
		ZCForumMemberSchema forumUser = new ZCForumMemberSchema();
		forumUser.setUserName($V("UserName"));
		forumUser.fill();
		forumUser.setNickName($V("NickName"));

		Transaction trans = new Transaction();
		trans.add(forumUser, OperateType.UPDATE);
		if (trans.commit())
			this.response.setLogInfo(1, "修改成功!");
		else
			this.response.setLogInfo(0, "修改失败!");
	}

	public void selfSettingSave() {
		ForumPriv priv = new ForumPriv($V("SiteID"));
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		member.setUserName($V("UserName"));
		member.fill();
		member.setForumSign($V("ForumSign"));
		member.setNickName($V("NickName"));
		member.setUseSelfImage($V("UserImageOption"));
		if (($V("UserImageOption").equals("1"))
				&& (!priv.hasPriv("AllowHeadImage"))) {
			this.response.setLogInfo(0, "您所在用户组不允许使用自定义头像!");
			return;
		}

		Transaction trans = new Transaction();
		trans.add(member, OperateType.UPDATE);
		if (trans.commit())
			this.response.setLogInfo(1, "修改成功!");
		else
			this.response.setLogInfo(0, "修改失败!");
	}

	public void doSave() {
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		member.setUserName($V("UserName"));
		member.fill();
		member.setValue(this.request);
		if (member.update())
			this.response.setLogInfo(1, "保存成功");
		else
			this.response.setLogInfo(0, "保存失败");
	}

	public void modifyPassword() {
		ZDMemberSchema forumUser = new ZDMemberSchema();
		forumUser.setUserName($V("UserName"));
		forumUser.fill();
		if (XString.md5Hex($V("Password").trim()).equals(
				forumUser.getPassword())) {
			String psd1 = $V("NewPwd1").trim();
			String psd2 = $V("NewPwd2").trim();
			if (psd1.equals(psd2)) {
				forumUser.setPassword(XString.md5Hex($V("NewPwd1")));
			} else {
				this.response.setLogInfo(1, "两次密码输入不一致!");
				return;
			}
		} else {
			this.response.setLogInfo(1, "原密码输入错误!");
			return;
		}

		Transaction trans = new Transaction();
		trans.add(forumUser, OperateType.UPDATE);
		if (trans.commit())
			this.response.setLogInfo(1, "修改成功!");
		else
			this.response.setLogInfo(0, "修改失败!");
	}
}

/*
 * com.xdarkness.bbs.ControlPanel JD-Core Version: 0.6.0
 */