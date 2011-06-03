package com.abigdreamer.schema;

import com.abigdreamer.schema.BZDFavoriteSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZDFavoriteSet extends SchemaSet {
	public BZDFavoriteSet() {
		this(10,0);
	}

	public BZDFavoriteSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDFavoriteSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDFavoriteSchema._TableCode;
		Columns = BZDFavoriteSchema._Columns;
		NameSpace = BZDFavoriteSchema._NameSpace;
		InsertAllSQL = BZDFavoriteSchema._InsertAllSQL;
		UpdateAllSQL = BZDFavoriteSchema._UpdateAllSQL;
		FillAllSQL = BZDFavoriteSchema._FillAllSQL;
		DeleteSQL = BZDFavoriteSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDFavoriteSet();
	}

	public boolean add(BZDFavoriteSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDFavoriteSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDFavoriteSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDFavoriteSchema get(int index) {
		BZDFavoriteSchema tSchema = (BZDFavoriteSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDFavoriteSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDFavoriteSet aSet) {
		return super.set(aSet);
	}
}
 