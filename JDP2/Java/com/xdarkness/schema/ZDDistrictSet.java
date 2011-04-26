package com.xdarkness.schema;

import com.xdarkness.schema.ZDDistrictSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZDDistrictSet extends SchemaSet {
	public ZDDistrictSet() {
		this(10,0);
	}

	public ZDDistrictSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDDistrictSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDDistrictSchema._TableCode;
		Columns = ZDDistrictSchema._Columns;
		NameSpace = ZDDistrictSchema._NameSpace;
		InsertAllSQL = ZDDistrictSchema._InsertAllSQL;
		UpdateAllSQL = ZDDistrictSchema._UpdateAllSQL;
		FillAllSQL = ZDDistrictSchema._FillAllSQL;
		DeleteSQL = ZDDistrictSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDDistrictSet();
	}

	public boolean add(ZDDistrictSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDDistrictSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDDistrictSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDDistrictSchema get(int index) {
		ZDDistrictSchema tSchema = (ZDDistrictSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDDistrictSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDDistrictSet aSet) {
		return super.set(aSet);
	}
}
 