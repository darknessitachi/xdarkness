package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCForumSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCForumSet extends SchemaSet {
	public BZCForumSet() {
		this(10,0);
	}

	public BZCForumSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCForumSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCForumSchema._TableCode;
		Columns = BZCForumSchema._Columns;
		NameSpace = BZCForumSchema._NameSpace;
		InsertAllSQL = BZCForumSchema._InsertAllSQL;
		UpdateAllSQL = BZCForumSchema._UpdateAllSQL;
		FillAllSQL = BZCForumSchema._FillAllSQL;
		DeleteSQL = BZCForumSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCForumSet();
	}

	public boolean add(BZCForumSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCForumSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCForumSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCForumSchema get(int index) {
		BZCForumSchema tSchema = (BZCForumSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCForumSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCForumSet aSet) {
		return super.set(aSet);
	}
}
 