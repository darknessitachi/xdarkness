package com.abigdreamer.schema;

import com.abigdreamer.schema.BZSSendSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZSSendSet extends SchemaSet {
	public BZSSendSet() {
		this(10,0);
	}

	public BZSSendSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZSSendSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZSSendSchema._TableCode;
		Columns = BZSSendSchema._Columns;
		NameSpace = BZSSendSchema._NameSpace;
		InsertAllSQL = BZSSendSchema._InsertAllSQL;
		UpdateAllSQL = BZSSendSchema._UpdateAllSQL;
		FillAllSQL = BZSSendSchema._FillAllSQL;
		DeleteSQL = BZSSendSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZSSendSet();
	}

	public boolean add(BZSSendSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZSSendSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZSSendSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZSSendSchema get(int index) {
		BZSSendSchema tSchema = (BZSSendSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZSSendSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZSSendSet aSet) {
		return super.set(aSet);
	}
}
 