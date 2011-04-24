package com.xdarkness.schema;

import com.xdarkness.schema.ZSOrderSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZSOrderSet extends SchemaSet {
	public ZSOrderSet() {
		this(10,0);
	}

	public ZSOrderSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZSOrderSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZSOrderSchema._TableCode;
		Columns = ZSOrderSchema._Columns;
		NameSpace = ZSOrderSchema._NameSpace;
		InsertAllSQL = ZSOrderSchema._InsertAllSQL;
		UpdateAllSQL = ZSOrderSchema._UpdateAllSQL;
		FillAllSQL = ZSOrderSchema._FillAllSQL;
		DeleteSQL = ZSOrderSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZSOrderSet();
	}

	public boolean add(ZSOrderSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZSOrderSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZSOrderSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZSOrderSchema get(int index) {
		ZSOrderSchema tSchema = (ZSOrderSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZSOrderSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZSOrderSet aSet) {
		return super.set(aSet);
	}
}
 