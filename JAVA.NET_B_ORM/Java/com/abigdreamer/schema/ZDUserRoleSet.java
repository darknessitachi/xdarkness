package com.abigdreamer.schema;

import com.abigdreamer.schema.ZDUserRoleSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZDUserRoleSet extends SchemaSet {
	public ZDUserRoleSet() {
		this(10,0);
	}

	public ZDUserRoleSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDUserRoleSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDUserRoleSchema._TableCode;
		Columns = ZDUserRoleSchema._Columns;
		NameSpace = ZDUserRoleSchema._NameSpace;
		InsertAllSQL = ZDUserRoleSchema._InsertAllSQL;
		UpdateAllSQL = ZDUserRoleSchema._UpdateAllSQL;
		FillAllSQL = ZDUserRoleSchema._FillAllSQL;
		DeleteSQL = ZDUserRoleSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDUserRoleSet();
	}

	public boolean add(ZDUserRoleSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDUserRoleSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDUserRoleSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDUserRoleSchema get(int index) {
		ZDUserRoleSchema tSchema = (ZDUserRoleSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDUserRoleSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDUserRoleSet aSet) {
		return super.set(aSet);
	}
}
 