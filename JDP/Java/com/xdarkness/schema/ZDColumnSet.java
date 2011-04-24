package com.xdarkness.schema;

import com.xdarkness.schema.ZDColumnSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZDColumnSet extends SchemaSet {
	public ZDColumnSet() {
		this(10,0);
	}

	public ZDColumnSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDColumnSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDColumnSchema._TableCode;
		Columns = ZDColumnSchema._Columns;
		NameSpace = ZDColumnSchema._NameSpace;
		InsertAllSQL = ZDColumnSchema._InsertAllSQL;
		UpdateAllSQL = ZDColumnSchema._UpdateAllSQL;
		FillAllSQL = ZDColumnSchema._FillAllSQL;
		DeleteSQL = ZDColumnSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDColumnSet();
	}

	public boolean add(ZDColumnSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDColumnSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDColumnSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDColumnSchema get(int index) {
		ZDColumnSchema tSchema = (ZDColumnSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDColumnSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDColumnSet aSet) {
		return super.set(aSet);
	}
}
 