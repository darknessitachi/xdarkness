package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCMagazineSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCMagazineSet extends SchemaSet {
	public ZCMagazineSet() {
		this(10,0);
	}

	public ZCMagazineSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCMagazineSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCMagazineSchema._TableCode;
		Columns = ZCMagazineSchema._Columns;
		NameSpace = ZCMagazineSchema._NameSpace;
		InsertAllSQL = ZCMagazineSchema._InsertAllSQL;
		UpdateAllSQL = ZCMagazineSchema._UpdateAllSQL;
		FillAllSQL = ZCMagazineSchema._FillAllSQL;
		DeleteSQL = ZCMagazineSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCMagazineSet();
	}

	public boolean add(ZCMagazineSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCMagazineSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCMagazineSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCMagazineSchema get(int index) {
		ZCMagazineSchema tSchema = (ZCMagazineSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCMagazineSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCMagazineSet aSet) {
		return super.set(aSet);
	}
}
 