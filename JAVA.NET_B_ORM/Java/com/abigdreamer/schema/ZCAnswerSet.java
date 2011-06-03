package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCAnswerSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCAnswerSet extends SchemaSet {
	public ZCAnswerSet() {
		this(10,0);
	}

	public ZCAnswerSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCAnswerSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCAnswerSchema._TableCode;
		Columns = ZCAnswerSchema._Columns;
		NameSpace = ZCAnswerSchema._NameSpace;
		InsertAllSQL = ZCAnswerSchema._InsertAllSQL;
		UpdateAllSQL = ZCAnswerSchema._UpdateAllSQL;
		FillAllSQL = ZCAnswerSchema._FillAllSQL;
		DeleteSQL = ZCAnswerSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCAnswerSet();
	}

	public boolean add(ZCAnswerSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCAnswerSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCAnswerSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCAnswerSchema get(int index) {
		ZCAnswerSchema tSchema = (ZCAnswerSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCAnswerSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCAnswerSet aSet) {
		return super.set(aSet);
	}
}
 