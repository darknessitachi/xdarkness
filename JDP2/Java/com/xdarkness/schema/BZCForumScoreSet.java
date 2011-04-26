package com.xdarkness.schema;

import com.xdarkness.schema.BZCForumScoreSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCForumScoreSet extends SchemaSet {
	public BZCForumScoreSet() {
		this(10,0);
	}

	public BZCForumScoreSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCForumScoreSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCForumScoreSchema._TableCode;
		Columns = BZCForumScoreSchema._Columns;
		NameSpace = BZCForumScoreSchema._NameSpace;
		InsertAllSQL = BZCForumScoreSchema._InsertAllSQL;
		UpdateAllSQL = BZCForumScoreSchema._UpdateAllSQL;
		FillAllSQL = BZCForumScoreSchema._FillAllSQL;
		DeleteSQL = BZCForumScoreSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCForumScoreSet();
	}

	public boolean add(BZCForumScoreSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCForumScoreSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCForumScoreSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCForumScoreSchema get(int index) {
		BZCForumScoreSchema tSchema = (BZCForumScoreSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCForumScoreSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCForumScoreSet aSet) {
		return super.set(aSet);
	}
}
 