package com.xdarkness.schema;

import com.xdarkness.schema.ZCKeywordTypeSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCKeywordTypeSet extends SchemaSet {
	public ZCKeywordTypeSet() {
		this(10,0);
	}

	public ZCKeywordTypeSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCKeywordTypeSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCKeywordTypeSchema._TableCode;
		Columns = ZCKeywordTypeSchema._Columns;
		NameSpace = ZCKeywordTypeSchema._NameSpace;
		InsertAllSQL = ZCKeywordTypeSchema._InsertAllSQL;
		UpdateAllSQL = ZCKeywordTypeSchema._UpdateAllSQL;
		FillAllSQL = ZCKeywordTypeSchema._FillAllSQL;
		DeleteSQL = ZCKeywordTypeSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCKeywordTypeSet();
	}

	public boolean add(ZCKeywordTypeSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCKeywordTypeSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCKeywordTypeSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCKeywordTypeSchema get(int index) {
		ZCKeywordTypeSchema tSchema = (ZCKeywordTypeSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCKeywordTypeSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCKeywordTypeSet aSet) {
		return super.set(aSet);
	}
}
 