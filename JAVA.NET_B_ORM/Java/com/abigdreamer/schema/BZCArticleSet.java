package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCArticleSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCArticleSet extends SchemaSet {
	public BZCArticleSet() {
		this(10,0);
	}

	public BZCArticleSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCArticleSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCArticleSchema._TableCode;
		Columns = BZCArticleSchema._Columns;
		NameSpace = BZCArticleSchema._NameSpace;
		InsertAllSQL = BZCArticleSchema._InsertAllSQL;
		UpdateAllSQL = BZCArticleSchema._UpdateAllSQL;
		FillAllSQL = BZCArticleSchema._FillAllSQL;
		DeleteSQL = BZCArticleSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCArticleSet();
	}

	public boolean add(BZCArticleSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCArticleSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCArticleSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCArticleSchema get(int index) {
		BZCArticleSchema tSchema = (BZCArticleSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCArticleSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCArticleSet aSet) {
		return super.set(aSet);
	}
}
 