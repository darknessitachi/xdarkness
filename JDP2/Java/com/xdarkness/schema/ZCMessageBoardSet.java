package com.xdarkness.schema;

import com.xdarkness.schema.ZCMessageBoardSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCMessageBoardSet extends SchemaSet {
	public ZCMessageBoardSet() {
		this(10,0);
	}

	public ZCMessageBoardSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCMessageBoardSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCMessageBoardSchema._TableCode;
		Columns = ZCMessageBoardSchema._Columns;
		NameSpace = ZCMessageBoardSchema._NameSpace;
		InsertAllSQL = ZCMessageBoardSchema._InsertAllSQL;
		UpdateAllSQL = ZCMessageBoardSchema._UpdateAllSQL;
		FillAllSQL = ZCMessageBoardSchema._FillAllSQL;
		DeleteSQL = ZCMessageBoardSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCMessageBoardSet();
	}

	public boolean add(ZCMessageBoardSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCMessageBoardSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCMessageBoardSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCMessageBoardSchema get(int index) {
		ZCMessageBoardSchema tSchema = (ZCMessageBoardSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCMessageBoardSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCMessageBoardSet aSet) {
		return super.set(aSet);
	}
}
 