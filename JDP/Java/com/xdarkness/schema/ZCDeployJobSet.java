package com.xdarkness.schema;

import com.xdarkness.schema.ZCDeployJobSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCDeployJobSet extends SchemaSet {
	public ZCDeployJobSet() {
		this(10,0);
	}

	public ZCDeployJobSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCDeployJobSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCDeployJobSchema._TableCode;
		Columns = ZCDeployJobSchema._Columns;
		NameSpace = ZCDeployJobSchema._NameSpace;
		InsertAllSQL = ZCDeployJobSchema._InsertAllSQL;
		UpdateAllSQL = ZCDeployJobSchema._UpdateAllSQL;
		FillAllSQL = ZCDeployJobSchema._FillAllSQL;
		DeleteSQL = ZCDeployJobSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCDeployJobSet();
	}

	public boolean add(ZCDeployJobSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCDeployJobSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCDeployJobSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCDeployJobSchema get(int index) {
		ZCDeployJobSchema tSchema = (ZCDeployJobSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCDeployJobSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCDeployJobSet aSet) {
		return super.set(aSet);
	}
}
 