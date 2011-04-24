package com.xdarkness.framework.jaf.controls;

import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.util.Mapx;

public abstract class CodeSource extends Ajax {
	public abstract DataTable getCodeData(String paramString, Mapx paramMapx);
}
