package com.abigdreamer.schema;

import com.abigdreamer.schema.BZDUserLogSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZDUserLogSet extends SchemaSet {
	public BZDUserLogSet() {
		this(10,0);
	}

	public BZDUserLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDUserLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDUserLogSchema._TableCode;
		Columns = BZDUserLogSchema._Columns;
		NameSpace = BZDUserLogSchema._NameSpace;
		InsertAllSQL = BZDUserLogSchema._InsertAllSQL;
		UpdateAllSQL = BZDUserLogSchema._UpdateAllSQL;
		FillAllSQL = BZDUserLogSchema._FillAllSQL;
		DeleteSQL = BZDUserLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDUserLogSet();
	}

	public boolean add(BZDUserLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDUserLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDUserLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDUserLogSchema get(int index) {
		BZDUserLogSchema tSchema = (BZDUserLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDUserLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDUserLogSet aSet) {
		return super.set(aSet);
	}
}
 