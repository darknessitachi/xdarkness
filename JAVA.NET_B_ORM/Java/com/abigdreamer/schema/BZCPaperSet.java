package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCPaperSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCPaperSet extends SchemaSet {
	public BZCPaperSet() {
		this(10,0);
	}

	public BZCPaperSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCPaperSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCPaperSchema._TableCode;
		Columns = BZCPaperSchema._Columns;
		NameSpace = BZCPaperSchema._NameSpace;
		InsertAllSQL = BZCPaperSchema._InsertAllSQL;
		UpdateAllSQL = BZCPaperSchema._UpdateAllSQL;
		FillAllSQL = BZCPaperSchema._FillAllSQL;
		DeleteSQL = BZCPaperSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCPaperSet();
	}

	public boolean add(BZCPaperSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCPaperSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCPaperSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCPaperSchema get(int index) {
		BZCPaperSchema tSchema = (BZCPaperSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCPaperSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCPaperSet aSet) {
		return super.set(aSet);
	}
}
 