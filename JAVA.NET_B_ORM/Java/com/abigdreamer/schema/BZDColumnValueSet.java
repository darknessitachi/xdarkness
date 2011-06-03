package com.abigdreamer.schema;

import com.abigdreamer.schema.BZDColumnValueSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZDColumnValueSet extends SchemaSet {
	public BZDColumnValueSet() {
		this(10,0);
	}

	public BZDColumnValueSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDColumnValueSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDColumnValueSchema._TableCode;
		Columns = BZDColumnValueSchema._Columns;
		NameSpace = BZDColumnValueSchema._NameSpace;
		InsertAllSQL = BZDColumnValueSchema._InsertAllSQL;
		UpdateAllSQL = BZDColumnValueSchema._UpdateAllSQL;
		FillAllSQL = BZDColumnValueSchema._FillAllSQL;
		DeleteSQL = BZDColumnValueSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDColumnValueSet();
	}

	public boolean add(BZDColumnValueSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDColumnValueSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDColumnValueSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDColumnValueSchema get(int index) {
		BZDColumnValueSchema tSchema = (BZDColumnValueSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDColumnValueSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDColumnValueSet aSet) {
		return super.set(aSet);
	}
}
 