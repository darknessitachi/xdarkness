package com.xdarkness.schema;

import com.xdarkness.schema.ZSFavoriteSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZSFavoriteSet extends SchemaSet {
	public ZSFavoriteSet() {
		this(10,0);
	}

	public ZSFavoriteSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZSFavoriteSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZSFavoriteSchema._TableCode;
		Columns = ZSFavoriteSchema._Columns;
		NameSpace = ZSFavoriteSchema._NameSpace;
		InsertAllSQL = ZSFavoriteSchema._InsertAllSQL;
		UpdateAllSQL = ZSFavoriteSchema._UpdateAllSQL;
		FillAllSQL = ZSFavoriteSchema._FillAllSQL;
		DeleteSQL = ZSFavoriteSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZSFavoriteSet();
	}

	public boolean add(ZSFavoriteSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZSFavoriteSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZSFavoriteSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZSFavoriteSchema get(int index) {
		ZSFavoriteSchema tSchema = (ZSFavoriteSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZSFavoriteSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZSFavoriteSet aSet) {
		return super.set(aSet);
	}
}
 