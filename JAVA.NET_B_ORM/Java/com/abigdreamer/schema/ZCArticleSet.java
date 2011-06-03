package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCArticleSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCArticleSet extends SchemaSet {
	public ZCArticleSet() {
		this(10,0);
	}

	public ZCArticleSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCArticleSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCArticleSchema._TableCode;
		Columns = ZCArticleSchema._Columns;
		NameSpace = ZCArticleSchema._NameSpace;
		InsertAllSQL = ZCArticleSchema._InsertAllSQL;
		UpdateAllSQL = ZCArticleSchema._UpdateAllSQL;
		FillAllSQL = ZCArticleSchema._FillAllSQL;
		DeleteSQL = ZCArticleSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCArticleSet();
	}

	public boolean add(ZCArticleSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCArticleSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCArticleSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCArticleSchema get(int index) {
		ZCArticleSchema tSchema = (ZCArticleSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCArticleSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCArticleSet aSet) {
		return super.set(aSet);
	}
}
 