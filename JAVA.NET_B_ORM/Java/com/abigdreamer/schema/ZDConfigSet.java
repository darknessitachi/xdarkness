package com.abigdreamer.schema;

import com.abigdreamer.schema.ZDConfigSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZDConfigSet extends SchemaSet {
	public ZDConfigSet() {
		this(10,0);
	}

	public ZDConfigSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDConfigSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDConfigSchema._TableCode;
		Columns = ZDConfigSchema._Columns;
		NameSpace = ZDConfigSchema._NameSpace;
		InsertAllSQL = ZDConfigSchema._InsertAllSQL;
		UpdateAllSQL = ZDConfigSchema._UpdateAllSQL;
		FillAllSQL = ZDConfigSchema._FillAllSQL;
		DeleteSQL = ZDConfigSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDConfigSet();
	}

	public boolean add(ZDConfigSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDConfigSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDConfigSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDConfigSchema get(int index) {
		ZDConfigSchema tSchema = (ZDConfigSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDConfigSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDConfigSet aSet) {
		return super.set(aSet);
	}
}
 