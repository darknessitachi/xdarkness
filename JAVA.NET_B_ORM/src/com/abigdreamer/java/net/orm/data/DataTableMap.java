package com.abigdreamer.java.net.orm.data;

import com.abigdreamer.java.net.util.Mapx;

public class DataTableMap<K, V> extends Mapx<K, V> {

	private static final long serialVersionUID = 3204371502117979614L;

	public DataTable toDataTable() {
		DataColumn[] dcs = { new DataColumn("Key", 1),
				new DataColumn("Value", 1) };
		Object[] ks = keyArray();
		Object[][] vs = new Object[ks.length][2];
		DataTable dt = new DataTable(dcs, vs);
		for (int i = 0; i < ks.length; ++i) {
			dt.set(i, 0, ks[i]);
			dt.set(i, 1, get(ks[i]));
		}
		return dt;
	}
	
}
