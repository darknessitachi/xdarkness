package com.xdarkness.schema;

import com.xdarkness.schema.ZCForumMemberSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCForumMemberSet extends SchemaSet {
	public ZCForumMemberSet() {
		this(10,0);
	}

	public ZCForumMemberSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCForumMemberSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCForumMemberSchema._TableCode;
		Columns = ZCForumMemberSchema._Columns;
		NameSpace = ZCForumMemberSchema._NameSpace;
		InsertAllSQL = ZCForumMemberSchema._InsertAllSQL;
		UpdateAllSQL = ZCForumMemberSchema._UpdateAllSQL;
		FillAllSQL = ZCForumMemberSchema._FillAllSQL;
		DeleteSQL = ZCForumMemberSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCForumMemberSet();
	}

	public boolean add(ZCForumMemberSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCForumMemberSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCForumMemberSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCForumMemberSchema get(int index) {
		ZCForumMemberSchema tSchema = (ZCForumMemberSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCForumMemberSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCForumMemberSet aSet) {
		return super.set(aSet);
	}
}
 