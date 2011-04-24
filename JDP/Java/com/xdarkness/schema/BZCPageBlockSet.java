package com.xdarkness.schema;

import com.xdarkness.schema.BZCPageBlockSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCPageBlockSet extends SchemaSet {
	public BZCPageBlockSet() {
		this(10,0);
	}

	public BZCPageBlockSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCPageBlockSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCPageBlockSchema._TableCode;
		Columns = BZCPageBlockSchema._Columns;
		NameSpace = BZCPageBlockSchema._NameSpace;
		InsertAllSQL = BZCPageBlockSchema._InsertAllSQL;
		UpdateAllSQL = BZCPageBlockSchema._UpdateAllSQL;
		FillAllSQL = BZCPageBlockSchema._FillAllSQL;
		DeleteSQL = BZCPageBlockSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCPageBlockSet();
	}

	public boolean add(BZCPageBlockSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCPageBlockSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCPageBlockSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCPageBlockSchema get(int index) {
		BZCPageBlockSchema tSchema = (BZCPageBlockSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCPageBlockSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCPageBlockSet aSet) {
		return super.set(aSet);
	}
}
 