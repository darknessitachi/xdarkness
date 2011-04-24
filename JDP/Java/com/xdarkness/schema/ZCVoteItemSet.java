package com.xdarkness.schema;

import com.xdarkness.schema.ZCVoteItemSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCVoteItemSet extends SchemaSet {
	public ZCVoteItemSet() {
		this(10,0);
	}

	public ZCVoteItemSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCVoteItemSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCVoteItemSchema._TableCode;
		Columns = ZCVoteItemSchema._Columns;
		NameSpace = ZCVoteItemSchema._NameSpace;
		InsertAllSQL = ZCVoteItemSchema._InsertAllSQL;
		UpdateAllSQL = ZCVoteItemSchema._UpdateAllSQL;
		FillAllSQL = ZCVoteItemSchema._FillAllSQL;
		DeleteSQL = ZCVoteItemSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCVoteItemSet();
	}

	public boolean add(ZCVoteItemSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCVoteItemSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCVoteItemSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCVoteItemSchema get(int index) {
		ZCVoteItemSchema tSchema = (ZCVoteItemSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCVoteItemSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCVoteItemSet aSet) {
		return super.set(aSet);
	}
}
 