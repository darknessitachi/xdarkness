package com.xdarkness.schema;

import com.xdarkness.schema.BZDColumnSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZDColumnSet extends SchemaSet {
	public BZDColumnSet() {
		this(10,0);
	}

	public BZDColumnSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDColumnSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDColumnSchema._TableCode;
		Columns = BZDColumnSchema._Columns;
		NameSpace = BZDColumnSchema._NameSpace;
		InsertAllSQL = BZDColumnSchema._InsertAllSQL;
		UpdateAllSQL = BZDColumnSchema._UpdateAllSQL;
		FillAllSQL = BZDColumnSchema._FillAllSQL;
		DeleteSQL = BZDColumnSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDColumnSet();
	}

	public boolean add(BZDColumnSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDColumnSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDColumnSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDColumnSchema get(int index) {
		BZDColumnSchema tSchema = (BZDColumnSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDColumnSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDColumnSet aSet) {
		return super.set(aSet);
	}
}
 