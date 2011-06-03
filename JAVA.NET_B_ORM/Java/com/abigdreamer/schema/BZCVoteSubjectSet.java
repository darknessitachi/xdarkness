package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCVoteSubjectSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCVoteSubjectSet extends SchemaSet {
	public BZCVoteSubjectSet() {
		this(10,0);
	}

	public BZCVoteSubjectSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCVoteSubjectSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCVoteSubjectSchema._TableCode;
		Columns = BZCVoteSubjectSchema._Columns;
		NameSpace = BZCVoteSubjectSchema._NameSpace;
		InsertAllSQL = BZCVoteSubjectSchema._InsertAllSQL;
		UpdateAllSQL = BZCVoteSubjectSchema._UpdateAllSQL;
		FillAllSQL = BZCVoteSubjectSchema._FillAllSQL;
		DeleteSQL = BZCVoteSubjectSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCVoteSubjectSet();
	}

	public boolean add(BZCVoteSubjectSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCVoteSubjectSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCVoteSubjectSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCVoteSubjectSchema get(int index) {
		BZCVoteSubjectSchema tSchema = (BZCVoteSubjectSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCVoteSubjectSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCVoteSubjectSet aSet) {
		return super.set(aSet);
	}
}
 