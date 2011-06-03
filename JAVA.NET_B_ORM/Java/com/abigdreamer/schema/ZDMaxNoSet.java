package com.abigdreamer.schema;

import com.abigdreamer.schema.ZDMaxNoSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZDMaxNoSet extends SchemaSet {
	public ZDMaxNoSet() {
		this(10,0);
	}

	public ZDMaxNoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDMaxNoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDMaxNoSchema._TableCode;
		Columns = ZDMaxNoSchema._Columns;
		NameSpace = ZDMaxNoSchema._NameSpace;
		InsertAllSQL = ZDMaxNoSchema._InsertAllSQL;
		UpdateAllSQL = ZDMaxNoSchema._UpdateAllSQL;
		FillAllSQL = ZDMaxNoSchema._FillAllSQL;
		DeleteSQL = ZDMaxNoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDMaxNoSet();
	}

	public boolean add(ZDMaxNoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDMaxNoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDMaxNoSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDMaxNoSchema get(int index) {
		ZDMaxNoSchema tSchema = (ZDMaxNoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDMaxNoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDMaxNoSet aSet) {
		return super.set(aSet);
	}
}
 