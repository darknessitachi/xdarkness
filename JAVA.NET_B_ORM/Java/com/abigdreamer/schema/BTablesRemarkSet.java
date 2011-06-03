package com.abigdreamer.schema;

import com.abigdreamer.schema.BTablesRemarkSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BTablesRemarkSet extends SchemaSet {
	public BTablesRemarkSet() {
		this(10,0);
	}

	public BTablesRemarkSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BTablesRemarkSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BTablesRemarkSchema._TableCode;
		Columns = BTablesRemarkSchema._Columns;
		NameSpace = BTablesRemarkSchema._NameSpace;
		InsertAllSQL = BTablesRemarkSchema._InsertAllSQL;
		UpdateAllSQL = BTablesRemarkSchema._UpdateAllSQL;
		FillAllSQL = BTablesRemarkSchema._FillAllSQL;
		DeleteSQL = BTablesRemarkSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BTablesRemarkSet();
	}

	public boolean add(BTablesRemarkSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BTablesRemarkSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BTablesRemarkSchema aSchema) {
		return super.remove(aSchema);
	}

	public BTablesRemarkSchema get(int index) {
		BTablesRemarkSchema tSchema = (BTablesRemarkSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BTablesRemarkSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BTablesRemarkSet aSet) {
		return super.set(aSet);
	}
}
 