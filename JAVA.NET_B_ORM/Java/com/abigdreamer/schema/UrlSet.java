package com.abigdreamer.schema;

import com.abigdreamer.schema.UrlSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class UrlSet extends SchemaSet {
	public UrlSet() {
		this(10,0);
	}

	public UrlSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public UrlSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = UrlSchema._TableCode;
		Columns = UrlSchema._Columns;
		NameSpace = UrlSchema._NameSpace;
		InsertAllSQL = UrlSchema._InsertAllSQL;
		UpdateAllSQL = UrlSchema._UpdateAllSQL;
		FillAllSQL = UrlSchema._FillAllSQL;
		DeleteSQL = UrlSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new UrlSet();
	}

	public boolean add(UrlSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(UrlSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(UrlSchema aSchema) {
		return super.remove(aSchema);
	}

	public UrlSchema get(int index) {
		UrlSchema tSchema = (UrlSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, UrlSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(UrlSet aSet) {
		return super.set(aSet);
	}
}
 