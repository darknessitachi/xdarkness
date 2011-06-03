package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCStatItemSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCStatItemSet extends SchemaSet {
	public BZCStatItemSet() {
		this(10,0);
	}

	public BZCStatItemSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCStatItemSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCStatItemSchema._TableCode;
		Columns = BZCStatItemSchema._Columns;
		NameSpace = BZCStatItemSchema._NameSpace;
		InsertAllSQL = BZCStatItemSchema._InsertAllSQL;
		UpdateAllSQL = BZCStatItemSchema._UpdateAllSQL;
		FillAllSQL = BZCStatItemSchema._FillAllSQL;
		DeleteSQL = BZCStatItemSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCStatItemSet();
	}

	public boolean add(BZCStatItemSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCStatItemSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCStatItemSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCStatItemSchema get(int index) {
		BZCStatItemSchema tSchema = (BZCStatItemSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCStatItemSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCStatItemSet aSet) {
		return super.set(aSet);
	}
}
 