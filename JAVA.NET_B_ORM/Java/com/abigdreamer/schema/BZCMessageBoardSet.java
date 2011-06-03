package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCMessageBoardSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCMessageBoardSet extends SchemaSet {
	public BZCMessageBoardSet() {
		this(10,0);
	}

	public BZCMessageBoardSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCMessageBoardSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCMessageBoardSchema._TableCode;
		Columns = BZCMessageBoardSchema._Columns;
		NameSpace = BZCMessageBoardSchema._NameSpace;
		InsertAllSQL = BZCMessageBoardSchema._InsertAllSQL;
		UpdateAllSQL = BZCMessageBoardSchema._UpdateAllSQL;
		FillAllSQL = BZCMessageBoardSchema._FillAllSQL;
		DeleteSQL = BZCMessageBoardSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCMessageBoardSet();
	}

	public boolean add(BZCMessageBoardSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCMessageBoardSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCMessageBoardSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCMessageBoardSchema get(int index) {
		BZCMessageBoardSchema tSchema = (BZCMessageBoardSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCMessageBoardSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCMessageBoardSet aSet) {
		return super.set(aSet);
	}
}
 