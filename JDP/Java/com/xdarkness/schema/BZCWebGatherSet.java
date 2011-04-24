package com.xdarkness.schema;

import com.xdarkness.schema.BZCWebGatherSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCWebGatherSet extends SchemaSet {
	public BZCWebGatherSet() {
		this(10,0);
	}

	public BZCWebGatherSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCWebGatherSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCWebGatherSchema._TableCode;
		Columns = BZCWebGatherSchema._Columns;
		NameSpace = BZCWebGatherSchema._NameSpace;
		InsertAllSQL = BZCWebGatherSchema._InsertAllSQL;
		UpdateAllSQL = BZCWebGatherSchema._UpdateAllSQL;
		FillAllSQL = BZCWebGatherSchema._FillAllSQL;
		DeleteSQL = BZCWebGatherSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCWebGatherSet();
	}

	public boolean add(BZCWebGatherSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCWebGatherSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCWebGatherSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCWebGatherSchema get(int index) {
		BZCWebGatherSchema tSchema = (BZCWebGatherSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCWebGatherSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCWebGatherSet aSet) {
		return super.set(aSet);
	}
}
 