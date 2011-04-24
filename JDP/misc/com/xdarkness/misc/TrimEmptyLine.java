package com.xdarkness.misc;

import java.io.File;

import com.xdarkness.framework.io.FileUtil;

public class TrimEmptyLine {
	public static void main(String[] args) {
		String path = "F:/Workspace_Platform/Platform/UI/Search/";
		File[] fs = new File(path).listFiles();
		for (int i = 0; i < fs.length; i++) {
			File f = fs[i];
			if (!f.getName().endsWith("jsp")) {
				continue;
			}
			String content = FileUtil.readText(f);
			String[] arr = content.split("\\n");
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < arr.length; j++) {
				if (XString.isEmpty(arr[j].trim())) {
					if ((j < arr.length - 1)
							&& (XString.isEmpty(arr[(j + 1)].trim()))) {
						sb.append(arr[j].trim());
						sb.append("\n");
					}
				} else {
					sb.append(XString.rightTrim(arr[j]));
					sb.append("\n");
				}
			}
			FileUtil.writeText(f.getAbsolutePath(), sb.toString());
		}
	}
}

/*
 * com.xdarkness.misc.TrimEmptyLine JD-Core Version: 0.6.0
 */