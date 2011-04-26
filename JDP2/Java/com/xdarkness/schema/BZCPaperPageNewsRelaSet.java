package com.xdarkness.schema;

import com.xdarkness.schema.BZCPaperPageNewsRelaSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCPaperPageNewsRelaSet extends SchemaSet {
	public BZCPaperPageNewsRelaSet() {
		this(10,0);
	}

	public BZCPaperPageNewsRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCPaperPageNewsRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCPaperPageNewsRelaSchema._TableCode;
		Columns = BZCPaperPageNewsRelaSchema._Columns;
		NameSpace = BZCPaperPageNewsRelaSchema._NameSpace;
		InsertAllSQL = BZCPaperPageNewsRelaSchema._InsertAllSQL;
		UpdateAllSQL = BZCPaperPageNewsRelaSchema._UpdateAllSQL;
		FillAllSQL = BZCPaperPageNewsRelaSchema._FillAllSQL;
		DeleteSQL = BZCPaperPageNewsRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCPaperPageNewsRelaSet();
	}

	public boolean add(BZCPaperPageNewsRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCPaperPageNewsRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCPaperPageNewsRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCPaperPageNewsRelaSchema get(int index) {
		BZCPaperPageNewsRelaSchema tSchema = (BZCPaperPageNewsRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCPaperPageNewsRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCPaperPageNewsRelaSet aSet) {
		return super.set(aSet);
	}
}
 