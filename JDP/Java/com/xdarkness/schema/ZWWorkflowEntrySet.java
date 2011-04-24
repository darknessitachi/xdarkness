package com.xdarkness.schema;

import com.xdarkness.schema.ZWWorkflowEntrySchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZWWorkflowEntrySet extends SchemaSet {
	public ZWWorkflowEntrySet() {
		this(10,0);
	}

	public ZWWorkflowEntrySet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZWWorkflowEntrySet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZWWorkflowEntrySchema._TableCode;
		Columns = ZWWorkflowEntrySchema._Columns;
		NameSpace = ZWWorkflowEntrySchema._NameSpace;
		InsertAllSQL = ZWWorkflowEntrySchema._InsertAllSQL;
		UpdateAllSQL = ZWWorkflowEntrySchema._UpdateAllSQL;
		FillAllSQL = ZWWorkflowEntrySchema._FillAllSQL;
		DeleteSQL = ZWWorkflowEntrySchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZWWorkflowEntrySet();
	}

	public boolean add(ZWWorkflowEntrySchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZWWorkflowEntrySet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZWWorkflowEntrySchema aSchema) {
		return super.remove(aSchema);
	}

	public ZWWorkflowEntrySchema get(int index) {
		ZWWorkflowEntrySchema tSchema = (ZWWorkflowEntrySchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZWWorkflowEntrySchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZWWorkflowEntrySet aSet) {
		return super.set(aSet);
	}
}
 