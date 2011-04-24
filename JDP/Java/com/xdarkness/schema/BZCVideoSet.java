package com.xdarkness.schema;

import com.xdarkness.schema.BZCVideoSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCVideoSet extends SchemaSet {
	public BZCVideoSet() {
		this(10,0);
	}

	public BZCVideoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCVideoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCVideoSchema._TableCode;
		Columns = BZCVideoSchema._Columns;
		NameSpace = BZCVideoSchema._NameSpace;
		InsertAllSQL = BZCVideoSchema._InsertAllSQL;
		UpdateAllSQL = BZCVideoSchema._UpdateAllSQL;
		FillAllSQL = BZCVideoSchema._FillAllSQL;
		DeleteSQL = BZCVideoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCVideoSet();
	}

	public boolean add(BZCVideoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCVideoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCVideoSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCVideoSchema get(int index) {
		BZCVideoSchema tSchema = (BZCVideoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCVideoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCVideoSet aSet) {
		return super.set(aSet);
	}
}
 