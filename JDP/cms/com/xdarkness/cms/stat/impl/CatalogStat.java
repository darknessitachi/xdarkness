package com.xdarkness.cms.stat.impl;

import java.util.ArrayList;

import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.cms.stat.AbstractStat;
import com.xdarkness.cms.stat.Visit;
import com.xdarkness.cms.stat.VisitCount;
import com.xdarkness.schema.ZCStatItemSchema;
import com.xdarkness.schema.ZCStatItemSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.sql.QueryBuilder;

public class CatalogStat extends AbstractStat {
	private static final String[] avgSubTypes = { "StickTime" };
	private static final String Type = "Catalog";

	public String getStatType() {
		return "Catalog";
	}

	public String[] getAverageSubTypes() {
		return avgSubTypes;
	}

	public void deal(Visit v) {
		if ((XString.isEmpty(v.Type)) || ("AD".equals(v.Type))
				|| ("Other".equals(v.Type))) {
			return;
		}
		String code = v.CatalogInnerCode;
		if ((code == null) || (code.length() % 6 != 0)) {
			code = "";
		}
		if ((XString.isNotEmpty(code))
				&& (CatalogUtil.getSiteIDByInnerCode(v.CatalogInnerCode) == null)) {
			return;
		}
		if (v.LeafID == 0L) {
			if ("Unload".equals(v.Event))
				VisitCount.getInstance().addAverage(v.SiteID, "Catalog",
						"StickTime", code + "Index", v.StickTime);
			else {
				VisitCount.getInstance().add(v.SiteID, "Catalog", "PV",
						code + "Index");
			}
		}
		String[] codes = new String[code.length() / 6];
		for (int i = 0; i < codes.length; i++) {
			codes[i] = code.substring(0, i * 6 + 6);
		}
		for (int i = 0; i < codes.length; i++) {
			code = codes[i];
			if ("Unload".equals(v.Event))
				VisitCount.getInstance().addAverage(v.SiteID, "Catalog",
						"StickTime", code, v.StickTime);
			else {
				VisitCount.getInstance().add(v.SiteID, "Catalog", "PV", code);
			}
			code = code.substring(0, code.length() - 6);
		}
	}

	public static void updateInnerCode(Transaction tran, long siteID,
			String oldInnerCode, String newInnerCode) {
		QueryBuilder qb = new QueryBuilder(
				"where SiteID=? and type=? and item like ?");
		qb.add(siteID);
		qb.add("Catalog");
		qb.add(oldInnerCode + "%");
		ZCStatItemSet statSet = new ZCStatItemSchema().query(qb);
		ZCStatItemSet childSet = new ZCStatItemSet();
		for (int i = 0; i < statSet.size(); i++) {
			String item = statSet.get(i).getItem();
			if (item.equals(oldInnerCode)) {
				childSet.add(statSet.get(i));
			}
			item = XString.replaceEx(item, oldInnerCode, newInnerCode);
			statSet.get(i).setItem(item);
		}

		ArrayList parentList = new ArrayList();
		ZCStatItemSet parentSet = null;
		String code = oldInnerCode;
		while (code.length() > 6) {
			code = code.substring(0, code.length() - 6);
			parentList.add(code);
		}
		if (parentList.size() > 0) {
			qb = new QueryBuilder("where SiteID=? and type=? and item in ("
					+ XString.join(parentList) + ")");
			qb.add(siteID);
			qb.add("Catalog");
			parentSet = new ZCStatItemSchema().query(qb);
		}

		parentList = new ArrayList();
		ZCStatItemSet newParentSet = null;
		code = newInnerCode;
		while (code.length() > 6) {
			code = code.substring(0, code.length() - 6);
			parentList.add(code);
		}
		if (parentList.size() > 0) {
			qb = new QueryBuilder("where SiteID=? and type=? and item in ("
					+ XString.join(parentList) + ")");
			qb.add(siteID);
			qb.add("Catalog");
			newParentSet = new ZCStatItemSchema().query(qb);
		}

		if (parentSet != null) {
			for (int i = 0; i < parentSet.size(); i++) {
				ZCStatItemSchema parent = parentSet.get(i);
				for (int j = 0; j < childSet.size(); j++) {
					ZCStatItemSchema child = childSet.get(j);
					if (child.getItem().endsWith("Index")) {
						continue;
					}
					if ((child.getPeriod().equals(parent.getPeriod()))
							&& (child.getSubType().equals(parent.getSubType()))) {
						for (int k = 5; k < child.getColumnCount(); k++) {
							long n1 = (Long) child.getV(k);
							long n2 = (Long) parent.getV(k);
							long v = n2 - n1;
							if (v < 0L) {
								v = 0L;
							}
							parent.setV(k, new Long(v));
						}
					}
				}
			}
		}

		ZCStatItemSet noExistsParentSet = new ZCStatItemSet();
		if (newParentSet != null) {
			for (int j = 0; j < childSet.size(); j++) {
				ZCStatItemSchema child = childSet.get(j);
				if (child.getItem().endsWith("Index")) {
					continue;
				}
				ArrayList list = (ArrayList) parentList.clone();
				for (int i = 0; i < newParentSet.size(); i++) {
					ZCStatItemSchema parent = newParentSet.get(i);
					if ((child.getPeriod().equals(parent.getPeriod()))
							&& (child.getSubType().equals(parent.getSubType()))) {
						for (int k = 5; k < child.getColumnCount(); k++) {
							long n1 = (Long) child.getV(k);
							long n2 = (Long) parent.getV(k);
							parent.setV(k, new Long(n2
									+ n1));
						}
						String item = parent.getItem();
						list.remove(item);
					}
				}
				for (int i = 0; i < list.size(); i++) {
					code = (String) list.get(i);
					ZCStatItemSchema si = (ZCStatItemSchema) child.clone();
					si.setItem(code);
					noExistsParentSet.add(si);
				}
			}
		}
		tran.add(noExistsParentSet, OperateType.INSERT);
		tran.add(parentSet, OperateType.UPDATE);
		tran.add(newParentSet, OperateType.UPDATE);
		tran.add(statSet, OperateType.UPDATE);
	}
}

/*
 * com.xdarkness.cms.stat.impl.CatalogStat JD-Core Version: 0.6.0
 */