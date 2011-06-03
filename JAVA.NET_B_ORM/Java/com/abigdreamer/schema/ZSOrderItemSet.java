package com.abigdreamer.schema;

import com.abigdreamer.schema.ZSOrderItemSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZSOrderItemSet extends SchemaSet {
	public ZSOrderItemSet() {
		this(10,0);
	}

	public ZSOrderItemSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZSOrderItemSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZSOrderItemSchema._TableCode;
		Columns = ZSOrderItemSchema._Columns;
		NameSpace = ZSOrderItemSchema._NameSpace;
		InsertAllSQL = ZSOrderItemSchema._InsertAllSQL;
		UpdateAllSQL = ZSOrderItemSchema._UpdateAllSQL;
		FillAllSQL = ZSOrderItemSchema._FillAllSQL;
		DeleteSQL = ZSOrderItemSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZSOrderItemSet();
	}

	public boolean add(ZSOrderItemSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZSOrderItemSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZSOrderItemSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZSOrderItemSchema get(int index) {
		ZSOrderItemSchema tSchema = (ZSOrderItemSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZSOrderItemSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZSOrderItemSet aSet) {
		return super.set(aSet);
	}
}
 