package com.xdarkness.schema;

import com.xdarkness.schema.CategoryValueSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class CategoryValueSet extends SchemaSet {
	public CategoryValueSet() {
		this(10,0);
	}

	public CategoryValueSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public CategoryValueSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = CategoryValueSchema._TableCode;
		Columns = CategoryValueSchema._Columns;
		NameSpace = CategoryValueSchema._NameSpace;
		InsertAllSQL = CategoryValueSchema._InsertAllSQL;
		UpdateAllSQL = CategoryValueSchema._UpdateAllSQL;
		FillAllSQL = CategoryValueSchema._FillAllSQL;
		DeleteSQL = CategoryValueSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new CategoryValueSet();
	}

	public boolean add(CategoryValueSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(CategoryValueSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(CategoryValueSchema aSchema) {
		return super.remove(aSchema);
	}

	public CategoryValueSchema get(int index) {
		CategoryValueSchema tSchema = (CategoryValueSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, CategoryValueSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(CategoryValueSet aSet) {
		return super.set(aSet);
	}
}
 