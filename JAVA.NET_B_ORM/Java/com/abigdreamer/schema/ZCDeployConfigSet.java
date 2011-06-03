package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCDeployConfigSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCDeployConfigSet extends SchemaSet {
	public ZCDeployConfigSet() {
		this(10,0);
	}

	public ZCDeployConfigSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCDeployConfigSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCDeployConfigSchema._TableCode;
		Columns = ZCDeployConfigSchema._Columns;
		NameSpace = ZCDeployConfigSchema._NameSpace;
		InsertAllSQL = ZCDeployConfigSchema._InsertAllSQL;
		UpdateAllSQL = ZCDeployConfigSchema._UpdateAllSQL;
		FillAllSQL = ZCDeployConfigSchema._FillAllSQL;
		DeleteSQL = ZCDeployConfigSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCDeployConfigSet();
	}

	public boolean add(ZCDeployConfigSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCDeployConfigSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCDeployConfigSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCDeployConfigSchema get(int index) {
		ZCDeployConfigSchema tSchema = (ZCDeployConfigSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCDeployConfigSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCDeployConfigSet aSet) {
		return super.set(aSet);
	}
}
 