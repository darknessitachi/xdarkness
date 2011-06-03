package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCAudioRelaSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCAudioRelaSet extends SchemaSet {
	public ZCAudioRelaSet() {
		this(10,0);
	}

	public ZCAudioRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCAudioRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCAudioRelaSchema._TableCode;
		Columns = ZCAudioRelaSchema._Columns;
		NameSpace = ZCAudioRelaSchema._NameSpace;
		InsertAllSQL = ZCAudioRelaSchema._InsertAllSQL;
		UpdateAllSQL = ZCAudioRelaSchema._UpdateAllSQL;
		FillAllSQL = ZCAudioRelaSchema._FillAllSQL;
		DeleteSQL = ZCAudioRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCAudioRelaSet();
	}

	public boolean add(ZCAudioRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCAudioRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCAudioRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCAudioRelaSchema get(int index) {
		ZCAudioRelaSchema tSchema = (ZCAudioRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCAudioRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCAudioRelaSet aSet) {
		return super.set(aSet);
	}
}
 