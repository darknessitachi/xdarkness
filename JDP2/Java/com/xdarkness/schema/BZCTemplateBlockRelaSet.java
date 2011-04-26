package com.xdarkness.schema;

import com.xdarkness.schema.BZCTemplateBlockRelaSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCTemplateBlockRelaSet extends SchemaSet {
	public BZCTemplateBlockRelaSet() {
		this(10,0);
	}

	public BZCTemplateBlockRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCTemplateBlockRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCTemplateBlockRelaSchema._TableCode;
		Columns = BZCTemplateBlockRelaSchema._Columns;
		NameSpace = BZCTemplateBlockRelaSchema._NameSpace;
		InsertAllSQL = BZCTemplateBlockRelaSchema._InsertAllSQL;
		UpdateAllSQL = BZCTemplateBlockRelaSchema._UpdateAllSQL;
		FillAllSQL = BZCTemplateBlockRelaSchema._FillAllSQL;
		DeleteSQL = BZCTemplateBlockRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCTemplateBlockRelaSet();
	}

	public boolean add(BZCTemplateBlockRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCTemplateBlockRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCTemplateBlockRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCTemplateBlockRelaSchema get(int index) {
		BZCTemplateBlockRelaSchema tSchema = (BZCTemplateBlockRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCTemplateBlockRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCTemplateBlockRelaSet aSet) {
		return super.set(aSet);
	}
}
 