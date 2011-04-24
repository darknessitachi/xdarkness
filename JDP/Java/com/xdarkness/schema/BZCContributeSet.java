package com.xdarkness.schema;

import com.xdarkness.schema.BZCContributeSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCContributeSet extends SchemaSet {
	public BZCContributeSet() {
		this(10,0);
	}

	public BZCContributeSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCContributeSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCContributeSchema._TableCode;
		Columns = BZCContributeSchema._Columns;
		NameSpace = BZCContributeSchema._NameSpace;
		InsertAllSQL = BZCContributeSchema._InsertAllSQL;
		UpdateAllSQL = BZCContributeSchema._UpdateAllSQL;
		FillAllSQL = BZCContributeSchema._FillAllSQL;
		DeleteSQL = BZCContributeSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCContributeSet();
	}

	public boolean add(BZCContributeSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCContributeSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCContributeSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCContributeSchema get(int index) {
		BZCContributeSchema tSchema = (BZCContributeSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCContributeSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCContributeSet aSet) {
		return super.set(aSet);
	}
}
 