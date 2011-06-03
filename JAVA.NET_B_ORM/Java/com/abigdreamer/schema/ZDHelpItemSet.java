package com.abigdreamer.schema;

import com.abigdreamer.schema.ZDHelpItemSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZDHelpItemSet extends SchemaSet {
	public ZDHelpItemSet() {
		this(10,0);
	}

	public ZDHelpItemSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDHelpItemSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDHelpItemSchema._TableCode;
		Columns = ZDHelpItemSchema._Columns;
		NameSpace = ZDHelpItemSchema._NameSpace;
		InsertAllSQL = ZDHelpItemSchema._InsertAllSQL;
		UpdateAllSQL = ZDHelpItemSchema._UpdateAllSQL;
		FillAllSQL = ZDHelpItemSchema._FillAllSQL;
		DeleteSQL = ZDHelpItemSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDHelpItemSet();
	}

	public boolean add(ZDHelpItemSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDHelpItemSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDHelpItemSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDHelpItemSchema get(int index) {
		ZDHelpItemSchema tSchema = (ZDHelpItemSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDHelpItemSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDHelpItemSet aSet) {
		return super.set(aSet);
	}
}
 