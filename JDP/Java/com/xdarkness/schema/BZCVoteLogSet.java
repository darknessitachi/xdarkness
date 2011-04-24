package com.xdarkness.schema;

import com.xdarkness.schema.BZCVoteLogSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCVoteLogSet extends SchemaSet {
	public BZCVoteLogSet() {
		this(10,0);
	}

	public BZCVoteLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCVoteLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCVoteLogSchema._TableCode;
		Columns = BZCVoteLogSchema._Columns;
		NameSpace = BZCVoteLogSchema._NameSpace;
		InsertAllSQL = BZCVoteLogSchema._InsertAllSQL;
		UpdateAllSQL = BZCVoteLogSchema._UpdateAllSQL;
		FillAllSQL = BZCVoteLogSchema._FillAllSQL;
		DeleteSQL = BZCVoteLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCVoteLogSet();
	}

	public boolean add(BZCVoteLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCVoteLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCVoteLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCVoteLogSchema get(int index) {
		BZCVoteLogSchema tSchema = (BZCVoteLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCVoteLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCVoteLogSet aSet) {
		return super.set(aSet);
	}
}
 