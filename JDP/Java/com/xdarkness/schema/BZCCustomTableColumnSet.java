package com.xdarkness.schema;

import com.xdarkness.schema.BZCCustomTableColumnSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCCustomTableColumnSet extends SchemaSet {
	public BZCCustomTableColumnSet() {
		this(10,0);
	}

	public BZCCustomTableColumnSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCCustomTableColumnSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCCustomTableColumnSchema._TableCode;
		Columns = BZCCustomTableColumnSchema._Columns;
		NameSpace = BZCCustomTableColumnSchema._NameSpace;
		InsertAllSQL = BZCCustomTableColumnSchema._InsertAllSQL;
		UpdateAllSQL = BZCCustomTableColumnSchema._UpdateAllSQL;
		FillAllSQL = BZCCustomTableColumnSchema._FillAllSQL;
		DeleteSQL = BZCCustomTableColumnSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCCustomTableColumnSet();
	}

	public boolean add(BZCCustomTableColumnSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCCustomTableColumnSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCCustomTableColumnSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCCustomTableColumnSchema get(int index) {
		BZCCustomTableColumnSchema tSchema = (BZCCustomTableColumnSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCCustomTableColumnSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCCustomTableColumnSet aSet) {
		return super.set(aSet);
	}
}
 