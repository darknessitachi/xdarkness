package com.xdarkness.schema;

import com.xdarkness.schema.ZSSendSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZSSendSet extends SchemaSet {
	public ZSSendSet() {
		this(10,0);
	}

	public ZSSendSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZSSendSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZSSendSchema._TableCode;
		Columns = ZSSendSchema._Columns;
		NameSpace = ZSSendSchema._NameSpace;
		InsertAllSQL = ZSSendSchema._InsertAllSQL;
		UpdateAllSQL = ZSSendSchema._UpdateAllSQL;
		FillAllSQL = ZSSendSchema._FillAllSQL;
		DeleteSQL = ZSSendSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZSSendSet();
	}

	public boolean add(ZSSendSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZSSendSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZSSendSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZSSendSchema get(int index) {
		ZSSendSchema tSchema = (ZSSendSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZSSendSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZSSendSet aSet) {
		return super.set(aSet);
	}
}
 