package com.xdarkness.framework.orm;

public class TUpdate {
//	public void modifyTable(Transaction tran, Mapx map, ArrayList deletedList,
//			SchemaColumn[] scs, String tableCode) throws Exception {
//		int count = 0;
//		int size = 500;
//		if ("true".equals(Config.getValue("App.MinimalMemory")))
//			size = 100;
//		try {
//			QueryBuilder qb = new QueryBuilder("select * from " + tableCode);
//			count = DBUtil.getCount(qb);
//			for (i = 0; i < count * 1.0D / size; ++i) {
//				DataTable dt = qb.executePagedDataTable(size, i);
//				FileUtil.serialize(dt, Config.getContextRealPath()
//						+ "/WEB-INF/data/_tmp_" + tableCode + "_" + i + ".dat");
//			}
//		} catch (Exception localException) {
//		}
//		TableCreator tc = new TableCreator(this.DBType);
//		tc.createTable(scs, tableCode);
//		tc.executeAndClear(tran);
//		for (int i = 0; i < count * 1.0D / size; ++i) {
//			String fileName = Config.getContextRealPath()
//					+ "/WEB-INF/data/_tmp_" + tableCode + "_" + i + ".dat";
//			DataTable dt = (DataTable) FileUtil.unserialize(fileName);
//			FileUtil.delete(fileName);
//			StringBuffer sb = new StringBuffer("insert into " + tableCode + "(");
//			for (int j = 0; j < scs.length; ++j) {
//				if (j != 0) {
//					sb.append(",");
//				}
//				sb.append(scs[j].getColumnName());
//			}
//			sb.append(") values (");
//			for (j = 0; j < scs.length; ++j) {
//				if (j != 0) {
//					sb.append(",");
//				}
//				sb.append("?");
//			}
//			sb.append(")");
//			QueryBuilder qb = new QueryBuilder(sb.toString());
//			qb.setBatchMode(true);
//			for (int j = 0; j < dt.getRowCount(); ++j) {
//				for (int k = 0; k < scs.length; ++k) {
//					SchemaColumn sc = scs[k];
//					String v = null;
//					if (map.containsKey(sc.getColumnName()))
//						v = dt.getString(j, map.getString(sc.getColumnName()));
//					else {
//						v = dt.getString(j, sc.getColumnName());
//					}
//					if (StringUtil.isEmpty(v)) {
//						v = null;
//					}
//
//					if (v != null) {
//						if ((sc.getColumnType() == 0)
//								&& (!(StringUtil.verify(v, "DateTime")))) {
//							throw new RuntimeException(
//									"修改自定义表字段时发生错误,字段值不是正确的日期:"
//											+ dt.getDataRow(j));
//						}
//
//						if ((sc.getColumnType() == 8)
//								|| (sc.getColumnType() == 9)) {
//							v = String
//									.valueOf(new Double(Double.parseDouble(v))
//											.intValue());
//						}
//						if (sc.getColumnType() == 7) {
//							v = String
//									.valueOf(new Double(Double.parseDouble(v))
//											.longValue());
//						}
//						if (sc.getColumnType() == 5) {
//							v = String
//									.valueOf(new Double(Double.parseDouble(v))
//											.floatValue());
//						}
//						if ((sc.getColumnType() == 4)
//								|| (sc.getColumnType() == 6)
//								|| (sc.getColumnType() == 3)) {
//							v = String.valueOf(Double.parseDouble(v));
//						}
//					}
//					qb.add(v);
//				}
//				qb.addBatch();
//			}
//			tran.add(qb);
//		}
//	}
}
