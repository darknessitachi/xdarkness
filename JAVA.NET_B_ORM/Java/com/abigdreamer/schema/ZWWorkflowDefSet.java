package com.abigdreamer.schema;

import com.abigdreamer.schema.ZWWorkflowDefSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZWWorkflowDefSet extends SchemaSet {
	public ZWWorkflowDefSet() {
		this(10,0);
	}

	public ZWWorkflowDefSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZWWorkflowDefSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZWWorkflowDefSchema._TableCode;
		Columns = ZWWorkflowDefSchema._Columns;
		NameSpace = ZWWorkflowDefSchema._NameSpace;
		InsertAllSQL = ZWWorkflowDefSchema._InsertAllSQL;
		UpdateAllSQL = ZWWorkflowDefSchema._UpdateAllSQL;
		FillAllSQL = ZWWorkflowDefSchema._FillAllSQL;
		DeleteSQL = ZWWorkflowDefSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZWWorkflowDefSet();
	}

	public boolean add(ZWWorkflowDefSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZWWorkflowDefSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZWWorkflowDefSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZWWorkflowDefSchema get(int index) {
		ZWWorkflowDefSchema tSchema = (ZWWorkflowDefSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZWWorkflowDefSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZWWorkflowDefSet aSet) {
		return super.set(aSet);
	}
}
 