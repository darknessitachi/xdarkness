package com.xdarkness.schema;

import com.xdarkness.schema.BZSShopConfigSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZSShopConfigSet extends SchemaSet {
	public BZSShopConfigSet() {
		this(10,0);
	}

	public BZSShopConfigSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZSShopConfigSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZSShopConfigSchema._TableCode;
		Columns = BZSShopConfigSchema._Columns;
		NameSpace = BZSShopConfigSchema._NameSpace;
		InsertAllSQL = BZSShopConfigSchema._InsertAllSQL;
		UpdateAllSQL = BZSShopConfigSchema._UpdateAllSQL;
		FillAllSQL = BZSShopConfigSchema._FillAllSQL;
		DeleteSQL = BZSShopConfigSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZSShopConfigSet();
	}

	public boolean add(BZSShopConfigSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZSShopConfigSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZSShopConfigSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZSShopConfigSchema get(int index) {
		BZSShopConfigSchema tSchema = (BZSShopConfigSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZSShopConfigSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZSShopConfigSet aSet) {
		return super.set(aSet);
	}
}
 