package com.xdarkness.schema;

import com.xdarkness.schema.BZCPaperPageSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCPaperPageSet extends SchemaSet {
	public BZCPaperPageSet() {
		this(10,0);
	}

	public BZCPaperPageSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCPaperPageSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCPaperPageSchema._TableCode;
		Columns = BZCPaperPageSchema._Columns;
		NameSpace = BZCPaperPageSchema._NameSpace;
		InsertAllSQL = BZCPaperPageSchema._InsertAllSQL;
		UpdateAllSQL = BZCPaperPageSchema._UpdateAllSQL;
		FillAllSQL = BZCPaperPageSchema._FillAllSQL;
		DeleteSQL = BZCPaperPageSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCPaperPageSet();
	}

	public boolean add(BZCPaperPageSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCPaperPageSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCPaperPageSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCPaperPageSchema get(int index) {
		BZCPaperPageSchema tSchema = (BZCPaperPageSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCPaperPageSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCPaperPageSet aSet) {
		return super.set(aSet);
	}
}
 