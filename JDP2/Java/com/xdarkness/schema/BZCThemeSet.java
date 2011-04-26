package com.xdarkness.schema;

import com.xdarkness.schema.BZCThemeSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCThemeSet extends SchemaSet {
	public BZCThemeSet() {
		this(10,0);
	}

	public BZCThemeSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCThemeSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCThemeSchema._TableCode;
		Columns = BZCThemeSchema._Columns;
		NameSpace = BZCThemeSchema._NameSpace;
		InsertAllSQL = BZCThemeSchema._InsertAllSQL;
		UpdateAllSQL = BZCThemeSchema._UpdateAllSQL;
		FillAllSQL = BZCThemeSchema._FillAllSQL;
		DeleteSQL = BZCThemeSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCThemeSet();
	}

	public boolean add(BZCThemeSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCThemeSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCThemeSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCThemeSchema get(int index) {
		BZCThemeSchema tSchema = (BZCThemeSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCThemeSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCThemeSet aSet) {
		return super.set(aSet);
	}
}
 