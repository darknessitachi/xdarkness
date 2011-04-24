package com.xdarkness.bbs;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xdarkness.bbs.admin.ForumScore;
import com.xdarkness.cms.pub.PubFun;
import com.xdarkness.platform.page.UserLogPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCForumAttachmentSchema;
import com.xdarkness.schema.ZCForumGroupSchema;
import com.xdarkness.schema.ZCForumMemberSchema;
import com.xdarkness.schema.ZCForumSchema;
import com.xdarkness.schema.ZCPostSchema;
import com.xdarkness.schema.ZCThemeSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class PostAdd extends Ajax {
	private static Mapx FACE_MAP = new Mapx();

	private static Pattern[] patterns = {
			Pattern.compile("<script.*?>(.*?)</script>", 2),
			Pattern.compile("\\[b\\](.+?)\\[/b\\]"),
			Pattern.compile("\\[i\\](.+?)\\[/i\\]"),
			Pattern.compile("\\[u\\](.+?)\\[/u\\]"),
			Pattern.compile("\\[code\\](.+?)\\[/code\\]"),
			Pattern.compile("\\[img\\](.+?)\\[/img\\]"),
			Pattern.compile("\\[url\\](.+?)\\[/url\\]") };

	private static String[] replaceMents = {
			"",
			"",
			"<font style=\"font-weight:bolder;\">",
			"</font>",
			"<font style=\"font-style:italic\">",
			"</font>",
			"<font style=\"text-decoration:underline;\">",
			"</font>",
			"<div style=\"border-style:1px;border-color:blue;\">代码：<hr/><font color=\"green\">",
			"</font></div>", "<img border=0 src=\"http://", "\" />",
			"<a href=\"http://", "\">", "</a>" };

	static {
		FACE_MAP.put(":)", "<img border=0 src='Images/face/smile.gif' />");
		FACE_MAP.put(":lol", "<img border=0 src='Images/face/lol.gif' />");
		FACE_MAP.put(":hug:", "<img border=0 src='Images/face/hug.gif' />");
		FACE_MAP.put(":victory:",
				"<img border=0 src='Images/face/victory.gif' />");
		FACE_MAP.put(":time:", "<img border=0 src='Images/face/time.gif' />");
		FACE_MAP.put(":kiss:", "<img border=0 src='Images/face/kiss.gif' />");
		FACE_MAP.put(":handshake",
				"<img border=0 src='Images/face/handshake.gif' />");
		FACE_MAP.put(":call:", "<img border=0 src='Images/face/call.gif' />");
		FACE_MAP.put(":loveliness:",
				"<img border=0 src='Images/face/loveliness.gif' />");
		FACE_MAP.put(":Q", "<img border=0 src='Images/face/mad.gif' />");
		FACE_MAP.put(":L", "<img border=0 src='Images/face/sweat.gif' />");
		FACE_MAP.put(":(", "<img border=0 src='Images/face/sad.gif' />");
		FACE_MAP.put(":D", "<img border=0 src='Images/face/biggrin.gif' />");
		FACE_MAP.put("cry", "<img border=0 src='Images/face/cry.gif' />");
		FACE_MAP.put(":@", "<img border=0 src='Images/face/huffy.gif' />");
		FACE_MAP.put(":o", "<img border=0 src='Images/face/shocked.gif' />");
		FACE_MAP.put(":P", "<img border=0 src='Images/face/tongue.gif' />");
		FACE_MAP.put(":$", "<img border=0 src='Images/face/shy.gif' />");
		FACE_MAP.put(";P", "<img border=0 src='Images/face/titter.gif' />");
		FACE_MAP.put(":funk:", "<img border=0 src='Images/face/funk.gif' />");
	}

	public static String processMsg(String msg) {
		StringBuffer sb = new StringBuffer();
		msg = msg.replaceAll(">", "&gt;");
		msg = msg.replaceAll("<", "&lt;");
		int i = 0;
		for (int j = 0; i < patterns.length; j += 2) {
			Matcher m = patterns[i].matcher(msg);
			while (m.find()) {
				String temp = replaceMents[j] + m.group(1)
						+ replaceMents[(j + 1)];
				if (i == patterns.length - 1) {
					temp = temp + m.group(1) + replaceMents[(j + 2)];
				}
				m.appendReplacement(sb, temp);
				m.appendTail(sb);
				msg = sb.toString();
				sb.setLength(0);
			}
			i++;
		}

		sb = new StringBuffer(msg);
		Object[] keys = FACE_MAP.keyArray();
		for ( i = 0; i < keys.length; i++) {
			int fromIndex = 0;
			String key = (String) keys[i];
			int p;
			while ((p = sb.indexOf(key, fromIndex)) >= 0) {
				sb.replace(p, p + key.length(), FACE_MAP.getString(keys[i]));
				fromIndex = p + FACE_MAP.getString(keys[i]).length();
			}
		}
		return sb.toString();
	}

	public void add() {
		if (ForumUtil.isNotReplyPost($V("SiteID"), $V("ForumID"))) {
			this.response.setLogInfo(0, "您没有权利回复！");
			return;
		}
		ForumScore forumScore = new ForumScore($V("SiteID"));
		ForumPriv priv = new ForumPriv($V("SiteID"));
		Transaction trans = new Transaction();
		ZCPostSchema post = new ZCPostSchema();
		ZCThemeSchema theme = new ZCThemeSchema();
		ZCForumMemberSchema user = new ZCForumMemberSchema();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		ZCForumSchema forum = new ZCForumSchema();

		post.setFirst("N");
		post.setValue(this.request);
		post.setID(NoUtil.getMaxID("PostID"));
		post.setSiteID($V("SiteID"));
		post.setFirst("N");
		post.setMessage(XString.htmlEncode(getQuoteContent($V("PostID"))
				+ processMsg($V("Message"))));
		theme.setID(post.getThemeID());
		theme.fill();
		forum = ForumCache.getForum(theme.getForumID());
		post.setAddUser(User.getUserName());
		post.setAddTime(new Date());
		post.setInvisible("Y");
		post.setVerifyFlag(forum.getVerify().equals("Y") ? "N" : "Y");
		user.setUserName(post.getAddUser());
		user.fill();
		userGroup = ForumCache.getGroup(user.getUserGroupID());
		if (userGroup.getVerify().equals("Y")) {
			post.setVerifyFlag("Y");
		}

		trans.add(user, OperateType.UPDATE);

		if (priv.hasPriv("Verify")) {
			post.setVerifyFlag("Y");
		}
		if (post.getVerifyFlag().equals("Y")) {
			post.setLayer(getMAXLayer(post.getThemeID()) + 1L);
			forum.setPostCount(forum.getPostCount() + 1);
			user.setReplyCount(user.getReplyCount() + 1L);
			forum.setLastPost(post.getSubject());
			forum.setLastPoster(post.getAddUser());
			forum.setLastThemeID(theme.getID());
			user.setForumScore(user.getForumScore() + forumScore.PublishPost);
			ForumUtil.userGroupChange(user);
			theme.setReplyCount(theme.getReplyCount() + 1);
			theme.setLastPostTime(new Date());
			theme.setOrderTime(new Date());
		}
		trans.add(post, OperateType.INSERT);
		trans.add(theme, OperateType.UPDATE);
		trans.add(forum, OperateType.UPDATE);
		if ($V("file").length() > 0) {
			String[] Attachments = $V("file").split(",");
			String[] indexs = $V("indexs").split(",");
			for (int i = 0; i < Attachments.length; i++) {
				ZCForumAttachmentSchema attachment = new ZCForumAttachmentSchema();
				attachment.setID(NoUtil.getMaxID("ForumAttachmentID"));
				attachment.setPostID(post.getID());
				attachment.setSiteID($V("SiteID"));
				String suffix = $V("file" + indexs[i]).substring(
						$V("file" + indexs[i]).lastIndexOf(".") + 1);
				if (PubFun.isAllowExt(suffix, "Attach")) {
					attachment.setType("attach");
				} else if (PubFun.isAllowExt(suffix, "Image")) {
					attachment.setType("image");
				} else if (PubFun.isAllowExt(suffix, "Audio")) {
					attachment.setType("audio");
				} else if (PubFun.isAllowExt(suffix, "Video")) {
					attachment.setType("video");
				} else {
					this.response.setLogInfo(0, "不允许上传该文件类型");
					return;
				}
				attachment.setSuffix(suffix);
				attachment.setName($V("file" + indexs[i]).substring(
						$V("file" + indexs[i]).lastIndexOf("\\") + 1));
				String[] file = Attachments[i].split("#");
				attachment.setPath(file[0]);
				attachment.setAttSize(file[1]);
				attachment.setDownCount(0L);
				attachment.setAddUser(User.getUserName());
				attachment.setAddTime(new Date());
				trans.add(attachment, OperateType.INSERT);
			}
		}
		if (trans.commit()) {
			if (post.getVerifyFlag().equals("Y"))
				this.response.setLogInfo(1, "回复成功");
			else
				this.response.setLogInfo(1, "管理员设置了审核机制，请等待审核!");
		} else
			this.response.setLogInfo(0, "回复失败!");
	}

	public static Mapx init(Mapx params) {
		String ForumID = params.getString("ForumID");
		String ThemeID = params.getString("ThemeID");
		String SiteID = params.getString("SiteID");
		ZCThemeSchema theme = new ZCThemeSchema();
		theme.setID(ThemeID);
		theme.setForumID(ForumID);
		theme.fill();
		Mapx map = theme.toMapx();
		ZCForumSchema forum = ForumCache.getForum(ForumID);

		map.put("SiteID", params.getString("SiteID"));
		map.put("Name", forum.getName());
		map.put("ForumID", forum.getID());
		map.put("AddUser", User.getUserName());
		map.put("Priv", ForumUtil.initPriv(ForumID, SiteID));
		map.put("BBSName", ForumUtil.getBBSName(SiteID));
		return map;
	}

	public static Mapx initAddDialog(Mapx params) {
		String PostID = params.getString("ID");
		String SiteID = params.getString("SiteID");
		if (!XString.isEmpty(PostID)) {
			ZCPostSchema post = new ZCPostSchema();
			post.setID(PostID);
			post.fill();
			params = post.toMapx();
			String subject = "引用于" + post.getAddUser() + "的回复";
			params.put("SiteID", SiteID);
			params.put("AddUser", User.getUserName());
			params.put("subject", subject);
			params.put("Priv", ForumUtil.initPriv(params.getString("SiteID")));
		}
		return params;
	}

	public static Mapx initEdit(Mapx params) {
		String PostID = params.getString("ID");

		if (!XString.isEmpty(PostID)) {
			ZCPostSchema post = new ZCPostSchema();
			post.setID(PostID);
			post.fill();
			String message = post.getMessage();
			if (message.startsWith("<div class='quote'>")) {
				String subMessage = message.substring(message
						.lastIndexOf("</div>"));
				post.setMessage(subMessage);
			}

			params = post.toMapx();
			if (post.getFirst().equals("Y")) {
				params.put("verify", "NotNull");
			}
		}
		return params;
	}

	public void editPost() {
		Transaction trans = new Transaction();
		ZCPostSchema post = new ZCPostSchema();
		ZCThemeSchema theme = new ZCThemeSchema();
		post.setID($V("ID"));
		post.fill();

		if (!ForumUtil.isEditPost(post.getSiteID()+"", post.getForumID()+"", post
				.getAddUser())) {
			this.response.setLogInfo(0, "您没有编辑帖子的权限");
			return;
		}
		theme.setID(post.getThemeID());
		theme.fill();
		String message = post.getMessage();
		post.setValue(this.request);

		if (message.startsWith("<div class='quote'>")) {
			StringBuffer newMessage = new StringBuffer(message.substring(0,
					message.lastIndexOf("</div>") + 6));
			newMessage.append(post.getMessage());
			post.setMessage(newMessage.toString());
		}
		post.setMessage(XString.htmlEncode(processMsg(post.getMessage())));
		if (post.getFirst().equals("Y")) {
			theme.setSubject(post.getSubject());
		}
		trans.add(post, OperateType.UPDATE);
		trans.add(theme, OperateType.UPDATE);
		if (trans.commit()) {
			UserLogPage.log("Forum", "EditPost", "编辑用户" + post.getAddUser()
					+ "的帖子成功", this.request.getClientIP());
			this.response.setLogInfo(1, "修改成功");
		} else {
			UserLogPage.log("Forum", "EditPost", "编辑用户" + post.getAddUser()
					+ "的帖子失败", this.request.getClientIP());
			this.response.setLogInfo(0, "修改失败");
		}
	}

	private long getMAXLayer(long ThemeID) {
		QueryBuilder qb = new QueryBuilder(
				"select Layer from ZCPost where ThemeID=? order by Layer desc",
				ThemeID);
		long layer = qb.executePagedDataTable(1, 0).getLong(0, 0);
		return layer;
	}

	private String getQuoteContent(String PostID) {
		ZCPostSchema post = new ZCPostSchema();
		post.setID(PostID);
		post.fill();
		String quote = post.getMessage();
		if (quote.indexOf("<h5>引用</h5>") >= 0) {
			quote = XString.replaceAllIgnoreCase(quote, "<h5>引用</h5>", "");
		}
		String content = "<div class='quote'><div class='blockquote'><h5>引用</h5>";
		content = content + "<i>" + post.getAddUser() + "</i>发表于"
				+ post.getAddTime() + "<br>";
		quote = content + quote + "</div></div>";
		return quote;
	}
}

/*
 * com.xdarkness.bbs.PostAdd JD-Core Version: 0.6.0
 */