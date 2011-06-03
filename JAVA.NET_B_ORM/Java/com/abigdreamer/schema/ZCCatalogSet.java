package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCCatalogSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCCatalogSet extends SchemaSet {
	public ZCCatalogSet() {
		this(10,0);
	}

	public ZCCatalogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCCatalogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCCatalogSchema._TableCode;
		Columns = ZCCatalogSchema._Columns;
		NameSpace = ZCCatalogSchema._NameSpace;
		InsertAllSQL = ZCCatalogSchema._InsertAllSQL;
		UpdateAllSQL = ZCCatalogSchema._UpdateAllSQL;
		FillAllSQL = ZCCatalogSchema._FillAllSQL;
		DeleteSQL = ZCCatalogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCCatalogSet();
	}

	public boolean add(ZCCatalogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCCatalogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCCatalogSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCCatalogSchema get(int index) {
		ZCCatalogSchema tSchema = (ZCCatalogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCCatalogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCCatalogSet aSet) {
		return super.set(aSet);
	}
}
 