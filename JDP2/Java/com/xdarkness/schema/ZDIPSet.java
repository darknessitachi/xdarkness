package com.xdarkness.schema;

import com.xdarkness.schema.ZDIPSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZDIPSet extends SchemaSet {
	public ZDIPSet() {
		this(10,0);
	}

	public ZDIPSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDIPSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDIPSchema._TableCode;
		Columns = ZDIPSchema._Columns;
		NameSpace = ZDIPSchema._NameSpace;
		InsertAllSQL = ZDIPSchema._InsertAllSQL;
		UpdateAllSQL = ZDIPSchema._UpdateAllSQL;
		FillAllSQL = ZDIPSchema._FillAllSQL;
		DeleteSQL = ZDIPSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDIPSet();
	}

	public boolean add(ZDIPSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDIPSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDIPSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDIPSchema get(int index) {
		ZDIPSchema tSchema = (ZDIPSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDIPSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDIPSet aSet) {
		return super.set(aSet);
	}
}
 