package com.xdarkness.schema;

import com.xdarkness.schema.ZCVideoSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCVideoSet extends SchemaSet {
	public ZCVideoSet() {
		this(10,0);
	}

	public ZCVideoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCVideoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCVideoSchema._TableCode;
		Columns = ZCVideoSchema._Columns;
		NameSpace = ZCVideoSchema._NameSpace;
		InsertAllSQL = ZCVideoSchema._InsertAllSQL;
		UpdateAllSQL = ZCVideoSchema._UpdateAllSQL;
		FillAllSQL = ZCVideoSchema._FillAllSQL;
		DeleteSQL = ZCVideoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCVideoSet();
	}

	public boolean add(ZCVideoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCVideoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCVideoSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCVideoSchema get(int index) {
		ZCVideoSchema tSchema = (ZCVideoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCVideoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCVideoSet aSet) {
		return super.set(aSet);
	}
}
 