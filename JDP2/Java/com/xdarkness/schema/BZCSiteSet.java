package com.xdarkness.schema;

import com.xdarkness.schema.BZCSiteSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCSiteSet extends SchemaSet {
	public BZCSiteSet() {
		this(10,0);
	}

	public BZCSiteSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCSiteSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCSiteSchema._TableCode;
		Columns = BZCSiteSchema._Columns;
		NameSpace = BZCSiteSchema._NameSpace;
		InsertAllSQL = BZCSiteSchema._InsertAllSQL;
		UpdateAllSQL = BZCSiteSchema._UpdateAllSQL;
		FillAllSQL = BZCSiteSchema._FillAllSQL;
		DeleteSQL = BZCSiteSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCSiteSet();
	}

	public boolean add(BZCSiteSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCSiteSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCSiteSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCSiteSchema get(int index) {
		BZCSiteSchema tSchema = (BZCSiteSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCSiteSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCSiteSet aSet) {
		return super.set(aSet);
	}
}
 