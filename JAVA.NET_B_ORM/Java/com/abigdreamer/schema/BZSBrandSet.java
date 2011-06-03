package com.abigdreamer.schema;

import com.abigdreamer.schema.BZSBrandSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZSBrandSet extends SchemaSet {
	public BZSBrandSet() {
		this(10,0);
	}

	public BZSBrandSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZSBrandSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZSBrandSchema._TableCode;
		Columns = BZSBrandSchema._Columns;
		NameSpace = BZSBrandSchema._NameSpace;
		InsertAllSQL = BZSBrandSchema._InsertAllSQL;
		UpdateAllSQL = BZSBrandSchema._UpdateAllSQL;
		FillAllSQL = BZSBrandSchema._FillAllSQL;
		DeleteSQL = BZSBrandSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZSBrandSet();
	}

	public boolean add(BZSBrandSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZSBrandSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZSBrandSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZSBrandSchema get(int index) {
		BZSBrandSchema tSchema = (BZSBrandSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZSBrandSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZSBrandSet aSet) {
		return super.set(aSet);
	}
}
 