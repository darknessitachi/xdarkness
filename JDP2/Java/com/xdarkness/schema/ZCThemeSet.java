package com.xdarkness.schema;

import com.xdarkness.schema.ZCThemeSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCThemeSet extends SchemaSet {
	public ZCThemeSet() {
		this(10,0);
	}

	public ZCThemeSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCThemeSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCThemeSchema._TableCode;
		Columns = ZCThemeSchema._Columns;
		NameSpace = ZCThemeSchema._NameSpace;
		InsertAllSQL = ZCThemeSchema._InsertAllSQL;
		UpdateAllSQL = ZCThemeSchema._UpdateAllSQL;
		FillAllSQL = ZCThemeSchema._FillAllSQL;
		DeleteSQL = ZCThemeSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCThemeSet();
	}

	public boolean add(ZCThemeSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCThemeSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCThemeSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCThemeSchema get(int index) {
		ZCThemeSchema tSchema = (ZCThemeSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCThemeSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCThemeSet aSet) {
		return super.set(aSet);
	}
}
 