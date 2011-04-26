package com.xdarkness.schema;

import com.xdarkness.schema.BZDMaxNoSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZDMaxNoSet extends SchemaSet {
	public BZDMaxNoSet() {
		this(10,0);
	}

	public BZDMaxNoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDMaxNoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDMaxNoSchema._TableCode;
		Columns = BZDMaxNoSchema._Columns;
		NameSpace = BZDMaxNoSchema._NameSpace;
		InsertAllSQL = BZDMaxNoSchema._InsertAllSQL;
		UpdateAllSQL = BZDMaxNoSchema._UpdateAllSQL;
		FillAllSQL = BZDMaxNoSchema._FillAllSQL;
		DeleteSQL = BZDMaxNoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDMaxNoSet();
	}

	public boolean add(BZDMaxNoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDMaxNoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDMaxNoSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDMaxNoSchema get(int index) {
		BZDMaxNoSchema tSchema = (BZDMaxNoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDMaxNoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDMaxNoSet aSet) {
		return super.set(aSet);
	}
}
 