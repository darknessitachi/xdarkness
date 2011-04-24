package com.xdarkness.schema;

import com.xdarkness.schema.BZWCurrentStepPrevSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZWCurrentStepPrevSet extends SchemaSet {
	public BZWCurrentStepPrevSet() {
		this(10,0);
	}

	public BZWCurrentStepPrevSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZWCurrentStepPrevSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZWCurrentStepPrevSchema._TableCode;
		Columns = BZWCurrentStepPrevSchema._Columns;
		NameSpace = BZWCurrentStepPrevSchema._NameSpace;
		InsertAllSQL = BZWCurrentStepPrevSchema._InsertAllSQL;
		UpdateAllSQL = BZWCurrentStepPrevSchema._UpdateAllSQL;
		FillAllSQL = BZWCurrentStepPrevSchema._FillAllSQL;
		DeleteSQL = BZWCurrentStepPrevSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZWCurrentStepPrevSet();
	}

	public boolean add(BZWCurrentStepPrevSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZWCurrentStepPrevSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZWCurrentStepPrevSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZWCurrentStepPrevSchema get(int index) {
		BZWCurrentStepPrevSchema tSchema = (BZWCurrentStepPrevSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZWCurrentStepPrevSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZWCurrentStepPrevSet aSet) {
		return super.set(aSet);
	}
}
 