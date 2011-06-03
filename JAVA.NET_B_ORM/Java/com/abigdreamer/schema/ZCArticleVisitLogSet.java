package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCArticleVisitLogSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCArticleVisitLogSet extends SchemaSet {
	public ZCArticleVisitLogSet() {
		this(10,0);
	}

	public ZCArticleVisitLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCArticleVisitLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCArticleVisitLogSchema._TableCode;
		Columns = ZCArticleVisitLogSchema._Columns;
		NameSpace = ZCArticleVisitLogSchema._NameSpace;
		InsertAllSQL = ZCArticleVisitLogSchema._InsertAllSQL;
		UpdateAllSQL = ZCArticleVisitLogSchema._UpdateAllSQL;
		FillAllSQL = ZCArticleVisitLogSchema._FillAllSQL;
		DeleteSQL = ZCArticleVisitLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCArticleVisitLogSet();
	}

	public boolean add(ZCArticleVisitLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCArticleVisitLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCArticleVisitLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCArticleVisitLogSchema get(int index) {
		ZCArticleVisitLogSchema tSchema = (ZCArticleVisitLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCArticleVisitLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCArticleVisitLogSet aSet) {
		return super.set(aSet);
	}
}
 