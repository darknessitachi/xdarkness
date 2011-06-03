package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCQuestionGroupSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCQuestionGroupSet extends SchemaSet {
	public ZCQuestionGroupSet() {
		this(10,0);
	}

	public ZCQuestionGroupSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCQuestionGroupSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCQuestionGroupSchema._TableCode;
		Columns = ZCQuestionGroupSchema._Columns;
		NameSpace = ZCQuestionGroupSchema._NameSpace;
		InsertAllSQL = ZCQuestionGroupSchema._InsertAllSQL;
		UpdateAllSQL = ZCQuestionGroupSchema._UpdateAllSQL;
		FillAllSQL = ZCQuestionGroupSchema._FillAllSQL;
		DeleteSQL = ZCQuestionGroupSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCQuestionGroupSet();
	}

	public boolean add(ZCQuestionGroupSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCQuestionGroupSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCQuestionGroupSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCQuestionGroupSchema get(int index) {
		ZCQuestionGroupSchema tSchema = (ZCQuestionGroupSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCQuestionGroupSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCQuestionGroupSet aSet) {
		return super.set(aSet);
	}
}
 