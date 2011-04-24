package com.xdarkness.schema;

import com.xdarkness.schema.BZWStepSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZWStepSet extends SchemaSet {
	public BZWStepSet() {
		this(10,0);
	}

	public BZWStepSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZWStepSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZWStepSchema._TableCode;
		Columns = BZWStepSchema._Columns;
		NameSpace = BZWStepSchema._NameSpace;
		InsertAllSQL = BZWStepSchema._InsertAllSQL;
		UpdateAllSQL = BZWStepSchema._UpdateAllSQL;
		FillAllSQL = BZWStepSchema._FillAllSQL;
		DeleteSQL = BZWStepSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZWStepSet();
	}

	public boolean add(BZWStepSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZWStepSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZWStepSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZWStepSchema get(int index) {
		BZWStepSchema tSchema = (BZWStepSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZWStepSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZWStepSet aSet) {
		return super.set(aSet);
	}
}
 