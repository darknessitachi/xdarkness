package com.abigdreamer.schema;

import com.abigdreamer.schema.BZSFavoriteSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZSFavoriteSet extends SchemaSet {
	public BZSFavoriteSet() {
		this(10,0);
	}

	public BZSFavoriteSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZSFavoriteSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZSFavoriteSchema._TableCode;
		Columns = BZSFavoriteSchema._Columns;
		NameSpace = BZSFavoriteSchema._NameSpace;
		InsertAllSQL = BZSFavoriteSchema._InsertAllSQL;
		UpdateAllSQL = BZSFavoriteSchema._UpdateAllSQL;
		FillAllSQL = BZSFavoriteSchema._FillAllSQL;
		DeleteSQL = BZSFavoriteSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZSFavoriteSet();
	}

	public boolean add(BZSFavoriteSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZSFavoriteSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZSFavoriteSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZSFavoriteSchema get(int index) {
		BZSFavoriteSchema tSchema = (BZSFavoriteSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZSFavoriteSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZSFavoriteSet aSet) {
		return super.set(aSet);
	}
}
 