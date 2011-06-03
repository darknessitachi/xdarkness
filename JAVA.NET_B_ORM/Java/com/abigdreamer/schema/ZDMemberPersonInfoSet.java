package com.abigdreamer.schema;

import com.abigdreamer.schema.ZDMemberPersonInfoSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZDMemberPersonInfoSet extends SchemaSet {
	public ZDMemberPersonInfoSet() {
		this(10,0);
	}

	public ZDMemberPersonInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDMemberPersonInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDMemberPersonInfoSchema._TableCode;
		Columns = ZDMemberPersonInfoSchema._Columns;
		NameSpace = ZDMemberPersonInfoSchema._NameSpace;
		InsertAllSQL = ZDMemberPersonInfoSchema._InsertAllSQL;
		UpdateAllSQL = ZDMemberPersonInfoSchema._UpdateAllSQL;
		FillAllSQL = ZDMemberPersonInfoSchema._FillAllSQL;
		DeleteSQL = ZDMemberPersonInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDMemberPersonInfoSet();
	}

	public boolean add(ZDMemberPersonInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDMemberPersonInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDMemberPersonInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDMemberPersonInfoSchema get(int index) {
		ZDMemberPersonInfoSchema tSchema = (ZDMemberPersonInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDMemberPersonInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDMemberPersonInfoSet aSet) {
		return super.set(aSet);
	}
}
 