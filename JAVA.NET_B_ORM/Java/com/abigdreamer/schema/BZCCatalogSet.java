package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCCatalogSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCCatalogSet extends SchemaSet {
	public BZCCatalogSet() {
		this(10,0);
	}

	public BZCCatalogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCCatalogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCCatalogSchema._TableCode;
		Columns = BZCCatalogSchema._Columns;
		NameSpace = BZCCatalogSchema._NameSpace;
		InsertAllSQL = BZCCatalogSchema._InsertAllSQL;
		UpdateAllSQL = BZCCatalogSchema._UpdateAllSQL;
		FillAllSQL = BZCCatalogSchema._FillAllSQL;
		DeleteSQL = BZCCatalogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCCatalogSet();
	}

	public boolean add(BZCCatalogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCCatalogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCCatalogSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCCatalogSchema get(int index) {
		BZCCatalogSchema tSchema = (BZCCatalogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCCatalogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCCatalogSet aSet) {
		return super.set(aSet);
	}
}
 