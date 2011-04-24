package com.xdarkness.schema;

import com.xdarkness.schema.BZDColumnRelaSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZDColumnRelaSet extends SchemaSet {
	public BZDColumnRelaSet() {
		this(10,0);
	}

	public BZDColumnRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDColumnRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDColumnRelaSchema._TableCode;
		Columns = BZDColumnRelaSchema._Columns;
		NameSpace = BZDColumnRelaSchema._NameSpace;
		InsertAllSQL = BZDColumnRelaSchema._InsertAllSQL;
		UpdateAllSQL = BZDColumnRelaSchema._UpdateAllSQL;
		FillAllSQL = BZDColumnRelaSchema._FillAllSQL;
		DeleteSQL = BZDColumnRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDColumnRelaSet();
	}

	public boolean add(BZDColumnRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDColumnRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDColumnRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDColumnRelaSchema get(int index) {
		BZDColumnRelaSchema tSchema = (BZDColumnRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDColumnRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDColumnRelaSet aSet) {
		return super.set(aSet);
	}
}
 