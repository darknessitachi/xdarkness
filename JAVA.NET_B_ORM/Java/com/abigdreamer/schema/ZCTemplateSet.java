package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCTemplateSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCTemplateSet extends SchemaSet {
	public ZCTemplateSet() {
		this(10,0);
	}

	public ZCTemplateSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCTemplateSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCTemplateSchema._TableCode;
		Columns = ZCTemplateSchema._Columns;
		NameSpace = ZCTemplateSchema._NameSpace;
		InsertAllSQL = ZCTemplateSchema._InsertAllSQL;
		UpdateAllSQL = ZCTemplateSchema._UpdateAllSQL;
		FillAllSQL = ZCTemplateSchema._FillAllSQL;
		DeleteSQL = ZCTemplateSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCTemplateSet();
	}

	public boolean add(ZCTemplateSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCTemplateSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCTemplateSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCTemplateSchema get(int index) {
		ZCTemplateSchema tSchema = (ZCTemplateSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCTemplateSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCTemplateSet aSet) {
		return super.set(aSet);
	}
}
 