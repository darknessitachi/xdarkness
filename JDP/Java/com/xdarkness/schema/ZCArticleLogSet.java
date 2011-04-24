package com.xdarkness.schema;

import com.xdarkness.schema.ZCArticleLogSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCArticleLogSet extends SchemaSet {
	public ZCArticleLogSet() {
		this(10,0);
	}

	public ZCArticleLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCArticleLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCArticleLogSchema._TableCode;
		Columns = ZCArticleLogSchema._Columns;
		NameSpace = ZCArticleLogSchema._NameSpace;
		InsertAllSQL = ZCArticleLogSchema._InsertAllSQL;
		UpdateAllSQL = ZCArticleLogSchema._UpdateAllSQL;
		FillAllSQL = ZCArticleLogSchema._FillAllSQL;
		DeleteSQL = ZCArticleLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCArticleLogSet();
	}

	public boolean add(ZCArticleLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCArticleLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCArticleLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCArticleLogSchema get(int index) {
		ZCArticleLogSchema tSchema = (ZCArticleLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCArticleLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCArticleLogSet aSet) {
		return super.set(aSet);
	}
}
 