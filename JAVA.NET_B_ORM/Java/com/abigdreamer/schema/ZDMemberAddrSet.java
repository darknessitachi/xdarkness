package com.abigdreamer.schema;

import com.abigdreamer.schema.ZDMemberAddrSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZDMemberAddrSet extends SchemaSet {
	public ZDMemberAddrSet() {
		this(10,0);
	}

	public ZDMemberAddrSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDMemberAddrSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDMemberAddrSchema._TableCode;
		Columns = ZDMemberAddrSchema._Columns;
		NameSpace = ZDMemberAddrSchema._NameSpace;
		InsertAllSQL = ZDMemberAddrSchema._InsertAllSQL;
		UpdateAllSQL = ZDMemberAddrSchema._UpdateAllSQL;
		FillAllSQL = ZDMemberAddrSchema._FillAllSQL;
		DeleteSQL = ZDMemberAddrSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDMemberAddrSet();
	}

	public boolean add(ZDMemberAddrSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDMemberAddrSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDMemberAddrSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDMemberAddrSchema get(int index) {
		ZDMemberAddrSchema tSchema = (ZDMemberAddrSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDMemberAddrSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDMemberAddrSet aSet) {
		return super.set(aSet);
	}
}
 