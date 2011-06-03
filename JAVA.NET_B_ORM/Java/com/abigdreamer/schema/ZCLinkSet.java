package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCLinkSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCLinkSet extends SchemaSet {
	public ZCLinkSet() {
		this(10,0);
	}

	public ZCLinkSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCLinkSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCLinkSchema._TableCode;
		Columns = ZCLinkSchema._Columns;
		NameSpace = ZCLinkSchema._NameSpace;
		InsertAllSQL = ZCLinkSchema._InsertAllSQL;
		UpdateAllSQL = ZCLinkSchema._UpdateAllSQL;
		FillAllSQL = ZCLinkSchema._FillAllSQL;
		DeleteSQL = ZCLinkSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCLinkSet();
	}

	public boolean add(ZCLinkSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCLinkSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCLinkSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCLinkSchema get(int index) {
		ZCLinkSchema tSchema = (ZCLinkSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCLinkSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCLinkSet aSet) {
		return super.set(aSet);
	}
}
 