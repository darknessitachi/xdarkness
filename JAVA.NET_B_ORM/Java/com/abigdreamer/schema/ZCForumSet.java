package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCForumSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCForumSet extends SchemaSet {
	public ZCForumSet() {
		this(10,0);
	}

	public ZCForumSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCForumSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCForumSchema._TableCode;
		Columns = ZCForumSchema._Columns;
		NameSpace = ZCForumSchema._NameSpace;
		InsertAllSQL = ZCForumSchema._InsertAllSQL;
		UpdateAllSQL = ZCForumSchema._UpdateAllSQL;
		FillAllSQL = ZCForumSchema._FillAllSQL;
		DeleteSQL = ZCForumSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCForumSet();
	}

	public boolean add(ZCForumSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCForumSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCForumSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCForumSchema get(int index) {
		ZCForumSchema tSchema = (ZCForumSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCForumSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCForumSet aSet) {
		return super.set(aSet);
	}
}
 