package com.xdarkness.schema;

import com.xdarkness.schema.BZDPrivilegeSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZDPrivilegeSet extends SchemaSet {
	public BZDPrivilegeSet() {
		this(10,0);
	}

	public BZDPrivilegeSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDPrivilegeSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDPrivilegeSchema._TableCode;
		Columns = BZDPrivilegeSchema._Columns;
		NameSpace = BZDPrivilegeSchema._NameSpace;
		InsertAllSQL = BZDPrivilegeSchema._InsertAllSQL;
		UpdateAllSQL = BZDPrivilegeSchema._UpdateAllSQL;
		FillAllSQL = BZDPrivilegeSchema._FillAllSQL;
		DeleteSQL = BZDPrivilegeSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDPrivilegeSet();
	}

	public boolean add(BZDPrivilegeSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDPrivilegeSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDPrivilegeSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDPrivilegeSchema get(int index) {
		BZDPrivilegeSchema tSchema = (BZDPrivilegeSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDPrivilegeSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDPrivilegeSet aSet) {
		return super.set(aSet);
	}
}
 