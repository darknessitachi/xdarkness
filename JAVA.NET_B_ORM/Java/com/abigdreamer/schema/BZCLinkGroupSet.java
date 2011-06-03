package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCLinkGroupSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCLinkGroupSet extends SchemaSet {
	public BZCLinkGroupSet() {
		this(10,0);
	}

	public BZCLinkGroupSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCLinkGroupSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCLinkGroupSchema._TableCode;
		Columns = BZCLinkGroupSchema._Columns;
		NameSpace = BZCLinkGroupSchema._NameSpace;
		InsertAllSQL = BZCLinkGroupSchema._InsertAllSQL;
		UpdateAllSQL = BZCLinkGroupSchema._UpdateAllSQL;
		FillAllSQL = BZCLinkGroupSchema._FillAllSQL;
		DeleteSQL = BZCLinkGroupSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCLinkGroupSet();
	}

	public boolean add(BZCLinkGroupSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCLinkGroupSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCLinkGroupSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCLinkGroupSchema get(int index) {
		BZCLinkGroupSchema tSchema = (BZCLinkGroupSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCLinkGroupSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCLinkGroupSet aSet) {
		return super.set(aSet);
	}
}
 