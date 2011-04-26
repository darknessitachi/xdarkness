package com.xdarkness.schema;

import com.xdarkness.schema.BZDScheduleSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZDScheduleSet extends SchemaSet {
	public BZDScheduleSet() {
		this(10,0);
	}

	public BZDScheduleSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDScheduleSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDScheduleSchema._TableCode;
		Columns = BZDScheduleSchema._Columns;
		NameSpace = BZDScheduleSchema._NameSpace;
		InsertAllSQL = BZDScheduleSchema._InsertAllSQL;
		UpdateAllSQL = BZDScheduleSchema._UpdateAllSQL;
		FillAllSQL = BZDScheduleSchema._FillAllSQL;
		DeleteSQL = BZDScheduleSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDScheduleSet();
	}

	public boolean add(BZDScheduleSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDScheduleSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDScheduleSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDScheduleSchema get(int index) {
		BZDScheduleSchema tSchema = (BZDScheduleSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDScheduleSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDScheduleSet aSet) {
		return super.set(aSet);
	}
}
 