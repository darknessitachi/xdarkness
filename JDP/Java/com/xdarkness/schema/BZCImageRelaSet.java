package com.xdarkness.schema;

import com.xdarkness.schema.BZCImageRelaSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCImageRelaSet extends SchemaSet {
	public BZCImageRelaSet() {
		this(10,0);
	}

	public BZCImageRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCImageRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCImageRelaSchema._TableCode;
		Columns = BZCImageRelaSchema._Columns;
		NameSpace = BZCImageRelaSchema._NameSpace;
		InsertAllSQL = BZCImageRelaSchema._InsertAllSQL;
		UpdateAllSQL = BZCImageRelaSchema._UpdateAllSQL;
		FillAllSQL = BZCImageRelaSchema._FillAllSQL;
		DeleteSQL = BZCImageRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCImageRelaSet();
	}

	public boolean add(BZCImageRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCImageRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCImageRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCImageRelaSchema get(int index) {
		BZCImageRelaSchema tSchema = (BZCImageRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCImageRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCImageRelaSet aSet) {
		return super.set(aSet);
	}
}
 