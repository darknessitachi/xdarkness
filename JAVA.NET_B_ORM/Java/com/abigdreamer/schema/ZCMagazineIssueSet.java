package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCMagazineIssueSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCMagazineIssueSet extends SchemaSet {
	public ZCMagazineIssueSet() {
		this(10,0);
	}

	public ZCMagazineIssueSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCMagazineIssueSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCMagazineIssueSchema._TableCode;
		Columns = ZCMagazineIssueSchema._Columns;
		NameSpace = ZCMagazineIssueSchema._NameSpace;
		InsertAllSQL = ZCMagazineIssueSchema._InsertAllSQL;
		UpdateAllSQL = ZCMagazineIssueSchema._UpdateAllSQL;
		FillAllSQL = ZCMagazineIssueSchema._FillAllSQL;
		DeleteSQL = ZCMagazineIssueSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCMagazineIssueSet();
	}

	public boolean add(ZCMagazineIssueSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCMagazineIssueSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCMagazineIssueSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCMagazineIssueSchema get(int index) {
		ZCMagazineIssueSchema tSchema = (ZCMagazineIssueSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCMagazineIssueSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCMagazineIssueSet aSet) {
		return super.set(aSet);
	}
}
 