package com.xdarkness.schema;

import com.xdarkness.schema.BZCArticleLogSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCArticleLogSet extends SchemaSet {
	public BZCArticleLogSet() {
		this(10,0);
	}

	public BZCArticleLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCArticleLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCArticleLogSchema._TableCode;
		Columns = BZCArticleLogSchema._Columns;
		NameSpace = BZCArticleLogSchema._NameSpace;
		InsertAllSQL = BZCArticleLogSchema._InsertAllSQL;
		UpdateAllSQL = BZCArticleLogSchema._UpdateAllSQL;
		FillAllSQL = BZCArticleLogSchema._FillAllSQL;
		DeleteSQL = BZCArticleLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCArticleLogSet();
	}

	public boolean add(BZCArticleLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCArticleLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCArticleLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCArticleLogSchema get(int index) {
		BZCArticleLogSchema tSchema = (BZCArticleLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCArticleLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCArticleLogSet aSet) {
		return super.set(aSet);
	}
}
 