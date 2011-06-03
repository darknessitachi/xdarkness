package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCImagePlayerSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCImagePlayerSet extends SchemaSet {
	public ZCImagePlayerSet() {
		this(10,0);
	}

	public ZCImagePlayerSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCImagePlayerSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCImagePlayerSchema._TableCode;
		Columns = ZCImagePlayerSchema._Columns;
		NameSpace = ZCImagePlayerSchema._NameSpace;
		InsertAllSQL = ZCImagePlayerSchema._InsertAllSQL;
		UpdateAllSQL = ZCImagePlayerSchema._UpdateAllSQL;
		FillAllSQL = ZCImagePlayerSchema._FillAllSQL;
		DeleteSQL = ZCImagePlayerSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCImagePlayerSet();
	}

	public boolean add(ZCImagePlayerSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCImagePlayerSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCImagePlayerSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCImagePlayerSchema get(int index) {
		ZCImagePlayerSchema tSchema = (ZCImagePlayerSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCImagePlayerSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCImagePlayerSet aSet) {
		return super.set(aSet);
	}
}
 