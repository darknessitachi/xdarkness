package com.xdarkness.schema;

import com.xdarkness.schema.BZCAdminGroupSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCAdminGroupSet extends SchemaSet {
	public BZCAdminGroupSet() {
		this(10,0);
	}

	public BZCAdminGroupSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCAdminGroupSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCAdminGroupSchema._TableCode;
		Columns = BZCAdminGroupSchema._Columns;
		NameSpace = BZCAdminGroupSchema._NameSpace;
		InsertAllSQL = BZCAdminGroupSchema._InsertAllSQL;
		UpdateAllSQL = BZCAdminGroupSchema._UpdateAllSQL;
		FillAllSQL = BZCAdminGroupSchema._FillAllSQL;
		DeleteSQL = BZCAdminGroupSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCAdminGroupSet();
	}

	public boolean add(BZCAdminGroupSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCAdminGroupSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCAdminGroupSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCAdminGroupSchema get(int index) {
		BZCAdminGroupSchema tSchema = (BZCAdminGroupSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCAdminGroupSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCAdminGroupSet aSet) {
		return super.set(aSet);
	}
}
 