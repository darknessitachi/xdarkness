package com.abigdreamer.schema;

import com.abigdreamer.schema.ColsRemarkSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ColsRemarkSet extends SchemaSet {
	public ColsRemarkSet() {
		this(10,0);
	}

	public ColsRemarkSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ColsRemarkSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ColsRemarkSchema._TableCode;
		Columns = ColsRemarkSchema._Columns;
		NameSpace = ColsRemarkSchema._NameSpace;
		InsertAllSQL = ColsRemarkSchema._InsertAllSQL;
		UpdateAllSQL = ColsRemarkSchema._UpdateAllSQL;
		FillAllSQL = ColsRemarkSchema._FillAllSQL;
		DeleteSQL = ColsRemarkSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ColsRemarkSet();
	}

	public boolean add(ColsRemarkSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ColsRemarkSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ColsRemarkSchema aSchema) {
		return super.remove(aSchema);
	}

	public ColsRemarkSchema get(int index) {
		ColsRemarkSchema tSchema = (ColsRemarkSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ColsRemarkSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ColsRemarkSet aSet) {
		return super.set(aSet);
	}
}
 