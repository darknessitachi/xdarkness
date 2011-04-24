package com.xdarkness.schema;

import com.xdarkness.schema.ZCDatabaseSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCDatabaseSet extends SchemaSet {
	public ZCDatabaseSet() {
		this(10,0);
	}

	public ZCDatabaseSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCDatabaseSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCDatabaseSchema._TableCode;
		Columns = ZCDatabaseSchema._Columns;
		NameSpace = ZCDatabaseSchema._NameSpace;
		InsertAllSQL = ZCDatabaseSchema._InsertAllSQL;
		UpdateAllSQL = ZCDatabaseSchema._UpdateAllSQL;
		FillAllSQL = ZCDatabaseSchema._FillAllSQL;
		DeleteSQL = ZCDatabaseSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCDatabaseSet();
	}

	public boolean add(ZCDatabaseSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCDatabaseSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCDatabaseSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCDatabaseSchema get(int index) {
		ZCDatabaseSchema tSchema = (ZCDatabaseSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCDatabaseSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCDatabaseSet aSet) {
		return super.set(aSet);
	}
}
 