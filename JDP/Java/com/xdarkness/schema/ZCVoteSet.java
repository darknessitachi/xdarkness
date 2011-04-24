package com.xdarkness.schema;

import com.xdarkness.schema.ZCVoteSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCVoteSet extends SchemaSet {
	public ZCVoteSet() {
		this(10,0);
	}

	public ZCVoteSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCVoteSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCVoteSchema._TableCode;
		Columns = ZCVoteSchema._Columns;
		NameSpace = ZCVoteSchema._NameSpace;
		InsertAllSQL = ZCVoteSchema._InsertAllSQL;
		UpdateAllSQL = ZCVoteSchema._UpdateAllSQL;
		FillAllSQL = ZCVoteSchema._FillAllSQL;
		DeleteSQL = ZCVoteSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCVoteSet();
	}

	public boolean add(ZCVoteSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCVoteSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCVoteSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCVoteSchema get(int index) {
		ZCVoteSchema tSchema = (ZCVoteSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCVoteSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCVoteSet aSet) {
		return super.set(aSet);
	}
}
 