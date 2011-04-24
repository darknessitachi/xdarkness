package com.xdarkness.schema;

import com.xdarkness.schema.BZDCodeSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZDCodeSet extends SchemaSet {
	public BZDCodeSet() {
		this(10,0);
	}

	public BZDCodeSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDCodeSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDCodeSchema._TableCode;
		Columns = BZDCodeSchema._Columns;
		NameSpace = BZDCodeSchema._NameSpace;
		InsertAllSQL = BZDCodeSchema._InsertAllSQL;
		UpdateAllSQL = BZDCodeSchema._UpdateAllSQL;
		FillAllSQL = BZDCodeSchema._FillAllSQL;
		DeleteSQL = BZDCodeSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDCodeSet();
	}

	public boolean add(BZDCodeSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDCodeSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDCodeSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDCodeSchema get(int index) {
		BZDCodeSchema tSchema = (BZDCodeSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDCodeSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDCodeSet aSet) {
		return super.set(aSet);
	}
}
 