package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCBadWordSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCBadWordSet extends SchemaSet {
	public ZCBadWordSet() {
		this(10,0);
	}

	public ZCBadWordSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCBadWordSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCBadWordSchema._TableCode;
		Columns = ZCBadWordSchema._Columns;
		NameSpace = ZCBadWordSchema._NameSpace;
		InsertAllSQL = ZCBadWordSchema._InsertAllSQL;
		UpdateAllSQL = ZCBadWordSchema._UpdateAllSQL;
		FillAllSQL = ZCBadWordSchema._FillAllSQL;
		DeleteSQL = ZCBadWordSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCBadWordSet();
	}

	public boolean add(ZCBadWordSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCBadWordSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCBadWordSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCBadWordSchema get(int index) {
		ZCBadWordSchema tSchema = (ZCBadWordSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCBadWordSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCBadWordSet aSet) {
		return super.set(aSet);
	}
}
 