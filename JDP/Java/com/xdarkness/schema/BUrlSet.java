package com.xdarkness.schema;

import com.xdarkness.schema.BUrlSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BUrlSet extends SchemaSet {
	public BUrlSet() {
		this(10,0);
	}

	public BUrlSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BUrlSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BUrlSchema._TableCode;
		Columns = BUrlSchema._Columns;
		NameSpace = BUrlSchema._NameSpace;
		InsertAllSQL = BUrlSchema._InsertAllSQL;
		UpdateAllSQL = BUrlSchema._UpdateAllSQL;
		FillAllSQL = BUrlSchema._FillAllSQL;
		DeleteSQL = BUrlSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BUrlSet();
	}

	public boolean add(BUrlSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BUrlSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BUrlSchema aSchema) {
		return super.remove(aSchema);
	}

	public BUrlSchema get(int index) {
		BUrlSchema tSchema = (BUrlSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BUrlSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BUrlSet aSet) {
		return super.set(aSet);
	}
}
 