package com.xdarkness.schema;

import com.xdarkness.schema.BZCAdVisitLogSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCAdVisitLogSet extends SchemaSet {
	public BZCAdVisitLogSet() {
		this(10,0);
	}

	public BZCAdVisitLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCAdVisitLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCAdVisitLogSchema._TableCode;
		Columns = BZCAdVisitLogSchema._Columns;
		NameSpace = BZCAdVisitLogSchema._NameSpace;
		InsertAllSQL = BZCAdVisitLogSchema._InsertAllSQL;
		UpdateAllSQL = BZCAdVisitLogSchema._UpdateAllSQL;
		FillAllSQL = BZCAdVisitLogSchema._FillAllSQL;
		DeleteSQL = BZCAdVisitLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCAdVisitLogSet();
	}

	public boolean add(BZCAdVisitLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCAdVisitLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCAdVisitLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCAdVisitLogSchema get(int index) {
		BZCAdVisitLogSchema tSchema = (BZCAdVisitLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCAdVisitLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCAdVisitLogSet aSet) {
		return super.set(aSet);
	}
}
 