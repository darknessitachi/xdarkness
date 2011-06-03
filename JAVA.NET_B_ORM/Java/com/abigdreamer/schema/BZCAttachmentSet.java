package com.abigdreamer.schema;

import com.abigdreamer.schema.BZCAttachmentSchema;
import com.abigdreamer.java.net.orm.SchemaSet;

public class BZCAttachmentSet extends SchemaSet {
	public BZCAttachmentSet() {
		this(10,0);
	}

	public BZCAttachmentSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public BZCAttachmentSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = BZCAttachmentSchema._TableCode;
		Columns = BZCAttachmentSchema._Columns;
		NameSpace = BZCAttachmentSchema._NameSpace;
		InsertAllSQL = BZCAttachmentSchema._InsertAllSQL;
		UpdateAllSQL = BZCAttachmentSchema._UpdateAllSQL;
		FillAllSQL = BZCAttachmentSchema._FillAllSQL;
		DeleteSQL = BZCAttachmentSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new BZCAttachmentSet();
	}

	public boolean add(BZCAttachmentSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(BZCAttachmentSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(BZCAttachmentSchema aSchema) {
		return super.remove(aSchema);
	}

	public BZCAttachmentSchema get(int index) {
		BZCAttachmentSchema tSchema = (BZCAttachmentSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, BZCAttachmentSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(BZCAttachmentSet aSet) {
		return super.set(aSet);
	}
}
 