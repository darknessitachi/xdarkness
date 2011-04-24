package com.xdarkness.schema;

import com.xdarkness.schema.ZCCatalogConfigSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCCatalogConfigSet extends SchemaSet {
	public ZCCatalogConfigSet() {
		this(10,0);
	}

	public ZCCatalogConfigSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCCatalogConfigSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCCatalogConfigSchema._TableCode;
		Columns = ZCCatalogConfigSchema._Columns;
		NameSpace = ZCCatalogConfigSchema._NameSpace;
		InsertAllSQL = ZCCatalogConfigSchema._InsertAllSQL;
		UpdateAllSQL = ZCCatalogConfigSchema._UpdateAllSQL;
		FillAllSQL = ZCCatalogConfigSchema._FillAllSQL;
		DeleteSQL = ZCCatalogConfigSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCCatalogConfigSet();
	}

	public boolean add(ZCCatalogConfigSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCCatalogConfigSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCCatalogConfigSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCCatalogConfigSchema get(int index) {
		ZCCatalogConfigSchema tSchema = (ZCCatalogConfigSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCCatalogConfigSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCCatalogConfigSet aSet) {
		return super.set(aSet);
	}
}
 