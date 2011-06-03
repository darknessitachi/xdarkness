package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCCustomTableColumnSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCCustomTableColumnSet extends SchemaSet {
	public ZCCustomTableColumnSet() {
		this(10,0);
	}

	public ZCCustomTableColumnSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCCustomTableColumnSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCCustomTableColumnSchema._TableCode;
		Columns = ZCCustomTableColumnSchema._Columns;
		NameSpace = ZCCustomTableColumnSchema._NameSpace;
		InsertAllSQL = ZCCustomTableColumnSchema._InsertAllSQL;
		UpdateAllSQL = ZCCustomTableColumnSchema._UpdateAllSQL;
		FillAllSQL = ZCCustomTableColumnSchema._FillAllSQL;
		DeleteSQL = ZCCustomTableColumnSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCCustomTableColumnSet();
	}

	public boolean add(ZCCustomTableColumnSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCCustomTableColumnSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCCustomTableColumnSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCCustomTableColumnSchema get(int index) {
		ZCCustomTableColumnSchema tSchema = (ZCCustomTableColumnSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCCustomTableColumnSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCCustomTableColumnSet aSet) {
		return super.set(aSet);
	}
}
 