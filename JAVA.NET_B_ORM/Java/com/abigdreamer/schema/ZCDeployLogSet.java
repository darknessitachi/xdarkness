package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCDeployLogSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCDeployLogSet extends SchemaSet {
	public ZCDeployLogSet() {
		this(10,0);
	}

	public ZCDeployLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCDeployLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCDeployLogSchema._TableCode;
		Columns = ZCDeployLogSchema._Columns;
		NameSpace = ZCDeployLogSchema._NameSpace;
		InsertAllSQL = ZCDeployLogSchema._InsertAllSQL;
		UpdateAllSQL = ZCDeployLogSchema._UpdateAllSQL;
		FillAllSQL = ZCDeployLogSchema._FillAllSQL;
		DeleteSQL = ZCDeployLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCDeployLogSet();
	}

	public boolean add(ZCDeployLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCDeployLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCDeployLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCDeployLogSchema get(int index) {
		ZCDeployLogSchema tSchema = (ZCDeployLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCDeployLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCDeployLogSet aSet) {
		return super.set(aSet);
	}
}
 