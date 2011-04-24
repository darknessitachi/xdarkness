package com.xdarkness.schema;

import com.xdarkness.schema.BCategoryValueSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BCategoryValueSet extends SchemaSet {
	public BCategoryValueSet() {
		this(10,0);
	}

	public BCategoryValueSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BCategoryValueSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BCategoryValueSchema._TableCode;
		Columns = BCategoryValueSchema._Columns;
		NameSpace = BCategoryValueSchema._NameSpace;
		InsertAllSQL = BCategoryValueSchema._InsertAllSQL;
		UpdateAllSQL = BCategoryValueSchema._UpdateAllSQL;
		FillAllSQL = BCategoryValueSchema._FillAllSQL;
		DeleteSQL = BCategoryValueSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BCategoryValueSet();
	}

	public boolean add(BCategoryValueSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BCategoryValueSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BCategoryValueSchema aSchema) {
		return super.remove(aSchema);
	}

	public BCategoryValueSchema get(int index) {
		BCategoryValueSchema tSchema = (BCategoryValueSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BCategoryValueSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BCategoryValueSet aSet) {
		return super.set(aSet);
	}
}
 