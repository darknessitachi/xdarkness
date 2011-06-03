package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCAdVisitLogSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCAdVisitLogSet extends SchemaSet {
	public ZCAdVisitLogSet() {
		this(10,0);
	}

	public ZCAdVisitLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCAdVisitLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCAdVisitLogSchema._TableCode;
		Columns = ZCAdVisitLogSchema._Columns;
		NameSpace = ZCAdVisitLogSchema._NameSpace;
		InsertAllSQL = ZCAdVisitLogSchema._InsertAllSQL;
		UpdateAllSQL = ZCAdVisitLogSchema._UpdateAllSQL;
		FillAllSQL = ZCAdVisitLogSchema._FillAllSQL;
		DeleteSQL = ZCAdVisitLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCAdVisitLogSet();
	}

	public boolean add(ZCAdVisitLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCAdVisitLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCAdVisitLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCAdVisitLogSchema get(int index) {
		ZCAdVisitLogSchema tSchema = (ZCAdVisitLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCAdVisitLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCAdVisitLogSet aSet) {
		return super.set(aSet);
	}
}
 