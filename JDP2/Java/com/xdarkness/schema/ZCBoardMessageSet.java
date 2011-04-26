package com.xdarkness.schema;

import com.xdarkness.schema.ZCBoardMessageSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCBoardMessageSet extends SchemaSet {
	public ZCBoardMessageSet() {
		this(10,0);
	}

	public ZCBoardMessageSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCBoardMessageSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCBoardMessageSchema._TableCode;
		Columns = ZCBoardMessageSchema._Columns;
		NameSpace = ZCBoardMessageSchema._NameSpace;
		InsertAllSQL = ZCBoardMessageSchema._InsertAllSQL;
		UpdateAllSQL = ZCBoardMessageSchema._UpdateAllSQL;
		FillAllSQL = ZCBoardMessageSchema._FillAllSQL;
		DeleteSQL = ZCBoardMessageSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCBoardMessageSet();
	}

	public boolean add(ZCBoardMessageSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCBoardMessageSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCBoardMessageSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCBoardMessageSchema get(int index) {
		ZCBoardMessageSchema tSchema = (ZCBoardMessageSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCBoardMessageSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCBoardMessageSet aSet) {
		return super.set(aSet);
	}
}
 