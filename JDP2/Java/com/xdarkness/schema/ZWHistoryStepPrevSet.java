package com.xdarkness.schema;

import com.xdarkness.schema.ZWHistoryStepPrevSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZWHistoryStepPrevSet extends SchemaSet {
	public ZWHistoryStepPrevSet() {
		this(10,0);
	}

	public ZWHistoryStepPrevSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZWHistoryStepPrevSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZWHistoryStepPrevSchema._TableCode;
		Columns = ZWHistoryStepPrevSchema._Columns;
		NameSpace = ZWHistoryStepPrevSchema._NameSpace;
		InsertAllSQL = ZWHistoryStepPrevSchema._InsertAllSQL;
		UpdateAllSQL = ZWHistoryStepPrevSchema._UpdateAllSQL;
		FillAllSQL = ZWHistoryStepPrevSchema._FillAllSQL;
		DeleteSQL = ZWHistoryStepPrevSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZWHistoryStepPrevSet();
	}

	public boolean add(ZWHistoryStepPrevSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZWHistoryStepPrevSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZWHistoryStepPrevSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZWHistoryStepPrevSchema get(int index) {
		ZWHistoryStepPrevSchema tSchema = (ZWHistoryStepPrevSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZWHistoryStepPrevSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZWHistoryStepPrevSet aSet) {
		return super.set(aSet);
	}
}
 