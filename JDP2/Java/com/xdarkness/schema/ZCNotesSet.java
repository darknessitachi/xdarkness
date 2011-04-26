package com.xdarkness.schema;

import com.xdarkness.schema.ZCNotesSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCNotesSet extends SchemaSet {
	public ZCNotesSet() {
		this(10,0);
	}

	public ZCNotesSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCNotesSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCNotesSchema._TableCode;
		Columns = ZCNotesSchema._Columns;
		NameSpace = ZCNotesSchema._NameSpace;
		InsertAllSQL = ZCNotesSchema._InsertAllSQL;
		UpdateAllSQL = ZCNotesSchema._UpdateAllSQL;
		FillAllSQL = ZCNotesSchema._FillAllSQL;
		DeleteSQL = ZCNotesSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCNotesSet();
	}

	public boolean add(ZCNotesSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCNotesSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCNotesSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCNotesSchema get(int index) {
		ZCNotesSchema tSchema = (ZCNotesSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCNotesSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCNotesSet aSet) {
		return super.set(aSet);
	}
}
 