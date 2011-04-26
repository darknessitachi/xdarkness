package com.xdarkness.schema;

import com.xdarkness.schema.ZSPaymentSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZSPaymentSet extends SchemaSet {
	public ZSPaymentSet() {
		this(10,0);
	}

	public ZSPaymentSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZSPaymentSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZSPaymentSchema._TableCode;
		Columns = ZSPaymentSchema._Columns;
		NameSpace = ZSPaymentSchema._NameSpace;
		InsertAllSQL = ZSPaymentSchema._InsertAllSQL;
		UpdateAllSQL = ZSPaymentSchema._UpdateAllSQL;
		FillAllSQL = ZSPaymentSchema._FillAllSQL;
		DeleteSQL = ZSPaymentSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZSPaymentSet();
	}

	public boolean add(ZSPaymentSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZSPaymentSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZSPaymentSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZSPaymentSchema get(int index) {
		ZSPaymentSchema tSchema = (ZSPaymentSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZSPaymentSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZSPaymentSet aSet) {
		return super.set(aSet);
	}
}
 