package com.xdarkness.schema;

import com.xdarkness.schema.ZCLinkGroupSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCLinkGroupSet extends SchemaSet {
	public ZCLinkGroupSet() {
		this(10,0);
	}

	public ZCLinkGroupSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCLinkGroupSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCLinkGroupSchema._TableCode;
		Columns = ZCLinkGroupSchema._Columns;
		NameSpace = ZCLinkGroupSchema._NameSpace;
		InsertAllSQL = ZCLinkGroupSchema._InsertAllSQL;
		UpdateAllSQL = ZCLinkGroupSchema._UpdateAllSQL;
		FillAllSQL = ZCLinkGroupSchema._FillAllSQL;
		DeleteSQL = ZCLinkGroupSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCLinkGroupSet();
	}

	public boolean add(ZCLinkGroupSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCLinkGroupSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCLinkGroupSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCLinkGroupSchema get(int index) {
		ZCLinkGroupSchema tSchema = (ZCLinkGroupSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCLinkGroupSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCLinkGroupSet aSet) {
		return super.set(aSet);
	}
}
 