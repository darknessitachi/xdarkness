package com.xdarkness.framework.clustering.server;

import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.XString;

public class ClusteringData {
	public static final String RESULT_NULL = "Null";
	public static final String RESULT_NOTFOUND = "NotFound";
	public static final String RESULT_EMPTYKEY = "EmptyKey";
	public static final String RESULT_SUCCESS = "Success";
	public static final String RESULT_TRUE = "True";
	public static final String RESULT_FALSE = "False";
	public static final String RESULT_NOSUPPORTACTION = "NoSupportAction";
	public static final String RESULT_NOTNUMBER = "NotNumber";
	protected static Mapx data = new Mapx();

	public static String get(String key) {
		return data.getString(key);
	}

	public static synchronized void put(String key, String value) {
		data.put(key, value);
	}

	public static synchronized void remove(String key) {
		data.remove(key);
	}

	public static boolean containsKey(String key) {
		return data.containsKey(key);
	}

	public static Object[] getAllKeys() {
		return data.keyArray();
	}

	public static String dealRequest(String type, String key, String action,
			String value) {
		if ("Command".equalsIgnoreCase(type)) {
			return dealCommand(key, value);
		}
		if ("Get".equals(action)) {
			Object obj = data.get(key);
			if (obj == null) {
				return "Null\n";
			}
			return "Success\n" + obj.toString();
		}
		if ("Put".equals(action)) {
			if ((value == null) || (value.equals("_SKY_NULL"))) {
				value = null;
			}
			if (XString.isEmpty(key)) {
				return "EmptyKey\n";
			}
			synchronized (data) {
				data.put(key, value);
			}
			return "Success\n";
		}
		if ("Add".equals(action)) {
			if ((value == null) || (value.equals("_SKY_NULL"))) {
				value = null;
			}
			if (XString.isEmpty(key)) {
				return "EmptyKey\n";
			}
			synchronized (data) {
				Double d = (Double) data.get(key);
				if (d == null) {
					d = new Double(0.0D);
				}
				d = new Double(d.doubleValue() + Double.parseDouble(value));
				data.put(key, d);
				return "Success\n" + d.doubleValue();
			}
		}
		if ("AddAverage".equals(action)) {
			if ((value == null) || (value.equals("_SKY_NULL"))) {
				value = "0|0";
			}
			if (XString.isEmpty(key)) {
				return "EmptyKey\n";
			}
			String[] numbers = value.split("\\|");
			if (numbers.length != 2) {
				return "NotNumber\n";
			}
			synchronized (data) {
				double[] arr = (double[]) data.get(key);
				if (arr == null) {
					arr = new double[] { 0.0D, 0.0D };
				}
				arr[0] += Double.parseDouble(numbers[0]);
				arr[1] += Double.parseDouble(numbers[1]);
				data.put(key, arr);
				return "Success\n"
						+ (arr[1] == 0.0D ? "0" : String.valueOf(arr[0]
								/ arr[1]));
			}
		}
		if ("ContainsKey".equals(action))
			return data.containsKey(key) ? "True\n" : "False\n";
		if ("Remove".equals(action)) {
			synchronized (data) {
				data.remove(key);
			}
			return "Success\n";
		}
		return "NoSupportAction\n";
	}

	public static String dealCommand(String key, String value) {
		return "";
	}
}

/*
 * com.xdarkness.framework.clustering.server.ClusteringData JD-Core Version: 0.6.0
 */