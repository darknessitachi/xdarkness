package com.xdarkness.schema;

import com.xdarkness.schema.ZCForumGroupSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCForumGroupSet extends SchemaSet {
	public ZCForumGroupSet() {
		this(10,0);
	}

	public ZCForumGroupSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCForumGroupSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCForumGroupSchema._TableCode;
		Columns = ZCForumGroupSchema._Columns;
		NameSpace = ZCForumGroupSchema._NameSpace;
		InsertAllSQL = ZCForumGroupSchema._InsertAllSQL;
		UpdateAllSQL = ZCForumGroupSchema._UpdateAllSQL;
		FillAllSQL = ZCForumGroupSchema._FillAllSQL;
		DeleteSQL = ZCForumGroupSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCForumGroupSet();
	}

	public boolean add(ZCForumGroupSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCForumGroupSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCForumGroupSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCForumGroupSchema get(int index) {
		ZCForumGroupSchema tSchema = (ZCForumGroupSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCForumGroupSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCForumGroupSet aSet) {
		return super.set(aSet);
	}
}
 