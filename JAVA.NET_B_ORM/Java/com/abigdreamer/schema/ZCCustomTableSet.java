package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCCustomTableSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCCustomTableSet extends SchemaSet {
	public ZCCustomTableSet() {
		this(10,0);
	}

	public ZCCustomTableSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCCustomTableSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCCustomTableSchema._TableCode;
		Columns = ZCCustomTableSchema._Columns;
		NameSpace = ZCCustomTableSchema._NameSpace;
		InsertAllSQL = ZCCustomTableSchema._InsertAllSQL;
		UpdateAllSQL = ZCCustomTableSchema._UpdateAllSQL;
		FillAllSQL = ZCCustomTableSchema._FillAllSQL;
		DeleteSQL = ZCCustomTableSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCCustomTableSet();
	}

	public boolean add(ZCCustomTableSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCCustomTableSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCCustomTableSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCCustomTableSchema get(int index) {
		ZCCustomTableSchema tSchema = (ZCCustomTableSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCCustomTableSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCCustomTableSet aSet) {
		return super.set(aSet);
	}
}
 