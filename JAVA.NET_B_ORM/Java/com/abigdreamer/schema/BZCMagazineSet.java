package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCMagazineSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCMagazineSet extends SchemaSet {
	public BZCMagazineSet() {
		this(10,0);
	}

	public BZCMagazineSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCMagazineSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCMagazineSchema._TableCode;
		Columns = BZCMagazineSchema._Columns;
		NameSpace = BZCMagazineSchema._NameSpace;
		InsertAllSQL = BZCMagazineSchema._InsertAllSQL;
		UpdateAllSQL = BZCMagazineSchema._UpdateAllSQL;
		FillAllSQL = BZCMagazineSchema._FillAllSQL;
		DeleteSQL = BZCMagazineSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCMagazineSet();
	}

	public boolean add(BZCMagazineSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCMagazineSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCMagazineSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCMagazineSchema get(int index) {
		BZCMagazineSchema tSchema = (BZCMagazineSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCMagazineSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCMagazineSet aSet) {
		return super.set(aSet);
	}
}
 