package com.xdarkness.schema;

import com.xdarkness.schema.BZCCommentSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCCommentSet extends SchemaSet {
	public BZCCommentSet() {
		this(10,0);
	}

	public BZCCommentSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCCommentSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCCommentSchema._TableCode;
		Columns = BZCCommentSchema._Columns;
		NameSpace = BZCCommentSchema._NameSpace;
		InsertAllSQL = BZCCommentSchema._InsertAllSQL;
		UpdateAllSQL = BZCCommentSchema._UpdateAllSQL;
		FillAllSQL = BZCCommentSchema._FillAllSQL;
		DeleteSQL = BZCCommentSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCCommentSet();
	}

	public boolean add(BZCCommentSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCCommentSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCCommentSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCCommentSchema get(int index) {
		BZCCommentSchema tSchema = (BZCCommentSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCCommentSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCCommentSet aSet) {
		return super.set(aSet);
	}
}
 