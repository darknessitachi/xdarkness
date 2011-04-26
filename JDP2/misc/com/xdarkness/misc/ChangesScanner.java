package com.xdarkness.misc;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xdarkness.framework.io.FileUtil;

public class ChangesScanner {
	private String target;
	private long date;
	private String prefix;

	public static void main(String[] args) {
		ChangesScanner cs = new ChangesScanner("2010-04-27", "F:/LastModified/");
		cs.scan();
	}

	public ChangesScanner(String date, String path) {
		this.target = path;
		this.date = DateUtil.parseDateTime(date).getTime();
	}

	public void scan() {
		FileUtil.delete(this.target);
		this.prefix = Config.getContextRealPath();
		if (this.prefix.endsWith("/")) {
			this.prefix = this.prefix.substring(0, this.prefix.length() - 1);
		}
		this.prefix = this.prefix.substring(0, this.prefix.lastIndexOf('/'));
		File f = new File(this.prefix);
		scanOneDir(f);

		String txt = FileUtil.readText(this.prefix + "/.project");
		Pattern p = Pattern.compile("<location>(.*?)<\\/location>", 34);
		Matcher m = p.matcher(txt);
		int lastIndex = 0;
		while (m.find(lastIndex)) {
			String location = m.group(1);
			if (location.toLowerCase().indexOf("framework") > 0) {
				scanOneDir(new File(location));
				break;
			}
			lastIndex = m.end();
		}
	}

	public void scanOneDir(File f) {
		if (f.isFile()) {
			if (f.lastModified() > this.date) {
				if (f.getName().endsWith(".shtml")) {
					return;
				}
				String path = f.getAbsolutePath();
				path = path.replace('\\', '/');
				path = path.substring(0, path.lastIndexOf('/'));
				path = path.substring(this.prefix.length());
				path = this.target + path + "/";
				File tf = new File(path);
				if (!tf.exists()) {
					tf.mkdirs();
				}
				FileUtil.copy(f, path + f.getName());
				System.out.println(path + f.getName());
			}
		} else {
			if (f.getName().equals(".svn")) {
				return;
			}
			if (f.getName().equals("classes")) {
				return;
			}
			if (f.getAbsolutePath().indexOf("WEB-INF/data") >= 0) {
				return;
			}
			if (f.getAbsolutePath().indexOf("WEB-INF/index") >= 0) {
				return;
			}
			if (f.getName().equals("logs")) {
				return;
			}
			File[] fs = f.listFiles();
			for (int i = 0; i < fs.length; i++)
				scanOneDir(fs[i]);
		}
	}
}

/*
 * com.xdarkness.misc.ChangesScanner JD-Core Version: 0.6.0
 */