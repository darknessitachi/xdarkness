package com.xdarkness.schema;

import com.xdarkness.schema.ZDColumnValueSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZDColumnValueSet extends SchemaSet {
	public ZDColumnValueSet() {
		this(10,0);
	}

	public ZDColumnValueSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDColumnValueSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDColumnValueSchema._TableCode;
		Columns = ZDColumnValueSchema._Columns;
		NameSpace = ZDColumnValueSchema._NameSpace;
		InsertAllSQL = ZDColumnValueSchema._InsertAllSQL;
		UpdateAllSQL = ZDColumnValueSchema._UpdateAllSQL;
		FillAllSQL = ZDColumnValueSchema._FillAllSQL;
		DeleteSQL = ZDColumnValueSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDColumnValueSet();
	}

	public boolean add(ZDColumnValueSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDColumnValueSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDColumnValueSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDColumnValueSchema get(int index) {
		ZDColumnValueSchema tSchema = (ZDColumnValueSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDColumnValueSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDColumnValueSet aSet) {
		return super.set(aSet);
	}
}
 