package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCForumConfigSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCForumConfigSet extends SchemaSet {
	public BZCForumConfigSet() {
		this(10,0);
	}

	public BZCForumConfigSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCForumConfigSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCForumConfigSchema._TableCode;
		Columns = BZCForumConfigSchema._Columns;
		NameSpace = BZCForumConfigSchema._NameSpace;
		InsertAllSQL = BZCForumConfigSchema._InsertAllSQL;
		UpdateAllSQL = BZCForumConfigSchema._UpdateAllSQL;
		FillAllSQL = BZCForumConfigSchema._FillAllSQL;
		DeleteSQL = BZCForumConfigSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCForumConfigSet();
	}

	public boolean add(BZCForumConfigSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCForumConfigSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCForumConfigSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCForumConfigSchema get(int index) {
		BZCForumConfigSchema tSchema = (BZCForumConfigSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCForumConfigSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCForumConfigSet aSet) {
		return super.set(aSet);
	}
}
 