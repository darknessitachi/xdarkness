package com.xdarkness.schema;

import com.xdarkness.schema.BZXTestSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZXTestSet extends SchemaSet {
	public BZXTestSet() {
		this(10,0);
	}

	public BZXTestSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZXTestSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZXTestSchema._TableCode;
		Columns = BZXTestSchema._Columns;
		NameSpace = BZXTestSchema._NameSpace;
		InsertAllSQL = BZXTestSchema._InsertAllSQL;
		UpdateAllSQL = BZXTestSchema._UpdateAllSQL;
		FillAllSQL = BZXTestSchema._FillAllSQL;
		DeleteSQL = BZXTestSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZXTestSet();
	}

	public boolean add(BZXTestSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZXTestSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZXTestSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZXTestSchema get(int index) {
		BZXTestSchema tSchema = (BZXTestSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZXTestSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZXTestSet aSet) {
		return super.set(aSet);
	}
}
 