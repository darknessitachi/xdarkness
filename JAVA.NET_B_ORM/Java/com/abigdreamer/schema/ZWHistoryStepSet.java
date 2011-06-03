package com.abigdreamer.schema;

import com.abigdreamer.schema.ZWHistoryStepSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZWHistoryStepSet extends SchemaSet {
	public ZWHistoryStepSet() {
		this(10,0);
	}

	public ZWHistoryStepSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZWHistoryStepSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZWHistoryStepSchema._TableCode;
		Columns = ZWHistoryStepSchema._Columns;
		NameSpace = ZWHistoryStepSchema._NameSpace;
		InsertAllSQL = ZWHistoryStepSchema._InsertAllSQL;
		UpdateAllSQL = ZWHistoryStepSchema._UpdateAllSQL;
		FillAllSQL = ZWHistoryStepSchema._FillAllSQL;
		DeleteSQL = ZWHistoryStepSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZWHistoryStepSet();
	}

	public boolean add(ZWHistoryStepSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZWHistoryStepSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZWHistoryStepSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZWHistoryStepSchema get(int index) {
		ZWHistoryStepSchema tSchema = (ZWHistoryStepSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZWHistoryStepSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZWHistoryStepSet aSet) {
		return super.set(aSet);
	}
}
 