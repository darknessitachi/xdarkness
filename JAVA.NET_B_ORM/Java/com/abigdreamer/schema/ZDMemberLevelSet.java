package com.abigdreamer.schema;

import com.abigdreamer.schema.ZDMemberLevelSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZDMemberLevelSet extends SchemaSet {
	public ZDMemberLevelSet() {
		this(10,0);
	}

	public ZDMemberLevelSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDMemberLevelSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDMemberLevelSchema._TableCode;
		Columns = ZDMemberLevelSchema._Columns;
		NameSpace = ZDMemberLevelSchema._NameSpace;
		InsertAllSQL = ZDMemberLevelSchema._InsertAllSQL;
		UpdateAllSQL = ZDMemberLevelSchema._UpdateAllSQL;
		FillAllSQL = ZDMemberLevelSchema._FillAllSQL;
		DeleteSQL = ZDMemberLevelSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDMemberLevelSet();
	}

	public boolean add(ZDMemberLevelSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDMemberLevelSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDMemberLevelSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDMemberLevelSchema get(int index) {
		ZDMemberLevelSchema tSchema = (ZDMemberLevelSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDMemberLevelSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDMemberLevelSet aSet) {
		return super.set(aSet);
	}
}
 