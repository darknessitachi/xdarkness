package com.xdarkness.schema;

import com.xdarkness.schema.BZCAudioSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCAudioSet extends SchemaSet {
	public BZCAudioSet() {
		this(10,0);
	}

	public BZCAudioSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCAudioSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCAudioSchema._TableCode;
		Columns = BZCAudioSchema._Columns;
		NameSpace = BZCAudioSchema._NameSpace;
		InsertAllSQL = BZCAudioSchema._InsertAllSQL;
		UpdateAllSQL = BZCAudioSchema._UpdateAllSQL;
		FillAllSQL = BZCAudioSchema._FillAllSQL;
		DeleteSQL = BZCAudioSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCAudioSet();
	}

	public boolean add(BZCAudioSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCAudioSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCAudioSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCAudioSchema get(int index) {
		BZCAudioSchema tSchema = (BZCAudioSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCAudioSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCAudioSet aSet) {
		return super.set(aSet);
	}
}
 