package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCPaperPageNewsRelaSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCPaperPageNewsRelaSet extends SchemaSet {
	public ZCPaperPageNewsRelaSet() {
		this(10,0);
	}

	public ZCPaperPageNewsRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCPaperPageNewsRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCPaperPageNewsRelaSchema._TableCode;
		Columns = ZCPaperPageNewsRelaSchema._Columns;
		NameSpace = ZCPaperPageNewsRelaSchema._NameSpace;
		InsertAllSQL = ZCPaperPageNewsRelaSchema._InsertAllSQL;
		UpdateAllSQL = ZCPaperPageNewsRelaSchema._UpdateAllSQL;
		FillAllSQL = ZCPaperPageNewsRelaSchema._FillAllSQL;
		DeleteSQL = ZCPaperPageNewsRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCPaperPageNewsRelaSet();
	}

	public boolean add(ZCPaperPageNewsRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCPaperPageNewsRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCPaperPageNewsRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCPaperPageNewsRelaSchema get(int index) {
		ZCPaperPageNewsRelaSchema tSchema = (ZCPaperPageNewsRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCPaperPageNewsRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCPaperPageNewsRelaSet aSet) {
		return super.set(aSet);
	}
}
 