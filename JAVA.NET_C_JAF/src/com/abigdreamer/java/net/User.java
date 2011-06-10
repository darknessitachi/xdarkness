package com.abigdreamer.java.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.abigdreamer.java.net.clustering.Clustering;
import com.abigdreamer.java.net.jaf.WebConfig;
import com.abigdreamer.java.net.util.LogUtil;
import com.abigdreamer.java.net.util.Mapx;

public class User {
	public static final String ONLINE = "online";
	public static final String OFFLINE = "offline";
	public static final String BUSY = "busy";
	public static final String AWAY = "away";
	public static final String HIDDEN = "hidden";
	public static final Mapx USER_STATUS_MAP = new Mapx();
	private static ThreadLocal UserLocal;

	static {
		USER_STATUS_MAP.put("online", "在线");
		USER_STATUS_MAP.put("offline", "下线");
		USER_STATUS_MAP.put("busy", "忙碌");
		USER_STATUS_MAP.put("away", "离开");
		USER_STATUS_MAP.put("hidden", "隐身");

		UserLocal = new ThreadLocal();
	}

	public static String getUserName() {
		return getCurrent().UserName;
	}

	public static void setUserName(String UserName) {
		getCurrent().setUserName(UserName);
	}

	public static String getRealName() {
		return getCurrent().RealName;
	}

	public static void setRealName(String RealName) {
		getCurrent().RealName = RealName;
		if (Config.isDebugMode())
			cacheUser(getCurrent());
	}

	public static String getBranchInnerCode() {
		return getCurrent().BranchInnerCode;
	}

	public static void setBranchInnerCode(String BranchInnerCode) {
		getCurrent().BranchInnerCode = BranchInnerCode;
		if (Config.isDebugMode())
			cacheUser(getCurrent());
	}

	public static String getType() {
		return getCurrent().Type;
	}

	public static void setType(String Type) {
		getCurrent().Type = Type;
		if (Config.isDebugMode())
			cacheUser(getCurrent());
	}

	public static String getStatus() {
		return getCurrent().Status;
	}

	public static void setStatus(String Status) {
		getCurrent().Status = Status;
		if (Config.isDebugMode())
			cacheUser(getCurrent());
	}

	public static int getValueCount() {
		return getCurrent().Map.size();
	}

	public static Object getValue(Object key) {
		return getCurrent().Map.get(key);
	}

	public static Mapx getValues() {
		return getCurrent().Map;
	}

	public static void setValue(Object key, Object value) {
		Mapx map = getCurrent().Map;
		synchronized (map) {
			map.put(key, value);
		}
		if (Config.isDebugMode())
			cacheUser(getCurrent());
	}

	public static boolean isLogin() {
		return getCurrent().isLogin;
	}

	public static void setLogin(boolean isLogin) {
		if (isLogin) {
			if (!getCurrent().isLogin) {
				WebConfig.LoginUserCount += 1;
			}
		} else if (getCurrent().isLogin) {
			WebConfig.LoginUserCount -= 1;
		}

		getCurrent().isLogin = isLogin;
		if (Config.isDebugMode())
			cacheUser(getCurrent());
	}

	public static void setCurrent(UserData user) {
		UserLocal.set(user);
		if (Config.isDebugMode())
			cacheUser(user);
	}

	public static UserData getCurrent() {
		return (UserData) UserLocal.get();
	}

	protected static void cacheUser(UserData u) {
		if ((getCurrent() != u) || (getCurrent() == null)) {
			return;
		}
		if (Clustering.isClustering()) {
			Clustering.cacheUser(u);
		}
		if (Config.isDebugMode())
			try {
				FileOutputStream f = new FileOutputStream(Config
						.getContextRealPath()
						+ "WEB-INF/cache/" + u.getSessionID());
				ObjectOutputStream s = new ObjectOutputStream(f);
				s.writeObject(u);
				s.flush();
				s.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	protected static UserData getCachedUser(String sessionID) {
		try {
			File in = new File(Config.getContextRealPath() + "WEB-INF/cache/"
					+ sessionID);
			if (in.exists()) {
				ObjectInputStream s = new ObjectInputStream(
						new FileInputStream(in));
				Object o = s.readObject();
				if (User.class.isInstance(o)) {
					s.close();
					in.delete();
					UserData u = (UserData) o;
					if (u.isLogin()) {
						WebConfig.LoginUserCount += 1;
					}
					return u;
				}
				s.close();
			}
		} catch (Exception e) {
			LogUtil.warn("未取到缓存的User对象");
		}
		return null;
	}

	public static void destory() {
		setCurrent(new UserData());
	}

	public static String getSessionID() {
		return getCurrent().SessionID;
	}

	protected static void setSessionID(String sessionID) {
		getCurrent().SessionID = sessionID;
		if (Config.isDebugMode())
			cacheUser(getCurrent());
	}

	public static boolean isMember() {
		return getCurrent().isMember;
	}

	public static void setMember(boolean isMember) {
		getCurrent().isMember = isMember;
		if (Config.isDebugMode())
			cacheUser(getCurrent());
	}

	public static boolean isManager() {
		return getCurrent().isManager;
	}

	public static void setManager(boolean isManager) {
		getCurrent().isManager = isManager;
		if (Config.isDebugMode())
			cacheUser(getCurrent());
	}

	public static class UserData implements Serializable {
		private static final long serialVersionUID = 1L;
		private String Type;
		private String Status;
		private String UserName;
		private String RealName;
		private String BranchInnerCode;
		private boolean isLogin = false;

		private boolean isMember = true;

		private boolean isManager = false;
		private String SessionID;
		private Mapx Map = new Mapx();

		public String getType() {
			return this.Type;
		}

		public void setType(String type) {
			this.Type = type;
			User.cacheUser(User.getCurrent());
		}

		public String getStatus() {
			return this.Status;
		}

		public void setStatus(String status) {
			this.Status = status;
			User.cacheUser(User.getCurrent());
		}

		public String getUserName() {
			return this.UserName;
		}

		public void setUserName(String userName) {
			this.UserName = userName;
			User.cacheUser(User.getCurrent());
		}

		public String getRealName() {
			return this.RealName;
		}

		public void setRealName(String realName) {
			this.RealName = realName;
			User.cacheUser(User.getCurrent());
		}

		public String getBranchInnerCode() {
			return this.BranchInnerCode;
		}

		public void setBranchInnerCode(String branchInnerCode) {
			this.BranchInnerCode = branchInnerCode;
			User.cacheUser(User.getCurrent());
		}

		public boolean isLogin() {
			return this.isLogin;
		}

		public void setLogin(boolean isLogin) {
			this.isLogin = isLogin;
			User.cacheUser(User.getCurrent());
		}

		public boolean isMember() {
			return this.isMember;
		}

		public void setMember(boolean isMember) {
			this.isMember = isMember;
			User.cacheUser(User.getCurrent());
		}

		public boolean isManager() {
			return this.isManager;
		}

		public void setManager(boolean isManager) {
			this.isManager = isManager;
			User.cacheUser(User.getCurrent());
		}

		public String getSessionID() {
			return this.SessionID;
		}

		public void setSessionID(String sessionID) {
			this.SessionID = sessionID;
			User.cacheUser(User.getCurrent());
		}

		public Mapx getMap() {
			return this.Map;
		}

		public void setMap(Mapx map) {
			this.Map = map;
			User.cacheUser(User.getCurrent());
		}
	}
}
