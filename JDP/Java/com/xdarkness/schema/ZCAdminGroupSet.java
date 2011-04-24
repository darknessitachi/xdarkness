package com.xdarkness.schema;

import com.xdarkness.schema.ZCAdminGroupSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCAdminGroupSet extends SchemaSet {
	public ZCAdminGroupSet() {
		this(10,0);
	}

	public ZCAdminGroupSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCAdminGroupSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCAdminGroupSchema._TableCode;
		Columns = ZCAdminGroupSchema._Columns;
		NameSpace = ZCAdminGroupSchema._NameSpace;
		InsertAllSQL = ZCAdminGroupSchema._InsertAllSQL;
		UpdateAllSQL = ZCAdminGroupSchema._UpdateAllSQL;
		FillAllSQL = ZCAdminGroupSchema._FillAllSQL;
		DeleteSQL = ZCAdminGroupSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCAdminGroupSet();
	}

	public boolean add(ZCAdminGroupSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCAdminGroupSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCAdminGroupSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCAdminGroupSchema get(int index) {
		ZCAdminGroupSchema tSchema = (ZCAdminGroupSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCAdminGroupSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCAdminGroupSet aSet) {
		return super.set(aSet);
	}
}
 