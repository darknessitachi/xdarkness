package com.xdarkness.schema;

import com.xdarkness.schema.ZDMemberCompanyInfoSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZDMemberCompanyInfoSet extends SchemaSet {
	public ZDMemberCompanyInfoSet() {
		this(10,0);
	}

	public ZDMemberCompanyInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZDMemberCompanyInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZDMemberCompanyInfoSchema._TableCode;
		Columns = ZDMemberCompanyInfoSchema._Columns;
		NameSpace = ZDMemberCompanyInfoSchema._NameSpace;
		InsertAllSQL = ZDMemberCompanyInfoSchema._InsertAllSQL;
		UpdateAllSQL = ZDMemberCompanyInfoSchema._UpdateAllSQL;
		FillAllSQL = ZDMemberCompanyInfoSchema._FillAllSQL;
		DeleteSQL = ZDMemberCompanyInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZDMemberCompanyInfoSet();
	}

	public boolean add(ZDMemberCompanyInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZDMemberCompanyInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZDMemberCompanyInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZDMemberCompanyInfoSchema get(int index) {
		ZDMemberCompanyInfoSchema tSchema = (ZDMemberCompanyInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZDMemberCompanyInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZDMemberCompanyInfoSet aSet) {
		return super.set(aSet);
	}
}
 