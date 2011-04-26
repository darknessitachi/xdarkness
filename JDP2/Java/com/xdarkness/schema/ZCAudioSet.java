package com.xdarkness.schema;

import com.xdarkness.schema.ZCAudioSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCAudioSet extends SchemaSet {
	public ZCAudioSet() {
		this(10,0);
	}

	public ZCAudioSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCAudioSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCAudioSchema._TableCode;
		Columns = ZCAudioSchema._Columns;
		NameSpace = ZCAudioSchema._NameSpace;
		InsertAllSQL = ZCAudioSchema._InsertAllSQL;
		UpdateAllSQL = ZCAudioSchema._UpdateAllSQL;
		FillAllSQL = ZCAudioSchema._FillAllSQL;
		DeleteSQL = ZCAudioSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCAudioSet();
	}

	public boolean add(ZCAudioSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCAudioSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCAudioSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCAudioSchema get(int index) {
		ZCAudioSchema tSchema = (ZCAudioSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCAudioSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCAudioSet aSet) {
		return super.set(aSet);
	}
}
 