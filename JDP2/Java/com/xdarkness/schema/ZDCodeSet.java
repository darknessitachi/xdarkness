package com.xdarkness.schema;

import com.xdarkness.schema.ZDCodeSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZDCodeSet extends SchemaSet {
	public ZDCodeSet() {
		this(10,0);
	}

	public ZDCodeSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDCodeSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDCodeSchema._TableCode;
		Columns = ZDCodeSchema._Columns;
		NameSpace = ZDCodeSchema._NameSpace;
		InsertAllSQL = ZDCodeSchema._InsertAllSQL;
		UpdateAllSQL = ZDCodeSchema._UpdateAllSQL;
		FillAllSQL = ZDCodeSchema._FillAllSQL;
		DeleteSQL = ZDCodeSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDCodeSet();
	}

	public boolean add(ZDCodeSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDCodeSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDCodeSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDCodeSchema get(int index) {
		ZDCodeSchema tSchema = (ZDCodeSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDCodeSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDCodeSet aSet) {
		return super.set(aSet);
	}
}
 