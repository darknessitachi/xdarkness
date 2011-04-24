package com.xdarkness.schema;

import com.xdarkness.schema.BZWCurrentStepSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZWCurrentStepSet extends SchemaSet {
	public BZWCurrentStepSet() {
		this(10,0);
	}

	public BZWCurrentStepSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZWCurrentStepSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZWCurrentStepSchema._TableCode;
		Columns = BZWCurrentStepSchema._Columns;
		NameSpace = BZWCurrentStepSchema._NameSpace;
		InsertAllSQL = BZWCurrentStepSchema._InsertAllSQL;
		UpdateAllSQL = BZWCurrentStepSchema._UpdateAllSQL;
		FillAllSQL = BZWCurrentStepSchema._FillAllSQL;
		DeleteSQL = BZWCurrentStepSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZWCurrentStepSet();
	}

	public boolean add(BZWCurrentStepSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZWCurrentStepSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZWCurrentStepSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZWCurrentStepSchema get(int index) {
		BZWCurrentStepSchema tSchema = (BZWCurrentStepSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZWCurrentStepSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZWCurrentStepSet aSet) {
		return super.set(aSet);
	}
}
 