package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCMessageSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCMessageSet extends SchemaSet {
	public ZCMessageSet() {
		this(10,0);
	}

	public ZCMessageSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCMessageSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCMessageSchema._TableCode;
		Columns = ZCMessageSchema._Columns;
		NameSpace = ZCMessageSchema._NameSpace;
		InsertAllSQL = ZCMessageSchema._InsertAllSQL;
		UpdateAllSQL = ZCMessageSchema._UpdateAllSQL;
		FillAllSQL = ZCMessageSchema._FillAllSQL;
		DeleteSQL = ZCMessageSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCMessageSet();
	}

	public boolean add(ZCMessageSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCMessageSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCMessageSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCMessageSchema get(int index) {
		ZCMessageSchema tSchema = (ZCMessageSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCMessageSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCMessageSet aSet) {
		return super.set(aSet);
	}
}
 