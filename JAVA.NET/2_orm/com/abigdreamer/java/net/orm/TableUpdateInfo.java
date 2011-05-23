package com.abigdreamer.java.net.orm;

import com.abigdreamer.java.net.connection.DBTypes;

public abstract class TableUpdateInfo {
	public abstract String[] toSQLArray(DBTypes paramString);
}
