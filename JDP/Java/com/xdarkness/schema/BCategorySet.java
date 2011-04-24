package com.xdarkness.schema;

import com.xdarkness.schema.BCategorySchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BCategorySet extends SchemaSet {
	public BCategorySet() {
		this(10,0);
	}

	public BCategorySet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BCategorySet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BCategorySchema._TableCode;
		Columns = BCategorySchema._Columns;
		NameSpace = BCategorySchema._NameSpace;
		InsertAllSQL = BCategorySchema._InsertAllSQL;
		UpdateAllSQL = BCategorySchema._UpdateAllSQL;
		FillAllSQL = BCategorySchema._FillAllSQL;
		DeleteSQL = BCategorySchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BCategorySet();
	}

	public boolean add(BCategorySchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BCategorySet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BCategorySchema aSchema) {
		return super.remove(aSchema);
	}

	public BCategorySchema get(int index) {
		BCategorySchema tSchema = (BCategorySchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BCategorySchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BCategorySet aSet) {
		return super.set(aSet);
	}
}
 