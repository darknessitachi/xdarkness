package com.xdarkness.schema;

import com.xdarkness.schema.ZCAttachmentSchema;
import com.xdarkness.framework.orm.SchemaSet;

public class ZCAttachmentSet extends SchemaSet {
	public ZCAttachmentSet() {
		this(10,0);
	}

	public ZCAttachmentSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCAttachmentSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCAttachmentSchema._TableCode;
		Columns = ZCAttachmentSchema._Columns;
		NameSpace = ZCAttachmentSchema._NameSpace;
		InsertAllSQL = ZCAttachmentSchema._InsertAllSQL;
		UpdateAllSQL = ZCAttachmentSchema._UpdateAllSQL;
		FillAllSQL = ZCAttachmentSchema._FillAllSQL;
		DeleteSQL = ZCAttachmentSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCAttachmentSet();
	}

	public boolean add(ZCAttachmentSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCAttachmentSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCAttachmentSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCAttachmentSchema get(int index) {
		ZCAttachmentSchema tSchema = (ZCAttachmentSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCAttachmentSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCAttachmentSet aSet) {
		return super.set(aSet);
	}
}
 