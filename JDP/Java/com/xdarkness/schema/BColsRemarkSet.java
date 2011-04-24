package com.xdarkness.schema;

import com.xdarkness.schema.BColsRemarkSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BColsRemarkSet extends SchemaSet {
	public BColsRemarkSet() {
		this(10,0);
	}

	public BColsRemarkSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BColsRemarkSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BColsRemarkSchema._TableCode;
		Columns = BColsRemarkSchema._Columns;
		NameSpace = BColsRemarkSchema._NameSpace;
		InsertAllSQL = BColsRemarkSchema._InsertAllSQL;
		UpdateAllSQL = BColsRemarkSchema._UpdateAllSQL;
		FillAllSQL = BColsRemarkSchema._FillAllSQL;
		DeleteSQL = BColsRemarkSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BColsRemarkSet();
	}

	public boolean add(BColsRemarkSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BColsRemarkSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BColsRemarkSchema aSchema) {
		return super.remove(aSchema);
	}

	public BColsRemarkSchema get(int index) {
		BColsRemarkSchema tSchema = (BColsRemarkSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BColsRemarkSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BColsRemarkSet aSet) {
		return super.set(aSet);
	}
}
 