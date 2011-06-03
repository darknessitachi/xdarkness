package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCTemplateSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCTemplateSet extends SchemaSet {
	public BZCTemplateSet() {
		this(10,0);
	}

	public BZCTemplateSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCTemplateSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCTemplateSchema._TableCode;
		Columns = BZCTemplateSchema._Columns;
		NameSpace = BZCTemplateSchema._NameSpace;
		InsertAllSQL = BZCTemplateSchema._InsertAllSQL;
		UpdateAllSQL = BZCTemplateSchema._UpdateAllSQL;
		FillAllSQL = BZCTemplateSchema._FillAllSQL;
		DeleteSQL = BZCTemplateSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCTemplateSet();
	}

	public boolean add(BZCTemplateSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCTemplateSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCTemplateSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCTemplateSchema get(int index) {
		BZCTemplateSchema tSchema = (BZCTemplateSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCTemplateSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCTemplateSet aSet) {
		return super.set(aSet);
	}
}
 