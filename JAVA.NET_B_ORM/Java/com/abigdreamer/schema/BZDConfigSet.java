package com.abigdreamer.schema;

import com.abigdreamer.schema.BZDConfigSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZDConfigSet extends SchemaSet {
	public BZDConfigSet() {
		this(10,0);
	}

	public BZDConfigSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDConfigSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDConfigSchema._TableCode;
		Columns = BZDConfigSchema._Columns;
		NameSpace = BZDConfigSchema._NameSpace;
		InsertAllSQL = BZDConfigSchema._InsertAllSQL;
		UpdateAllSQL = BZDConfigSchema._UpdateAllSQL;
		FillAllSQL = BZDConfigSchema._FillAllSQL;
		DeleteSQL = BZDConfigSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDConfigSet();
	}

	public boolean add(BZDConfigSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDConfigSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDConfigSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDConfigSchema get(int index) {
		BZDConfigSchema tSchema = (BZDConfigSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDConfigSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDConfigSet aSet) {
		return super.set(aSet);
	}
}
 