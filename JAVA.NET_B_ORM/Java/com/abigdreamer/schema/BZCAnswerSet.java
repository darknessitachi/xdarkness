package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCAnswerSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCAnswerSet extends SchemaSet {
	public BZCAnswerSet() {
		this(10,0);
	}

	public BZCAnswerSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCAnswerSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCAnswerSchema._TableCode;
		Columns = BZCAnswerSchema._Columns;
		NameSpace = BZCAnswerSchema._NameSpace;
		InsertAllSQL = BZCAnswerSchema._InsertAllSQL;
		UpdateAllSQL = BZCAnswerSchema._UpdateAllSQL;
		FillAllSQL = BZCAnswerSchema._FillAllSQL;
		DeleteSQL = BZCAnswerSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCAnswerSet();
	}

	public boolean add(BZCAnswerSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCAnswerSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCAnswerSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCAnswerSchema get(int index) {
		BZCAnswerSchema tSchema = (BZCAnswerSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCAnswerSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCAnswerSet aSet) {
		return super.set(aSet);
	}
}
 