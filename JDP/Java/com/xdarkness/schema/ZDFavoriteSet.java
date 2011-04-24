package com.xdarkness.schema;

import com.xdarkness.schema.ZDFavoriteSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZDFavoriteSet extends SchemaSet {
	public ZDFavoriteSet() {
		this(10,0);
	}

	public ZDFavoriteSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDFavoriteSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDFavoriteSchema._TableCode;
		Columns = ZDFavoriteSchema._Columns;
		NameSpace = ZDFavoriteSchema._NameSpace;
		InsertAllSQL = ZDFavoriteSchema._InsertAllSQL;
		UpdateAllSQL = ZDFavoriteSchema._UpdateAllSQL;
		FillAllSQL = ZDFavoriteSchema._FillAllSQL;
		DeleteSQL = ZDFavoriteSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDFavoriteSet();
	}

	public boolean add(ZDFavoriteSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDFavoriteSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDFavoriteSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDFavoriteSchema get(int index) {
		ZDFavoriteSchema tSchema = (ZDFavoriteSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDFavoriteSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDFavoriteSet aSet) {
		return super.set(aSet);
	}
}
 