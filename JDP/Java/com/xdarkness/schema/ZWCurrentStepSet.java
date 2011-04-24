package com.xdarkness.schema;

import com.xdarkness.schema.ZWCurrentStepSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZWCurrentStepSet extends SchemaSet {
	public ZWCurrentStepSet() {
		this(10,0);
	}

	public ZWCurrentStepSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZWCurrentStepSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZWCurrentStepSchema._TableCode;
		Columns = ZWCurrentStepSchema._Columns;
		NameSpace = ZWCurrentStepSchema._NameSpace;
		InsertAllSQL = ZWCurrentStepSchema._InsertAllSQL;
		UpdateAllSQL = ZWCurrentStepSchema._UpdateAllSQL;
		FillAllSQL = ZWCurrentStepSchema._FillAllSQL;
		DeleteSQL = ZWCurrentStepSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZWCurrentStepSet();
	}

	public boolean add(ZWCurrentStepSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZWCurrentStepSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZWCurrentStepSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZWCurrentStepSchema get(int index) {
		ZWCurrentStepSchema tSchema = (ZWCurrentStepSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZWCurrentStepSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZWCurrentStepSet aSet) {
		return super.set(aSet);
	}
}
 