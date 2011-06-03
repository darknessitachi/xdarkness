package com.abigdreamer.schema;

import com.abigdreamer.schema.BZWInstanceSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZWInstanceSet extends SchemaSet {
	public BZWInstanceSet() {
		this(10,0);
	}

	public BZWInstanceSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZWInstanceSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZWInstanceSchema._TableCode;
		Columns = BZWInstanceSchema._Columns;
		NameSpace = BZWInstanceSchema._NameSpace;
		InsertAllSQL = BZWInstanceSchema._InsertAllSQL;
		UpdateAllSQL = BZWInstanceSchema._UpdateAllSQL;
		FillAllSQL = BZWInstanceSchema._FillAllSQL;
		DeleteSQL = BZWInstanceSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZWInstanceSet();
	}

	public boolean add(BZWInstanceSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZWInstanceSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZWInstanceSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZWInstanceSchema get(int index) {
		BZWInstanceSchema tSchema = (BZWInstanceSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZWInstanceSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZWInstanceSet aSet) {
		return super.set(aSet);
	}
}
 