package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCApplySchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCApplySet extends SchemaSet {
	public BZCApplySet() {
		this(10,0);
	}

	public BZCApplySet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCApplySet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCApplySchema._TableCode;
		Columns = BZCApplySchema._Columns;
		NameSpace = BZCApplySchema._NameSpace;
		InsertAllSQL = BZCApplySchema._InsertAllSQL;
		UpdateAllSQL = BZCApplySchema._UpdateAllSQL;
		FillAllSQL = BZCApplySchema._FillAllSQL;
		DeleteSQL = BZCApplySchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCApplySet();
	}

	public boolean add(BZCApplySchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCApplySet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCApplySchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCApplySchema get(int index) {
		BZCApplySchema tSchema = (BZCApplySchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCApplySchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCApplySet aSet) {
		return super.set(aSet);
	}
}
 