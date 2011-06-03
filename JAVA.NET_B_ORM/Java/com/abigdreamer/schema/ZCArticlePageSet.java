package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCArticlePageSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCArticlePageSet extends SchemaSet {
	public ZCArticlePageSet() {
		this(10,0);
	}

	public ZCArticlePageSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCArticlePageSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCArticlePageSchema._TableCode;
		Columns = ZCArticlePageSchema._Columns;
		NameSpace = ZCArticlePageSchema._NameSpace;
		InsertAllSQL = ZCArticlePageSchema._InsertAllSQL;
		UpdateAllSQL = ZCArticlePageSchema._UpdateAllSQL;
		FillAllSQL = ZCArticlePageSchema._FillAllSQL;
		DeleteSQL = ZCArticlePageSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCArticlePageSet();
	}

	public boolean add(ZCArticlePageSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCArticlePageSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCArticlePageSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCArticlePageSchema get(int index) {
		ZCArticlePageSchema tSchema = (ZCArticlePageSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCArticlePageSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCArticlePageSet aSet) {
		return super.set(aSet);
	}
}
 