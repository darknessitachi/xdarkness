package com.xdarkness.schema;

import com.xdarkness.schema.BUseOffSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BUseOffSet extends SchemaSet {
	public BUseOffSet() {
		this(10,0);
	}

	public BUseOffSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BUseOffSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BUseOffSchema._TableCode;
		Columns = BUseOffSchema._Columns;
		NameSpace = BUseOffSchema._NameSpace;
		InsertAllSQL = BUseOffSchema._InsertAllSQL;
		UpdateAllSQL = BUseOffSchema._UpdateAllSQL;
		FillAllSQL = BUseOffSchema._FillAllSQL;
		DeleteSQL = BUseOffSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BUseOffSet();
	}

	public boolean add(BUseOffSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BUseOffSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BUseOffSchema aSchema) {
		return super.remove(aSchema);
	}

	public BUseOffSchema get(int index) {
		BUseOffSchema tSchema = (BUseOffSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BUseOffSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BUseOffSet aSet) {
		return super.set(aSet);
	}
}
 