package com.xdarkness.schema;

import com.xdarkness.schema.ZCPostSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCPostSet extends SchemaSet {
	public ZCPostSet() {
		this(10,0);
	}

	public ZCPostSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCPostSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCPostSchema._TableCode;
		Columns = ZCPostSchema._Columns;
		NameSpace = ZCPostSchema._NameSpace;
		InsertAllSQL = ZCPostSchema._InsertAllSQL;
		UpdateAllSQL = ZCPostSchema._UpdateAllSQL;
		FillAllSQL = ZCPostSchema._FillAllSQL;
		DeleteSQL = ZCPostSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCPostSet();
	}

	public boolean add(ZCPostSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCPostSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCPostSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCPostSchema get(int index) {
		ZCPostSchema tSchema = (ZCPostSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCPostSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCPostSet aSet) {
		return super.set(aSet);
	}
}
 