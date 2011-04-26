package com.xdarkness.schema;

import com.xdarkness.schema.BZCMagazineCatalogRelaSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCMagazineCatalogRelaSet extends SchemaSet {
	public BZCMagazineCatalogRelaSet() {
		this(10,0);
	}

	public BZCMagazineCatalogRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCMagazineCatalogRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCMagazineCatalogRelaSchema._TableCode;
		Columns = BZCMagazineCatalogRelaSchema._Columns;
		NameSpace = BZCMagazineCatalogRelaSchema._NameSpace;
		InsertAllSQL = BZCMagazineCatalogRelaSchema._InsertAllSQL;
		UpdateAllSQL = BZCMagazineCatalogRelaSchema._UpdateAllSQL;
		FillAllSQL = BZCMagazineCatalogRelaSchema._FillAllSQL;
		DeleteSQL = BZCMagazineCatalogRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCMagazineCatalogRelaSet();
	}

	public boolean add(BZCMagazineCatalogRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCMagazineCatalogRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCMagazineCatalogRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCMagazineCatalogRelaSchema get(int index) {
		BZCMagazineCatalogRelaSchema tSchema = (BZCMagazineCatalogRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCMagazineCatalogRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCMagazineCatalogRelaSet aSet) {
		return super.set(aSet);
	}
}
 