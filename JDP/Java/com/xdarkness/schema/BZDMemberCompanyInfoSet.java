package com.xdarkness.schema;

import com.xdarkness.schema.BZDMemberCompanyInfoSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZDMemberCompanyInfoSet extends SchemaSet {
	public BZDMemberCompanyInfoSet() {
		this(10,0);
	}

	public BZDMemberCompanyInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDMemberCompanyInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDMemberCompanyInfoSchema._TableCode;
		Columns = BZDMemberCompanyInfoSchema._Columns;
		NameSpace = BZDMemberCompanyInfoSchema._NameSpace;
		InsertAllSQL = BZDMemberCompanyInfoSchema._InsertAllSQL;
		UpdateAllSQL = BZDMemberCompanyInfoSchema._UpdateAllSQL;
		FillAllSQL = BZDMemberCompanyInfoSchema._FillAllSQL;
		DeleteSQL = BZDMemberCompanyInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDMemberCompanyInfoSet();
	}

	public boolean add(BZDMemberCompanyInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDMemberCompanyInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDMemberCompanyInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDMemberCompanyInfoSchema get(int index) {
		BZDMemberCompanyInfoSchema tSchema = (BZDMemberCompanyInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDMemberCompanyInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDMemberCompanyInfoSet aSet) {
		return super.set(aSet);
	}
}
 