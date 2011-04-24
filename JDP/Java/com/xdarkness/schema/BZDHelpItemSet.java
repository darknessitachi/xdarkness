package com.xdarkness.schema;

import com.xdarkness.schema.BZDHelpItemSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZDHelpItemSet extends SchemaSet {
	public BZDHelpItemSet() {
		this(10,0);
	}

	public BZDHelpItemSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDHelpItemSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDHelpItemSchema._TableCode;
		Columns = BZDHelpItemSchema._Columns;
		NameSpace = BZDHelpItemSchema._NameSpace;
		InsertAllSQL = BZDHelpItemSchema._InsertAllSQL;
		UpdateAllSQL = BZDHelpItemSchema._UpdateAllSQL;
		FillAllSQL = BZDHelpItemSchema._FillAllSQL;
		DeleteSQL = BZDHelpItemSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDHelpItemSet();
	}

	public boolean add(BZDHelpItemSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDHelpItemSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDHelpItemSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDHelpItemSchema get(int index) {
		BZDHelpItemSchema tSchema = (BZDHelpItemSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDHelpItemSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDHelpItemSet aSet) {
		return super.set(aSet);
	}
}
 