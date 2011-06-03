package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCFullTextSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCFullTextSet extends SchemaSet {
	public ZCFullTextSet() {
		this(10,0);
	}

	public ZCFullTextSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCFullTextSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCFullTextSchema._TableCode;
		Columns = ZCFullTextSchema._Columns;
		NameSpace = ZCFullTextSchema._NameSpace;
		InsertAllSQL = ZCFullTextSchema._InsertAllSQL;
		UpdateAllSQL = ZCFullTextSchema._UpdateAllSQL;
		FillAllSQL = ZCFullTextSchema._FillAllSQL;
		DeleteSQL = ZCFullTextSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCFullTextSet();
	}

	public boolean add(ZCFullTextSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCFullTextSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCFullTextSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCFullTextSchema get(int index) {
		ZCFullTextSchema tSchema = (ZCFullTextSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCFullTextSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCFullTextSet aSet) {
		return super.set(aSet);
	}
}
 