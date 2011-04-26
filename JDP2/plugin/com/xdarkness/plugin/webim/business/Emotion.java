package com.xdarkness.plugin.webim.business;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.xdarkness.framework.Config;
import com.xdarkness.framework.util.ServletUtil;

public class Emotion {
	// 表情格式
	private static final String emotionFormat = ".png.gif.jpg.bmp";

	/**
	 * 表情图标路径
	 * 
	 * @return
	 */
	public static String getEmotionIcosPath() {
		return "include/images/emoticons";
	}

	/**
	 * 获取表情图标
	 * 
	 * @return
	 */
	public static List<String> GetEmotionIcos() {
		File emotionFolder = new File(Config.getContextRealPath()
				+ getEmotionIcosPath());
		File[] emotionsFiles = emotionFolder.listFiles();

		List<String> emotionList = new ArrayList<String>();

		for (File file : emotionsFiles) {

			String ext = ServletUtil.getUrlExtension(file.getAbsolutePath());
			if (emotionFormat.indexOf(ext) != -1) {
				emotionList.add(file.getName());
			}
		}

		return emotionList;
	}

	/**
	 * 过滤发送内容中含有表情的信息
	 * 
	 * @param content
	 *            发送内容
	 * @return
	 */
	public static String FilterContent(String content) {
		List<String> emotions = GetEmotionIcos();

		for (String emotion : emotions) {
			content = content.replace("{{{" + emotion + "}}}",
					"<img alt=\"\" src=\"../" + getEmotionIcosPath() + "/{"
							+ emotion + "}\" />");
		}

		return content;
	}
}
