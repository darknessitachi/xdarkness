package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCQuestionGroupSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCQuestionGroupSet extends SchemaSet {
	public BZCQuestionGroupSet() {
		this(10,0);
	}

	public BZCQuestionGroupSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCQuestionGroupSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCQuestionGroupSchema._TableCode;
		Columns = BZCQuestionGroupSchema._Columns;
		NameSpace = BZCQuestionGroupSchema._NameSpace;
		InsertAllSQL = BZCQuestionGroupSchema._InsertAllSQL;
		UpdateAllSQL = BZCQuestionGroupSchema._UpdateAllSQL;
		FillAllSQL = BZCQuestionGroupSchema._FillAllSQL;
		DeleteSQL = BZCQuestionGroupSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCQuestionGroupSet();
	}

	public boolean add(BZCQuestionGroupSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCQuestionGroupSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCQuestionGroupSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCQuestionGroupSchema get(int index) {
		BZCQuestionGroupSchema tSchema = (BZCQuestionGroupSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCQuestionGroupSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCQuestionGroupSet aSet) {
		return super.set(aSet);
	}
}
 