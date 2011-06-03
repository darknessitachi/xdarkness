package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCVoteLogSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCVoteLogSet extends SchemaSet {
	public ZCVoteLogSet() {
		this(10,0);
	}

	public ZCVoteLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCVoteLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCVoteLogSchema._TableCode;
		Columns = ZCVoteLogSchema._Columns;
		NameSpace = ZCVoteLogSchema._NameSpace;
		InsertAllSQL = ZCVoteLogSchema._InsertAllSQL;
		UpdateAllSQL = ZCVoteLogSchema._UpdateAllSQL;
		FillAllSQL = ZCVoteLogSchema._FillAllSQL;
		DeleteSQL = ZCVoteLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCVoteLogSet();
	}

	public boolean add(ZCVoteLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCVoteLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCVoteLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCVoteLogSchema get(int index) {
		ZCVoteLogSchema tSchema = (ZCVoteLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCVoteLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCVoteLogSet aSet) {
		return super.set(aSet);
	}
}
 