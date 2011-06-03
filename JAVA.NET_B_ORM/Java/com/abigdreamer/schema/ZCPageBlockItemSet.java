package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCPageBlockItemSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCPageBlockItemSet extends SchemaSet {
	public ZCPageBlockItemSet() {
		this(10,0);
	}

	public ZCPageBlockItemSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCPageBlockItemSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCPageBlockItemSchema._TableCode;
		Columns = ZCPageBlockItemSchema._Columns;
		NameSpace = ZCPageBlockItemSchema._NameSpace;
		InsertAllSQL = ZCPageBlockItemSchema._InsertAllSQL;
		UpdateAllSQL = ZCPageBlockItemSchema._UpdateAllSQL;
		FillAllSQL = ZCPageBlockItemSchema._FillAllSQL;
		DeleteSQL = ZCPageBlockItemSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCPageBlockItemSet();
	}

	public boolean add(ZCPageBlockItemSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCPageBlockItemSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCPageBlockItemSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCPageBlockItemSchema get(int index) {
		ZCPageBlockItemSchema tSchema = (ZCPageBlockItemSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCPageBlockItemSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCPageBlockItemSet aSet) {
		return super.set(aSet);
	}
}
 