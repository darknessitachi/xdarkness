package com.xdarkness.schema;

import com.xdarkness.schema.ZDColumnRelaSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZDColumnRelaSet extends SchemaSet {
	public ZDColumnRelaSet() {
		this(10,0);
	}

	public ZDColumnRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDColumnRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDColumnRelaSchema._TableCode;
		Columns = ZDColumnRelaSchema._Columns;
		NameSpace = ZDColumnRelaSchema._NameSpace;
		InsertAllSQL = ZDColumnRelaSchema._InsertAllSQL;
		UpdateAllSQL = ZDColumnRelaSchema._UpdateAllSQL;
		FillAllSQL = ZDColumnRelaSchema._FillAllSQL;
		DeleteSQL = ZDColumnRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDColumnRelaSet();
	}

	public boolean add(ZDColumnRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDColumnRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDColumnRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDColumnRelaSchema get(int index) {
		ZDColumnRelaSchema tSchema = (ZDColumnRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDColumnRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDColumnRelaSet aSet) {
		return super.set(aSet);
	}
}
 