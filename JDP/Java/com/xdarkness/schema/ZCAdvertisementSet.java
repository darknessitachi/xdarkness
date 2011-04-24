package com.xdarkness.schema;

import com.xdarkness.schema.ZCAdvertisementSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCAdvertisementSet extends SchemaSet {
	public ZCAdvertisementSet() {
		this(10,0);
	}

	public ZCAdvertisementSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCAdvertisementSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCAdvertisementSchema._TableCode;
		Columns = ZCAdvertisementSchema._Columns;
		NameSpace = ZCAdvertisementSchema._NameSpace;
		InsertAllSQL = ZCAdvertisementSchema._InsertAllSQL;
		UpdateAllSQL = ZCAdvertisementSchema._UpdateAllSQL;
		FillAllSQL = ZCAdvertisementSchema._FillAllSQL;
		DeleteSQL = ZCAdvertisementSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCAdvertisementSet();
	}

	public boolean add(ZCAdvertisementSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCAdvertisementSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCAdvertisementSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCAdvertisementSchema get(int index) {
		ZCAdvertisementSchema tSchema = (ZCAdvertisementSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCAdvertisementSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCAdvertisementSet aSet) {
		return super.set(aSet);
	}
}
 