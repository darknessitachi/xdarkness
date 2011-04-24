package com.xdarkness.schema;

import com.xdarkness.schema.BZCAdPositionSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class BZCAdPositionSet extends SchemaSet {
	public BZCAdPositionSet() {
		this(10,0);
	}

	public BZCAdPositionSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCAdPositionSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCAdPositionSchema._TableCode;
		Columns = BZCAdPositionSchema._Columns;
		NameSpace = BZCAdPositionSchema._NameSpace;
		InsertAllSQL = BZCAdPositionSchema._InsertAllSQL;
		UpdateAllSQL = BZCAdPositionSchema._UpdateAllSQL;
		FillAllSQL = BZCAdPositionSchema._FillAllSQL;
		DeleteSQL = BZCAdPositionSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCAdPositionSet();
	}

	public boolean add(BZCAdPositionSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCAdPositionSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCAdPositionSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCAdPositionSchema get(int index) {
		BZCAdPositionSchema tSchema = (BZCAdPositionSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCAdPositionSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCAdPositionSet aSet) {
		return super.set(aSet);
	}
}
 