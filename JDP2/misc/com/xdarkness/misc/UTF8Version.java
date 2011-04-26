package com.xdarkness.misc;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import com.xdarkness.framework.io.FileUtil;

public class UTF8Version {
	static Pattern p1 = Pattern.compile("charset\\s*\\=\\s*gbk", 34);

	static Pattern p2 = Pattern.compile("charset\\s*\\=\\s*gb2312", 34);

	private String target = null;

	private ArrayList list = new ArrayList();

	private long lastConvertTime = 0L;
	private String currentBase;
	private String currentDest;

	public static void main(String[] args) {
		long t = System.currentTimeMillis();
		UTF8Version uv = new UTF8Version();
		uv.setTarget("F:/WorkSpace_Product/ZCMS1.3UTF8/");

		uv.addDir("F:/WorkSpace_Product/ZCMS/Java", "Java");
		uv.addDir("F:/WorkSpace_Product/ZCMS/UI", "UI");
		uv.addDir("F:/WorkSpace_Product/ZCMS/UI/wwwroot/ZCMSDemo",
				"UI/wwwroot/ZCMSDemo");
		uv.addDir("F:/WorkSpace_Platform/Framework/Java", "Java");

		uv.convert();
		System.out.println("扫描改动共耗时:" + (System.currentTimeMillis() - t));
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void addDir(String src, String dest) {
		this.list.add(new String[] { src, dest });
	}

	private void cleanDeleted(String path) {
		File f = new File(path);
		File[] fs = f.listFiles();
		for (int i = 0; (fs != null) && (i < fs.length); i++)
			;
	}

	public void convert() {
		long current = System.currentTimeMillis();
		File f = new File(this.target + "/convert.lock");
		if (f.exists()) {
			this.lastConvertTime = Long.parseLong(FileUtil.readText(f).trim());
		}
		for (int i = 0; i < this.list.size(); i++) {
			String[] arr = (String[]) this.list.get(i);
			this.currentBase = XString.replaceEx(arr[0], "\\", "/");
			this.currentDest = arr[1];
			convertDir(arr[0]);
		}
		FileUtil.writeText(this.target + "/convert.lock", current+"");
		cleanDeleted(this.target);
	}

	private void convertDir(String src) {
		File f = new File(src);
		File[] fs = f.listFiles();

		for (int i = 0; (fs != null) && (i < fs.length); i++) {
			f = fs[i];
			String path = XString.replaceEx(f.getAbsolutePath(), "\\", "/");
			path = path.substring(this.currentBase.length());
			new File(this.target + this.currentDest).mkdirs();
			String dest = this.target + this.currentDest + path;
			String name = f.getName().toLowerCase();
			if (name.equals(".svn")) {
				continue;
			}
			if (f.isDirectory()) {
				FileUtil.mkdir(dest);
				if (name.equals("classes")) {
					continue;
				}
				if (name.equals("classes")) {
					continue;
				}
				if ((dest.indexOf("wwwroot") >= 0)
						&& (this.currentBase.indexOf("wwwroot") < 0)) {
					continue;
				}
				if (dest.indexOf("WEB-INF/cache") >= 0) {
					continue;
				}
				if (dest.indexOf("WEB-INF/backup") >= 0) {
					continue;
				}
				if (f.getName().equals(".svn")) {
					continue;
				}
				if (f.getName().equals("classes")) {
					continue;
				}
				if (f.getName().toLowerCase().equals("tools")) {
					continue;
				}
				if (f.getName().toLowerCase().equals("test")) {
					continue;
				}
				if (f.getName().toLowerCase().equals("project")) {
					continue;
				}
				if (dest.indexOf("WEB-INF/data/index") >= 0) {
					continue;
				}
				if (f.getName().equals("logs")) {
					continue;
				}
				convertDir(f.getAbsolutePath());
			} else {
				if (f.isFile()) {
					File destFile = new File(dest);
					if ((destFile.exists())
							&& (f.lastModified() < this.lastConvertTime)) {
						continue;
					}
				}
				if ((dest.indexOf("wwwroot") > 0) && (dest.endsWith(".shtml"))) {
					continue;
				}
				if ((name.endsWith(".java")) || (name.endsWith(".xml"))) {
					String txt = FileUtil.readText(f, "GBK");
					if ((name.endsWith(".xml")) && (txt.indexOf("UTF-8") > 0)) {
						txt = FileUtil.readText(f, "UTF-8");
					}

					if (name.equals("web.xml")) {
						txt = XString.replaceEx(txt,
								"<page-encoding>GBK</page-encoding>",
								"<page-encoding>UTF-8</page-encoding>");
					}
					if (name.equals("constant.java"))
						txt = XString.replaceEx(txt,
								"GlobalCharset = \"GBK\";",
								"GlobalCharset = \"UTF-8\";");
					try {
						txt = new String(XString.GBKToUTF8(txt), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					FileUtil.writeText(dest, txt, "UTF-8", name
							.endsWith(".java"));
				} else if ((name.endsWith(".html"))
						|| (name.endsWith(".shtml")) || (name.endsWith(".htm"))
						|| (name.endsWith(".jsp")) || (name.endsWith(".js"))
						|| (name.endsWith(".css"))) {
					byte[] bs = FileUtil.readByte(f);
					if (XString.isUTF8(bs)) {
						FileUtil.copy(f, dest);
					} else {
						String txt = null;
						try {
							txt = new String(bs, "GBK");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
						}
						txt = p1.matcher(txt).replaceAll("charset=utf-8");
						txt = p2.matcher(txt).replaceAll("charset=utf-8");
						txt = XString.replaceEx(txt, "\"GBK\"", "\"UTF-8\"");
						try {
							txt = new String(XString.GBKToUTF8(txt), "UTF-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						FileUtil.writeText(dest, txt, "UTF-8");
					}
				} else if (name.equalsIgnoreCase("charset.config")) {
					String txt = FileUtil.readText(f, "UTF-8");
					txt = XString.replaceEx(txt, "GBK", "UTF-8");
					FileUtil.writeText(dest, txt, "UTF-8");
				} else {
					FileUtil.copy(f, dest);
				}
				System.out.println(dest);
			}
		}
	}
}

/*
 * com.xdarkness.misc.UTF8Version JD-Core Version: 0.6.0
 */