package com.xdarkness.platform.pub;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;

import com.abigdreamer.java.net.data.Transaction;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.XString;

public class OrderUtil {
	private static long currentOrder = System.currentTimeMillis();

	public static boolean updateOrder(String table, String type,
			String targetOrder, String orders, String wherePart) {
		return updateOrder(table, "OrderFlag", type, targetOrder, orders,
				wherePart);
	}

	public static boolean updateOrder(String table, String column, String type,
			String targetOrder, String orders, String wherePart) {
		return updateOrder(table, column, type, targetOrder, orders, wherePart,
				null);
	}

	public static boolean updateOrder(String table, String column, String type,
			String targetOrder, String orders, String wherePart,
			Transaction tran) {
		if ((XString.isEmpty(targetOrder)) || (targetOrder.length() < 13)) {
			targetOrder = getDefaultOrder()+"";
		}
		if (!XString.checkID(targetOrder)) {
			return false;
		}
		if (!XString.checkID(orders)) {
			return false;
		}
		if (XString.isEmpty(wherePart)) {
			wherePart = "1=1";
		}

		String[] arrtmp = orders.split(",");
		arrtmp = (String[]) ArrayUtils.removeElement(arrtmp, targetOrder);
		long[] arr = new long[arrtmp.length + 1];
		for (int i = 0; i < arrtmp.length; i++) {
			arr[i] = Long.parseLong(arrtmp[i]);
		}
		long target = Long.parseLong(targetOrder);
		arr[arrtmp.length] = target;
		Arrays.sort(arr);

		boolean bFlag = true;
		if (tran == null) {
			tran = new Transaction();
			bFlag = false;
		}
		QueryBuilder qb = null;
		boolean flag = type.equals("After");
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == target) {
				if (flag) {
					target = target + arr.length - i - 1L;
					int d = arr.length - 1;
					for (int j = 0; j < arr.length; j++) {
						if (j != i) {
							qb = new QueryBuilder("update " + table + " set "
									+ column + "=? where " + column + "=?",
									(target - d) * 10L);
							d--;
						} else {
							qb = new QueryBuilder("update " + table + " set "
									+ column + "=? where " + column + "=?",
									target * 10L);
						}
						qb.add(arr[j]);
						tran.add(qb);
					}

					for (int j = 0; j < i; j++) {
						if (arr[j] + 1L == arr[(j + 1)]) {
							continue;
						}
						qb = new QueryBuilder("update " + table + " set "
								+ column + "=" + column + "-? where " + column
								+ " between ? and ? and " + wherePart);
						qb.add(j + 1);
						qb.add(arr[j]);
						qb.add(arr[(j + 1)]);
						tran.add(qb);
					}

					for (int j = arr.length - 1; j > i; j--) {
						if (arr[j] == arr[(j - 1)] + 1L) {
							continue;
						}
						qb = new QueryBuilder("update " + table + " set "
								+ column + "=" + column + "+? where " + column
								+ " between ? and ? and " + wherePart);
						qb.add(arr.length - j);
						qb.add(arr[(j - 1)]);
						qb.add(arr[j]);
						tran.add(qb);
					}
				} else {
					target -= i;
					int d = 1;
					for (int j = 0; j < arr.length; j++) {
						if (j != i) {
							qb = new QueryBuilder("update " + table + " set "
									+ column + "=? where " + column + "=?",
									(target + d) * 10L);
							d++;
						} else {
							qb = new QueryBuilder("update " + table + " set "
									+ column + "=? where " + column + "=?",
									target * 10L);
						}
						qb.add(arr[j]);
						tran.add(qb);
					}

					for (int j = 0; j < i; j++) {
						if (arr[j] + 1L == arr[(j + 1)]) {
							continue;
						}
						qb = new QueryBuilder("update " + table + " set "
								+ column + "=" + column + "-? where " + column
								+ " between ? and ? and " + wherePart);
						qb.add(j + 1);
						qb.add(arr[j]);
						qb.add(arr[(j + 1)]);
						tran.add(qb);
					}

					for (int j = arr.length - 1; j > i; j--) {
						if (arr[j] == arr[(j - 1)] + 1L) {
							continue;
						}
						qb = new QueryBuilder("update " + table + " set "
								+ column + "=" + column + "+? where " + column
								+ " between ? and ? and " + wherePart);
						qb.add(arr.length - j);
						qb.add(arr[(j - 1)]);
						qb.add(arr[j]);
						tran.add(qb);
					}
				}
				qb = new QueryBuilder("update " + table + " set " + column
						+ "=" + column + "/10 where " + column + ">? and "
						+ wherePart, target * 9L);
				tran.add(qb);
				if (bFlag) {
					return true;
				}
				return tran.commit();
			}
		}

		return false;
	}

	public static synchronized long getDefaultOrder() {
		if (System.currentTimeMillis() <= currentOrder) {
			return ++currentOrder;
		}
		return OrderUtil.currentOrder = System.currentTimeMillis();
	}
}

/*
 * com.xdarkness.platform.pub.OrderUtil JD-Core Version: 0.6.0
 */