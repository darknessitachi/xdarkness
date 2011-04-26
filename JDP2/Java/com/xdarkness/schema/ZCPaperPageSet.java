package com.xdarkness.schema;

import com.xdarkness.schema.ZCPaperPageSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCPaperPageSet extends SchemaSet {
	public ZCPaperPageSet() {
		this(10,0);
	}

	public ZCPaperPageSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCPaperPageSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCPaperPageSchema._TableCode;
		Columns = ZCPaperPageSchema._Columns;
		NameSpace = ZCPaperPageSchema._NameSpace;
		InsertAllSQL = ZCPaperPageSchema._InsertAllSQL;
		UpdateAllSQL = ZCPaperPageSchema._UpdateAllSQL;
		FillAllSQL = ZCPaperPageSchema._FillAllSQL;
		DeleteSQL = ZCPaperPageSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCPaperPageSet();
	}

	public boolean add(ZCPaperPageSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCPaperPageSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCPaperPageSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCPaperPageSchema get(int index) {
		ZCPaperPageSchema tSchema = (ZCPaperPageSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCPaperPageSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCPaperPageSet aSet) {
		return super.set(aSet);
	}
}
 