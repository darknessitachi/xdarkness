package com.abigdreamer.schema;

import com.abigdreamer.schema.BZWHistoryStepSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZWHistoryStepSet extends SchemaSet {
	public BZWHistoryStepSet() {
		this(10,0);
	}

	public BZWHistoryStepSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZWHistoryStepSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZWHistoryStepSchema._TableCode;
		Columns = BZWHistoryStepSchema._Columns;
		NameSpace = BZWHistoryStepSchema._NameSpace;
		InsertAllSQL = BZWHistoryStepSchema._InsertAllSQL;
		UpdateAllSQL = BZWHistoryStepSchema._UpdateAllSQL;
		FillAllSQL = BZWHistoryStepSchema._FillAllSQL;
		DeleteSQL = BZWHistoryStepSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZWHistoryStepSet();
	}

	public boolean add(BZWHistoryStepSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZWHistoryStepSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZWHistoryStepSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZWHistoryStepSchema get(int index) {
		BZWHistoryStepSchema tSchema = (BZWHistoryStepSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZWHistoryStepSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZWHistoryStepSet aSet) {
		return super.set(aSet);
	}
}
 