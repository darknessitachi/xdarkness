package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCApplySchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCApplySet extends SchemaSet {
	public ZCApplySet() {
		this(10,0);
	}

	public ZCApplySet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCApplySet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCApplySchema._TableCode;
		Columns = ZCApplySchema._Columns;
		NameSpace = ZCApplySchema._NameSpace;
		InsertAllSQL = ZCApplySchema._InsertAllSQL;
		UpdateAllSQL = ZCApplySchema._UpdateAllSQL;
		FillAllSQL = ZCApplySchema._FillAllSQL;
		DeleteSQL = ZCApplySchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCApplySet();
	}

	public boolean add(ZCApplySchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCApplySet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCApplySchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCApplySchema get(int index) {
		ZCApplySchema tSchema = (ZCApplySchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCApplySchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCApplySet aSet) {
		return super.set(aSet);
	}
}
 