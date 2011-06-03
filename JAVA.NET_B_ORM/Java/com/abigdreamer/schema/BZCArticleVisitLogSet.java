package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCArticleVisitLogSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCArticleVisitLogSet extends SchemaSet {
	public BZCArticleVisitLogSet() {
		this(10,0);
	}

	public BZCArticleVisitLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCArticleVisitLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCArticleVisitLogSchema._TableCode;
		Columns = BZCArticleVisitLogSchema._Columns;
		NameSpace = BZCArticleVisitLogSchema._NameSpace;
		InsertAllSQL = BZCArticleVisitLogSchema._InsertAllSQL;
		UpdateAllSQL = BZCArticleVisitLogSchema._UpdateAllSQL;
		FillAllSQL = BZCArticleVisitLogSchema._FillAllSQL;
		DeleteSQL = BZCArticleVisitLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCArticleVisitLogSet();
	}

	public boolean add(BZCArticleVisitLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCArticleVisitLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCArticleVisitLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCArticleVisitLogSchema get(int index) {
		BZCArticleVisitLogSchema tSchema = (BZCArticleVisitLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCArticleVisitLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCArticleVisitLogSet aSet) {
		return super.set(aSet);
	}
}
 