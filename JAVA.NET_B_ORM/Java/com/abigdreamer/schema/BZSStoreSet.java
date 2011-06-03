package com.abigdreamer.schema;

import com.abigdreamer.schema.BZSStoreSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZSStoreSet extends SchemaSet {
	public BZSStoreSet() {
		this(10,0);
	}

	public BZSStoreSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZSStoreSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZSStoreSchema._TableCode;
		Columns = BZSStoreSchema._Columns;
		NameSpace = BZSStoreSchema._NameSpace;
		InsertAllSQL = BZSStoreSchema._InsertAllSQL;
		UpdateAllSQL = BZSStoreSchema._UpdateAllSQL;
		FillAllSQL = BZSStoreSchema._FillAllSQL;
		DeleteSQL = BZSStoreSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZSStoreSet();
	}

	public boolean add(BZSStoreSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZSStoreSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZSStoreSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZSStoreSchema get(int index) {
		BZSStoreSchema tSchema = (BZSStoreSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZSStoreSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZSStoreSet aSet) {
		return super.set(aSet);
	}
}
 