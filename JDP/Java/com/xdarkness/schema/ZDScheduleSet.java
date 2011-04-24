package com.xdarkness.schema;

import com.xdarkness.schema.ZDScheduleSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZDScheduleSet extends SchemaSet {
	public ZDScheduleSet() {
		this(10,0);
	}

	public ZDScheduleSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDScheduleSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDScheduleSchema._TableCode;
		Columns = ZDScheduleSchema._Columns;
		NameSpace = ZDScheduleSchema._NameSpace;
		InsertAllSQL = ZDScheduleSchema._InsertAllSQL;
		UpdateAllSQL = ZDScheduleSchema._UpdateAllSQL;
		FillAllSQL = ZDScheduleSchema._FillAllSQL;
		DeleteSQL = ZDScheduleSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDScheduleSet();
	}

	public boolean add(ZDScheduleSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDScheduleSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDScheduleSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDScheduleSchema get(int index) {
		ZDScheduleSchema tSchema = (ZDScheduleSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDScheduleSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDScheduleSet aSet) {
		return super.set(aSet);
	}
}
 