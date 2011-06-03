package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCVideoRelaSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCVideoRelaSet extends SchemaSet {
	public ZCVideoRelaSet() {
		this(10,0);
	}

	public ZCVideoRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCVideoRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCVideoRelaSchema._TableCode;
		Columns = ZCVideoRelaSchema._Columns;
		NameSpace = ZCVideoRelaSchema._NameSpace;
		InsertAllSQL = ZCVideoRelaSchema._InsertAllSQL;
		UpdateAllSQL = ZCVideoRelaSchema._UpdateAllSQL;
		FillAllSQL = ZCVideoRelaSchema._FillAllSQL;
		DeleteSQL = ZCVideoRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCVideoRelaSet();
	}

	public boolean add(ZCVideoRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCVideoRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCVideoRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCVideoRelaSchema get(int index) {
		ZCVideoRelaSchema tSchema = (ZCVideoRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCVideoRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCVideoRelaSet aSet) {
		return super.set(aSet);
	}
}
 