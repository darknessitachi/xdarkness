package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCCatalogConfigSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCCatalogConfigSet extends SchemaSet {
	public BZCCatalogConfigSet() {
		this(10,0);
	}

	public BZCCatalogConfigSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCCatalogConfigSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCCatalogConfigSchema._TableCode;
		Columns = BZCCatalogConfigSchema._Columns;
		NameSpace = BZCCatalogConfigSchema._NameSpace;
		InsertAllSQL = BZCCatalogConfigSchema._InsertAllSQL;
		UpdateAllSQL = BZCCatalogConfigSchema._UpdateAllSQL;
		FillAllSQL = BZCCatalogConfigSchema._FillAllSQL;
		DeleteSQL = BZCCatalogConfigSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCCatalogConfigSet();
	}

	public boolean add(BZCCatalogConfigSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCCatalogConfigSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCCatalogConfigSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCCatalogConfigSchema get(int index) {
		BZCCatalogConfigSchema tSchema = (BZCCatalogConfigSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCCatalogConfigSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCCatalogConfigSet aSet) {
		return super.set(aSet);
	}
}
 