package com.xdarkness.schema;

import com.xdarkness.schema.ZCInnerGatherSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCInnerGatherSet extends SchemaSet {
	public ZCInnerGatherSet() {
		this(10,0);
	}

	public ZCInnerGatherSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCInnerGatherSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCInnerGatherSchema._TableCode;
		Columns = ZCInnerGatherSchema._Columns;
		NameSpace = ZCInnerGatherSchema._NameSpace;
		InsertAllSQL = ZCInnerGatherSchema._InsertAllSQL;
		UpdateAllSQL = ZCInnerGatherSchema._UpdateAllSQL;
		FillAllSQL = ZCInnerGatherSchema._FillAllSQL;
		DeleteSQL = ZCInnerGatherSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCInnerGatherSet();
	}

	public boolean add(ZCInnerGatherSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCInnerGatherSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCInnerGatherSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCInnerGatherSchema get(int index) {
		ZCInnerGatherSchema tSchema = (ZCInnerGatherSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCInnerGatherSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCInnerGatherSet aSet) {
		return super.set(aSet);
	}
}
 