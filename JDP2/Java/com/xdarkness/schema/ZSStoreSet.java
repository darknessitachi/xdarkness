package com.xdarkness.schema;

import com.xdarkness.schema.ZSStoreSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZSStoreSet extends SchemaSet {
	public ZSStoreSet() {
		this(10,0);
	}

	public ZSStoreSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZSStoreSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZSStoreSchema._TableCode;
		Columns = ZSStoreSchema._Columns;
		NameSpace = ZSStoreSchema._NameSpace;
		InsertAllSQL = ZSStoreSchema._InsertAllSQL;
		UpdateAllSQL = ZSStoreSchema._UpdateAllSQL;
		FillAllSQL = ZSStoreSchema._FillAllSQL;
		DeleteSQL = ZSStoreSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZSStoreSet();
	}

	public boolean add(ZSStoreSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZSStoreSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZSStoreSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZSStoreSchema get(int index) {
		ZSStoreSchema tSchema = (ZSStoreSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZSStoreSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZSStoreSet aSet) {
		return super.set(aSet);
	}
}
 