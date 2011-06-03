package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCImagePlayerSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCImagePlayerSet extends SchemaSet {
	public BZCImagePlayerSet() {
		this(10,0);
	}

	public BZCImagePlayerSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCImagePlayerSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCImagePlayerSchema._TableCode;
		Columns = BZCImagePlayerSchema._Columns;
		NameSpace = BZCImagePlayerSchema._NameSpace;
		InsertAllSQL = BZCImagePlayerSchema._InsertAllSQL;
		UpdateAllSQL = BZCImagePlayerSchema._UpdateAllSQL;
		FillAllSQL = BZCImagePlayerSchema._FillAllSQL;
		DeleteSQL = BZCImagePlayerSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCImagePlayerSet();
	}

	public boolean add(BZCImagePlayerSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCImagePlayerSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCImagePlayerSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCImagePlayerSchema get(int index) {
		BZCImagePlayerSchema tSchema = (BZCImagePlayerSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCImagePlayerSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCImagePlayerSet aSet) {
		return super.set(aSet);
	}
}
 