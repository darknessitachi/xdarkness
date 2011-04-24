package com.xdarkness.misc;

import com.xdarkness.schema.ZDUserSchema;

public class RestorePassword extends GeneralTask {
	public void execute() {
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName("demo");
		user.fill();
		user.setPassword(XString.md5Hex("demo"));
		user.setStatus("1");
		user.update();
	}

	public String getCronExpression() {
		return "* * * * *";
	}

	public long getID() {
		return 200905251628L;
	}

	public String getName() {
		return "定时恢复密码，演示站专用";
	}

	public static void main(String[] args) {
		RestorePassword rp = new RestorePassword();
		rp.execute();
	}
}

/*
 * com.xdarkness.misc.RestorePassword JD-Core Version: 0.6.0
 */