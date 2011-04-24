package com.xdarkness.schema;

import com.xdarkness.schema.ZCImageRelaSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCImageRelaSet extends SchemaSet {
	public ZCImageRelaSet() {
		this(10,0);
	}

	public ZCImageRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCImageRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCImageRelaSchema._TableCode;
		Columns = ZCImageRelaSchema._Columns;
		NameSpace = ZCImageRelaSchema._NameSpace;
		InsertAllSQL = ZCImageRelaSchema._InsertAllSQL;
		UpdateAllSQL = ZCImageRelaSchema._UpdateAllSQL;
		FillAllSQL = ZCImageRelaSchema._FillAllSQL;
		DeleteSQL = ZCImageRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCImageRelaSet();
	}

	public boolean add(ZCImageRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCImageRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCImageRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCImageRelaSchema get(int index) {
		ZCImageRelaSchema tSchema = (ZCImageRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCImageRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCImageRelaSet aSet) {
		return super.set(aSet);
	}
}
 