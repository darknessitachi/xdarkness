package com.xdarkness.schema;

import com.xdarkness.schema.BZWHistoryStepPrevSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZWHistoryStepPrevSet extends SchemaSet {
	public BZWHistoryStepPrevSet() {
		this(10,0);
	}

	public BZWHistoryStepPrevSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZWHistoryStepPrevSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZWHistoryStepPrevSchema._TableCode;
		Columns = BZWHistoryStepPrevSchema._Columns;
		NameSpace = BZWHistoryStepPrevSchema._NameSpace;
		InsertAllSQL = BZWHistoryStepPrevSchema._InsertAllSQL;
		UpdateAllSQL = BZWHistoryStepPrevSchema._UpdateAllSQL;
		FillAllSQL = BZWHistoryStepPrevSchema._FillAllSQL;
		DeleteSQL = BZWHistoryStepPrevSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZWHistoryStepPrevSet();
	}

	public boolean add(BZWHistoryStepPrevSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZWHistoryStepPrevSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZWHistoryStepPrevSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZWHistoryStepPrevSchema get(int index) {
		BZWHistoryStepPrevSchema tSchema = (BZWHistoryStepPrevSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZWHistoryStepPrevSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZWHistoryStepPrevSet aSet) {
		return super.set(aSet);
	}
}
 