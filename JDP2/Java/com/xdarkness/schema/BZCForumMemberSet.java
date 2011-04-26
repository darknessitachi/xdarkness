package com.xdarkness.schema;

import com.xdarkness.schema.BZCForumMemberSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCForumMemberSet extends SchemaSet {
	public BZCForumMemberSet() {
		this(10,0);
	}

	public BZCForumMemberSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCForumMemberSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCForumMemberSchema._TableCode;
		Columns = BZCForumMemberSchema._Columns;
		NameSpace = BZCForumMemberSchema._NameSpace;
		InsertAllSQL = BZCForumMemberSchema._InsertAllSQL;
		UpdateAllSQL = BZCForumMemberSchema._UpdateAllSQL;
		FillAllSQL = BZCForumMemberSchema._FillAllSQL;
		DeleteSQL = BZCForumMemberSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCForumMemberSet();
	}

	public boolean add(BZCForumMemberSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCForumMemberSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCForumMemberSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCForumMemberSchema get(int index) {
		BZCForumMemberSchema tSchema = (BZCForumMemberSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCForumMemberSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCForumMemberSet aSet) {
		return super.set(aSet);
	}
}
 