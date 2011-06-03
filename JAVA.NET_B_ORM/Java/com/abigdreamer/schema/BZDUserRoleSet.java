package com.abigdreamer.schema;

import com.abigdreamer.schema.BZDUserRoleSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZDUserRoleSet extends SchemaSet {
	public BZDUserRoleSet() {
		this(10,0);
	}

	public BZDUserRoleSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDUserRoleSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDUserRoleSchema._TableCode;
		Columns = BZDUserRoleSchema._Columns;
		NameSpace = BZDUserRoleSchema._NameSpace;
		InsertAllSQL = BZDUserRoleSchema._InsertAllSQL;
		UpdateAllSQL = BZDUserRoleSchema._UpdateAllSQL;
		FillAllSQL = BZDUserRoleSchema._FillAllSQL;
		DeleteSQL = BZDUserRoleSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDUserRoleSet();
	}

	public boolean add(BZDUserRoleSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDUserRoleSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDUserRoleSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDUserRoleSchema get(int index) {
		BZDUserRoleSchema tSchema = (BZDUserRoleSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDUserRoleSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDUserRoleSet aSet) {
		return super.set(aSet);
	}
}
 