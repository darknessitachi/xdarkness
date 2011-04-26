package com.xdarkness.schema;

import com.xdarkness.schema.BZCAdvertisementSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCAdvertisementSet extends SchemaSet {
	public BZCAdvertisementSet() {
		this(10,0);
	}

	public BZCAdvertisementSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCAdvertisementSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCAdvertisementSchema._TableCode;
		Columns = BZCAdvertisementSchema._Columns;
		NameSpace = BZCAdvertisementSchema._NameSpace;
		InsertAllSQL = BZCAdvertisementSchema._InsertAllSQL;
		UpdateAllSQL = BZCAdvertisementSchema._UpdateAllSQL;
		FillAllSQL = BZCAdvertisementSchema._FillAllSQL;
		DeleteSQL = BZCAdvertisementSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCAdvertisementSet();
	}

	public boolean add(BZCAdvertisementSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCAdvertisementSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCAdvertisementSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCAdvertisementSchema get(int index) {
		BZCAdvertisementSchema tSchema = (BZCAdvertisementSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCAdvertisementSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCAdvertisementSet aSet) {
		return super.set(aSet);
	}
}
 