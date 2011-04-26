package com.xdarkness.schema;

import com.xdarkness.schema.BZDMemberSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZDMemberSet extends SchemaSet {
	public BZDMemberSet() {
		this(10,0);
	}

	public BZDMemberSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDMemberSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDMemberSchema._TableCode;
		Columns = BZDMemberSchema._Columns;
		NameSpace = BZDMemberSchema._NameSpace;
		InsertAllSQL = BZDMemberSchema._InsertAllSQL;
		UpdateAllSQL = BZDMemberSchema._UpdateAllSQL;
		FillAllSQL = BZDMemberSchema._FillAllSQL;
		DeleteSQL = BZDMemberSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDMemberSet();
	}

	public boolean add(BZDMemberSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDMemberSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDMemberSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDMemberSchema get(int index) {
		BZDMemberSchema tSchema = (BZDMemberSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDMemberSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDMemberSet aSet) {
		return super.set(aSet);
	}
}
 