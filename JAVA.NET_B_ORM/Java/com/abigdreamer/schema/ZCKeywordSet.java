package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCKeywordSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCKeywordSet extends SchemaSet {
	public ZCKeywordSet() {
		this(10,0);
	}

	public ZCKeywordSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCKeywordSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCKeywordSchema._TableCode;
		Columns = ZCKeywordSchema._Columns;
		NameSpace = ZCKeywordSchema._NameSpace;
		InsertAllSQL = ZCKeywordSchema._InsertAllSQL;
		UpdateAllSQL = ZCKeywordSchema._UpdateAllSQL;
		FillAllSQL = ZCKeywordSchema._FillAllSQL;
		DeleteSQL = ZCKeywordSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCKeywordSet();
	}

	public boolean add(ZCKeywordSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCKeywordSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCKeywordSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCKeywordSchema get(int index) {
		ZCKeywordSchema tSchema = (ZCKeywordSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCKeywordSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCKeywordSet aSet) {
		return super.set(aSet);
	}
}
 