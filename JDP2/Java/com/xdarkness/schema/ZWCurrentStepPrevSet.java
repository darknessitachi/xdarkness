package com.xdarkness.schema;

import com.xdarkness.schema.ZWCurrentStepPrevSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZWCurrentStepPrevSet extends SchemaSet {
	public ZWCurrentStepPrevSet() {
		this(10,0);
	}

	public ZWCurrentStepPrevSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZWCurrentStepPrevSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZWCurrentStepPrevSchema._TableCode;
		Columns = ZWCurrentStepPrevSchema._Columns;
		NameSpace = ZWCurrentStepPrevSchema._NameSpace;
		InsertAllSQL = ZWCurrentStepPrevSchema._InsertAllSQL;
		UpdateAllSQL = ZWCurrentStepPrevSchema._UpdateAllSQL;
		FillAllSQL = ZWCurrentStepPrevSchema._FillAllSQL;
		DeleteSQL = ZWCurrentStepPrevSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZWCurrentStepPrevSet();
	}

	public boolean add(ZWCurrentStepPrevSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZWCurrentStepPrevSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZWCurrentStepPrevSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZWCurrentStepPrevSchema get(int index) {
		ZWCurrentStepPrevSchema tSchema = (ZWCurrentStepPrevSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZWCurrentStepPrevSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZWCurrentStepPrevSet aSet) {
		return super.set(aSet);
	}
}
 