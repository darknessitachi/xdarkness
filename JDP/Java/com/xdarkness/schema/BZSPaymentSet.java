package com.xdarkness.schema;

import com.xdarkness.schema.BZSPaymentSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZSPaymentSet extends SchemaSet {
	public BZSPaymentSet() {
		this(10,0);
	}

	public BZSPaymentSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZSPaymentSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZSPaymentSchema._TableCode;
		Columns = BZSPaymentSchema._Columns;
		NameSpace = BZSPaymentSchema._NameSpace;
		InsertAllSQL = BZSPaymentSchema._InsertAllSQL;
		UpdateAllSQL = BZSPaymentSchema._UpdateAllSQL;
		FillAllSQL = BZSPaymentSchema._FillAllSQL;
		DeleteSQL = BZSPaymentSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZSPaymentSet();
	}

	public boolean add(BZSPaymentSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZSPaymentSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZSPaymentSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZSPaymentSchema get(int index) {
		BZSPaymentSchema tSchema = (BZSPaymentSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZSPaymentSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZSPaymentSet aSet) {
		return super.set(aSet);
	}
}
 