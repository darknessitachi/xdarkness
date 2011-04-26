package com.xdarkness.schema;

import com.xdarkness.schema.ZCCommentSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCCommentSet extends SchemaSet {
	public ZCCommentSet() {
		this(10,0);
	}

	public ZCCommentSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCCommentSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCCommentSchema._TableCode;
		Columns = ZCCommentSchema._Columns;
		NameSpace = ZCCommentSchema._NameSpace;
		InsertAllSQL = ZCCommentSchema._InsertAllSQL;
		UpdateAllSQL = ZCCommentSchema._UpdateAllSQL;
		FillAllSQL = ZCCommentSchema._FillAllSQL;
		DeleteSQL = ZCCommentSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCCommentSet();
	}

	public boolean add(ZCCommentSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCCommentSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCCommentSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCCommentSchema get(int index) {
		ZCCommentSchema tSchema = (ZCCommentSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCCommentSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCCommentSet aSet) {
		return super.set(aSet);
	}
}
 