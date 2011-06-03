package com.abigdreamer.schema;

import com.abigdreamer.schema.BZDMenuSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZDMenuSet extends SchemaSet {
	public BZDMenuSet() {
		this(10,0);
	}

	public BZDMenuSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDMenuSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDMenuSchema._TableCode;
		Columns = BZDMenuSchema._Columns;
		NameSpace = BZDMenuSchema._NameSpace;
		InsertAllSQL = BZDMenuSchema._InsertAllSQL;
		UpdateAllSQL = BZDMenuSchema._UpdateAllSQL;
		FillAllSQL = BZDMenuSchema._FillAllSQL;
		DeleteSQL = BZDMenuSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDMenuSet();
	}

	public boolean add(BZDMenuSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDMenuSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDMenuSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDMenuSchema get(int index) {
		BZDMenuSchema tSchema = (BZDMenuSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDMenuSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDMenuSet aSet) {
		return super.set(aSet);
	}
}
 