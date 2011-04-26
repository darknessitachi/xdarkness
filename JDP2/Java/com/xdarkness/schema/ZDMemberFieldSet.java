package com.xdarkness.schema;

import com.xdarkness.schema.ZDMemberFieldSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZDMemberFieldSet extends SchemaSet {
	public ZDMemberFieldSet() {
		this(10,0);
	}

	public ZDMemberFieldSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDMemberFieldSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDMemberFieldSchema._TableCode;
		Columns = ZDMemberFieldSchema._Columns;
		NameSpace = ZDMemberFieldSchema._NameSpace;
		InsertAllSQL = ZDMemberFieldSchema._InsertAllSQL;
		UpdateAllSQL = ZDMemberFieldSchema._UpdateAllSQL;
		FillAllSQL = ZDMemberFieldSchema._FillAllSQL;
		DeleteSQL = ZDMemberFieldSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDMemberFieldSet();
	}

	public boolean add(ZDMemberFieldSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDMemberFieldSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDMemberFieldSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDMemberFieldSchema get(int index) {
		ZDMemberFieldSchema tSchema = (ZDMemberFieldSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDMemberFieldSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDMemberFieldSet aSet) {
		return super.set(aSet);
	}
}
 