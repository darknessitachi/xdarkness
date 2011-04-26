package com.xdarkness.schema;

import com.xdarkness.schema.BZCVideoRelaSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCVideoRelaSet extends SchemaSet {
	public BZCVideoRelaSet() {
		this(10,0);
	}

	public BZCVideoRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCVideoRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCVideoRelaSchema._TableCode;
		Columns = BZCVideoRelaSchema._Columns;
		NameSpace = BZCVideoRelaSchema._NameSpace;
		InsertAllSQL = BZCVideoRelaSchema._InsertAllSQL;
		UpdateAllSQL = BZCVideoRelaSchema._UpdateAllSQL;
		FillAllSQL = BZCVideoRelaSchema._FillAllSQL;
		DeleteSQL = BZCVideoRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCVideoRelaSet();
	}

	public boolean add(BZCVideoRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCVideoRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCVideoRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCVideoRelaSchema get(int index) {
		BZCVideoRelaSchema tSchema = (BZCVideoRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCVideoRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCVideoRelaSet aSet) {
		return super.set(aSet);
	}
}
 