package com.abigdreamer.schema;

import com.abigdreamer.schema.BZWWorkflowDefSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZWWorkflowDefSet extends SchemaSet {
	public BZWWorkflowDefSet() {
		this(10,0);
	}

	public BZWWorkflowDefSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZWWorkflowDefSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZWWorkflowDefSchema._TableCode;
		Columns = BZWWorkflowDefSchema._Columns;
		NameSpace = BZWWorkflowDefSchema._NameSpace;
		InsertAllSQL = BZWWorkflowDefSchema._InsertAllSQL;
		UpdateAllSQL = BZWWorkflowDefSchema._UpdateAllSQL;
		FillAllSQL = BZWWorkflowDefSchema._FillAllSQL;
		DeleteSQL = BZWWorkflowDefSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZWWorkflowDefSet();
	}

	public boolean add(BZWWorkflowDefSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZWWorkflowDefSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZWWorkflowDefSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZWWorkflowDefSchema get(int index) {
		BZWWorkflowDefSchema tSchema = (BZWWorkflowDefSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZWWorkflowDefSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZWWorkflowDefSet aSet) {
		return super.set(aSet);
	}
}
 