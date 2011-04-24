package com.xdarkness.schema;

import com.xdarkness.schema.ZCAuthorSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCAuthorSet extends SchemaSet {
	public ZCAuthorSet() {
		this(10,0);
	}

	public ZCAuthorSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCAuthorSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCAuthorSchema._TableCode;
		Columns = ZCAuthorSchema._Columns;
		NameSpace = ZCAuthorSchema._NameSpace;
		InsertAllSQL = ZCAuthorSchema._InsertAllSQL;
		UpdateAllSQL = ZCAuthorSchema._UpdateAllSQL;
		FillAllSQL = ZCAuthorSchema._FillAllSQL;
		DeleteSQL = ZCAuthorSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCAuthorSet();
	}

	public boolean add(ZCAuthorSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCAuthorSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCAuthorSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCAuthorSchema get(int index) {
		ZCAuthorSchema tSchema = (ZCAuthorSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCAuthorSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCAuthorSet aSet) {
		return super.set(aSet);
	}
}
 