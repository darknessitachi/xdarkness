package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCVoteItemSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCVoteItemSet extends SchemaSet {
	public BZCVoteItemSet() {
		this(10,0);
	}

	public BZCVoteItemSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCVoteItemSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCVoteItemSchema._TableCode;
		Columns = BZCVoteItemSchema._Columns;
		NameSpace = BZCVoteItemSchema._NameSpace;
		InsertAllSQL = BZCVoteItemSchema._InsertAllSQL;
		UpdateAllSQL = BZCVoteItemSchema._UpdateAllSQL;
		FillAllSQL = BZCVoteItemSchema._FillAllSQL;
		DeleteSQL = BZCVoteItemSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCVoteItemSet();
	}

	public boolean add(BZCVoteItemSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCVoteItemSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCVoteItemSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCVoteItemSchema get(int index) {
		BZCVoteItemSchema tSchema = (BZCVoteItemSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCVoteItemSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCVoteItemSet aSet) {
		return super.set(aSet);
	}
}
 