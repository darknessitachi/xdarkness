package com.xdarkness.schema;

import com.xdarkness.schema.ZDBranchSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZDBranchSet extends SchemaSet {
	public ZDBranchSet() {
		this(10,0);
	}

	public ZDBranchSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDBranchSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDBranchSchema._TableCode;
		Columns = ZDBranchSchema._Columns;
		NameSpace = ZDBranchSchema._NameSpace;
		InsertAllSQL = ZDBranchSchema._InsertAllSQL;
		UpdateAllSQL = ZDBranchSchema._UpdateAllSQL;
		FillAllSQL = ZDBranchSchema._FillAllSQL;
		DeleteSQL = ZDBranchSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDBranchSet();
	}

	public boolean add(ZDBranchSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDBranchSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDBranchSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDBranchSchema get(int index) {
		ZDBranchSchema tSchema = (ZDBranchSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDBranchSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDBranchSet aSet) {
		return super.set(aSet);
	}
}
 