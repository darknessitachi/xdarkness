package com.xdarkness.schema;

import com.xdarkness.schema.ZCApprovalSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCApprovalSet extends SchemaSet {
	public ZCApprovalSet() {
		this(10,0);
	}

	public ZCApprovalSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCApprovalSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCApprovalSchema._TableCode;
		Columns = ZCApprovalSchema._Columns;
		NameSpace = ZCApprovalSchema._NameSpace;
		InsertAllSQL = ZCApprovalSchema._InsertAllSQL;
		UpdateAllSQL = ZCApprovalSchema._UpdateAllSQL;
		FillAllSQL = ZCApprovalSchema._FillAllSQL;
		DeleteSQL = ZCApprovalSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCApprovalSet();
	}

	public boolean add(ZCApprovalSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCApprovalSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCApprovalSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCApprovalSchema get(int index) {
		ZCApprovalSchema tSchema = (ZCApprovalSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCApprovalSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCApprovalSet aSet) {
		return super.set(aSet);
	}
}
 