package com.xdarkness.schema;

import com.xdarkness.schema.BZDMemberLevelSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZDMemberLevelSet extends SchemaSet {
	public BZDMemberLevelSet() {
		this(10,0);
	}

	public BZDMemberLevelSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDMemberLevelSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDMemberLevelSchema._TableCode;
		Columns = BZDMemberLevelSchema._Columns;
		NameSpace = BZDMemberLevelSchema._NameSpace;
		InsertAllSQL = BZDMemberLevelSchema._InsertAllSQL;
		UpdateAllSQL = BZDMemberLevelSchema._UpdateAllSQL;
		FillAllSQL = BZDMemberLevelSchema._FillAllSQL;
		DeleteSQL = BZDMemberLevelSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDMemberLevelSet();
	}

	public boolean add(BZDMemberLevelSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDMemberLevelSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDMemberLevelSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDMemberLevelSchema get(int index) {
		BZDMemberLevelSchema tSchema = (BZDMemberLevelSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDMemberLevelSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDMemberLevelSet aSet) {
		return super.set(aSet);
	}
}
 