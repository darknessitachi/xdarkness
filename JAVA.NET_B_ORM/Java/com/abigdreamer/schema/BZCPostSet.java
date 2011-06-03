package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCPostSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCPostSet extends SchemaSet {
	public BZCPostSet() {
		this(10,0);
	}

	public BZCPostSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCPostSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCPostSchema._TableCode;
		Columns = BZCPostSchema._Columns;
		NameSpace = BZCPostSchema._NameSpace;
		InsertAllSQL = BZCPostSchema._InsertAllSQL;
		UpdateAllSQL = BZCPostSchema._UpdateAllSQL;
		FillAllSQL = BZCPostSchema._FillAllSQL;
		DeleteSQL = BZCPostSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCPostSet();
	}

	public boolean add(BZCPostSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCPostSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCPostSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCPostSchema get(int index) {
		BZCPostSchema tSchema = (BZCPostSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCPostSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCPostSet aSet) {
		return super.set(aSet);
	}
}
 