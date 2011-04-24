package com.xdarkness.schema;

import com.xdarkness.schema.UseOffSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class UseOffSet extends SchemaSet {
	public UseOffSet() {
		this(10,0);
	}

	public UseOffSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public UseOffSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = UseOffSchema._TableCode;
		Columns = UseOffSchema._Columns;
		NameSpace = UseOffSchema._NameSpace;
		InsertAllSQL = UseOffSchema._InsertAllSQL;
		UpdateAllSQL = UseOffSchema._UpdateAllSQL;
		FillAllSQL = UseOffSchema._FillAllSQL;
		DeleteSQL = UseOffSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new UseOffSet();
	}

	public boolean add(UseOffSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(UseOffSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(UseOffSchema aSchema) {
		return super.remove(aSchema);
	}

	public UseOffSchema get(int index) {
		UseOffSchema tSchema = (UseOffSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, UseOffSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(UseOffSet aSet) {
		return super.set(aSet);
	}
}
 