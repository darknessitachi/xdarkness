package com.abigdreamer.schema;

import com.abigdreamer.schema.ZCForumAttachmentSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class ZCForumAttachmentSet extends SchemaSet {
	public ZCForumAttachmentSet() {
		this(10,0);
	}

	public ZCForumAttachmentSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ZCForumAttachmentSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ZCForumAttachmentSchema._TableCode;
		Columns = ZCForumAttachmentSchema._Columns;
		NameSpace = ZCForumAttachmentSchema._NameSpace;
		InsertAllSQL = ZCForumAttachmentSchema._InsertAllSQL;
		UpdateAllSQL = ZCForumAttachmentSchema._UpdateAllSQL;
		FillAllSQL = ZCForumAttachmentSchema._FillAllSQL;
		DeleteSQL = ZCForumAttachmentSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ZCForumAttachmentSet();
	}

	public boolean add(ZCForumAttachmentSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ZCForumAttachmentSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ZCForumAttachmentSchema aSchema) {
		return super.remove(aSchema);
	}

	public ZCForumAttachmentSchema get(int index) {
		ZCForumAttachmentSchema tSchema = (ZCForumAttachmentSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ZCForumAttachmentSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ZCForumAttachmentSet aSet) {
		return super.set(aSet);
	}
}
 