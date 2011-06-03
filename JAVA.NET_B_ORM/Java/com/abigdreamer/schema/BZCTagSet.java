package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCTagSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCTagSet extends SchemaSet {
	public BZCTagSet() {
		this(10,0);
	}

	public BZCTagSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCTagSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCTagSchema._TableCode;
		Columns = BZCTagSchema._Columns;
		NameSpace = BZCTagSchema._NameSpace;
		InsertAllSQL = BZCTagSchema._InsertAllSQL;
		UpdateAllSQL = BZCTagSchema._UpdateAllSQL;
		FillAllSQL = BZCTagSchema._FillAllSQL;
		DeleteSQL = BZCTagSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCTagSet();
	}

	public boolean add(BZCTagSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCTagSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCTagSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCTagSchema get(int index) {
		BZCTagSchema tSchema = (BZCTagSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCTagSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCTagSet aSet) {
		return super.set(aSet);
	}
}
 