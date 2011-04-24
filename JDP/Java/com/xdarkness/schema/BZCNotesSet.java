package com.xdarkness.schema;

import com.xdarkness.schema.BZCNotesSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCNotesSet extends SchemaSet {
	public BZCNotesSet() {
		this(10,0);
	}

	public BZCNotesSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCNotesSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCNotesSchema._TableCode;
		Columns = BZCNotesSchema._Columns;
		NameSpace = BZCNotesSchema._NameSpace;
		InsertAllSQL = BZCNotesSchema._InsertAllSQL;
		UpdateAllSQL = BZCNotesSchema._UpdateAllSQL;
		FillAllSQL = BZCNotesSchema._FillAllSQL;
		DeleteSQL = BZCNotesSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCNotesSet();
	}

	public boolean add(BZCNotesSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCNotesSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCNotesSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCNotesSchema get(int index) {
		BZCNotesSchema tSchema = (BZCNotesSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCNotesSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCNotesSet aSet) {
		return super.set(aSet);
	}
}
 