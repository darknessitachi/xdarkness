package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCVisitLogSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCVisitLogSet extends SchemaSet {
	public BZCVisitLogSet() {
		this(10,0);
	}

	public BZCVisitLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCVisitLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCVisitLogSchema._TableCode;
		Columns = BZCVisitLogSchema._Columns;
		NameSpace = BZCVisitLogSchema._NameSpace;
		InsertAllSQL = BZCVisitLogSchema._InsertAllSQL;
		UpdateAllSQL = BZCVisitLogSchema._UpdateAllSQL;
		FillAllSQL = BZCVisitLogSchema._FillAllSQL;
		DeleteSQL = BZCVisitLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCVisitLogSet();
	}

	public boolean add(BZCVisitLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCVisitLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCVisitLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCVisitLogSchema get(int index) {
		BZCVisitLogSchema tSchema = (BZCVisitLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCVisitLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCVisitLogSet aSet) {
		return super.set(aSet);
	}
}
 