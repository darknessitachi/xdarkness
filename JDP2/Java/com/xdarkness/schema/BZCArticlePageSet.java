package com.xdarkness.schema;

import com.xdarkness.schema.BZCArticlePageSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCArticlePageSet extends SchemaSet {
	public BZCArticlePageSet() {
		this(10,0);
	}

	public BZCArticlePageSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCArticlePageSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCArticlePageSchema._TableCode;
		Columns = BZCArticlePageSchema._Columns;
		NameSpace = BZCArticlePageSchema._NameSpace;
		InsertAllSQL = BZCArticlePageSchema._InsertAllSQL;
		UpdateAllSQL = BZCArticlePageSchema._UpdateAllSQL;
		FillAllSQL = BZCArticlePageSchema._FillAllSQL;
		DeleteSQL = BZCArticlePageSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCArticlePageSet();
	}

	public boolean add(BZCArticlePageSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCArticlePageSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCArticlePageSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCArticlePageSchema get(int index) {
		BZCArticlePageSchema tSchema = (BZCArticlePageSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCArticlePageSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCArticlePageSet aSet) {
		return super.set(aSet);
	}
}
 