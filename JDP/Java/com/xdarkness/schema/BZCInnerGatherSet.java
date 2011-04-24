package com.xdarkness.schema;

import com.xdarkness.schema.BZCInnerGatherSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCInnerGatherSet extends SchemaSet {
	public BZCInnerGatherSet() {
		this(10,0);
	}

	public BZCInnerGatherSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCInnerGatherSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCInnerGatherSchema._TableCode;
		Columns = BZCInnerGatherSchema._Columns;
		NameSpace = BZCInnerGatherSchema._NameSpace;
		InsertAllSQL = BZCInnerGatherSchema._InsertAllSQL;
		UpdateAllSQL = BZCInnerGatherSchema._UpdateAllSQL;
		FillAllSQL = BZCInnerGatherSchema._FillAllSQL;
		DeleteSQL = BZCInnerGatherSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCInnerGatherSet();
	}

	public boolean add(BZCInnerGatherSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCInnerGatherSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCInnerGatherSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCInnerGatherSchema get(int index) {
		BZCInnerGatherSchema tSchema = (BZCInnerGatherSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCInnerGatherSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCInnerGatherSet aSet) {
		return super.set(aSet);
	}
}
 