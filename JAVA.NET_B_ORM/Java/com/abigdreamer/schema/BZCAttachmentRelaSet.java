package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCAttachmentRelaSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCAttachmentRelaSet extends SchemaSet {
	public BZCAttachmentRelaSet() {
		this(10,0);
	}

	public BZCAttachmentRelaSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCAttachmentRelaSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCAttachmentRelaSchema._TableCode;
		Columns = BZCAttachmentRelaSchema._Columns;
		NameSpace = BZCAttachmentRelaSchema._NameSpace;
		InsertAllSQL = BZCAttachmentRelaSchema._InsertAllSQL;
		UpdateAllSQL = BZCAttachmentRelaSchema._UpdateAllSQL;
		FillAllSQL = BZCAttachmentRelaSchema._FillAllSQL;
		DeleteSQL = BZCAttachmentRelaSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCAttachmentRelaSet();
	}

	public boolean add(BZCAttachmentRelaSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCAttachmentRelaSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCAttachmentRelaSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCAttachmentRelaSchema get(int index) {
		BZCAttachmentRelaSchema tSchema = (BZCAttachmentRelaSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCAttachmentRelaSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCAttachmentRelaSet aSet) {
		return super.set(aSet);
	}
}
 