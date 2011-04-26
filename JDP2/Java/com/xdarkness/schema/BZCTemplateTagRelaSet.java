package com.xdarkness.schema;

import com.xdarkness.schema.BZCTemplateTagRelaSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCTemplateTagRelaSet extends SchemaSet {
	public BZCTemplateTagRelaSet() {
		this(10,0);
	}

	public BZCTemplateTagRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCTemplateTagRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCTemplateTagRelaSchema._TableCode;
		Columns = BZCTemplateTagRelaSchema._Columns;
		NameSpace = BZCTemplateTagRelaSchema._NameSpace;
		InsertAllSQL = BZCTemplateTagRelaSchema._InsertAllSQL;
		UpdateAllSQL = BZCTemplateTagRelaSchema._UpdateAllSQL;
		FillAllSQL = BZCTemplateTagRelaSchema._FillAllSQL;
		DeleteSQL = BZCTemplateTagRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCTemplateTagRelaSet();
	}

	public boolean add(BZCTemplateTagRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCTemplateTagRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCTemplateTagRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCTemplateTagRelaSchema get(int index) {
		BZCTemplateTagRelaSchema tSchema = (BZCTemplateTagRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCTemplateTagRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCTemplateTagRelaSet aSet) {
		return super.set(aSet);
	}
}
 