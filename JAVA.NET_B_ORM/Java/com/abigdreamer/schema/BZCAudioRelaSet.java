package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCAudioRelaSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCAudioRelaSet extends SchemaSet {
	public BZCAudioRelaSet() {
		this(10,0);
	}

	public BZCAudioRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCAudioRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCAudioRelaSchema._TableCode;
		Columns = BZCAudioRelaSchema._Columns;
		NameSpace = BZCAudioRelaSchema._NameSpace;
		InsertAllSQL = BZCAudioRelaSchema._InsertAllSQL;
		UpdateAllSQL = BZCAudioRelaSchema._UpdateAllSQL;
		FillAllSQL = BZCAudioRelaSchema._FillAllSQL;
		DeleteSQL = BZCAudioRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCAudioRelaSet();
	}

	public boolean add(BZCAudioRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCAudioRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCAudioRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCAudioRelaSchema get(int index) {
		BZCAudioRelaSchema tSchema = (BZCAudioRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCAudioRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCAudioRelaSet aSet) {
		return super.set(aSet);
	}
}
 