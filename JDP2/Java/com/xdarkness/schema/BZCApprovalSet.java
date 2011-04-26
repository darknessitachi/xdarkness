package com.xdarkness.schema;

import com.xdarkness.schema.BZCApprovalSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCApprovalSet extends SchemaSet {
	public BZCApprovalSet() {
		this(10,0);
	}

	public BZCApprovalSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCApprovalSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCApprovalSchema._TableCode;
		Columns = BZCApprovalSchema._Columns;
		NameSpace = BZCApprovalSchema._NameSpace;
		InsertAllSQL = BZCApprovalSchema._InsertAllSQL;
		UpdateAllSQL = BZCApprovalSchema._UpdateAllSQL;
		FillAllSQL = BZCApprovalSchema._FillAllSQL;
		DeleteSQL = BZCApprovalSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCApprovalSet();
	}

	public boolean add(BZCApprovalSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCApprovalSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCApprovalSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCApprovalSchema get(int index) {
		BZCApprovalSchema tSchema = (BZCApprovalSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCApprovalSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCApprovalSet aSet) {
		return super.set(aSet);
	}
}
 