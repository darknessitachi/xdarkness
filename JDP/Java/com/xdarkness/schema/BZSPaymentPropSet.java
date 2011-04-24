package com.xdarkness.schema;

import com.xdarkness.schema.BZSPaymentPropSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZSPaymentPropSet extends SchemaSet {
	public BZSPaymentPropSet() {
		this(10,0);
	}

	public BZSPaymentPropSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZSPaymentPropSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZSPaymentPropSchema._TableCode;
		Columns = BZSPaymentPropSchema._Columns;
		NameSpace = BZSPaymentPropSchema._NameSpace;
		InsertAllSQL = BZSPaymentPropSchema._InsertAllSQL;
		UpdateAllSQL = BZSPaymentPropSchema._UpdateAllSQL;
		FillAllSQL = BZSPaymentPropSchema._FillAllSQL;
		DeleteSQL = BZSPaymentPropSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZSPaymentPropSet();
	}

	public boolean add(BZSPaymentPropSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZSPaymentPropSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZSPaymentPropSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZSPaymentPropSchema get(int index) {
		BZSPaymentPropSchema tSchema = (BZSPaymentPropSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZSPaymentPropSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZSPaymentPropSet aSet) {
		return super.set(aSet);
	}
}
 