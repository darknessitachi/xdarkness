package com.xdarkness.framework.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import com.xdarkness.framework.Config;
import com.xdarkness.framework.Constant;
import com.xdarkness.framework.io.FileUtil;

public class Mail {
	public static final String SUCCESS = "success";
	public static final String FAILED_SEND = "failed_send";
	public static final String FAILED_WRONG = "failed_wrong";
	public static final String FAILED_EMPTY_TOUSER = "failed_empty_user";
	public static final String FAILED_EMPTY_CONTENT = "failed_empty_content";
	public static final String FAILED_EMPTY_URL = "failed_empty_url";

	public static String sendSimpleEmail(Mapx map) {
		String host = Config.getValue("mail.host");
		String userName = Config.getValue("mail.username");
		String password = Config.getValue("mail.password");
		if (map == null)
			return "failed_wrong";
		if (XString.isEmpty(map.getString("ToUser")))
			return "failed_empty_user";
		if (XString.isEmpty(map.getString("Content"))) {
			return "failed_empty_content";
		}

		String realName = map.getString("RealName");
		if (XString.isEmpty(realName)) {
			realName = map.getString("ToUser");
		}

		String subject = map.getString("Subject");
		if (XString.isEmpty(subject)) {
			subject = "来自" + realName + "的系统邮件";
		}

		SimpleEmail email = new SimpleEmail();
		try {
			email.setAuthentication(userName, password);
			email.setHostName(host);
			email.addTo(map.getString("ToUser"), realName);
			email.setFrom(userName, password);
			email.setSubject(subject);
			email.setContent(map.getString("Content"), "text/html;charset="
					+ Constant.GlobalCharset);
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
			return "failed_send";
		}
		return "success";
	}

	public String sendHtmlMail(Mapx map) {
		String host = Config.getValue("mail.host");
		String userName = Config.getValue("mail.username");
		String password = Config.getValue("mail.password");
		if (map == null)
			return "failed_wrong";
		if (XString.isEmpty(map.getString("ToUser")))
			return "failed_empty_user";
		if (XString.isEmpty(map.getString("URL"))) {
			return "failed_empty_url";
		}

		String realName = map.getString("RealName");
		if (XString.isEmpty(realName)) {
			realName = map.getString("ToUser");
		}

		String subject = map.getString("Subject");
		if (XString.isEmpty(subject)) {
			subject = "来自" + realName + "的系统邮件";
		}

		String htmlContent = FileUtil.readURLText(map.getString("URL"));
		HtmlEmail email = new HtmlEmail();
		try {
			email.setAuthentication(userName, password);
			email.addTo(map.getString("ToUser"), realName);
			email.setFrom(host, userName);
			email.setSubject(subject);
			email.setHtmlMsg(htmlContent);
			email.send();
		} catch (EmailException e) {
			return "failed_send";
		}
		return "success";
	}
}

/*
 * com.xdarkness.framework.utility.Mail JD-Core Version: 0.6.0
 */