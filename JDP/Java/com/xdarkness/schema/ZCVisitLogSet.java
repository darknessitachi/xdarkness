package com.xdarkness.schema;

import com.xdarkness.schema.ZCVisitLogSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCVisitLogSet extends SchemaSet {
	public ZCVisitLogSet() {
		this(10,0);
	}

	public ZCVisitLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCVisitLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCVisitLogSchema._TableCode;
		Columns = ZCVisitLogSchema._Columns;
		NameSpace = ZCVisitLogSchema._NameSpace;
		InsertAllSQL = ZCVisitLogSchema._InsertAllSQL;
		UpdateAllSQL = ZCVisitLogSchema._UpdateAllSQL;
		FillAllSQL = ZCVisitLogSchema._FillAllSQL;
		DeleteSQL = ZCVisitLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCVisitLogSet();
	}

	public boolean add(ZCVisitLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCVisitLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCVisitLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCVisitLogSchema get(int index) {
		ZCVisitLogSchema tSchema = (ZCVisitLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCVisitLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCVisitLogSet aSet) {
		return super.set(aSet);
	}
}
 