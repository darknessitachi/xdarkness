package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCContributeSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCContributeSet extends SchemaSet {
	public ZCContributeSet() {
		this(10,0);
	}

	public ZCContributeSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCContributeSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCContributeSchema._TableCode;
		Columns = ZCContributeSchema._Columns;
		NameSpace = ZCContributeSchema._NameSpace;
		InsertAllSQL = ZCContributeSchema._InsertAllSQL;
		UpdateAllSQL = ZCContributeSchema._UpdateAllSQL;
		FillAllSQL = ZCContributeSchema._FillAllSQL;
		DeleteSQL = ZCContributeSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCContributeSet();
	}

	public boolean add(ZCContributeSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCContributeSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCContributeSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCContributeSchema get(int index) {
		ZCContributeSchema tSchema = (ZCContributeSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCContributeSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCContributeSet aSet) {
		return super.set(aSet);
	}
}
 