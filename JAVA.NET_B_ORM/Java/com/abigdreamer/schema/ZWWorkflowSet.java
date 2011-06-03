package com.abigdreamer.schema;

import com.abigdreamer.schema.ZWWorkflowSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZWWorkflowSet extends SchemaSet {
	public ZWWorkflowSet() {
		this(10,0);
	}

	public ZWWorkflowSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZWWorkflowSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZWWorkflowSchema._TableCode;
		Columns = ZWWorkflowSchema._Columns;
		NameSpace = ZWWorkflowSchema._NameSpace;
		InsertAllSQL = ZWWorkflowSchema._InsertAllSQL;
		UpdateAllSQL = ZWWorkflowSchema._UpdateAllSQL;
		FillAllSQL = ZWWorkflowSchema._FillAllSQL;
		DeleteSQL = ZWWorkflowSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZWWorkflowSet();
	}

	public boolean add(ZWWorkflowSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZWWorkflowSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZWWorkflowSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZWWorkflowSchema get(int index) {
		ZWWorkflowSchema tSchema = (ZWWorkflowSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZWWorkflowSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZWWorkflowSet aSet) {
		return super.set(aSet);
	}
}
 