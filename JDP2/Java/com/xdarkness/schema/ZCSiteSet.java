package com.xdarkness.schema;

import com.xdarkness.schema.ZCSiteSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCSiteSet extends SchemaSet {
	public ZCSiteSet() {
		this(10,0);
	}

	public ZCSiteSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCSiteSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCSiteSchema._TableCode;
		Columns = ZCSiteSchema._Columns;
		NameSpace = ZCSiteSchema._NameSpace;
		InsertAllSQL = ZCSiteSchema._InsertAllSQL;
		UpdateAllSQL = ZCSiteSchema._UpdateAllSQL;
		FillAllSQL = ZCSiteSchema._FillAllSQL;
		DeleteSQL = ZCSiteSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCSiteSet();
	}

	public boolean add(ZCSiteSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCSiteSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCSiteSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCSiteSchema get(int index) {
		ZCSiteSchema tSchema = (ZCSiteSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCSiteSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCSiteSet aSet) {
		return super.set(aSet);
	}
}
 