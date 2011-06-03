package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCDBGatherSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCDBGatherSet extends SchemaSet {
	public BZCDBGatherSet() {
		this(10,0);
	}

	public BZCDBGatherSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCDBGatherSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCDBGatherSchema._TableCode;
		Columns = BZCDBGatherSchema._Columns;
		NameSpace = BZCDBGatherSchema._NameSpace;
		InsertAllSQL = BZCDBGatherSchema._InsertAllSQL;
		UpdateAllSQL = BZCDBGatherSchema._UpdateAllSQL;
		FillAllSQL = BZCDBGatherSchema._FillAllSQL;
		DeleteSQL = BZCDBGatherSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCDBGatherSet();
	}

	public boolean add(BZCDBGatherSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCDBGatherSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCDBGatherSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCDBGatherSchema get(int index) {
		BZCDBGatherSchema tSchema = (BZCDBGatherSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCDBGatherSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCDBGatherSet aSet) {
		return super.set(aSet);
	}
}
 