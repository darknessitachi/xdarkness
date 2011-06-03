package com.abigdreamer.schema;

import com.abigdreamer.schema.BZDRoleSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZDRoleSet extends SchemaSet {
	public BZDRoleSet() {
		this(10,0);
	}

	public BZDRoleSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDRoleSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDRoleSchema._TableCode;
		Columns = BZDRoleSchema._Columns;
		NameSpace = BZDRoleSchema._NameSpace;
		InsertAllSQL = BZDRoleSchema._InsertAllSQL;
		UpdateAllSQL = BZDRoleSchema._UpdateAllSQL;
		FillAllSQL = BZDRoleSchema._FillAllSQL;
		DeleteSQL = BZDRoleSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDRoleSet();
	}

	public boolean add(BZDRoleSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDRoleSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDRoleSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDRoleSchema get(int index) {
		BZDRoleSchema tSchema = (BZDRoleSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDRoleSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDRoleSet aSet) {
		return super.set(aSet);
	}
}
 