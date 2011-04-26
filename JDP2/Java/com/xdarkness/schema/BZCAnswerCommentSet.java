package com.xdarkness.schema;

import com.xdarkness.schema.BZCAnswerCommentSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCAnswerCommentSet extends SchemaSet {
	public BZCAnswerCommentSet() {
		this(10,0);
	}

	public BZCAnswerCommentSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCAnswerCommentSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCAnswerCommentSchema._TableCode;
		Columns = BZCAnswerCommentSchema._Columns;
		NameSpace = BZCAnswerCommentSchema._NameSpace;
		InsertAllSQL = BZCAnswerCommentSchema._InsertAllSQL;
		UpdateAllSQL = BZCAnswerCommentSchema._UpdateAllSQL;
		FillAllSQL = BZCAnswerCommentSchema._FillAllSQL;
		DeleteSQL = BZCAnswerCommentSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCAnswerCommentSet();
	}

	public boolean add(BZCAnswerCommentSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCAnswerCommentSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCAnswerCommentSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCAnswerCommentSchema get(int index) {
		BZCAnswerCommentSchema tSchema = (BZCAnswerCommentSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCAnswerCommentSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCAnswerCommentSet aSet) {
		return super.set(aSet);
	}
}
 