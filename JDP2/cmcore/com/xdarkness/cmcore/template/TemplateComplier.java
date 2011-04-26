package com.xdarkness.cmcore.template;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.ArrayUtils;

import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.util.Errorx;
import com.xdarkness.framework.util.Treex;

public class TemplateComplier {
	public void complieFile(String templateFileName) {
		byte[] bs = FileUtil.readByte(templateFileName);
		String text = null;
		if (XString.isUTF8(bs)) {
			if (XString.hexEncode(ArrayUtils.subarray(bs, 0, 3)).equals(
					"efbbbf"))
				bs = ArrayUtils.subarray(bs, 3, bs.length);
			try {
				text = new String(bs, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			try {
				text = new String(bs, "GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		complieText(text);
	}

	public void complieText(String text) {
		TemplateParser parser = new TemplateParser(text);
		parser.parse();
		if (Errorx.hasError()) {
			System.out.println(XString.join(Errorx.getMessages(), "\n"));
			return;
		}

		Treex tree = parser.getTree();
		TemplateConfig config = parser.getConfig();

		System.out.println(tree);

		String[] messages = Errorx.getMessages();
		if (messages.length > 0)
			System.out.println(XString.join(messages, "\n"));
	}

	public static void main(String[] args) {
		TemplateComplier tc = new TemplateComplier();
		tc
				.complieFile("F:/Workspace_Product/ZCMS/UI/wwwroot/ZCMSDemo/template/kjindex.html");
	}
}

/*
 * com.xdarkness.cmcore.template.TemplateComplier JD-Core Version: 0.6.0
 */