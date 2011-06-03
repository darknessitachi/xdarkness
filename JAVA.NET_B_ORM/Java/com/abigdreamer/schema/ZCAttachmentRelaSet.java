package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCAttachmentRelaSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCAttachmentRelaSet extends SchemaSet {
	public ZCAttachmentRelaSet() {
		this(10,0);
	}

	public ZCAttachmentRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCAttachmentRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCAttachmentRelaSchema._TableCode;
		Columns = ZCAttachmentRelaSchema._Columns;
		NameSpace = ZCAttachmentRelaSchema._NameSpace;
		InsertAllSQL = ZCAttachmentRelaSchema._InsertAllSQL;
		UpdateAllSQL = ZCAttachmentRelaSchema._UpdateAllSQL;
		FillAllSQL = ZCAttachmentRelaSchema._FillAllSQL;
		DeleteSQL = ZCAttachmentRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCAttachmentRelaSet();
	}

	public boolean add(ZCAttachmentRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCAttachmentRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCAttachmentRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCAttachmentRelaSchema get(int index) {
		ZCAttachmentRelaSchema tSchema = (ZCAttachmentRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCAttachmentRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCAttachmentRelaSet aSet) {
		return super.set(aSet);
	}
}
 