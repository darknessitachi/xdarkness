package com.xdarkness.schema;

import com.xdarkness.schema.BZWWorkflowEntrySchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZWWorkflowEntrySet extends SchemaSet {
	public BZWWorkflowEntrySet() {
		this(10,0);
	}

	public BZWWorkflowEntrySet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZWWorkflowEntrySet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZWWorkflowEntrySchema._TableCode;
		Columns = BZWWorkflowEntrySchema._Columns;
		NameSpace = BZWWorkflowEntrySchema._NameSpace;
		InsertAllSQL = BZWWorkflowEntrySchema._InsertAllSQL;
		UpdateAllSQL = BZWWorkflowEntrySchema._UpdateAllSQL;
		FillAllSQL = BZWWorkflowEntrySchema._FillAllSQL;
		DeleteSQL = BZWWorkflowEntrySchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZWWorkflowEntrySet();
	}

	public boolean add(BZWWorkflowEntrySchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZWWorkflowEntrySet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZWWorkflowEntrySchema aSchema) {
		return super.remove(aSchema);
	}

	public BZWWorkflowEntrySchema get(int index) {
		BZWWorkflowEntrySchema tSchema = (BZWWorkflowEntrySchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZWWorkflowEntrySchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZWWorkflowEntrySet aSet) {
		return super.set(aSet);
	}
}
 