package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCForumGroupSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCForumGroupSet extends SchemaSet {
	public BZCForumGroupSet() {
		this(10,0);
	}

	public BZCForumGroupSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCForumGroupSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCForumGroupSchema._TableCode;
		Columns = BZCForumGroupSchema._Columns;
		NameSpace = BZCForumGroupSchema._NameSpace;
		InsertAllSQL = BZCForumGroupSchema._InsertAllSQL;
		UpdateAllSQL = BZCForumGroupSchema._UpdateAllSQL;
		FillAllSQL = BZCForumGroupSchema._FillAllSQL;
		DeleteSQL = BZCForumGroupSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCForumGroupSet();
	}

	public boolean add(BZCForumGroupSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCForumGroupSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCForumGroupSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCForumGroupSchema get(int index) {
		BZCForumGroupSchema tSchema = (BZCForumGroupSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCForumGroupSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCForumGroupSet aSet) {
		return super.set(aSet);
	}
}
 