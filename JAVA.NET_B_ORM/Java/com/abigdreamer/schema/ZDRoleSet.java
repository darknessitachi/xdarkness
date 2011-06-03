package com.abigdreamer.schema;

import com.abigdreamer.schema.ZDRoleSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZDRoleSet extends SchemaSet {
	public ZDRoleSet() {
		this(10,0);
	}

	public ZDRoleSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDRoleSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDRoleSchema._TableCode;
		Columns = ZDRoleSchema._Columns;
		NameSpace = ZDRoleSchema._NameSpace;
		InsertAllSQL = ZDRoleSchema._InsertAllSQL;
		UpdateAllSQL = ZDRoleSchema._UpdateAllSQL;
		FillAllSQL = ZDRoleSchema._FillAllSQL;
		DeleteSQL = ZDRoleSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDRoleSet();
	}

	public boolean add(ZDRoleSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDRoleSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDRoleSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDRoleSchema get(int index) {
		ZDRoleSchema tSchema = (ZDRoleSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDRoleSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDRoleSet aSet) {
		return super.set(aSet);
	}
}
 