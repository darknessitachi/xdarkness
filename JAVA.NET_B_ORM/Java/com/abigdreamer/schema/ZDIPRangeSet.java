package com.abigdreamer.schema;

import com.abigdreamer.schema.ZDIPRangeSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZDIPRangeSet extends SchemaSet {
	public ZDIPRangeSet() {
		this(10,0);
	}

	public ZDIPRangeSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDIPRangeSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDIPRangeSchema._TableCode;
		Columns = ZDIPRangeSchema._Columns;
		NameSpace = ZDIPRangeSchema._NameSpace;
		InsertAllSQL = ZDIPRangeSchema._InsertAllSQL;
		UpdateAllSQL = ZDIPRangeSchema._UpdateAllSQL;
		FillAllSQL = ZDIPRangeSchema._FillAllSQL;
		DeleteSQL = ZDIPRangeSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDIPRangeSet();
	}

	public boolean add(ZDIPRangeSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDIPRangeSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDIPRangeSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDIPRangeSchema get(int index) {
		ZDIPRangeSchema tSchema = (ZDIPRangeSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDIPRangeSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDIPRangeSet aSet) {
		return super.set(aSet);
	}
}
 