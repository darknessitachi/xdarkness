package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCInnerDeploySchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCInnerDeploySet extends SchemaSet {
	public ZCInnerDeploySet() {
		this(10,0);
	}

	public ZCInnerDeploySet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCInnerDeploySet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCInnerDeploySchema._TableCode;
		Columns = ZCInnerDeploySchema._Columns;
		NameSpace = ZCInnerDeploySchema._NameSpace;
		InsertAllSQL = ZCInnerDeploySchema._InsertAllSQL;
		UpdateAllSQL = ZCInnerDeploySchema._UpdateAllSQL;
		FillAllSQL = ZCInnerDeploySchema._FillAllSQL;
		DeleteSQL = ZCInnerDeploySchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCInnerDeploySet();
	}

	public boolean add(ZCInnerDeploySchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCInnerDeploySet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCInnerDeploySchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCInnerDeploySchema get(int index) {
		ZCInnerDeploySchema tSchema = (ZCInnerDeploySchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCInnerDeploySchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCInnerDeploySet aSet) {
		return super.set(aSet);
	}
}
 