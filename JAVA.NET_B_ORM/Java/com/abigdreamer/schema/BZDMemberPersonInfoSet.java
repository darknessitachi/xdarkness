package com.abigdreamer.schema;

import com.abigdreamer.schema.BZDMemberPersonInfoSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZDMemberPersonInfoSet extends SchemaSet {
	public BZDMemberPersonInfoSet() {
		this(10,0);
	}

	public BZDMemberPersonInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDMemberPersonInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDMemberPersonInfoSchema._TableCode;
		Columns = BZDMemberPersonInfoSchema._Columns;
		NameSpace = BZDMemberPersonInfoSchema._NameSpace;
		InsertAllSQL = BZDMemberPersonInfoSchema._InsertAllSQL;
		UpdateAllSQL = BZDMemberPersonInfoSchema._UpdateAllSQL;
		FillAllSQL = BZDMemberPersonInfoSchema._FillAllSQL;
		DeleteSQL = BZDMemberPersonInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDMemberPersonInfoSet();
	}

	public boolean add(BZDMemberPersonInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDMemberPersonInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDMemberPersonInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDMemberPersonInfoSchema get(int index) {
		BZDMemberPersonInfoSchema tSchema = (BZDMemberPersonInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDMemberPersonInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDMemberPersonInfoSet aSet) {
		return super.set(aSet);
	}
}
 