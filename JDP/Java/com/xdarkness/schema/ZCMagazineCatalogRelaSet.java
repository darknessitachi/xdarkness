package com.xdarkness.schema;

import com.xdarkness.schema.ZCMagazineCatalogRelaSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCMagazineCatalogRelaSet extends SchemaSet {
	public ZCMagazineCatalogRelaSet() {
		this(10,0);
	}

	public ZCMagazineCatalogRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCMagazineCatalogRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCMagazineCatalogRelaSchema._TableCode;
		Columns = ZCMagazineCatalogRelaSchema._Columns;
		NameSpace = ZCMagazineCatalogRelaSchema._NameSpace;
		InsertAllSQL = ZCMagazineCatalogRelaSchema._InsertAllSQL;
		UpdateAllSQL = ZCMagazineCatalogRelaSchema._UpdateAllSQL;
		FillAllSQL = ZCMagazineCatalogRelaSchema._FillAllSQL;
		DeleteSQL = ZCMagazineCatalogRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCMagazineCatalogRelaSet();
	}

	public boolean add(ZCMagazineCatalogRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCMagazineCatalogRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCMagazineCatalogRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCMagazineCatalogRelaSchema get(int index) {
		ZCMagazineCatalogRelaSchema tSchema = (ZCMagazineCatalogRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCMagazineCatalogRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCMagazineCatalogRelaSet aSet) {
		return super.set(aSet);
	}
}
 