package com.xdarkness.schema;

import com.xdarkness.schema.ZCTemplateBlockRelaSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCTemplateBlockRelaSet extends SchemaSet {
	public ZCTemplateBlockRelaSet() {
		this(10,0);
	}

	public ZCTemplateBlockRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCTemplateBlockRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCTemplateBlockRelaSchema._TableCode;
		Columns = ZCTemplateBlockRelaSchema._Columns;
		NameSpace = ZCTemplateBlockRelaSchema._NameSpace;
		InsertAllSQL = ZCTemplateBlockRelaSchema._InsertAllSQL;
		UpdateAllSQL = ZCTemplateBlockRelaSchema._UpdateAllSQL;
		FillAllSQL = ZCTemplateBlockRelaSchema._FillAllSQL;
		DeleteSQL = ZCTemplateBlockRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCTemplateBlockRelaSet();
	}

	public boolean add(ZCTemplateBlockRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCTemplateBlockRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCTemplateBlockRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCTemplateBlockRelaSchema get(int index) {
		ZCTemplateBlockRelaSchema tSchema = (ZCTemplateBlockRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCTemplateBlockRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCTemplateBlockRelaSet aSet) {
		return super.set(aSet);
	}
}
 