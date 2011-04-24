package com.xdarkness.schema;

import com.xdarkness.schema.BZCLinkSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCLinkSet extends SchemaSet {
	public BZCLinkSet() {
		this(10,0);
	}

	public BZCLinkSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCLinkSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCLinkSchema._TableCode;
		Columns = BZCLinkSchema._Columns;
		NameSpace = BZCLinkSchema._NameSpace;
		InsertAllSQL = BZCLinkSchema._InsertAllSQL;
		UpdateAllSQL = BZCLinkSchema._UpdateAllSQL;
		FillAllSQL = BZCLinkSchema._FillAllSQL;
		DeleteSQL = BZCLinkSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCLinkSet();
	}

	public boolean add(BZCLinkSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCLinkSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCLinkSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCLinkSchema get(int index) {
		BZCLinkSchema tSchema = (BZCLinkSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCLinkSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCLinkSet aSet) {
		return super.set(aSet);
	}
}
 