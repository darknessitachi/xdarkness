package com.xdarkness.schema;

import com.xdarkness.schema.BZCMessageSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCMessageSet extends SchemaSet {
	public BZCMessageSet() {
		this(10,0);
	}

	public BZCMessageSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCMessageSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCMessageSchema._TableCode;
		Columns = BZCMessageSchema._Columns;
		NameSpace = BZCMessageSchema._NameSpace;
		InsertAllSQL = BZCMessageSchema._InsertAllSQL;
		UpdateAllSQL = BZCMessageSchema._UpdateAllSQL;
		FillAllSQL = BZCMessageSchema._FillAllSQL;
		DeleteSQL = BZCMessageSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCMessageSet();
	}

	public boolean add(BZCMessageSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCMessageSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCMessageSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCMessageSchema get(int index) {
		BZCMessageSchema tSchema = (BZCMessageSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCMessageSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCMessageSet aSet) {
		return super.set(aSet);
	}
}
 