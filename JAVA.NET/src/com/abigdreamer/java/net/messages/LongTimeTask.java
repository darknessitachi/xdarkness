package com.abigdreamer.java.net.messages;

import java.util.ArrayList;

import com.abigdreamer.java.net.User;
import com.abigdreamer.java.net.util.LogUtil;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;

public abstract class LongTimeTask extends Thread {
	private static Mapx map = new Mapx();

	private static long IDBase = System.currentTimeMillis();
	private static final int MaxListSize = 1000;
	private long id;
	private ArrayList list = new ArrayList();
	protected int percent;
	protected String currentInfo;
	protected ArrayList Errors = new ArrayList();
	private boolean stopFlag;
	private User.UserData user;
	private String type;
	private long stopTime = System.currentTimeMillis() + 1440000L;

	public static LongTimeTask createEmptyInstance() {
		return new LongTimeTask(false) {
			public void execute() {
			}
		};
	}

	public static LongTimeTask getInstanceById(long id) {
		return (LongTimeTask) map.get(new Long(id));
	}

	public static void removeInstanceById(long id) {
		synchronized (LongTimeTask.class) {
			map.remove(new Long(id));
		}
	}

	public static String cancelByType(String type) {
		String message = "没有相关的正在运行的任务!";
		LongTimeTask ltt = getInstanceByType(type);
		if (ltt != null) {
			ltt.stopTask();
			message = "任务己接收到中止命令，但还需要做一些清理工作，请稍等一会!";
		}
		return message;
	}

	public static LongTimeTask getInstanceByType(String type) {
		if (XString.isNotEmpty(type)) {
			Object[] ks = map.keyArray();
			Object[] vs = map.valueArray();
			long current = System.currentTimeMillis();
			for (int i = 0; i < map.size(); i++) {
				LongTimeTask ltt = (LongTimeTask) vs[i];
				if (type.equals(ltt.getType())) {
					if (current - ltt.stopTime > 60000L) {
						map.remove(ks[i]);
						return null;
					}
					return ltt;
				}
			}
		}
		return null;
	}

	public LongTimeTask() {
		this(true);
	}

	private LongTimeTask(boolean flag) {
		if (flag) {
			setName("LongTimeTask Thread");
			synchronized (LongTimeTask.class) {
				this.id = (IDBase++);
				map.put(new Long(this.id), this);
				clearStopedTask();
			}
		}
	}

	private void clearStopedTask() {
		synchronized (LongTimeTask.class) {
			long current = System.currentTimeMillis();
			Object[] ks = map.keyArray();
			Object[] vs = map.valueArray();
			for (int i = 0; i < map.size(); i++) {
				LongTimeTask ltt = (LongTimeTask) vs[i];
				if (current - ltt.stopTime > 60000L)
					map.remove(ks[i]);
			}
		}
	}

	public long getTaskID() {
		return this.id;
	}

	public void info(String message) {
		LogUtil.getLogger().info(message);
		this.list.add(message);
		if (this.list.size() > 1000)
			this.list.remove(0);
	}

	public String[] getMessages() {
		String[] arr = new String[this.list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = ((String) this.list.get(i));
		}
		this.list.clear();
		return arr;
	}

	public void run() {
		if (XString.isNotEmpty(this.type)) {
			LongTimeTask ltt = getInstanceByType(this.type);
			if ((ltt != null) && (ltt != this))
				return;
		}
		try {
			User.setCurrent(this.user);
			execute();
		} catch (StopThreadException ie) {
			interrupt();
		} finally {
			this.stopTime = System.currentTimeMillis();
		}
	}

	public abstract void execute();

	public boolean checkStop() {
		return this.stopFlag;
	}

	public void stopTask() {
		clearStopedTask();
		this.stopFlag = true;
	}

	public int getPercent() {
		return this.percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public void setCurrentInfo(String currentInfo) {
		this.currentInfo = currentInfo;
		LogUtil.info(currentInfo);
	}

	public String getCurrentInfo() {
		return this.currentInfo;
	}

	public void setUser(User.UserData user) {
		this.user = user;
	}

	public void addError(String error) {
		this.Errors.add(error);
	}

	public void addError(String[] errors) {
		for (int i = 0; i < errors.length; i++)
			this.Errors.add(errors[i]);
	}

	public String getAllErrors() {
		if (this.Errors.size() == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("共有" + this.Errors.size() + "个错误:<br>");
		for (int i = 0; i < this.Errors.size(); i++) {
			sb.append(i + 1);
			sb.append(": ");
			sb.append(this.Errors.get(i));
			sb.append("<br>");
		}
		return sb.toString();
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	LongTimeTask(boolean paramBoolean, LongTimeTask paramLongTimeTask) {
		this(paramBoolean);
	}
}