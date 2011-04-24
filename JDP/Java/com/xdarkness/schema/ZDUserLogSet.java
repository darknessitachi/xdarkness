package com.xdarkness.schema;

import com.xdarkness.schema.ZDUserLogSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZDUserLogSet extends SchemaSet {
	public ZDUserLogSet() {
		this(10,0);
	}

	public ZDUserLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDUserLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDUserLogSchema._TableCode;
		Columns = ZDUserLogSchema._Columns;
		NameSpace = ZDUserLogSchema._NameSpace;
		InsertAllSQL = ZDUserLogSchema._InsertAllSQL;
		UpdateAllSQL = ZDUserLogSchema._UpdateAllSQL;
		FillAllSQL = ZDUserLogSchema._FillAllSQL;
		DeleteSQL = ZDUserLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDUserLogSet();
	}

	public boolean add(ZDUserLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDUserLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDUserLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDUserLogSchema get(int index) {
		ZDUserLogSchema tSchema = (ZDUserLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDUserLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDUserLogSet aSet) {
		return super.set(aSet);
	}
}
 