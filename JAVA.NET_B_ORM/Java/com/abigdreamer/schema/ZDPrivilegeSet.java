package com.abigdreamer.schema;

import com.abigdreamer.schema.ZDPrivilegeSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZDPrivilegeSet extends SchemaSet {
	public ZDPrivilegeSet() {
		this(10,0);
	}

	public ZDPrivilegeSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDPrivilegeSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDPrivilegeSchema._TableCode;
		Columns = ZDPrivilegeSchema._Columns;
		NameSpace = ZDPrivilegeSchema._NameSpace;
		InsertAllSQL = ZDPrivilegeSchema._InsertAllSQL;
		UpdateAllSQL = ZDPrivilegeSchema._UpdateAllSQL;
		FillAllSQL = ZDPrivilegeSchema._FillAllSQL;
		DeleteSQL = ZDPrivilegeSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDPrivilegeSet();
	}

	public boolean add(ZDPrivilegeSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDPrivilegeSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDPrivilegeSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDPrivilegeSchema get(int index) {
		ZDPrivilegeSchema tSchema = (ZDPrivilegeSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDPrivilegeSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDPrivilegeSet aSet) {
		return super.set(aSet);
	}
}
 