package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCPaperIssueSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCPaperIssueSet extends SchemaSet {
	public ZCPaperIssueSet() {
		this(10,0);
	}

	public ZCPaperIssueSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCPaperIssueSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCPaperIssueSchema._TableCode;
		Columns = ZCPaperIssueSchema._Columns;
		NameSpace = ZCPaperIssueSchema._NameSpace;
		InsertAllSQL = ZCPaperIssueSchema._InsertAllSQL;
		UpdateAllSQL = ZCPaperIssueSchema._UpdateAllSQL;
		FillAllSQL = ZCPaperIssueSchema._FillAllSQL;
		DeleteSQL = ZCPaperIssueSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCPaperIssueSet();
	}

	public boolean add(ZCPaperIssueSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCPaperIssueSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCPaperIssueSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCPaperIssueSchema get(int index) {
		ZCPaperIssueSchema tSchema = (ZCPaperIssueSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCPaperIssueSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCPaperIssueSet aSet) {
		return super.set(aSet);
	}
}
 