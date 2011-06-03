package com.abigdreamer.schema;

import com.abigdreamer.schema.BZDDistrictSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZDDistrictSet extends SchemaSet {
	public BZDDistrictSet() {
		this(10,0);
	}

	public BZDDistrictSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDDistrictSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDDistrictSchema._TableCode;
		Columns = BZDDistrictSchema._Columns;
		NameSpace = BZDDistrictSchema._NameSpace;
		InsertAllSQL = BZDDistrictSchema._InsertAllSQL;
		UpdateAllSQL = BZDDistrictSchema._UpdateAllSQL;
		FillAllSQL = BZDDistrictSchema._FillAllSQL;
		DeleteSQL = BZDDistrictSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDDistrictSet();
	}

	public boolean add(BZDDistrictSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDDistrictSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDDistrictSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDDistrictSchema get(int index) {
		BZDDistrictSchema tSchema = (BZDDistrictSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDDistrictSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDDistrictSet aSet) {
		return super.set(aSet);
	}
}
 