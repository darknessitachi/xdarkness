package com.xdarkness.schema;

import com.xdarkness.schema.ZDMenuSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZDMenuSet extends SchemaSet {
	public ZDMenuSet() {
		this(10,0);
	}

	public ZDMenuSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDMenuSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDMenuSchema._TableCode;
		Columns = ZDMenuSchema._Columns;
		NameSpace = ZDMenuSchema._NameSpace;
		InsertAllSQL = ZDMenuSchema._InsertAllSQL;
		UpdateAllSQL = ZDMenuSchema._UpdateAllSQL;
		FillAllSQL = ZDMenuSchema._FillAllSQL;
		DeleteSQL = ZDMenuSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDMenuSet();
	}

	public boolean add(ZDMenuSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDMenuSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDMenuSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDMenuSchema get(int index) {
		ZDMenuSchema tSchema = (ZDMenuSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDMenuSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDMenuSet aSet) {
		return super.set(aSet);
	}
}
 