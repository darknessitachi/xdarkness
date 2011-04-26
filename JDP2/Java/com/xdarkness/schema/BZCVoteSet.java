package com.xdarkness.schema;

import com.xdarkness.schema.BZCVoteSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCVoteSet extends SchemaSet {
	public BZCVoteSet() {
		this(10,0);
	}

	public BZCVoteSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCVoteSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCVoteSchema._TableCode;
		Columns = BZCVoteSchema._Columns;
		NameSpace = BZCVoteSchema._NameSpace;
		InsertAllSQL = BZCVoteSchema._InsertAllSQL;
		UpdateAllSQL = BZCVoteSchema._UpdateAllSQL;
		FillAllSQL = BZCVoteSchema._FillAllSQL;
		DeleteSQL = BZCVoteSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCVoteSet();
	}

	public boolean add(BZCVoteSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCVoteSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCVoteSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCVoteSchema get(int index) {
		BZCVoteSchema tSchema = (BZCVoteSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCVoteSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCVoteSet aSet) {
		return super.set(aSet);
	}
}
 