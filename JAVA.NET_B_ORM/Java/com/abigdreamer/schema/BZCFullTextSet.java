package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCFullTextSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCFullTextSet extends SchemaSet {
	public BZCFullTextSet() {
		this(10,0);
	}

	public BZCFullTextSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCFullTextSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCFullTextSchema._TableCode;
		Columns = BZCFullTextSchema._Columns;
		NameSpace = BZCFullTextSchema._NameSpace;
		InsertAllSQL = BZCFullTextSchema._InsertAllSQL;
		UpdateAllSQL = BZCFullTextSchema._UpdateAllSQL;
		FillAllSQL = BZCFullTextSchema._FillAllSQL;
		DeleteSQL = BZCFullTextSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCFullTextSet();
	}

	public boolean add(BZCFullTextSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCFullTextSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCFullTextSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCFullTextSchema get(int index) {
		BZCFullTextSchema tSchema = (BZCFullTextSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCFullTextSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCFullTextSet aSet) {
		return super.set(aSet);
	}
}
 