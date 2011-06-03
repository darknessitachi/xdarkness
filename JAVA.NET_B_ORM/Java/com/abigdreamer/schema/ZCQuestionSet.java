package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCQuestionSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCQuestionSet extends SchemaSet {
	public ZCQuestionSet() {
		this(10,0);
	}

	public ZCQuestionSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCQuestionSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCQuestionSchema._TableCode;
		Columns = ZCQuestionSchema._Columns;
		NameSpace = ZCQuestionSchema._NameSpace;
		InsertAllSQL = ZCQuestionSchema._InsertAllSQL;
		UpdateAllSQL = ZCQuestionSchema._UpdateAllSQL;
		FillAllSQL = ZCQuestionSchema._FillAllSQL;
		DeleteSQL = ZCQuestionSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCQuestionSet();
	}

	public boolean add(ZCQuestionSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCQuestionSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCQuestionSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCQuestionSchema get(int index) {
		ZCQuestionSchema tSchema = (ZCQuestionSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCQuestionSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCQuestionSet aSet) {
		return super.set(aSet);
	}
}
 