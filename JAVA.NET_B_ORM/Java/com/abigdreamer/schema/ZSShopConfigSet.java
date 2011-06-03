package com.abigdreamer.schema;

import com.abigdreamer.schema.ZSShopConfigSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZSShopConfigSet extends SchemaSet {
	public ZSShopConfigSet() {
		this(10,0);
	}

	public ZSShopConfigSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZSShopConfigSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZSShopConfigSchema._TableCode;
		Columns = ZSShopConfigSchema._Columns;
		NameSpace = ZSShopConfigSchema._NameSpace;
		InsertAllSQL = ZSShopConfigSchema._InsertAllSQL;
		UpdateAllSQL = ZSShopConfigSchema._UpdateAllSQL;
		FillAllSQL = ZSShopConfigSchema._FillAllSQL;
		DeleteSQL = ZSShopConfigSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZSShopConfigSet();
	}

	public boolean add(ZSShopConfigSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZSShopConfigSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZSShopConfigSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZSShopConfigSchema get(int index) {
		ZSShopConfigSchema tSchema = (ZSShopConfigSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZSShopConfigSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZSShopConfigSet aSet) {
		return super.set(aSet);
	}
}
 