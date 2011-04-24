package com.xdarkness.schema;

import com.xdarkness.schema.ZCJsFileSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCJsFileSet extends SchemaSet {
	public ZCJsFileSet() {
		this(10,0);
	}

	public ZCJsFileSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCJsFileSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCJsFileSchema._TableCode;
		Columns = ZCJsFileSchema._Columns;
		NameSpace = ZCJsFileSchema._NameSpace;
		InsertAllSQL = ZCJsFileSchema._InsertAllSQL;
		UpdateAllSQL = ZCJsFileSchema._UpdateAllSQL;
		FillAllSQL = ZCJsFileSchema._FillAllSQL;
		DeleteSQL = ZCJsFileSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCJsFileSet();
	}

	public boolean add(ZCJsFileSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCJsFileSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCJsFileSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCJsFileSchema get(int index) {
		ZCJsFileSchema tSchema = (ZCJsFileSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCJsFileSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCJsFileSet aSet) {
		return super.set(aSet);
	}
}
 