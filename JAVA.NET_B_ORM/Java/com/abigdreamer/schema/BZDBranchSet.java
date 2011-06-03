package com.abigdreamer.schema;

import com.abigdreamer.schema.BZDBranchSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZDBranchSet extends SchemaSet {
	public BZDBranchSet() {
		this(10,0);
	}

	public BZDBranchSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDBranchSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDBranchSchema._TableCode;
		Columns = BZDBranchSchema._Columns;
		NameSpace = BZDBranchSchema._NameSpace;
		InsertAllSQL = BZDBranchSchema._InsertAllSQL;
		UpdateAllSQL = BZDBranchSchema._UpdateAllSQL;
		FillAllSQL = BZDBranchSchema._FillAllSQL;
		DeleteSQL = BZDBranchSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDBranchSet();
	}

	public boolean add(BZDBranchSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDBranchSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDBranchSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDBranchSchema get(int index) {
		BZDBranchSchema tSchema = (BZDBranchSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDBranchSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDBranchSet aSet) {
		return super.set(aSet);
	}
}
 