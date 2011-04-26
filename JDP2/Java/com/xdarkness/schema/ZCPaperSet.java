package com.xdarkness.schema;

import com.xdarkness.schema.ZCPaperSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCPaperSet extends SchemaSet {
	public ZCPaperSet() {
		this(10,0);
	}

	public ZCPaperSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCPaperSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCPaperSchema._TableCode;
		Columns = ZCPaperSchema._Columns;
		NameSpace = ZCPaperSchema._NameSpace;
		InsertAllSQL = ZCPaperSchema._InsertAllSQL;
		UpdateAllSQL = ZCPaperSchema._UpdateAllSQL;
		FillAllSQL = ZCPaperSchema._FillAllSQL;
		DeleteSQL = ZCPaperSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCPaperSet();
	}

	public boolean add(ZCPaperSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCPaperSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCPaperSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCPaperSchema get(int index) {
		ZCPaperSchema tSchema = (ZCPaperSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCPaperSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCPaperSet aSet) {
		return super.set(aSet);
	}
}
 