package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCDeployConfigSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCDeployConfigSet extends SchemaSet {
	public BZCDeployConfigSet() {
		this(10,0);
	}

	public BZCDeployConfigSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCDeployConfigSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCDeployConfigSchema._TableCode;
		Columns = BZCDeployConfigSchema._Columns;
		NameSpace = BZCDeployConfigSchema._NameSpace;
		InsertAllSQL = BZCDeployConfigSchema._InsertAllSQL;
		UpdateAllSQL = BZCDeployConfigSchema._UpdateAllSQL;
		FillAllSQL = BZCDeployConfigSchema._FillAllSQL;
		DeleteSQL = BZCDeployConfigSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCDeployConfigSet();
	}

	public boolean add(BZCDeployConfigSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCDeployConfigSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCDeployConfigSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCDeployConfigSchema get(int index) {
		BZCDeployConfigSchema tSchema = (BZCDeployConfigSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCDeployConfigSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCDeployConfigSet aSet) {
		return super.set(aSet);
	}
}
 