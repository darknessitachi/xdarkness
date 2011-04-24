package com.xdarkness.schema;

import com.xdarkness.schema.ZWStepSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZWStepSet extends SchemaSet {
	public ZWStepSet() {
		this(10,0);
	}

	public ZWStepSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZWStepSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZWStepSchema._TableCode;
		Columns = ZWStepSchema._Columns;
		NameSpace = ZWStepSchema._NameSpace;
		InsertAllSQL = ZWStepSchema._InsertAllSQL;
		UpdateAllSQL = ZWStepSchema._UpdateAllSQL;
		FillAllSQL = ZWStepSchema._FillAllSQL;
		DeleteSQL = ZWStepSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZWStepSet();
	}

	public boolean add(ZWStepSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZWStepSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZWStepSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZWStepSchema get(int index) {
		ZWStepSchema tSchema = (ZWStepSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZWStepSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZWStepSet aSet) {
		return super.set(aSet);
	}
}
 