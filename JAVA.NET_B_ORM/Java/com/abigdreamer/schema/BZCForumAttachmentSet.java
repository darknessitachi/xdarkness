package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCForumAttachmentSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCForumAttachmentSet extends SchemaSet {
	public BZCForumAttachmentSet() {
		this(10,0);
	}

	public BZCForumAttachmentSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCForumAttachmentSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCForumAttachmentSchema._TableCode;
		Columns = BZCForumAttachmentSchema._Columns;
		NameSpace = BZCForumAttachmentSchema._NameSpace;
		InsertAllSQL = BZCForumAttachmentSchema._InsertAllSQL;
		UpdateAllSQL = BZCForumAttachmentSchema._UpdateAllSQL;
		FillAllSQL = BZCForumAttachmentSchema._FillAllSQL;
		DeleteSQL = BZCForumAttachmentSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCForumAttachmentSet();
	}

	public boolean add(BZCForumAttachmentSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCForumAttachmentSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCForumAttachmentSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCForumAttachmentSchema get(int index) {
		BZCForumAttachmentSchema tSchema = (BZCForumAttachmentSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCForumAttachmentSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCForumAttachmentSet aSet) {
		return super.set(aSet);
	}
}
 