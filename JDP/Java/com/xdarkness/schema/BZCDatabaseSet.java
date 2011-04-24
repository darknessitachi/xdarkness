package com.xdarkness.schema;

import com.xdarkness.schema.BZCDatabaseSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCDatabaseSet extends SchemaSet {
	public BZCDatabaseSet() {
		this(10,0);
	}

	public BZCDatabaseSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCDatabaseSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCDatabaseSchema._TableCode;
		Columns = BZCDatabaseSchema._Columns;
		NameSpace = BZCDatabaseSchema._NameSpace;
		InsertAllSQL = BZCDatabaseSchema._InsertAllSQL;
		UpdateAllSQL = BZCDatabaseSchema._UpdateAllSQL;
		FillAllSQL = BZCDatabaseSchema._FillAllSQL;
		DeleteSQL = BZCDatabaseSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCDatabaseSet();
	}

	public boolean add(BZCDatabaseSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCDatabaseSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCDatabaseSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCDatabaseSchema get(int index) {
		BZCDatabaseSchema tSchema = (BZCDatabaseSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCDatabaseSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCDatabaseSet aSet) {
		return super.set(aSet);
	}
}
 