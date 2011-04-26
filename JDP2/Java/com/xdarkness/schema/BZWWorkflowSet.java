package com.xdarkness.schema;

import com.xdarkness.schema.BZWWorkflowSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZWWorkflowSet extends SchemaSet {
	public BZWWorkflowSet() {
		this(10,0);
	}

	public BZWWorkflowSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZWWorkflowSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZWWorkflowSchema._TableCode;
		Columns = BZWWorkflowSchema._Columns;
		NameSpace = BZWWorkflowSchema._NameSpace;
		InsertAllSQL = BZWWorkflowSchema._InsertAllSQL;
		UpdateAllSQL = BZWWorkflowSchema._UpdateAllSQL;
		FillAllSQL = BZWWorkflowSchema._FillAllSQL;
		DeleteSQL = BZWWorkflowSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZWWorkflowSet();
	}

	public boolean add(BZWWorkflowSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZWWorkflowSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZWWorkflowSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZWWorkflowSchema get(int index) {
		BZWWorkflowSchema tSchema = (BZWWorkflowSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZWWorkflowSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZWWorkflowSet aSet) {
		return super.set(aSet);
	}
}
 