package com.xdarkness.schema;

import com.xdarkness.schema.ZCImageSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCImageSet extends SchemaSet {
	public ZCImageSet() {
		this(10,0);
	}

	public ZCImageSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCImageSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCImageSchema._TableCode;
		Columns = ZCImageSchema._Columns;
		NameSpace = ZCImageSchema._NameSpace;
		InsertAllSQL = ZCImageSchema._InsertAllSQL;
		UpdateAllSQL = ZCImageSchema._UpdateAllSQL;
		FillAllSQL = ZCImageSchema._FillAllSQL;
		DeleteSQL = ZCImageSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCImageSet();
	}

	public boolean add(ZCImageSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCImageSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCImageSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCImageSchema get(int index) {
		ZCImageSchema tSchema = (ZCImageSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCImageSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCImageSet aSet) {
		return super.set(aSet);
	}
}
 