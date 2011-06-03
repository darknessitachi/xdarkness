package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCForumScoreSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCForumScoreSet extends SchemaSet {
	public ZCForumScoreSet() {
		this(10,0);
	}

	public ZCForumScoreSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCForumScoreSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCForumScoreSchema._TableCode;
		Columns = ZCForumScoreSchema._Columns;
		NameSpace = ZCForumScoreSchema._NameSpace;
		InsertAllSQL = ZCForumScoreSchema._InsertAllSQL;
		UpdateAllSQL = ZCForumScoreSchema._UpdateAllSQL;
		FillAllSQL = ZCForumScoreSchema._FillAllSQL;
		DeleteSQL = ZCForumScoreSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCForumScoreSet();
	}

	public boolean add(ZCForumScoreSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCForumScoreSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCForumScoreSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCForumScoreSchema get(int index) {
		ZCForumScoreSchema tSchema = (ZCForumScoreSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCForumScoreSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCForumScoreSet aSet) {
		return super.set(aSet);
	}
}
 