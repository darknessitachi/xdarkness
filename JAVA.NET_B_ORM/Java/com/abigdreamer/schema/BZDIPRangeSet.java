package com.abigdreamer.schema;

import com.abigdreamer.schema.BZDIPRangeSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZDIPRangeSet extends SchemaSet {
	public BZDIPRangeSet() {
		this(10,0);
	}

	public BZDIPRangeSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZDIPRangeSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZDIPRangeSchema._TableCode;
		Columns = BZDIPRangeSchema._Columns;
		NameSpace = BZDIPRangeSchema._NameSpace;
		InsertAllSQL = BZDIPRangeSchema._InsertAllSQL;
		UpdateAllSQL = BZDIPRangeSchema._UpdateAllSQL;
		FillAllSQL = BZDIPRangeSchema._FillAllSQL;
		DeleteSQL = BZDIPRangeSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZDIPRangeSet();
	}

	public boolean add(BZDIPRangeSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZDIPRangeSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZDIPRangeSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZDIPRangeSchema get(int index) {
		BZDIPRangeSchema tSchema = (BZDIPRangeSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZDIPRangeSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZDIPRangeSet aSet) {
		return super.set(aSet);
	}
}
 