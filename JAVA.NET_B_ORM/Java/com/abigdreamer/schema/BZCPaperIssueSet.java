package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCPaperIssueSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCPaperIssueSet extends SchemaSet {
	public BZCPaperIssueSet() {
		this(10,0);
	}

	public BZCPaperIssueSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCPaperIssueSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCPaperIssueSchema._TableCode;
		Columns = BZCPaperIssueSchema._Columns;
		NameSpace = BZCPaperIssueSchema._NameSpace;
		InsertAllSQL = BZCPaperIssueSchema._InsertAllSQL;
		UpdateAllSQL = BZCPaperIssueSchema._UpdateAllSQL;
		FillAllSQL = BZCPaperIssueSchema._FillAllSQL;
		DeleteSQL = BZCPaperIssueSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCPaperIssueSet();
	}

	public boolean add(BZCPaperIssueSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCPaperIssueSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCPaperIssueSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCPaperIssueSchema get(int index) {
		BZCPaperIssueSchema tSchema = (BZCPaperIssueSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCPaperIssueSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCPaperIssueSet aSet) {
		return super.set(aSet);
	}
}
 