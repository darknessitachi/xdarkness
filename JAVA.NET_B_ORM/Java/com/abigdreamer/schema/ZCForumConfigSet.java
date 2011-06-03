package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCForumConfigSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCForumConfigSet extends SchemaSet {
	public ZCForumConfigSet() {
		this(10,0);
	}

	public ZCForumConfigSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCForumConfigSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCForumConfigSchema._TableCode;
		Columns = ZCForumConfigSchema._Columns;
		NameSpace = ZCForumConfigSchema._NameSpace;
		InsertAllSQL = ZCForumConfigSchema._InsertAllSQL;
		UpdateAllSQL = ZCForumConfigSchema._UpdateAllSQL;
		FillAllSQL = ZCForumConfigSchema._FillAllSQL;
		DeleteSQL = ZCForumConfigSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCForumConfigSet();
	}

	public boolean add(ZCForumConfigSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCForumConfigSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCForumConfigSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCForumConfigSchema get(int index) {
		ZCForumConfigSchema tSchema = (ZCForumConfigSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCForumConfigSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCForumConfigSet aSet) {
		return super.set(aSet);
	}
}
 