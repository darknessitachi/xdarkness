package com.abigdreamer.schema;

import com.abigdreamer.schema.ZWInstanceSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZWInstanceSet extends SchemaSet {
	public ZWInstanceSet() {
		this(10,0);
	}

	public ZWInstanceSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZWInstanceSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZWInstanceSchema._TableCode;
		Columns = ZWInstanceSchema._Columns;
		NameSpace = ZWInstanceSchema._NameSpace;
		InsertAllSQL = ZWInstanceSchema._InsertAllSQL;
		UpdateAllSQL = ZWInstanceSchema._UpdateAllSQL;
		FillAllSQL = ZWInstanceSchema._FillAllSQL;
		DeleteSQL = ZWInstanceSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZWInstanceSet();
	}

	public boolean add(ZWInstanceSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZWInstanceSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZWInstanceSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZWInstanceSchema get(int index) {
		ZWInstanceSchema tSchema = (ZWInstanceSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZWInstanceSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZWInstanceSet aSet) {
		return super.set(aSet);
	}
}
 