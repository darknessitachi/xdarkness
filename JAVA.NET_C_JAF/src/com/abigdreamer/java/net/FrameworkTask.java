package com.abigdreamer.java.net;

import java.io.File;

import com.abigdreamer.java.net.io.FileUtil;
import com.abigdreamer.java.net.schedule.GeneralTask;

public class FrameworkTask extends GeneralTask {
	public void execute() {
		File dir = new File(Config.getContextRealPath() + "WEB-INF/cache/");
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			File f = fs[i];
			if (f.isFile())
				FileUtil.delete(f);
		}
	}

	public long getID() {
		return 20080315112344L;
	}

	public String getName() {
		return "Framework定时任务";
	}
}
