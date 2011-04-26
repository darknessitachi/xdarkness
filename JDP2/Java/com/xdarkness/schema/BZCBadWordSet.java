package com.xdarkness.schema;

import com.xdarkness.schema.BZCBadWordSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCBadWordSet extends SchemaSet {
	public BZCBadWordSet() {
		this(10,0);
	}

	public BZCBadWordSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCBadWordSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCBadWordSchema._TableCode;
		Columns = BZCBadWordSchema._Columns;
		NameSpace = BZCBadWordSchema._NameSpace;
		InsertAllSQL = BZCBadWordSchema._InsertAllSQL;
		UpdateAllSQL = BZCBadWordSchema._UpdateAllSQL;
		FillAllSQL = BZCBadWordSchema._FillAllSQL;
		DeleteSQL = BZCBadWordSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCBadWordSet();
	}

	public boolean add(BZCBadWordSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCBadWordSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCBadWordSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCBadWordSchema get(int index) {
		BZCBadWordSchema tSchema = (BZCBadWordSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCBadWordSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCBadWordSet aSet) {
		return super.set(aSet);
	}
}
 