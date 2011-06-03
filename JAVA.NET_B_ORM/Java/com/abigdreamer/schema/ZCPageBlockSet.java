package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCPageBlockSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCPageBlockSet extends SchemaSet {
	public ZCPageBlockSet() {
		this(10,0);
	}

	public ZCPageBlockSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCPageBlockSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCPageBlockSchema._TableCode;
		Columns = ZCPageBlockSchema._Columns;
		NameSpace = ZCPageBlockSchema._NameSpace;
		InsertAllSQL = ZCPageBlockSchema._InsertAllSQL;
		UpdateAllSQL = ZCPageBlockSchema._UpdateAllSQL;
		FillAllSQL = ZCPageBlockSchema._FillAllSQL;
		DeleteSQL = ZCPageBlockSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCPageBlockSet();
	}

	public boolean add(ZCPageBlockSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCPageBlockSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCPageBlockSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCPageBlockSchema get(int index) {
		ZCPageBlockSchema tSchema = (ZCPageBlockSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCPageBlockSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCPageBlockSet aSet) {
		return super.set(aSet);
	}
}
 