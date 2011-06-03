package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCDeployLogSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCDeployLogSet extends SchemaSet {
	public BZCDeployLogSet() {
		this(10,0);
	}

	public BZCDeployLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCDeployLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCDeployLogSchema._TableCode;
		Columns = BZCDeployLogSchema._Columns;
		NameSpace = BZCDeployLogSchema._NameSpace;
		InsertAllSQL = BZCDeployLogSchema._InsertAllSQL;
		UpdateAllSQL = BZCDeployLogSchema._UpdateAllSQL;
		FillAllSQL = BZCDeployLogSchema._FillAllSQL;
		DeleteSQL = BZCDeployLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCDeployLogSet();
	}

	public boolean add(BZCDeployLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCDeployLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCDeployLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCDeployLogSchema get(int index) {
		BZCDeployLogSchema tSchema = (BZCDeployLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCDeployLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCDeployLogSet aSet) {
		return super.set(aSet);
	}
}
 