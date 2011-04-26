package com.xdarkness.schema;

import com.xdarkness.schema.BZSOrderSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZSOrderSet extends SchemaSet {
	public BZSOrderSet() {
		this(10,0);
	}

	public BZSOrderSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZSOrderSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZSOrderSchema._TableCode;
		Columns = BZSOrderSchema._Columns;
		NameSpace = BZSOrderSchema._NameSpace;
		InsertAllSQL = BZSOrderSchema._InsertAllSQL;
		UpdateAllSQL = BZSOrderSchema._UpdateAllSQL;
		FillAllSQL = BZSOrderSchema._FillAllSQL;
		DeleteSQL = BZSOrderSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZSOrderSet();
	}

	public boolean add(BZSOrderSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZSOrderSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZSOrderSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZSOrderSchema get(int index) {
		BZSOrderSchema tSchema = (BZSOrderSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZSOrderSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZSOrderSet aSet) {
		return super.set(aSet);
	}
}
 