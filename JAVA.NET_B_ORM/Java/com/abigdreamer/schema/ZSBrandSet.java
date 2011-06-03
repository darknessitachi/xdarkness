package com.abigdreamer.schema;

import com.abigdreamer.schema.ZSBrandSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZSBrandSet extends SchemaSet {
	public ZSBrandSet() {
		this(10,0);
	}

	public ZSBrandSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZSBrandSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZSBrandSchema._TableCode;
		Columns = ZSBrandSchema._Columns;
		NameSpace = ZSBrandSchema._NameSpace;
		InsertAllSQL = ZSBrandSchema._InsertAllSQL;
		UpdateAllSQL = ZSBrandSchema._UpdateAllSQL;
		FillAllSQL = ZSBrandSchema._FillAllSQL;
		DeleteSQL = ZSBrandSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZSBrandSet();
	}

	public boolean add(ZSBrandSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZSBrandSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZSBrandSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZSBrandSchema get(int index) {
		ZSBrandSchema tSchema = (ZSBrandSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZSBrandSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZSBrandSet aSet) {
		return super.set(aSet);
	}
}
 