package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCAnswerCommentSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCAnswerCommentSet extends SchemaSet {
	public ZCAnswerCommentSet() {
		this(10,0);
	}

	public ZCAnswerCommentSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCAnswerCommentSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCAnswerCommentSchema._TableCode;
		Columns = ZCAnswerCommentSchema._Columns;
		NameSpace = ZCAnswerCommentSchema._NameSpace;
		InsertAllSQL = ZCAnswerCommentSchema._InsertAllSQL;
		UpdateAllSQL = ZCAnswerCommentSchema._UpdateAllSQL;
		FillAllSQL = ZCAnswerCommentSchema._FillAllSQL;
		DeleteSQL = ZCAnswerCommentSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCAnswerCommentSet();
	}

	public boolean add(ZCAnswerCommentSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCAnswerCommentSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCAnswerCommentSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCAnswerCommentSchema get(int index) {
		ZCAnswerCommentSchema tSchema = (ZCAnswerCommentSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCAnswerCommentSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCAnswerCommentSet aSet) {
		return super.set(aSet);
	}
}
 