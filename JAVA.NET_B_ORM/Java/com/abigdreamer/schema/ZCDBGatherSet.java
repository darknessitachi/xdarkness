package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCDBGatherSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCDBGatherSet extends SchemaSet {
	public ZCDBGatherSet() {
		this(10,0);
	}

	public ZCDBGatherSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCDBGatherSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCDBGatherSchema._TableCode;
		Columns = ZCDBGatherSchema._Columns;
		NameSpace = ZCDBGatherSchema._NameSpace;
		InsertAllSQL = ZCDBGatherSchema._InsertAllSQL;
		UpdateAllSQL = ZCDBGatherSchema._UpdateAllSQL;
		FillAllSQL = ZCDBGatherSchema._FillAllSQL;
		DeleteSQL = ZCDBGatherSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCDBGatherSet();
	}

	public boolean add(ZCDBGatherSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCDBGatherSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCDBGatherSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCDBGatherSchema get(int index) {
		ZCDBGatherSchema tSchema = (ZCDBGatherSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCDBGatherSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCDBGatherSet aSet) {
		return super.set(aSet);
	}
}
 