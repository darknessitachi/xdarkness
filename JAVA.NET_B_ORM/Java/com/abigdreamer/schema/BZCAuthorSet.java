package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCAuthorSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCAuthorSet extends SchemaSet {
	public BZCAuthorSet() {
		this(10,0);
	}

	public BZCAuthorSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCAuthorSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCAuthorSchema._TableCode;
		Columns = BZCAuthorSchema._Columns;
		NameSpace = BZCAuthorSchema._NameSpace;
		InsertAllSQL = BZCAuthorSchema._InsertAllSQL;
		UpdateAllSQL = BZCAuthorSchema._UpdateAllSQL;
		FillAllSQL = BZCAuthorSchema._FillAllSQL;
		DeleteSQL = BZCAuthorSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCAuthorSet();
	}

	public boolean add(BZCAuthorSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCAuthorSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCAuthorSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCAuthorSchema get(int index) {
		BZCAuthorSchema tSchema = (BZCAuthorSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCAuthorSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCAuthorSet aSet) {
		return super.set(aSet);
	}
}
 