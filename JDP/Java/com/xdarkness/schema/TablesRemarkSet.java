package com.xdarkness.schema;

import com.xdarkness.schema.TablesRemarkSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class TablesRemarkSet extends SchemaSet {
	public TablesRemarkSet() {
		this(10,0);
	}

	public TablesRemarkSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public TablesRemarkSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = TablesRemarkSchema._TableCode;
		Columns = TablesRemarkSchema._Columns;
		NameSpace = TablesRemarkSchema._NameSpace;
		InsertAllSQL = TablesRemarkSchema._InsertAllSQL;
		UpdateAllSQL = TablesRemarkSchema._UpdateAllSQL;
		FillAllSQL = TablesRemarkSchema._FillAllSQL;
		DeleteSQL = TablesRemarkSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new TablesRemarkSet();
	}

	public boolean add(TablesRemarkSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(TablesRemarkSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(TablesRemarkSchema aSchema) {
		return super.remove(aSchema);
	}

	public TablesRemarkSchema get(int index) {
		TablesRemarkSchema tSchema = (TablesRemarkSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, TablesRemarkSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(TablesRemarkSet aSet) {
		return super.set(aSet);
	}
}
 