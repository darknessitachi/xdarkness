package com.abigdreamer.schema;

import com.abigdreamer.schema.BZDMemberAddrSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZDMemberAddrSet extends SchemaSet {
	public BZDMemberAddrSet() {
		this(10,0);
	}

	public BZDMemberAddrSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDMemberAddrSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDMemberAddrSchema._TableCode;
		Columns = BZDMemberAddrSchema._Columns;
		NameSpace = BZDMemberAddrSchema._NameSpace;
		InsertAllSQL = BZDMemberAddrSchema._InsertAllSQL;
		UpdateAllSQL = BZDMemberAddrSchema._UpdateAllSQL;
		FillAllSQL = BZDMemberAddrSchema._FillAllSQL;
		DeleteSQL = BZDMemberAddrSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDMemberAddrSet();
	}

	public boolean add(BZDMemberAddrSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDMemberAddrSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDMemberAddrSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDMemberAddrSchema get(int index) {
		BZDMemberAddrSchema tSchema = (BZDMemberAddrSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDMemberAddrSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDMemberAddrSet aSet) {
		return super.set(aSet);
	}
}
 