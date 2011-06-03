package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCTemplateTagRelaSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCTemplateTagRelaSet extends SchemaSet {
	public ZCTemplateTagRelaSet() {
		this(10,0);
	}

	public ZCTemplateTagRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCTemplateTagRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCTemplateTagRelaSchema._TableCode;
		Columns = ZCTemplateTagRelaSchema._Columns;
		NameSpace = ZCTemplateTagRelaSchema._NameSpace;
		InsertAllSQL = ZCTemplateTagRelaSchema._InsertAllSQL;
		UpdateAllSQL = ZCTemplateTagRelaSchema._UpdateAllSQL;
		FillAllSQL = ZCTemplateTagRelaSchema._FillAllSQL;
		DeleteSQL = ZCTemplateTagRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCTemplateTagRelaSet();
	}

	public boolean add(ZCTemplateTagRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCTemplateTagRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCTemplateTagRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCTemplateTagRelaSchema get(int index) {
		ZCTemplateTagRelaSchema tSchema = (ZCTemplateTagRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCTemplateTagRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCTemplateTagRelaSet aSet) {
		return super.set(aSet);
	}
}
 