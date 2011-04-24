package com.xdarkness.schema;

import com.xdarkness.schema.ZDMemberSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZDMemberSet extends SchemaSet {
	public ZDMemberSet() {
		this(10,0);
	}

	public ZDMemberSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDMemberSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDMemberSchema._TableCode;
		Columns = ZDMemberSchema._Columns;
		NameSpace = ZDMemberSchema._NameSpace;
		InsertAllSQL = ZDMemberSchema._InsertAllSQL;
		UpdateAllSQL = ZDMemberSchema._UpdateAllSQL;
		FillAllSQL = ZDMemberSchema._FillAllSQL;
		DeleteSQL = ZDMemberSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDMemberSet();
	}

	public boolean add(ZDMemberSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDMemberSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDMemberSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDMemberSchema get(int index) {
		ZDMemberSchema tSchema = (ZDMemberSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDMemberSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDMemberSet aSet) {
		return super.set(aSet);
	}
}
 