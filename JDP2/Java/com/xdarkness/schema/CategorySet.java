package com.xdarkness.schema;

import com.xdarkness.schema.CategorySchema;
import com.xdarkness.framework.orm.SchemaSet;

public class CategorySet extends SchemaSet {
	public CategorySet() {
		this(10,0);
	}

	public CategorySet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public CategorySet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = CategorySchema._TableCode;
		Columns = CategorySchema._Columns;
		NameSpace = CategorySchema._NameSpace;
		InsertAllSQL = CategorySchema._InsertAllSQL;
		UpdateAllSQL = CategorySchema._UpdateAllSQL;
		FillAllSQL = CategorySchema._FillAllSQL;
		DeleteSQL = CategorySchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new CategorySet();
	}

	public boolean add(CategorySchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(CategorySet aSet) {
		return super.add(aSet);
	}

	public boolean remove(CategorySchema aSchema) {
		return super.remove(aSchema);
	}

	public CategorySchema get(int index) {
		CategorySchema tSchema = (CategorySchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, CategorySchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(CategorySet aSet) {
		return super.set(aSet);
	}
}
 