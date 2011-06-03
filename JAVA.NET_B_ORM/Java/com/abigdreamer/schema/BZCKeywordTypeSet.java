package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCKeywordTypeSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCKeywordTypeSet extends SchemaSet {
	public BZCKeywordTypeSet() {
		this(10,0);
	}

	public BZCKeywordTypeSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCKeywordTypeSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCKeywordTypeSchema._TableCode;
		Columns = BZCKeywordTypeSchema._Columns;
		NameSpace = BZCKeywordTypeSchema._NameSpace;
		InsertAllSQL = BZCKeywordTypeSchema._InsertAllSQL;
		UpdateAllSQL = BZCKeywordTypeSchema._UpdateAllSQL;
		FillAllSQL = BZCKeywordTypeSchema._FillAllSQL;
		DeleteSQL = BZCKeywordTypeSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCKeywordTypeSet();
	}

	public boolean add(BZCKeywordTypeSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCKeywordTypeSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCKeywordTypeSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCKeywordTypeSchema get(int index) {
		BZCKeywordTypeSchema tSchema = (BZCKeywordTypeSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCKeywordTypeSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCKeywordTypeSet aSet) {
		return super.set(aSet);
	}
}
 