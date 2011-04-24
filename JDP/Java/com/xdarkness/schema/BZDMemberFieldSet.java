package com.xdarkness.schema;

import com.xdarkness.schema.BZDMemberFieldSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZDMemberFieldSet extends SchemaSet {
	public BZDMemberFieldSet() {
		this(10,0);
	}

	public BZDMemberFieldSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDMemberFieldSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDMemberFieldSchema._TableCode;
		Columns = BZDMemberFieldSchema._Columns;
		NameSpace = BZDMemberFieldSchema._NameSpace;
		InsertAllSQL = BZDMemberFieldSchema._InsertAllSQL;
		UpdateAllSQL = BZDMemberFieldSchema._UpdateAllSQL;
		FillAllSQL = BZDMemberFieldSchema._FillAllSQL;
		DeleteSQL = BZDMemberFieldSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDMemberFieldSet();
	}

	public boolean add(BZDMemberFieldSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDMemberFieldSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDMemberFieldSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDMemberFieldSchema get(int index) {
		BZDMemberFieldSchema tSchema = (BZDMemberFieldSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDMemberFieldSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDMemberFieldSet aSet) {
		return super.set(aSet);
	}
}
 