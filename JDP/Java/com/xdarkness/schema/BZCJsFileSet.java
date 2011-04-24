package com.xdarkness.schema;

import com.xdarkness.schema.BZCJsFileSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCJsFileSet extends SchemaSet {
	public BZCJsFileSet() {
		this(10,0);
	}

	public BZCJsFileSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCJsFileSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCJsFileSchema._TableCode;
		Columns = BZCJsFileSchema._Columns;
		NameSpace = BZCJsFileSchema._NameSpace;
		InsertAllSQL = BZCJsFileSchema._InsertAllSQL;
		UpdateAllSQL = BZCJsFileSchema._UpdateAllSQL;
		FillAllSQL = BZCJsFileSchema._FillAllSQL;
		DeleteSQL = BZCJsFileSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCJsFileSet();
	}

	public boolean add(BZCJsFileSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCJsFileSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCJsFileSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCJsFileSchema get(int index) {
		BZCJsFileSchema tSchema = (BZCJsFileSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCJsFileSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCJsFileSet aSet) {
		return super.set(aSet);
	}
}
 