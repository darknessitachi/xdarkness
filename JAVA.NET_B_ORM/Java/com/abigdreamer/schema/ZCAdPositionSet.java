package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCAdPositionSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCAdPositionSet extends SchemaSet {
	public ZCAdPositionSet() {
		this(10,0);
	}

	public ZCAdPositionSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCAdPositionSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCAdPositionSchema._TableCode;
		Columns = ZCAdPositionSchema._Columns;
		NameSpace = ZCAdPositionSchema._NameSpace;
		InsertAllSQL = ZCAdPositionSchema._InsertAllSQL;
		UpdateAllSQL = ZCAdPositionSchema._UpdateAllSQL;
		FillAllSQL = ZCAdPositionSchema._FillAllSQL;
		DeleteSQL = ZCAdPositionSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCAdPositionSet();
	}

	public boolean add(ZCAdPositionSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCAdPositionSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCAdPositionSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCAdPositionSchema get(int index) {
		ZCAdPositionSchema tSchema = (ZCAdPositionSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCAdPositionSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCAdPositionSet aSet) {
		return super.set(aSet);
	}
}
 