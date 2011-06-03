package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCBoardMessageSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCBoardMessageSet extends SchemaSet {
	public BZCBoardMessageSet() {
		this(10,0);
	}

	public BZCBoardMessageSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCBoardMessageSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCBoardMessageSchema._TableCode;
		Columns = BZCBoardMessageSchema._Columns;
		NameSpace = BZCBoardMessageSchema._NameSpace;
		InsertAllSQL = BZCBoardMessageSchema._InsertAllSQL;
		UpdateAllSQL = BZCBoardMessageSchema._UpdateAllSQL;
		FillAllSQL = BZCBoardMessageSchema._FillAllSQL;
		DeleteSQL = BZCBoardMessageSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCBoardMessageSet();
	}

	public boolean add(BZCBoardMessageSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCBoardMessageSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCBoardMessageSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCBoardMessageSchema get(int index) {
		BZCBoardMessageSchema tSchema = (BZCBoardMessageSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCBoardMessageSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCBoardMessageSet aSet) {
		return super.set(aSet);
	}
}
 