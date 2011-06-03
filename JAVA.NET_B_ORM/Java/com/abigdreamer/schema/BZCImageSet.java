package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCImageSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCImageSet extends SchemaSet {
	public BZCImageSet() {
		this(10,0);
	}

	public BZCImageSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCImageSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCImageSchema._TableCode;
		Columns = BZCImageSchema._Columns;
		NameSpace = BZCImageSchema._NameSpace;
		InsertAllSQL = BZCImageSchema._InsertAllSQL;
		UpdateAllSQL = BZCImageSchema._UpdateAllSQL;
		FillAllSQL = BZCImageSchema._FillAllSQL;
		DeleteSQL = BZCImageSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCImageSet();
	}

	public boolean add(BZCImageSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCImageSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCImageSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCImageSchema get(int index) {
		BZCImageSchema tSchema = (BZCImageSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCImageSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCImageSet aSet) {
		return super.set(aSet);
	}
}
 