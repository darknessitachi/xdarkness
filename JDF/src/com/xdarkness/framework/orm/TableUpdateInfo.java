package com.xdarkness.framework.orm;

import com.xdarkness.framework.connection.DBTypes;

public abstract class TableUpdateInfo {
	public abstract String[] toSQLArray(DBTypes paramString);
}
