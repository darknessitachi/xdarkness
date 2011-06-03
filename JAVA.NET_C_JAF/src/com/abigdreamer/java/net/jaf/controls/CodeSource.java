package com.abigdreamer.java.net.jaf.controls;

import com.abigdreamer.java.net.jaf.Ajax;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.util.Mapx;

public abstract class CodeSource extends Ajax {
	public abstract DataTable getCodeData(String paramString, Mapx paramMapx);
}
