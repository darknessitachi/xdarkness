package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCDeployJobSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCDeployJobSet extends SchemaSet {
	public BZCDeployJobSet() {
		this(10,0);
	}

	public BZCDeployJobSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCDeployJobSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCDeployJobSchema._TableCode;
		Columns = BZCDeployJobSchema._Columns;
		NameSpace = BZCDeployJobSchema._NameSpace;
		InsertAllSQL = BZCDeployJobSchema._InsertAllSQL;
		UpdateAllSQL = BZCDeployJobSchema._UpdateAllSQL;
		FillAllSQL = BZCDeployJobSchema._FillAllSQL;
		DeleteSQL = BZCDeployJobSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCDeployJobSet();
	}

	public boolean add(BZCDeployJobSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCDeployJobSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCDeployJobSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCDeployJobSchema get(int index) {
		BZCDeployJobSchema tSchema = (BZCDeployJobSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCDeployJobSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCDeployJobSet aSet) {
		return super.set(aSet);
	}
}
 