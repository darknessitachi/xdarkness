package com.xdarkness.schema;

import com.xdarkness.schema.ZSGoodsSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZSGoodsSet extends SchemaSet {
	public ZSGoodsSet() {
		this(10,0);
	}

	public ZSGoodsSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZSGoodsSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZSGoodsSchema._TableCode;
		Columns = ZSGoodsSchema._Columns;
		NameSpace = ZSGoodsSchema._NameSpace;
		InsertAllSQL = ZSGoodsSchema._InsertAllSQL;
		UpdateAllSQL = ZSGoodsSchema._UpdateAllSQL;
		FillAllSQL = ZSGoodsSchema._FillAllSQL;
		DeleteSQL = ZSGoodsSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZSGoodsSet();
	}

	public boolean add(ZSGoodsSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZSGoodsSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZSGoodsSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZSGoodsSchema get(int index) {
		ZSGoodsSchema tSchema = (ZSGoodsSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZSGoodsSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZSGoodsSet aSet) {
		return super.set(aSet);
	}
}
 