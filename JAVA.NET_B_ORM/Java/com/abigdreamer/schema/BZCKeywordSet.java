package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCKeywordSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCKeywordSet extends SchemaSet {
	public BZCKeywordSet() {
		this(10,0);
	}

	public BZCKeywordSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCKeywordSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCKeywordSchema._TableCode;
		Columns = BZCKeywordSchema._Columns;
		NameSpace = BZCKeywordSchema._NameSpace;
		InsertAllSQL = BZCKeywordSchema._InsertAllSQL;
		UpdateAllSQL = BZCKeywordSchema._UpdateAllSQL;
		FillAllSQL = BZCKeywordSchema._FillAllSQL;
		DeleteSQL = BZCKeywordSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCKeywordSet();
	}

	public boolean add(BZCKeywordSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCKeywordSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCKeywordSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCKeywordSchema get(int index) {
		BZCKeywordSchema tSchema = (BZCKeywordSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCKeywordSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCKeywordSet aSet) {
		return super.set(aSet);
	}
}
 