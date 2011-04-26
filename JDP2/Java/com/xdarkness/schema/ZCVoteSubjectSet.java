package com.xdarkness.schema;

import com.xdarkness.schema.ZCVoteSubjectSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCVoteSubjectSet extends SchemaSet {
	public ZCVoteSubjectSet() {
		this(10,0);
	}

	public ZCVoteSubjectSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCVoteSubjectSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCVoteSubjectSchema._TableCode;
		Columns = ZCVoteSubjectSchema._Columns;
		NameSpace = ZCVoteSubjectSchema._NameSpace;
		InsertAllSQL = ZCVoteSubjectSchema._InsertAllSQL;
		UpdateAllSQL = ZCVoteSubjectSchema._UpdateAllSQL;
		FillAllSQL = ZCVoteSubjectSchema._FillAllSQL;
		DeleteSQL = ZCVoteSubjectSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCVoteSubjectSet();
	}

	public boolean add(ZCVoteSubjectSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCVoteSubjectSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCVoteSubjectSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCVoteSubjectSchema get(int index) {
		ZCVoteSubjectSchema tSchema = (ZCVoteSubjectSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCVoteSubjectSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCVoteSubjectSet aSet) {
		return super.set(aSet);
	}
}
 