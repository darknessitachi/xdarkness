package com.abigdreamer.schema;

import com.abigdreamer.schema.BZSGoodsSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZSGoodsSet extends SchemaSet {
	public BZSGoodsSet() {
		this(10,0);
	}

	public BZSGoodsSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZSGoodsSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZSGoodsSchema._TableCode;
		Columns = BZSGoodsSchema._Columns;
		NameSpace = BZSGoodsSchema._NameSpace;
		InsertAllSQL = BZSGoodsSchema._InsertAllSQL;
		UpdateAllSQL = BZSGoodsSchema._UpdateAllSQL;
		FillAllSQL = BZSGoodsSchema._FillAllSQL;
		DeleteSQL = BZSGoodsSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZSGoodsSet();
	}

	public boolean add(BZSGoodsSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZSGoodsSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZSGoodsSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZSGoodsSchema get(int index) {
		BZSGoodsSchema tSchema = (BZSGoodsSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZSGoodsSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZSGoodsSet aSet) {
		return super.set(aSet);
	}
}
 