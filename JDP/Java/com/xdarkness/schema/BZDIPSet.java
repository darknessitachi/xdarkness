package com.xdarkness.schema;

import com.xdarkness.schema.BZDIPSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZDIPSet extends SchemaSet {
	public BZDIPSet() {
		this(10,0);
	}

	public BZDIPSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDIPSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDIPSchema._TableCode;
		Columns = BZDIPSchema._Columns;
		NameSpace = BZDIPSchema._NameSpace;
		InsertAllSQL = BZDIPSchema._InsertAllSQL;
		UpdateAllSQL = BZDIPSchema._UpdateAllSQL;
		FillAllSQL = BZDIPSchema._FillAllSQL;
		DeleteSQL = BZDIPSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDIPSet();
	}

	public boolean add(BZDIPSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDIPSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDIPSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDIPSchema get(int index) {
		BZDIPSchema tSchema = (BZDIPSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDIPSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDIPSet aSet) {
		return super.set(aSet);
	}
}
 