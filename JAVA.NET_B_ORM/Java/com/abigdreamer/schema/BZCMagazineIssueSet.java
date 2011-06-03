package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCMagazineIssueSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCMagazineIssueSet extends SchemaSet {
	public BZCMagazineIssueSet() {
		this(10,0);
	}

	public BZCMagazineIssueSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCMagazineIssueSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCMagazineIssueSchema._TableCode;
		Columns = BZCMagazineIssueSchema._Columns;
		NameSpace = BZCMagazineIssueSchema._NameSpace;
		InsertAllSQL = BZCMagazineIssueSchema._InsertAllSQL;
		UpdateAllSQL = BZCMagazineIssueSchema._UpdateAllSQL;
		FillAllSQL = BZCMagazineIssueSchema._FillAllSQL;
		DeleteSQL = BZCMagazineIssueSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCMagazineIssueSet();
	}

	public boolean add(BZCMagazineIssueSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCMagazineIssueSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCMagazineIssueSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCMagazineIssueSchema get(int index) {
		BZCMagazineIssueSchema tSchema = (BZCMagazineIssueSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCMagazineIssueSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCMagazineIssueSet aSet) {
		return super.set(aSet);
	}
}
 