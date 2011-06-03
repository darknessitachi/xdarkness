package com.abigdreamer.schema;

import com.abigdreamer.schema.ZXTestSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZXTestSet extends SchemaSet {
	public ZXTestSet() {
		this(10,0);
	}

	public ZXTestSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZXTestSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZXTestSchema._TableCode;
		Columns = ZXTestSchema._Columns;
		NameSpace = ZXTestSchema._NameSpace;
		InsertAllSQL = ZXTestSchema._InsertAllSQL;
		UpdateAllSQL = ZXTestSchema._UpdateAllSQL;
		FillAllSQL = ZXTestSchema._FillAllSQL;
		DeleteSQL = ZXTestSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZXTestSet();
	}

	public boolean add(ZXTestSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZXTestSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZXTestSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZXTestSchema get(int index) {
		ZXTestSchema tSchema = (ZXTestSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZXTestSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZXTestSet aSet) {
		return super.set(aSet);
	}
}
 