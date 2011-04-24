package com.xdarkness.schema;

import com.xdarkness.schema.ZCTagSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCTagSet extends SchemaSet {
	public ZCTagSet() {
		this(10,0);
	}

	public ZCTagSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCTagSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCTagSchema._TableCode;
		Columns = ZCTagSchema._Columns;
		NameSpace = ZCTagSchema._NameSpace;
		InsertAllSQL = ZCTagSchema._InsertAllSQL;
		UpdateAllSQL = ZCTagSchema._UpdateAllSQL;
		FillAllSQL = ZCTagSchema._FillAllSQL;
		DeleteSQL = ZCTagSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCTagSet();
	}

	public boolean add(ZCTagSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCTagSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCTagSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCTagSchema get(int index) {
		ZCTagSchema tSchema = (ZCTagSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCTagSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCTagSet aSet) {
		return super.set(aSet);
	}
}
 