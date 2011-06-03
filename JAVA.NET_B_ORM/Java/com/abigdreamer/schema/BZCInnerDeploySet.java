package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCInnerDeploySchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCInnerDeploySet extends SchemaSet {
	public BZCInnerDeploySet() {
		this(10,0);
	}

	public BZCInnerDeploySet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCInnerDeploySet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCInnerDeploySchema._TableCode;
		Columns = BZCInnerDeploySchema._Columns;
		NameSpace = BZCInnerDeploySchema._NameSpace;
		InsertAllSQL = BZCInnerDeploySchema._InsertAllSQL;
		UpdateAllSQL = BZCInnerDeploySchema._UpdateAllSQL;
		FillAllSQL = BZCInnerDeploySchema._FillAllSQL;
		DeleteSQL = BZCInnerDeploySchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCInnerDeploySet();
	}

	public boolean add(BZCInnerDeploySchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCInnerDeploySet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCInnerDeploySchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCInnerDeploySchema get(int index) {
		BZCInnerDeploySchema tSchema = (BZCInnerDeploySchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCInnerDeploySchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCInnerDeploySet aSet) {
		return super.set(aSet);
	}
}
 