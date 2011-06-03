package com.abigdreamer.schema;

import com.abigdreamer.schema.BZSOrderItemSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZSOrderItemSet extends SchemaSet {
	public BZSOrderItemSet() {
		this(10,0);
	}

	public BZSOrderItemSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZSOrderItemSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZSOrderItemSchema._TableCode;
		Columns = BZSOrderItemSchema._Columns;
		NameSpace = BZSOrderItemSchema._NameSpace;
		InsertAllSQL = BZSOrderItemSchema._InsertAllSQL;
		UpdateAllSQL = BZSOrderItemSchema._UpdateAllSQL;
		FillAllSQL = BZSOrderItemSchema._FillAllSQL;
		DeleteSQL = BZSOrderItemSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZSOrderItemSet();
	}

	public boolean add(BZSOrderItemSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZSOrderItemSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZSOrderItemSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZSOrderItemSchema get(int index) {
		BZSOrderItemSchema tSchema = (BZSOrderItemSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZSOrderItemSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZSOrderItemSet aSet) {
		return super.set(aSet);
	}
}
 