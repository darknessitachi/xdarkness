package com.abigdreamer.schema;

import com.abigdreamer.schema.ZSPaymentPropSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZSPaymentPropSet extends SchemaSet {
	public ZSPaymentPropSet() {
		this(10,0);
	}

	public ZSPaymentPropSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZSPaymentPropSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZSPaymentPropSchema._TableCode;
		Columns = ZSPaymentPropSchema._Columns;
		NameSpace = ZSPaymentPropSchema._NameSpace;
		InsertAllSQL = ZSPaymentPropSchema._InsertAllSQL;
		UpdateAllSQL = ZSPaymentPropSchema._UpdateAllSQL;
		FillAllSQL = ZSPaymentPropSchema._FillAllSQL;
		DeleteSQL = ZSPaymentPropSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZSPaymentPropSet();
	}

	public boolean add(ZSPaymentPropSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZSPaymentPropSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZSPaymentPropSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZSPaymentPropSchema get(int index) {
		ZSPaymentPropSchema tSchema = (ZSPaymentPropSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZSPaymentPropSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZSPaymentPropSet aSet) {
		return super.set(aSet);
	}
}
 