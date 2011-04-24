package com.xdarkness.framework.orm;

import java.io.Serializable;

public class SchemaColumn implements Serializable {

	public SchemaColumn(String name, int type, int order, int length,
			int precision, boolean mandatory, boolean ispk) {
		ColumnType = type;
		ColumnName = name;
		ColumnOrder = order;
		Length = length;
		Precision = precision;
		Mandatory = mandatory;
		isPrimaryKey = ispk;
	}

	public String getColumnName() {
		return ColumnName;
	}

	public int getColumnOrder() {
		return ColumnOrder;
	}

	public int getColumnType() {
		return ColumnType;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public int getLength() {
		return Length;
	}

	public int getPrecision() {
		return Precision;
	}

	public boolean isMandatory() {
		return Mandatory;
	}

	private static final long serialVersionUID = 1L;
	private int ColumnType;
	private String ColumnName;
	private int ColumnOrder;
	private int Length;
	private int Precision;
	private boolean Mandatory;
	private boolean isPrimaryKey;
	
	
	
}
