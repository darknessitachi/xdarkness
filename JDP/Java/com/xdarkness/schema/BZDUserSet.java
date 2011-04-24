package com.xdarkness.schema;

import com.xdarkness.schema.BZDUserSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZDUserSet extends SchemaSet {
	public BZDUserSet() {
		this(10,0);
	}

	public BZDUserSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDUserSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDUserSchema._TableCode;
		Columns = BZDUserSchema._Columns;
		NameSpace = BZDUserSchema._NameSpace;
		InsertAllSQL = BZDUserSchema._InsertAllSQL;
		UpdateAllSQL = BZDUserSchema._UpdateAllSQL;
		FillAllSQL = BZDUserSchema._FillAllSQL;
		DeleteSQL = BZDUserSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDUserSet();
	}

	public boolean add(BZDUserSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDUserSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDUserSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDUserSchema get(int index) {
		BZDUserSchema tSchema = (BZDUserSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDUserSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDUserSet aSet) {
		return super.set(aSet);
	}
}
 