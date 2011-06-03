package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCQuestionSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCQuestionSet extends SchemaSet {
	public BZCQuestionSet() {
		this(10,0);
	}

	public BZCQuestionSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCQuestionSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCQuestionSchema._TableCode;
		Columns = BZCQuestionSchema._Columns;
		NameSpace = BZCQuestionSchema._NameSpace;
		InsertAllSQL = BZCQuestionSchema._InsertAllSQL;
		UpdateAllSQL = BZCQuestionSchema._UpdateAllSQL;
		FillAllSQL = BZCQuestionSchema._FillAllSQL;
		DeleteSQL = BZCQuestionSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCQuestionSet();
	}

	public boolean add(BZCQuestionSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCQuestionSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCQuestionSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCQuestionSchema get(int index) {
		BZCQuestionSchema tSchema = (BZCQuestionSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCQuestionSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCQuestionSet aSet) {
		return super.set(aSet);
	}
}
 