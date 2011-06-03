package com.abigdreamer.schema;

import com.abigdreamer.schema.ZDUserSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZDUserSet extends SchemaSet {
	public ZDUserSet() {
		this(10,0);
	}

	public ZDUserSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDUserSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDUserSchema._TableCode;
		Columns = ZDUserSchema._Columns;
		NameSpace = ZDUserSchema._NameSpace;
		InsertAllSQL = ZDUserSchema._InsertAllSQL;
		UpdateAllSQL = ZDUserSchema._UpdateAllSQL;
		FillAllSQL = ZDUserSchema._FillAllSQL;
		DeleteSQL = ZDUserSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDUserSet();
	}

	public boolean add(ZDUserSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDUserSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDUserSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDUserSchema get(int index) {
		ZDUserSchema tSchema = (ZDUserSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDUserSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDUserSet aSet) {
		return super.set(aSet);
	}
}
 