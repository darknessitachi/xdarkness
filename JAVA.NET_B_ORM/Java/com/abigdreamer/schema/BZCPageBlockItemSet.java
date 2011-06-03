package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCPageBlockItemSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCPageBlockItemSet extends SchemaSet {
	public BZCPageBlockItemSet() {
		this(10,0);
	}

	public BZCPageBlockItemSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCPageBlockItemSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCPageBlockItemSchema._TableCode;
		Columns = BZCPageBlockItemSchema._Columns;
		NameSpace = BZCPageBlockItemSchema._NameSpace;
		InsertAllSQL = BZCPageBlockItemSchema._InsertAllSQL;
		UpdateAllSQL = BZCPageBlockItemSchema._UpdateAllSQL;
		FillAllSQL = BZCPageBlockItemSchema._FillAllSQL;
		DeleteSQL = BZCPageBlockItemSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCPageBlockItemSet();
	}

	public boolean add(BZCPageBlockItemSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCPageBlockItemSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCPageBlockItemSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCPageBlockItemSchema get(int index) {
		BZCPageBlockItemSchema tSchema = (BZCPageBlockItemSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCPageBlockItemSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCPageBlockItemSet aSet) {
		return super.set(aSet);
	}
}
 