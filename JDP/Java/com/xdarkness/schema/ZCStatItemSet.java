package com.xdarkness.schema;

import com.xdarkness.schema.ZCStatItemSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCStatItemSet extends SchemaSet {
	public ZCStatItemSet() {
		this(10,0);
	}

	public ZCStatItemSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCStatItemSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCStatItemSchema._TableCode;
		Columns = ZCStatItemSchema._Columns;
		NameSpace = ZCStatItemSchema._NameSpace;
		InsertAllSQL = ZCStatItemSchema._InsertAllSQL;
		UpdateAllSQL = ZCStatItemSchema._UpdateAllSQL;
		FillAllSQL = ZCStatItemSchema._FillAllSQL;
		DeleteSQL = ZCStatItemSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCStatItemSet();
	}

	public boolean add(ZCStatItemSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCStatItemSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCStatItemSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCStatItemSchema get(int index) {
		ZCStatItemSchema tSchema = (ZCStatItemSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCStatItemSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCStatItemSet aSet) {
		return super.set(aSet);
	}
}
 