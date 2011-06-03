package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCCustomTableSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCCustomTableSet extends SchemaSet {
	public BZCCustomTableSet() {
		this(10,0);
	}

	public BZCCustomTableSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCCustomTableSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCCustomTableSchema._TableCode;
		Columns = BZCCustomTableSchema._Columns;
		NameSpace = BZCCustomTableSchema._NameSpace;
		InsertAllSQL = BZCCustomTableSchema._InsertAllSQL;
		UpdateAllSQL = BZCCustomTableSchema._UpdateAllSQL;
		FillAllSQL = BZCCustomTableSchema._FillAllSQL;
		DeleteSQL = BZCCustomTableSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCCustomTableSet();
	}

	public boolean add(BZCCustomTableSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCCustomTableSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCCustomTableSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCCustomTableSchema get(int index) {
		BZCCustomTableSchema tSchema = (BZCCustomTableSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCCustomTableSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCCustomTableSet aSet) {
		return super.set(aSet);
	}
}
 