package com.xdarkness.schema;

import com.xdarkness.schema.ZCWebGatherSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCWebGatherSet extends SchemaSet {
	public ZCWebGatherSet() {
		this(10,0);
	}

	public ZCWebGatherSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCWebGatherSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCWebGatherSchema._TableCode;
		Columns = ZCWebGatherSchema._Columns;
		NameSpace = ZCWebGatherSchema._NameSpace;
		InsertAllSQL = ZCWebGatherSchema._InsertAllSQL;
		UpdateAllSQL = ZCWebGatherSchema._UpdateAllSQL;
		FillAllSQL = ZCWebGatherSchema._FillAllSQL;
		DeleteSQL = ZCWebGatherSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCWebGatherSet();
	}

	public boolean add(ZCWebGatherSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCWebGatherSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCWebGatherSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCWebGatherSchema get(int index) {
		ZCWebGatherSchema tSchema = (ZCWebGatherSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCWebGatherSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCWebGatherSet aSet) {
		return super.set(aSet);
	}
}
 